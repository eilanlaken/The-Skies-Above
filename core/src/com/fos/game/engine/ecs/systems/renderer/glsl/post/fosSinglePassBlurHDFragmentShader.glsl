#version 330
#define SAMPLES 5

in vec2 uv;

uniform sampler2D u_texture;
uniform float screen_width;
uniform float screen_height;
uniform float radius;

/*
const float gaussian[169] = float[169]
    (
        0.005925448770092601,0.022798418304162224,0.06864392844199704,0.16175522802031042,0.2983460404653773,0.43075007410468985,0.48684489398955805,0.43075007410468985,0.2983460404653773,0.16175522802031042,0.06864392844199704,0.022798418304162224,0.005925448770092601,0.022798418304162224,0.087717892321502,0.264110459035691,0.6223601780012603,1.14789918769543,1.6573293863502807,1.8731566119414789,1.6573293863502807,1.14789918769543,0.6223601780012603,0.264110459035691,0.087717892321502,0.022798418304162224,0.06864392844199704,0.264110459035691,0.7952121594119149,1.8738689216903963,3.4562182624926265,4.990065463474622,5.639901273646251,4.990065463474622,3.4562182624926265,1.8738689216903963,0.7952121594119149,0.264110459035691,0.06864392844199704,0.16175522802031042,0.6223601780012603,1.8738689216903963,4.415657751352684,8.144367401843168,11.758784719942968,13.290083146997516,11.758784719942968,8.144367401843168,4.415657751352684,1.8738689216903963,0.6223601780012603,0.16175522802031042,0.2983460404653773,1.14789918769543,3.4562182624926265,8.144367401843168,15.021707775220127,21.688244051309844,24.512615344120366,21.688244051309844,15.021707775220127,8.144367401843168,3.4562182624926265,1.14789918769543,0.2983460404653773,0.43075007410468985,1.6573293863502807,4.990065463474622,11.758784719942968,21.688244051309844,31.31334579714815,35.39115471252564,31.31334579714815,21.688244051309844,11.758784719942968,4.990065463474622,1.6573293863502807,0.43075007410468985,0.48684489398955805,1.8731566119414789,5.639901273646251,13.290083146997516,24.512615344120366,35.39115471252564,40,35.39115471252564,24.512615344120366,13.290083146997516,5.639901273646251,1.8731566119414789,0.48684489398955805,0.43075007410468985,1.6573293863502807,4.990065463474622,11.758784719942968,21.688244051309844,31.31334579714815,35.39115471252564,31.31334579714815,21.688244051309844,11.758784719942968,4.990065463474622,1.6573293863502807,0.43075007410468985,0.2983460404653773,1.14789918769543,3.4562182624926265,8.144367401843168,15.021707775220127,21.688244051309844,24.512615344120366,21.688244051309844,15.021707775220127,8.144367401843168,3.4562182624926265,1.14789918769543,0.2983460404653773,0.16175522802031042,0.6223601780012603,1.8738689216903963,4.415657751352684,8.144367401843168,11.758784719942968,13.290083146997516,11.758784719942968,8.144367401843168,4.415657751352684,1.8738689216903963,0.6223601780012603,0.16175522802031042,0.06864392844199704,0.264110459035691,0.7952121594119149,1.8738689216903963,3.4562182624926265,4.990065463474622,5.639901273646251,4.990065463474622,3.4562182624926265,1.8738689216903963,0.7952121594119149,0.264110459035691,0.06864392844199704,0.022798418304162224,0.087717892321502,0.264110459035691,0.6223601780012603,1.14789918769543,1.6573293863502807,1.8731566119414789,1.6573293863502807,1.14789918769543,0.6223601780012603,0.264110459035691,0.087717892321502,0.022798418304162224,0.005925448770092601,0.022798418304162224,0.06864392844199704,0.16175522802031042,0.2983460404653773,0.43075007410468985,0.48684489398955805,0.43075007410468985,0.2983460404653773,0.16175522802031042,0.06864392844199704,0.022798418304162224,0.005925448770092601
    )
;
*/
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
