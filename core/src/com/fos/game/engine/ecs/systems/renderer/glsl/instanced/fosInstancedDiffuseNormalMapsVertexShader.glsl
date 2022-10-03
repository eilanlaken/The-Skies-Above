#version 330

attribute vec3 a_position;
attribute vec2 a_texCoord0;
attribute vec3 a_normal;
attribute vec3 a_tangent;
attribute vec3 a_binormal;
attribute vec3 i_transform_col_0, i_transform_col_1, i_transform_col_2, i_transform_col_3; // per instance: the columns of the transform matrix

// uniforms
uniform vec3 camera_position;
uniform mat4 camera_view; // cameraTransform = inverse(cameraView)
uniform mat4 camera_projection;
uniform mat4 camera_combined; // proj * view

// outputs
varying vec3 world_vertex_position;
varying vec3 unit_vertex_to_camera;
varying vec2 uv;
varying mat3 invTBN; // invTBN is a matrix that transforms vectors from xyz space to tbn space

void main() {
    // calculate vertex immediate output
    vec4 i_transform_col_0_fix = vec4(i_transform_col_0, 0.0);
    vec4 i_transform_col_1_fix = vec4(i_transform_col_1, 0.0);
    vec4 i_transform_col_2_fix = vec4(i_transform_col_2, 0.0);
    vec4 i_transform_col_3_fix = vec4(i_transform_col_3, 1.0);
    mat4 i_transform = mat4(i_transform_col_0_fix, i_transform_col_1_fix, i_transform_col_2_fix, i_transform_col_3_fix);
    vec4 vertex_position = i_transform * vec4(a_position, 1.0);
    gl_Position = camera_combined * vertex_position;

    // compute TBN matrix
    vec3 T = normalize(vec3(i_transform * vec4(a_tangent,   0.0)));
    vec3 B = normalize(vec3(i_transform * vec4(a_binormal, 0.0)));
    vec3 N = normalize(vec3(i_transform * vec4(a_normal,    0.0)));
    mat3 TBN = mat3(T, B, N);

    invTBN = transpose(TBN); // TBN is orthogonal therefore inverse(TBN) = transpose(TBN)
    unit_vertex_to_camera = normalize(invTBN * (camera_position - vertex_position.xyz));
    world_vertex_position = vertex_position.xyz;
    uv = a_texCoord0;
}
