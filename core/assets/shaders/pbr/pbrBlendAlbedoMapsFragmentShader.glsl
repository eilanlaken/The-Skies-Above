#version 330

#define MAX_POINT_LIGHTS 10
#define MAX_DIRECTIONAL_LIGHTS 1
#define PI 3.1415926538
#define BLENDED_MAPS_NUMBER 4

struct PointLight {
    vec3 position;
    vec3 color;
    float intensity;
};

struct DirectionalLight {
    vec3 position;
    vec3 direction;
    vec3 color;
    float intensity;
};

struct BasicMaterialMap {
    sampler2D texture;
    float roughness;
    float metallic;
    float ambient;
};

// inputs
in vec2 uv;
in mat3 invTBN;
in vec3 unit_vertex_to_camera;
in vec3 world_vertex_position;

// uniforms
uniform sampler2D blend_map;
uniform BasicMaterialMap material_maps[BLENDED_MAPS_NUMBER];
uniform PointLight point_lights[MAX_POINT_LIGHTS];
uniform DirectionalLight directional_lights[MAX_DIRECTIONAL_LIGHTS];

uniform float gamma; // <- gamma correction from options

layout (location = 0) out vec4 out_color;
layout (location = 1) out vec4 out_color_emissive;
layout (location = 2) out vec4 out_black;

// functions
vec4 get_emissive_color(vec3, vec3);
float distribution_GGX(vec3 n, vec3 h, float roughness);
float geometry_schlick_GGX(float ndotv, float roughness);
float geometry_smith(vec3 n, vec3 v, vec3 l, float roughness);
vec3 fresnel_schlick(float cos_theta, vec3 f0);
vec4 get_average_albedo(BasicMaterialMap material_maps[BLENDED_MAPS_NUMBER], sampler2D blend_map, vec2 uv);
float get_average_roughness(BasicMaterialMap material_maps[BLENDED_MAPS_NUMBER], sampler2D blend_map, vec2 uv);
float get_average_metallic(BasicMaterialMap material_maps[BLENDED_MAPS_NUMBER], sampler2D blend_map, vec2 uv);
float get_average_ambient(BasicMaterialMap material_maps[BLENDED_MAPS_NUMBER], sampler2D blend_map, vec2 uv);

void main()
{
    // sampling and averaging
    vec3 albedo = get_average_albedo(material_maps, blend_map, uv).rgb;
    float roughness = get_average_roughness(material_maps, blend_map, uv);
    float metallic = get_average_metallic(material_maps, blend_map, uv);
    float ambient = get_average_ambient(material_maps, blend_map, uv);

    // pbr required geometry
    vec3 N = vec3(0,0,1);//normalize(vec3(0.502, 0.502, 0) * 2.0 - 1.0);
    vec3 V = unit_vertex_to_camera;
    vec3 F0 = mix(vec3(0.04), albedo, metallic);

    // summation
    vec3 Lo = vec3(0.0);
    vec3 total_specular = vec3(0,0,0);

    // for point lights
    for (int i = 0; i < MAX_POINT_LIGHTS; i++)
    {
        // calculate per-box2DLight radiance
        vec3 vertex_to_light    = invTBN * (point_lights[i].position - world_vertex_position);
        float distance_to_light = length(vertex_to_light);
        float attenuation       = 1.0 / (1.0 + 0.01 * distance_to_light + 0.001 * distance_to_light * distance_to_light);
        vec3 radiance           = point_lights[i].intensity * point_lights[i].color * attenuation;

        // cook-torrance brdf
        vec3 L                  = normalize(vertex_to_light);
        vec3 H                  = normalize(V + L);
        float NDF = distribution_GGX(N, H, roughness);
        float G   = geometry_smith(N, V, L, roughness);
        vec3  F   = fresnel_schlick(max(dot(H, V), 0.0), F0);

        vec3 numerator    = NDF * G * F;
        float denominator = 4.0 * max(dot(N, V), 0.0) * max(dot(N, L), 0.0) + 0.0001;
        vec3 specular     = numerator / denominator;
        total_specular += specular;

        vec3 kS = F;
        vec3 kD = vec3(1.0) - kS;
        kD *= 1.0 - metallic;
        // add to outgoing radiance Lo
        float NdotL = max(dot(N, L), 0.0);
        Lo += (kD * albedo / PI + specular) * radiance * NdotL;
    }

    // sum directional lights
    for (int i = 0; i < MAX_DIRECTIONAL_LIGHTS; i++) {
        // calculate per-box2DLight radiance
        vec3 vertex_to_light    = invTBN * (directional_lights[i].position - world_vertex_position);
        vec3 radiance           = directional_lights[i].intensity * directional_lights[i].color;

        // cook-torrance brdf
        vec3 L    = normalize(vertex_to_light);
        vec3 H    = normalize(V + L);
        float NDF = distribution_GGX(N, H, roughness);
        float G   = geometry_smith(N, V, L, roughness);
        vec3  F   = fresnel_schlick(max(dot(H, V), 0.0), F0);

        vec3 numerator    = NDF * G * F;
        float denominator = 4.0 * max(dot(N, V), 0.0) * max(dot(N, L), 0.0) + 0.0001;
        vec3 specular     = numerator / denominator;
        total_specular += specular;

        vec3 kS = F;
        vec3 kD = vec3(1.0) - kS;
        kD *= 1.0 - metallic;
        // add to outgoing radiance Lo
        float NdotL = max(dot(N, L), 0.0);
        Lo += (kD * albedo / PI + specular) * radiance * NdotL;
    }


    vec3 color = albedo + Lo; // <- fix
    //color = albedo;
    //color = texture(material_maps[3].texture, uv).rgb;
    //color = texture(blend_map, uv).rgb;
    //color              = pow(color, vec3(1.0 / gamma));
    out_color          = vec4(color, 1.0);
    out_color_emissive = get_emissive_color(total_specular, out_color.rgb);
    out_black          = vec4(0.0, 0.0, 0.0, 1.0);
}

