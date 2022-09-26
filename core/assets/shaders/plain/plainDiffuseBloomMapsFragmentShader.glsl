#version 330

struct RemappingParams {
    float x;
    float y;
};

// inputs
in vec2 uv;

// uniforms
uniform sampler2D image;
uniform float atlas_width_inv;
uniform float atlas_height_inv;
uniform float width;
uniform float height;
uniform RemappingParams diffuse_map;
uniform RemappingParams bloom_map;
uniform float intensity;

// outputs
layout (location = 0) out vec4 out_color;
layout (location = 1) out vec4 out_bloom;
layout (location = 2) out vec4 out_black;

void main() {

    vec2 uv_diffuse = vec2((diffuse_map.x + uv.x * width) * atlas_width_inv, (diffuse_map.y + uv.y * height) * atlas_height_inv);
    vec2 uv_bloom = vec2((bloom_map.x + uv.x * width) * atlas_width_inv, (bloom_map.y + uv.y * height) * atlas_height_inv);

    out_color = texture(image, uv_diffuse);
    out_bloom = vec4(texture(image, uv_bloom).rgb, intensity);
    out_black = vec4(0.0, 0.0, 0.0, 1.0);

}