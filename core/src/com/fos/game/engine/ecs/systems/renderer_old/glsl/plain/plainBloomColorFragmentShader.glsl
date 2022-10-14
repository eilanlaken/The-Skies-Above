#version 330

uniform float r;
uniform float g;
uniform float b;
uniform float intensity;

// outputs
layout (location = 0) out vec4 out_color;
layout (location = 1) out vec4 out_bloom;
layout (location = 2) out vec4 out_black;

void main() {
    out_color = vec4(r, g, b, 1.0);
    out_bloom = vec4(r, g, b, intensity);
    out_black = vec4(0.0, 0.0, 0.0, 1.0);
}