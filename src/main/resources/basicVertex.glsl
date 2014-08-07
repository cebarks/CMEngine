#version 330

layout (location = 0) in vec3 position;

uniform mat4 uniformPosition;
uniform vec3 uniformColor;

out vec4 color;

void main() {
	gl_Position = uniformPosition * vec4(position, 1);
	color = vec4(uniformColor, 1);
}