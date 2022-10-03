#version 330

in vec2 uv;

uniform sampler2D u_texture;
uniform float resolution;
uniform float radius;
uniform vec2 dir;

void main() {
    vec4 sum = vec4(0.0);
    vec2 tc = uv;

    // Number of pixels off the central pixel to sample from
    float blur = radius / resolution;

    // Blur direction
    float ustep = dir.x;
    float vstep = dir.y;

    // Apply blur using 9 samples and predefined gaussian weights
    sum += texture(u_texture, vec2(tc.x - 4.0 * blur * ustep, tc.y - 4.0 * blur * vstep)) * 0.006;
    sum += texture(u_texture, vec2(tc.x - 3.0 * blur * ustep, tc.y - 3.0 * blur* vstep)) * 0.044;
    sum += texture(u_texture, vec2(tc.x - 2.0 * blur * ustep, tc.y - 2.0 * blur * vstep)) * 0.121;
    sum += texture(u_texture, vec2(tc.x - 1.0 * blur * ustep, tc.y - 1.0 * blur * vstep)) * 0.194;

    vec4 center_color = texture(u_texture, vec2(tc.x, tc.y));
    sum += center_color * 0.27;

    sum += texture(u_texture, vec2(tc.x + 1.0 * blur * ustep, tc.y + 1.0 * blur * vstep)) * 0.194;
    sum += texture(u_texture, vec2(tc.x + 2.0 * blur * ustep, tc.y + 2.0 * blur * vstep)) * 0.121;
    sum += texture(u_texture, vec2(tc.x + 3.0 * blur * ustep, tc.y + 3.0 * blur * vstep)) * 0.044;
    sum += texture(u_texture, vec2(tc.x + 4.0 * blur * ustep, tc.y + 4.0 * blur * vstep)) * 0.006;

    sum = sum * 2;

    gl_FragColor = vec4(sum.xyz * 1.05, sum.a * 0.8);
}