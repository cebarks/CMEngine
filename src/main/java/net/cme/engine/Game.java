package net.cme.engine;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

import java.io.FileNotFoundException;
import java.io.IOException;

import net.cme.model.Model;
import net.cme.model.Shader;
import net.cme.model.Transform;
import net.cme.util.Vector3;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Game {
	
	public Transform transform, testtransform;
	public Shader shader;
	public Model model;
	
	private float x = 0.01f;
	
	public void load() { 
		transform = new Transform();
		testtransform = new Transform();

		shader = new Shader();
		shader.addProgram("basicVertex.glsl", GL_VERTEX_SHADER);
		shader.addProgram("basicFragment.glsl", GL_FRAGMENT_SHADER);
		shader.compileShader();

		shader.addUniform("uniformPosition");

		try {
			model = Model.loadModel("cow.obj");
		} catch (FileNotFoundException e) {
			CMEngine.LOGGER.error("Could not load models");
			CMEngine.exitOnError(1, e);
		} catch (IOException e) {
			CMEngine.LOGGER.error("Could not load models");
			CMEngine.exitOnError(1, e);
		}
		
		model.generateModel(shader);
	}
	
	public void update() {
		
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			x += 0.01f;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			x -= 0.01f;
		}
		
		transform.translation = new Vector3(0, -0.5f, 0);
		transform.rotation = new Vector3(Mouse.getY(), Mouse.getX(), 0);
		transform.scale = new Vector3(x, x, x);

		shader.setUniformMat4("uniformPosition", transform.getTransformation());
		shader.bind();
		
		model.render();
	}

	public void exit() {

	}
}