vec4 get_average_albedo(BasicMaterialMap material_maps[BLENDED_MAPS_NUMBER], sampler2D blend_map, vec2 uv)
{
    vec3 blend_color = texture(blend_map, uv).rgb;
    float background_weight = 1 - (blend_color.r + blend_color.g + blend_color.b);
    vec3 background_color =  texture(material_maps[0].texture, uv).rgb * background_weight;
    vec3 color_map_1 = texture(material_maps[1].texture, uv).rgb * blend_color.r;
    vec3 color_map_2 = texture(material_maps[2].texture, uv).rgb * blend_color.g;
    vec3 color_map_3 = texture(material_maps[3].texture, uv).rgb * blend_color.b;

    vec3 total_color = background_color + color_map_1 + color_map_2 + color_map_3;
    return vec4(total_color, 1.0);
}

float get_average_roughness(BasicMaterialMap material_maps[BLENDED_MAPS_NUMBER], sampler2D blend_map, vec2 uv) {
    vec3 blend_color = texture(blend_map, uv).rgb;
    float background_weight = 1 - (blend_color.r + blend_color.g + blend_color.b);
    float r_weight = blend_color.r;
    float g_weight = blend_color.g;
    float b_weight = blend_color.b;
    float total_roughness = material_maps[0].roughness * background_weight +
        material_maps[1].roughness * r_weight +
        material_maps[2].roughness * g_weight +
        material_maps[3].roughness * b_weight;
    return total_roughness;
    //return 1;
}

float get_average_metallic(BasicMaterialMap material_maps[BLENDED_MAPS_NUMBER], sampler2D blend_map, vec2 uv) {
    vec3 blend_color = texture(blend_map, uv).rgb;
    float background_weight = 1 - (blend_color.r + blend_color.g + blend_color.b);
    float r_weight = blend_color.r;
    float g_weight = blend_color.g;
    float b_weight = blend_color.b;
    float total_metallic = material_maps[0].metallic * background_weight +
    material_maps[1].metallic * r_weight +
    material_maps[2].metallic * g_weight +
    material_maps[3].metallic * b_weight;
    return total_metallic;
    //return 0.04;
}

float get_average_ambient(BasicMaterialMap material_maps[BLENDED_MAPS_NUMBER], sampler2D blend_map, vec2 uv) {
    vec3 blend_color = texture(blend_map, uv).rgb;
    float background_weight = 1 - (blend_color.r + blend_color.g + blend_color.b);
    float r_weight = blend_color.r;
    float g_weight = blend_color.g;
    float b_weight = blend_color.b;
    float total_ambient = material_maps[0].ambient * background_weight +
    material_maps[1].ambient * r_weight +
    material_maps[2].ambient * g_weight +
    material_maps[3].ambient * b_weight;
    return total_ambient;
    //return 0.2f;
}


// functions
vec4 get_emissive_color(vec3 total_specular, vec3 color)
{
    float bloom_filter = dot(total_specular, vec3(0.2126, 0.7152, 0.0722));
    if (bloom_filter > 0.5) {
        return vec4(color, 0.87);
    } else {
        return vec4(0,0,0,0);
    }
}

float distribution_GGX(vec3 n, vec3 h, float roughness)
{
    float a      = roughness * roughness;
    float a2     = a * a;
    float ndoth  = max(dot(n, h), 0.0);
    float ndoth2 = ndoth * ndoth;

    float num   = a2;
    float denom = (ndoth2 * (a2 - 1.0) + 1.0);
    denom = PI * denom * denom;

    return num / denom;
}

float geometry_schlick_GGX(float ndotv, float roughness)
{
    float r = (roughness + 1.0);
    float k = (r * r) / 8.0;

    float num   = ndotv;
    float denom = ndotv * (1.0 - k) + k;

    return num / denom;
}

float geometry_smith(vec3 n, vec3 v, vec3 l, float roughness)
{
    float ndotv = max(dot(n, v), 0.0);
    float ndotl = max(dot(n, l), 0.0);
    float ggx2  = geometry_schlick_GGX(ndotv, roughness);
    float ggx1  = geometry_schlick_GGX(ndotl, roughness);

    return ggx1 * ggx2;
}

vec3 fresnel_schlick(float cos_theta, vec3 f0)
{
    return f0 + (1.0 - f0) * pow(clamp(1.0 - cos_theta, 0.0, 1.0), 5.0);
}
