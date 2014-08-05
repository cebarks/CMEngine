#version 330

layout (location = 0) in vec3 position;

out vec4 color;

uniform float uniformFloat;

void main() {
	gl_Position = vec4(uniformFloat * position, 2);
}