#version 330

in vec4 final_color;

layout (location = 0) out vec4 out_color;
layout (location = 1) out vec4 out_bloom;

void main() {

    out_color = final_color;
    out_bloom = vec4(0.0, 0.0, 0.0, 0.0);

}