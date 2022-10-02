#version 330
#define SAMPLES 5

in vec2 uv;

uniform sampler2D u_texture;
uniform float screen_width;
uniform float screen_height;
uniform float radius;

const float gaussian[25] = float[25]
    (
        0.003765,	0.015019,	0.023792,	0.015019,	0.003765,
        0.015019,	0.059912,	0.094907,	0.059912,	0.015019,
        0.023792,	0.094907,	0.250342,	0.094907,	0.023792,
        0.015019,	0.059912,	0.094907,	0.059912,	0.015019,
        0.003765,	0.015019,	0.023792,	0.015019,	0.003765
    )
;
const int samples = 5;

void main() {
    vec4 sum = vec4(0.0);

    // Number of pixels off the central pixel to sample from
    float horizontal_blur = radius / screen_width;
    float vertical_blur = horizontal_blur; //radius / screen_height;

    for (int i = 0; i < samples; i++) {
        float horizontal_offset = -1 * float(samples) / 2 + i;
        for (int j = 0; j < samples; j++) {
            float vertical_offset = -1 * float(samples) / 2 + j;
            sum += texture(u_texture, vec2(uv.x + horizontal_offset * horizontal_blur, uv.y + vertical_offset * vertical_blur)) * 6 * gaussian[i * j];
        }
    }

    gl_FragColor = vec4(sum.xyz * 1.0, sum.a * 0.3);
    //gl_FragColor = sum;
}

