#version 330
#define MAX_POINT_LIGHTS 10

attribute vec3 a_position;
attribute vec2 a_texCoord0;
attribute vec3 a_normal;

// renderable
uniform mat4 body_transform;
// camera transforms
uniform vec3 camera_position;
uniform mat4 camera_view; // cameraTransform = inverse(cameraView)
uniform mat4 camera_projection;
uniform mat4 camera_combined; // proj * view

// lighting
struct PointLight {
    vec3 position;
    vec3 color;
    float intensity;
};
uniform PointLight point_lights[MAX_POINT_LIGHTS];

// material params
uniform float shine_damper;
uniform float reflectivity;
uniform float ambient;

// output
out vec3 total_diffuse;
out vec3 total_specular;

void main() {
    // calculate vertex immediate output
    vec4 vertex_position = body_transform * vec4(a_position, 1.0);
    gl_Position = camera_combined * vertex_position;

    // Gouraud shading
    vec3 unit_vertex_to_camera = normalize(camera_position - vertex_position.xyz);
    vec4 temp = body_transform * vec4(a_normal, 1.0);
    vec3 unit_world_normal = normalize(temp.xyz);

    vec3 diffuse = vec3(0.0, 0.0, 0.0);
    vec3 specular = vec3(0.0, 0.0, 0.0);
    for (int i = 0; i < MAX_POINT_LIGHTS; i++) {
        vec3 vertex_to_light = point_lights[i].position - vertex_position.xyz;
        float distance_to_light = length(vertex_to_light);
        float attenuation_factor = 1.0 + 0.01 * distance_to_light + 0.001 * distance_to_light * distance_to_light;

        vec3 unit_vertex_to_light = normalize(vertex_to_light);
        float nDotl = dot(unit_world_normal, unit_vertex_to_light);
        float brightness = max(0.0, nDotl);
        diffuse = diffuse + (point_lights[i].intensity * brightness * point_lights[i].color) / attenuation_factor;

        vec3 light_direction = -unit_vertex_to_light;
        vec3 reflected_light_direction = reflect(light_direction, unit_world_normal);
        float specular_factor = dot(reflected_light_direction, unit_vertex_to_camera);
        specular_factor = max(specular_factor, 0.0);
        float damped_factor = pow(specular_factor, shine_damper);
        specular = specular + (damped_factor * point_lights[i].intensity * reflectivity * point_lights[i].color) / attenuation_factor;
    }

    // output
    total_diffuse = max(diffuse, ambient);
    total_specular = specular * 0.5; // multiply by a factor to further reduce the specular component
}
