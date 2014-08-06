#version 330

layout (location = 0) in vec3 position;

uniform mat4 uniformPosition;

void main() {
	gl_Position = uniformPosition * vec4(position, 1);
}