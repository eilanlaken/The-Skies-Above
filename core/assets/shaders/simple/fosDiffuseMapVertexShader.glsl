#version 330
#define MAX_POINT_LIGHTS 10

attribute vec3 a_position;
attribute vec2 a_texCoord0;
attribute vec3 a_normal;

// uniforms
uniform mat4 body_transform;
uniform vec3 camera_position;
uniform mat4 camera_combined; // proj * view

// outputs
varying vec3 unit_vertex_to_camera;
varying vec3 unit_world_normal;
varying vec3 world_vertex_position;
varying vec2 uv;

void main() {
    // calculate vertex immediate output
    vec4 vertex_position = body_transform * vec4(a_position, 1.0);
    gl_Position = camera_combined * vertex_position;

    world_vertex_position = vertex_position.xyz;
    unit_vertex_to_camera = normalize(camera_position - vertex_position.xyz);
    unit_world_normal = normalize((body_transform * vec4(a_normal, 1.0)).xyz);
    uv = a_texCoord0;
}
