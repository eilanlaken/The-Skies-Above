#version 330
#define MAX_POINT_LIGHTS 10

attribute vec3 a_position;
attribute vec2 a_texCoord0;
attribute vec3 a_normal;

// renderable
uniform mat4 body_transform;

// camera transforms
uniform vec3 camera_position;
uniform mat4 camera_view; // cameraTransform = inverse(cameraView)
uniform mat4 camera_projection;
uniform mat4 camera_combined; // proj * view

// remapping params
uniform float atlas_width_inv;
uniform float atlas_height_inv;
struct RemappingParams {
    float x;
    float y;
};
uniform RemappingParams diffuse_map;
uniform float width;
uniform float height;

// furry params
uniform  float fur_flow_offset;
uniform  float current_layer;
uniform  float layers;
uniform	 float fur_length;

vec4 g = vec4(0.0f, -2.0f, 0.0f, 1.0f);

out vec2 uv;

// TODO:
// https://github.com/McNopper/OpenGL/tree/master/Example26/shader
/*
or :
https://github.com/NickJ25/OpenGL-Fur-Shader/blob/master/AGP_Individual/furShader.vert
https://github.com/NickJ25/OpenGL-Fur-Shader/blob/master/AGP_Individual/furShader.frag
 */

void main() {

    // Extrude the surface by the normal by the gap
    vec3 extruded_position = a_position.xyz + (a_normal * (current_layer * (fur_length / layers)));
    // Translate into worldspace
    vec4 extruded_position_global = (body_transform * vec4(extruded_position, 1.0));

    // As the categories gets closer to the tip, bend more
    float layer_normalize = (currentLayer / layers);
    g = (g * body_transform);
    float k = pow(layer_normalize, 3) * 0.08;
    extruded_position_global = extruded_position_global + g * k;
    if(current_layer != 0){
        extruded_position_global = extruded_position_global + vec4(1.0f, 1.0f, 1.0f, 1.0f) * (fur_flow_offset);
    }

    // compute immediate output:
    gl_Position = camera_combined * extruded_position_global;

    // compute output to fragment shader



}
