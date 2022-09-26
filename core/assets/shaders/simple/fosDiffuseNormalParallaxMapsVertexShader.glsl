#version 330
#define MAX_POINT_LIGHTS 10

attribute vec3 a_position;
attribute vec2 a_texCoord0;
attribute vec3 a_normal;
attribute vec3 a_tangent;
attribute vec3 a_binormal;

// uniforms
uniform mat4 body_transform;
uniform vec3 camera_position;
uniform mat4 camera_view; // cameraTransform = inverse(cameraView)
uniform mat4 camera_projection;
uniform mat4 camera_combined; // proj * view

// outputs
varying vec3 unit_vertex_to_camera;
varying vec2 uv;
varying vec3 world_vertex_position;
varying mat3 invTBN; // invTBN is a matrix that transforms vectors from xyz space to tbn space

void main() {

    // calculate vertex immediate output
    vec4 vertex_position = body_transform * vec4(a_position, 1.0);
    gl_Position = camera_combined * vertex_position;

    // compute TBN matrix
    vec3 T = normalize(a_tangent);
    vec3 B = normalize(a_binormal);
    vec3 N = normalize(a_normal);
    mat3 TBN = mat3(T, B, N);

    // outputs
    invTBN = transpose(TBN); // TBN is orthogonal therefore inverse(TBN) = transpose(TBN)
    unit_vertex_to_camera = normalize(invTBN * (camera_position - vertex_position.xyz));
    world_vertex_position = vertex_position.xyz;
    uv = a_texCoord0;
}
