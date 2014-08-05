package net.cme.engine;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

import java.io.FileNotFoundException;
import java.io.IOException;

import net.cme.model.Model;
import net.cme.model.Shader;
import net.cme.model.Transform;
import net.cme.util.Vector3;

public class Game {
	
	public Transform transform, testtransform;
	public Shader shader;
	public Model model;
	
	public void load() { 
		transform = new Transform();
		testtransform = new Transform();

		shader = new Shader();
		shader.addProgram("basicVertex.glsl", GL_VERTEX_SHADER);
		shader.addProgram("basicFragment.glsl", GL_FRAGMENT_SHADER);
		shader.compileShader();

		shader.addUniform("uniformPosition");
		shader.addUniform("uniformColor");

		try {
			model = Model.loadModel("triangle.obj");
		} catch (FileNotFoundException e) {
			CMEngine.LOGGER.error("Could not load models");
			CMEngine.exitOnError(1, e);
		} catch (IOException e) {
			CMEngine.LOGGER.error("Could not load models");
			CMEngine.exitOnError(1, e);
		}
		
		model.generateModel(shader);
	}
	
	float x = 0;
	public void update() {
		x += 1f;
		
		transform.translation = new Vector3(0, -0.5f, 0);
		transform.rotation = new Vector3(0, x, 0);
		transform.scale = new Vector3(0.5f, 0.5f, 0.5f);

		shader.setUniformVec3("uniformColor", new Vector3(1, 1, 1));
		shader.setUniformMat4("uniformPosition", transform.getTransformation());
		shader.bind();

		model.render();
	}

	public void exit() {

	}
}
