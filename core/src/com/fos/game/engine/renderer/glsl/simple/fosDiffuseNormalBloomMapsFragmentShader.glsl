#version 330
#define MAX_POINT_LIGHTS 10
#define AMBIENT 0.3

// lighting
struct PointLight {
    vec3 position;
    vec3 color;
    float intensity;
};

struct RemappingParams {
    float x;
    float y;
};

// inputs
varying vec2 uv;
varying vec3 vertex_to_camera;
varying vec3 world_vertex_position;
// uniforms
uniform PointLight point_lights[MAX_POINT_LIGHTS];
uniform float atlas_width_inv;
uniform float atlas_height_inv;
uniform RemappingParams diffuse_map;
uniform RemappingParams normal_map;
uniform RemappingParams bloom_map;
uniform float width;
uniform float height;
uniform sampler2D image;
uniform float shine_damper;
uniform float reflectivity;
uniform float ambient;
uniform float gamma; // <- gamma correction from options

layout (location = 0) out vec4 out_color;
layout (location = 1) out vec4 out_color_emissive;
layout (location = 2) out vec4 out_black;

void main() {
    vec2 uv_diffuse = vec2((diffuse_map.x + uv.x * width) * atlas_width_inv, (diffuse_map.y + uv.y * height) * atlas_height_inv);
    vec2 uv_normal = vec2((normal_map.x + uv.x * width) * atlas_width_inv, (normal_map.y + uv.y * height) * atlas_height_inv);
    vec2 uv_bloom = vec2((bloom_map.x + uv.x * width) * atlas_width_inv, (bloom_map.y + uv.y * height) * atlas_height_inv);

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
        float damped_factor = pow(specular_factor, shine_damper);
        total_specular = total_specular + (point_lights[i].intensity * damped_factor * reflectivity * point_lights[i].color) / attenuation_factor;
    }
    //total_diffuse = max(total_diffuse, ambient);
    total_diffuse = max(total_diffuse, AMBIENT);

    out_color = vec4(total_diffuse, 1.0) * texture(image, uv_diffuse) + vec4(total_specular, 1.0);
    out_color = pow(out_color.rgba, vec4(1.0 / gamma));
    out_color_emissive = texture(image, uv_bloom);
    out_black = vec4(0.0, 0.0, 0.0, 1.0);
}