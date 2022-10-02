#version 330

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

// material params
uniform float r;
uniform float g;
uniform float b;

out float r_out;
out float b_out;
out float g_out;

void main() {
    // calculate vertex immediate output
    vec4 vertex_position = body_transform * vec4(a_position, 1.0);
    gl_Position = camera_combined * vertex_position;

    // output
    r_out = r;
    b_out = b;
    g_out = g;
}
