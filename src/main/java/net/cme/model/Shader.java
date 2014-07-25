package net.cme.model;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
<<<<<<< HEAD:src/main/java/net/cme/model/Shader.java
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
=======
>>>>>>> 2578798d5d2f4aaa983099f0898a655351286083:src/main/java/net/cme/util/Shader.java
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import java.io.BufferedReader;
import java.io.FileReader;

import net.cme.engine.CMEngine;

import org.lwjgl.LWJGLException;

public class Shader {
	private int program;

	public Shader() {
		program = glCreateProgram();
<<<<<<< HEAD:src/main/java/net/cme/model/Shader.java
=======

>>>>>>> 2578798d5d2f4aaa983099f0898a655351286083:src/main/java/net/cme/util/Shader.java
		if (program == 0) {
			CMEngine.LOGGER.error("Program could not be created: " + glGetShaderInfoLog(program, 1024));
			CMEngine.exitOnError(1, new Exception());
		}
	}
	
	public void bind() {
		glUseProgram(program);
	}

	public void addProgram(String location, int type) {
		String source = loadShaderSource(location);
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

		glAttachShader(shader, program);
	}

	public void compileShader() {
		glLinkProgram(program);
<<<<<<< HEAD:src/main/java/net/cme/model/Shader.java
		
		if (glGetProgrami(program, GL_LINK_STATUS) == 0) {
			CMEngine.LOGGER.error("Could not link program: \n" + glGetProgramInfoLog(program, 1024));
=======

		if (glGetShaderi(program, GL_LINK_STATUS) == GL_FALSE) {
			CMEngine.LOGGER.error("Could not link program: \n" + glGetShaderInfoLog(program, 1024));
>>>>>>> 2578798d5d2f4aaa983099f0898a655351286083:src/main/java/net/cme/util/Shader.java
			CMEngine.exitOnError(1, new LWJGLException());
		}

		glValidateProgram(program);
<<<<<<< HEAD:src/main/java/net/cme/model/Shader.java
		
		if (glGetProgrami(program, GL_VALIDATE_STATUS) == 0) {
			CMEngine.LOGGER.error("Could not validate program: \n" + glGetProgramInfoLog(program, 1024));
=======

		if (glGetShaderi(program, GL_VALIDATE_STATUS) == GL_FALSE) {
			CMEngine.LOGGER.error("Could not validate program: \n" + glGetShaderInfoLog(program, 1024));
>>>>>>> 2578798d5d2f4aaa983099f0898a655351286083:src/main/java/net/cme/util/Shader.java
			CMEngine.exitOnError(1, new LWJGLException());
		}
	}

	public String loadShaderSource(String location) {
		StringBuilder shaderSource = new StringBuilder();
		BufferedReader shaderReader = null;
		
		try {
			shaderReader = new BufferedReader(new FileReader("src/main/resources/" + location));
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
