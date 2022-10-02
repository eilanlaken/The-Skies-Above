#version 330

attribute vec3 a_position;
attribute vec2 a_texCoord0;
attribute vec3 a_normal;

// uniforms
uniform mat4 body_transform;
uniform vec3 camera_position;
uniform mat4 camera_view; // cameraTransform = inverse(cameraView)
uniform mat4 camera_projection;
uniform mat4 camera_combined; // proj * view

// outputs
varying vec3 unit_vertex_to_camera;
varying vec3 unit_world_normal;
varying vec3 world_vertex_position;

void main() {
    // calculate vertex immediate output
    vec4 vertex_position = body_transform * vec4(a_position, 1.0);
    gl_Position = camera_combined * vertex_position;

    // outputs
    unit_vertex_to_camera = normalize(camera_position - vertex_position.xyz);
    unit_world_normal = normalize((body_transform * vec4(a_normal, 1.0)).xyz);
    world_vertex_position = vertex_position.xyz;
}
