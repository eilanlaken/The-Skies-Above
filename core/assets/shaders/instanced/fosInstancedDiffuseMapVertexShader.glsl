#version 330

attribute vec3 a_position;
attribute vec2 a_texCoord0;
attribute vec3 a_normal;
attribute vec3 i_transform_col_0, i_transform_col_1, i_transform_col_2, i_transform_col_3; // per instance: the columns of the transform matrix

// uniforms
uniform vec3 camera_position;
uniform mat4 camera_combined; // proj * view

// outputs
varying vec3 unit_vertex_to_camera;
varying vec3 unit_world_normal;
varying vec3 world_vertex_position;
varying vec2 uv;

void main() {
    // calculate vertex immediate output
    vec4 i_transform_col_0_fix = vec4(i_transform_col_0, 0.0);
    vec4 i_transform_col_1_fix = vec4(i_transform_col_1, 0.0);
    vec4 i_transform_col_2_fix = vec4(i_transform_col_2, 0.0);
    vec4 i_transform_col_3_fix = vec4(i_transform_col_3, 1.0);
    mat4 i_transform = mat4(i_transform_col_0_fix, i_transform_col_1_fix, i_transform_col_2_fix, i_transform_col_3_fix);
    vec4 vertex_position = i_transform * vec4(a_position, 1.0);
    gl_Position = camera_combined * vertex_position;

    world_vertex_position = vertex_position.xyz;
    unit_vertex_to_camera = normalize(camera_position - vertex_position.xyz);
    unit_world_normal = normalize((i_transform * vec4(a_normal, 1.0)).xyz);
    uv = a_texCoord0;
}
