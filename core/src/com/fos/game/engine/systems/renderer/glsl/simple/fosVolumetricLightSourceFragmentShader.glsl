#version 330

in float r_out;
in float b_out;
in float g_out;

layout (location = 2) out vec4 out_light;

void main() {

    out_light = vec4(r_out, g_out, b_out, 1.0);
}