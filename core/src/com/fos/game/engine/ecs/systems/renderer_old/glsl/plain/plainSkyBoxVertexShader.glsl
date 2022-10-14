#version 330

attribute vec3 a_position;
attribute vec2 a_texCoord0;

// transforms amd camera
uniform mat4 skybox_transform;
uniform mat4 camera_combined; // <- the SkyBox specific camera

uniform float atlas_width_inv;
uniform float atlas_height_inv;
struct RemappingParams {
    float x;
    float y;
    float width;
    float height;
};
uniform RemappingParams diffuse_map;

out vec2 uv;

void main() {
    // calculate vertex immediate output
    gl_Position = camera_combined * skybox_transform * vec4(a_position, 1.0);

    // output
    uv = vec2((diffuse_map.x + a_texCoord0.x * diffuse_map.width) * atlas_width_inv, (diffuse_map.y + a_texCoord0.y * diffuse_map.height) * atlas_height_inv);
}
