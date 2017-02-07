#version 400

in vec2 tex_coord;

out vec4 frag_colour;

uniform sampler2D texture_sampler;

void main () {
    frag_colour = texture(texture_sampler, tex_coord);
}