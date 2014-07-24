package net.cme.util;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

import java.io.BufferedReader;
import java.io.FileReader;

import net.cme.engine.CMEngine;
import net.cme.model.Model;

public class Shader {
	private int program;

	public Shader(Model model) {

		model.bindShader(this);

		program = glCreateProgram();

		if (program == 0) {
			CMEngine.exitOnError(1, new Exception());
		}
	}

	public void addVertexShader(String source) {
		addProgram(source, GL_VERTEX_SHADER);
	}

	public void addFragmentShader(String source) {
		addProgram(source, GL_FRAGMENT_SHADER);
	}

	public void addGeometryShader(String source) {
		addProgram(source, GL_GEOMETRY_SHADER);
	}

	public void addProgram(String source, int type) {
		int shader = glCreateShader(type);

		if (shader == 0) {
			CMEngine.exitOnError(1, new Exception());
		}

		glShaderSource(shader, source);
		glCompileShader(shader);

		glAttachShader(shader, program);
		
		if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
			CMEngine.LOGGER.error("Could not create program");
			CMEngine.exitOnError(1, new Exception());
		}
	}

	public void compile() {
		glLinkProgram(program);
		
		if (glGetShaderi(program, GL_LINK_STATUS) == GL_FALSE) {
			CMEngine.LOGGER.error("Could not compile shader");
			CMEngine.exitOnError(1, new Exception());
		}
		
		glValidateProgram(program);
	}

	public String loadShaderSource(CMEngine engine, String location) {
		StringBuilder shaderSource = new StringBuilder();
		BufferedReader shaderReader = null;

		try {
			shaderReader = new BufferedReader(new FileReader( "src/main/resources/" + location));
			String line;

			while ((line = shaderReader.readLine()) != null) {
				shaderSource.append(line).append("\n");
			}

			shaderReader.close();
		} catch (Exception e) {
			CMEngine.exitOnError(1, e);
		}
		return shaderSource.toString();
	}
}
