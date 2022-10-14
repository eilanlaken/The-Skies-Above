#version 330
#define MAX_POINT_LIGHTS 10
#define AMBIENT 0.6

// inputs
varying vec2 uv;
varying mat3 invTBN;
varying vec3 unit_vertex_to_camera;
varying vec3 world_vertex_position;

// uniforms
struct PointLight {
    vec3 position;
    vec3 color;
    float intensity;
};
struct RemappingParams {
    float x;
    float y;
};
uniform PointLight point_lights[MAX_POINT_LIGHTS];
uniform sampler2D image;
uniform float ambient; // <- replace with sampling from environment cube map.
uniform float gamma; // <- gamma correction from options
// remapping params
uniform float atlas_width_inv;
uniform float atlas_height_inv;
uniform RemappingParams diffuse_map;
uniform RemappingParams normal_map;
uniform RemappingParams specular_map;
uniform RemappingParams roughness_map;
uniform float width;
uniform float height;

//out vec4 out_Color;
layout (location = 0) out vec4 out_color;
layout (location = 1) out vec4 out_color_emissive;
layout (location = 2) out vec4 out_black;

// functions
vec4 get_emissive_color(vec3);

void main() {
    vec2 uv_diffuse = vec2((diffuse_map.x + uv.x * width) * atlas_width_inv, (diffuse_map.y + uv.y * height) * atlas_height_inv);
    vec2 uv_normal = vec2((normal_map.x + uv.x * width) * atlas_width_inv, (normal_map.y + uv.y * height) * atlas_height_inv);
    vec2 uv_specular = vec2((specular_map.x + uv.x * width) * atlas_width_inv, (specular_map.y + uv.y * height) * atlas_height_inv);
    vec2 uv_roughness = vec2((roughness_map.x + uv.x * width) * atlas_width_inv, (roughness_map.y + uv.y * height) * atlas_height_inv);

    vec4 normal_map_value = texture(image, uv_normal);
    vec3 unit_normal = normalize(normal_map_value.xyz * 2.0 - 1.0);

    vec3 total_diffuse = vec3(0,0,0);
    vec3 total_specular = vec3(0,0,0);

    for (int i = 0; i < MAX_POINT_LIGHTS; i++) {
        vec3 vertex_to_light = invTBN * (point_lights[i].position - world_vertex_position);
        vec3 unit_vertex_to_light = normalize(vertex_to_light);

        float distance_to_light = length(vertex_to_light);
        float attenuation_factor = 1.0 + 0.01 * distance_to_light + 0.001 * distance_to_light * distance_to_light;
        float nDotl = dot(unit_normal, unit_vertex_to_light);
        float brightness = max(0.0, nDotl);
        total_diffuse = total_diffuse + (point_lights[i].intensity * brightness * point_lights[i].color) / attenuation_factor;

        vec3 light_direction = -unit_vertex_to_light;
        vec3 reflected_light_direction = reflect(light_direction, unit_normal);
        float specular_factor = dot(reflected_light_direction, unit_vertex_to_camera);
        specular_factor = max(specular_factor, 0.0);

        float reflectivity = texture(image, uv_specular).x;
        float roughness = texture(image, uv_roughness).x;
        float damped_factor = pow(specular_factor, roughness);
        total_specular = total_specular + (point_lights[i].intensity * damped_factor * reflectivity * point_lights[i].color) / attenuation_factor;
    }
    //total_diffuse = max(total_diffuse, ambient);
    total_diffuse = max(total_diffuse, AMBIENT);

    out_color = vec4(total_diffuse, 1.0) * texture(image, uv_diffuse) + vec4(total_specular, 1.0);
    out_color = pow(out_color.rgba, vec4(1.0 / gamma));
    out_color_emissive = get_emissive_color(total_specular);
    out_black = vec4(0.0, 0.0, 0.0, 1.0);
}

vec4 get_emissive_color(vec3 total_specular)
{
    float bloom_filter = dot(total_specular, vec3(0.2126, 0.7152, 0.0722));
    if (bloom_filter > 0.5) {
        return vec4(total_specular, 0.8);
    } else {
        return vec4(0,0,0,0);
    }
}