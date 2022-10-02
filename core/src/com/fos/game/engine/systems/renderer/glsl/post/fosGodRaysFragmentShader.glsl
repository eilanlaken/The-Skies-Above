#version 330 core

in vec2 uv;

uniform vec3 light_position;
uniform mat4 camera_combined;
uniform sampler2D u_texture;

const float exposure = 0.3f;
const float decay = 0.95815 ;
const float density  = 0.91;
const float weight  = 0.587;
const int NUM_SAMPLES = 233;

void main() {
    vec2 light_screen_position = (camera_combined * vec4(light_position.x, light_position.y, light_position.z, 0)).xy + vec2(0.5,0.5); // <- adjust to opengl
    vec2 delta_uv = vec2(uv.xy - light_screen_position.xy);
    vec2 uv_temp = uv;

    delta_uv *= 1.0f / NUM_SAMPLES * density;

    float illumination_decay = 1.0f;
    vec3 color = texture(u_texture, uv).rgb;

    for (int i = 0; i < NUM_SAMPLES; i++) {
        uv_temp -= delta_uv;
        vec3 sampled_color = texture(u_texture, uv_temp).rgb;
        sampled_color *= illumination_decay * weight;
        color += sampled_color;
        illumination_decay *= decay;
    }

    gl_FragColor = vec4(color * exposure, 1.0);
}