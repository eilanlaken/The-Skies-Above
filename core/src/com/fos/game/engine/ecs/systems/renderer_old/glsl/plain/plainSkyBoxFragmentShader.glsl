#version 330

in vec2 uv;

uniform sampler2D image;

out vec4 out_Color;

void main() {

    out_Color = texture(image, uv);

}