#version 330
#define MAX_POINT_LIGHTS 10
#define MAX_BONES 28

attribute vec3 a_position;
attribute vec2 a_texCoord0;
attribute vec3 a_normal;
attribute vec2 a_boneWeight0; // (index, weight)
attribute vec2 a_boneWeight1;
attribute vec2 a_boneWeight2;

// uniforms
uniform mat4 bones_transforms[MAX_BONES];
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
    vec4 total_local_vertex_position = a_boneWeight0.y * bones_transforms[int(a_boneWeight0.x)] * vec4(a_position, 1.0) +
    a_boneWeight1.y * bones_transforms[int(a_boneWeight1.x)] * vec4(a_position, 1.0) +
    a_boneWeight2.y * bones_transforms[int(a_boneWeight2.x)] * vec4(a_position, 1.0);

    vec4 total_local_vertex_normal = a_boneWeight0.y * bones_transforms[int(a_boneWeight0.x)] * vec4(a_normal, 1.0) +
    a_boneWeight1.y * bones_transforms[int(a_boneWeight1.x)] * vec4(a_normal, 1.0) +
    a_boneWeight2.y * bones_transforms[int(a_boneWeight2.x)] * vec4(a_normal, 1.0);

    vec4 vertex_position = body_transform * total_local_vertex_position;
    gl_Position = camera_combined * vertex_position;

    // outputs
    unit_vertex_to_camera = normalize(camera_position - vertex_position.xyz);
    unit_world_normal = normalize((body_transform * total_local_vertex_normal).xyz);
    world_vertex_position = vertex_position.xyz;
}