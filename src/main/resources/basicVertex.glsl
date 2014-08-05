#version 330

layout (location = 0) in vec3 position;

uniform vec3 uniformColor;
uniform mat4 uniformPosition;

out vec4 color;

void main() {
	gl_Position = uniformPosition * vec4(position, 1);
	color = vec4(uniformColor, position.z);
}