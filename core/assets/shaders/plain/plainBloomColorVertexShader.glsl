#version 330

attribute vec3 a_position;

// uniforms
uniform mat4 body_transform;
uniform mat4 camera_combined; // proj * view

void main() {

    // calculate vertex immediate output
    vec4 vertex_position = body_transform * vec4(a_position, 1.0);
    gl_Position =  camera_combined * vertex_position;

}