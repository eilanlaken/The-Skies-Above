#version 330
#define MAX_POINT_LIGHTS 10
#define MAX_DIRECTIONAL_LIGHTS 1
#define MAX_SPOT_LIGHTS 1
#define AMBIENT 0.1

struct PointLight {
    vec3 position;
    vec3 color;
    float intensity;
};

struct DirectionalLight {
    vec3 direction;
    vec3 color;
    float intensity;
};

struct SpotLight {
    vec3 position;
    vec3 direction;
    vec3 color;
    float angle;
    float intensity;
};

// inputs
varying vec3 unit_vertex_to_camera;
varying vec3 unit_world_normal;
varying vec3 world_vertex_position;

// uniforms
uniform PointLight point_lights[MAX_POINT_LIGHTS];
uniform DirectionalLight directional_lights[MAX_DIRECTIONAL_LIGHTS];
uniform SpotLight spot_lights[MAX_SPOT_LIGHTS];
uniform float r;
uniform float g;
uniform float b;
uniform float shine_damper;
uniform float reflectivity;
uniform float ambient;

// outputs
layout (location = 0) out vec4 out_color;
layout (location = 1) out vec4 out_bloom;
layout (location = 2) out vec4 out_black;

// functions
vec4 get_emissive_color(vec3);

void main() {

    vec3 total_diffuse = vec3(0,0,0);
    vec3 total_specular = vec3(0,0,0);

    // sum point lights
    for (int i = 0; i < MAX_POINT_LIGHTS; i++) {
        vec3 vertex_to_point_light = point_lights[i].position - world_vertex_position;
        float distance_to_light = length(vertex_to_point_light);
        float attenuation_factor = 1.0 + 0.01 * distance_to_light + 0.001 * distance_to_light * distance_to_light;

        vec3 unit_vertex_to_light = normalize(vertex_to_point_light);
        float nDotl = dot(unit_world_normal, unit_vertex_to_light);
        float brightness = max(0.0, nDotl);
        total_diffuse = total_diffuse + (point_lights[i].intensity * brightness * point_lights[i].color) / attenuation_factor;

        vec3 light_direction = -unit_vertex_to_light;
        vec3 reflected_light_direction = reflect(light_direction, unit_world_normal);
        float specular_factor = dot(reflected_light_direction, unit_vertex_to_camera);
        specular_factor = max(specular_factor, 0.0);
        float damped_factor = pow(specular_factor, shine_damper);
        total_specular = total_specular + (point_lights[i].intensity * damped_factor * reflectivity * point_lights[i].color) / attenuation_factor;
    }

    // sum directional lights
    for (int i = 0; i < MAX_DIRECTIONAL_LIGHTS; i++) {
        float nDotl = dot(unit_world_normal, directional_lights[i].direction);
        float brightness = max(0.0, nDotl);
        total_diffuse = total_diffuse + (directional_lights[i].intensity * brightness * directional_lights[i].color);

        vec3 light_direction = directional_lights[i].direction;
        vec3 reflected_light_direction = reflect(light_direction, unit_world_normal);
        float specular_factor = dot(reflected_light_direction, unit_vertex_to_camera);
        specular_factor = max(specular_factor, 0.0);
        float damped_factor = pow(specular_factor, shine_damper);
        total_specular = total_specular + (directional_lights[i].intensity * damped_factor * reflectivity * directional_lights[i].color);
    }

    // sum spot lights
    for (int i = 0; i < MAX_SPOT_LIGHTS; i++) {
        vec3 vertex_to_spot_light = spot_lights[i].position - world_vertex_position;
        float theta = dot(vertex_to_spot_light, -spot_lights[i].direction);
        if (theta > spot_lights[i].angle) {
            float distance_to_light = length(vertex_to_spot_light);
            float attenuation_factor = 1.0 + 0.01 * distance_to_light + 0.001 * distance_to_light * distance_to_light;

            vec3 unit_vertex_to_light = normalize(vertex_to_spot_light);
            float nDotl = dot(unit_world_normal, unit_vertex_to_light);
            float brightness = max(0.0, nDotl);
            total_diffuse = total_diffuse + (spot_lights[i].intensity * brightness * spot_lights[i].color) / attenuation_factor;

            vec3 light_direction = -unit_vertex_to_light;
            vec3 reflected_light_direction = reflect(light_direction, unit_world_normal);
            float specular_factor = dot(reflected_light_direction, unit_vertex_to_camera);
            specular_factor = max(specular_factor, 0.0);
            float damped_factor = pow(specular_factor, shine_damper);
            total_specular = total_specular + (spot_lights[i].intensity * damped_factor * reflectivity * spot_lights[i].color) / attenuation_factor;
        }
    }

    // apply ambient lighting
    out_color = vec4(total_diffuse, 1.0) * vec4(r, g, b, 1.0) * 0.1 + vec4(r, g, b, 1.0) + vec4(total_specular * 1, 1.0);
    out_bloom = get_emissive_color(total_specular);
    out_black = vec4(0.0, 0.0, 0.0, 1.0);
}

vec4 get_emissive_color(vec3 total_specular)
{
    float bloom_filter = dot(total_specular, vec3(0.2126, 0.7152, 0.0722));
    if (bloom_filter > 0.6) {
        return vec4(total_specular, 0.6);
    } else {
        return vec4(0,0,0,0);
    }
}