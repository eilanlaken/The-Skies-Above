#version 330

in vec3 total_diffuse;
in vec3 total_specular;

uniform float r;
uniform float g;
uniform float b;

layout (location = 0) out vec4 out_color;
layout (location = 1) out vec4 out_bloom;

// functions
vec4 get_emissive_color(vec3);

void main() {

    out_color = vec4(total_diffuse, 1.0) * vec4(r, g, b, 1.0) + vec4(total_specular, 1.0);
    out_bloom = get_emissive_color(total_specular);

}

vec4 get_emissive_color(vec3 total_specular)
{
    float bloom_filter = dot(total_specular, vec3(0.2126, 0.7152, 0.0722));
    if (bloom_filter > 0.6) {
        return vec4(total_specular, 0.6);
    } else {
        return vec4(0,0,0,0);
    }
}