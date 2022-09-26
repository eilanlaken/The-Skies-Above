#version 330
#define SAMPLES 5

in vec2 uv;

// post processing effects
uniform bool blur;
uniform bool tint;
uniform bool negative;

uniform sampler2D u_texture;
uniform float screen_width;
uniform float screen_height;
uniform float radius;

const int samples = 13;

void main() {

    vec4 sum = vec4(0.0);

    // Number of pixels off the central pixel to sample from
    float horizontal_blur = radius / screen_width;
    float vertical_blur = radius / screen_height;

    for (int i = 0; i < samples; i++) {
        float horizontal_offset = -1 * float(samples) / 2 + i;
        for (int j = 0; j < samples; j++) {
            float vertical_offset = -1 * float(samples) / 2 + j;
            sum += texture(u_texture, vec2(uv.x + horizontal_offset * horizontal_blur, uv.y + vertical_offset * vertical_blur)) * 0.022798418304162224;
        }
    }

    gl_FragColor = vec4(sum.xyz * 1.05, sum.a * 0.4);
}

