#version 400

layout(location = 0) in vec3 vertexPosition;
layout(location = 1) in vec2 uvTexCoord;

out vec2 tex_coord;

uniform mat4 mvp_matrix;

void main () {
    gl_Position = mvp_matrix * vec4(vertexPosition, 1.0);
    
    //STB loads images in DirectX UV format so this converts OpenGL UVs to DirectX UVs
    tex_coord = vec2(uvTexCoord.x, 1-uvTexCoord.y);
}
