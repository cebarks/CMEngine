package net.cme.shader;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import java.util.HashMap;

import net.cme.engine.CMEngine;
import net.cme.util.Matrix4;
import net.cme.util.Util;
import net.cme.util.Vector;

import org.lwjgl.LWJGLException;

public class Shader {
	private int program;
	private HashMap<String, Integer> uniforms;

	public Shader() {
		program = glCreateProgram();
		uniforms = new HashMap<String, Integer>();

		if (program == 0) {
			CMEngine.LOGGER.error("Program could not be created: " + glGetShaderInfoLog(program, 1024));
			CMEngine.exitOnError(1, new Exception());
		}
	}

	public void bind() {
		glUseProgram(program);
	}

	public void addProgram(String location, int type) {
		String source = Util.loadShaderSource(location);
		int shader = glCreateShader(type);

		if (shader == 0) {
			CMEngine.LOGGER.error("Shader is null");
			CMEngine.exitOnError(1, new LWJGLException());
		}

		glShaderSource(shader, source);
		glCompileShader(shader);

		if (glGetShaderi(shader, GL_COMPILE_STATUS) == 0) {
			CMEngine.LOGGER.error("Could not compile shader: \n" + glGetShaderInfoLog(shader, 1024));
			CMEngine.exitOnError(1, new LWJGLException());
		}

		glAttachShader(program, shader);
	}

	public void compileShader() {
		glLinkProgram(program);

		if (glGetProgrami(program, GL_LINK_STATUS) == 0) {
			CMEngine.LOGGER.error("Could not link program: \n" + glGetProgramInfoLog(program, 1024));
		}

		glValidateProgram(program);

		if (glGetShaderi(program, GL_VALIDATE_STATUS) == GL_FALSE) {
			CMEngine.LOGGER.error("Could not validate program: \n" + glGetShaderInfoLog(program, 1024));
			CMEngine.exitOnError(1, new LWJGLException());
		}
	}

	public void addUniform(String uniform) {
		int uniformLocation = glGetUniformLocation(program, uniform);

		if (uniformLocation == 0xFFFFFFFF) {
			CMEngine.LOGGER.error("Could not load uniform " + uniform);
			CMEngine.exitOnError(1, new Exception());
		}

		uniforms.put(uniform, uniformLocation);
	}

	public void setUniformInt(String name, int value) {
		glUniform1i(uniforms.get(name), value);
	}

	public void setUniformFloat(String name, float value) {
		glUniform1f(uniforms.get(name), value);
	}

	public void setUniformVec3(String name, Vector value) {
		glUniform3f(uniforms.get(name), value.x, value.y, value.z);
	}

	public void setUniformMat4(String name, Matrix4 value) {
		glUniformMatrix4(uniforms.get(name), true, Util.createFlippedMatrixBuffer(value));
	}
}
