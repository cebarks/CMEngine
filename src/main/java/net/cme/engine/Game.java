package net.cme.engine;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

import java.util.Random;

import net.cme.model.Model;
import net.cme.model.Shader;
import net.cme.model.Transform;
import net.cme.util.Vector3;

import org.lwjgl.input.Keyboard;

public class Game {
	
	public Transform transform;
	public Shader shader;
	public Model model, mainmodel, modelFly;
	
	private float tick = 1, mainBunnyY = 0, mainBunnyX = 0;
	
	public void load() { 
		transform = new Transform();
		transform.setProjection(0.1f, 1000, 800, 600, 70);

		shader = new Shader();
		shader.addProgram("basicVertex.glsl", GL_VERTEX_SHADER);
		shader.addProgram("basicFragment.glsl", GL_FRAGMENT_SHADER);
		shader.compileShader();
		
		shader.addUniform("uniformPosition");
		shader.addUniform("uniformColor");

		model = new Model("bunny.obj");
		model.setShader(shader);
		model.bufferData();
	}
	
	private void input() {
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			CMEngine.exit(0);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			mainBunnyY += 0.01f;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			mainBunnyY -= 0.01f;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			mainBunnyX -= 0.01f;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			mainBunnyX += 0.01f;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_R)) {
			mainBunnyY = 0;
			mainBunnyX = 0;
		}
		
		while(Keyboard.next()) {
			if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
				tick += 10;
			}
		}
	}
	
	public void update() {
		input();
		glClearColor((float) Math.sin((tick / 30) + 8), (float) Math.sin((tick / 30) + 9), (float) Math.sin((tick / 30) + 10), 1);
		
		tick++;
		
		//CONTROLLED BUNNY CODE
		transform.translation = new Vector3(mainBunnyX, mainBunnyY, 1);
		transform.rotation = new Vector3(-mainBunnyY * 300, 0, mainBunnyX * 300);
		transform.scale = new Vector3(0.1f, 0.1f, 0.1f);
		
		shader.setUniformMat4("uniformPosition", transform.getProjectedTransformation());
		shader.setUniformVec3("uniformColor", new Vector3((float) Math.sin((tick / 30) - 6), (float) Math.sin((tick / 30) + 7), (float) Math.sin((tick / 30) + 8)));
		shader.bind();
		
		model.render();

		//DANCING BUNNY CODE
		for(int i = -12; i < 12; i++) {
			transform.translation = new Vector3(i, (float) Math.sin((tick / 30) + i), 10);
			
			if(i % 2 == 0) 
				transform.rotation = new Vector3(0, tick * 3, 0);
			else
				transform.rotation = new Vector3(tick * 3, 0, 0);
			
			transform.scale = new Vector3((float) Math.sin((tick / 30) + i), (float) Math.sin((tick / 30) + i), (float) Math.sin((tick / 30) + i));

			shader.setUniformMat4("uniformPosition", transform.getProjectedTransformation());
			shader.setUniformVec3("uniformColor", new Vector3((float) Math.sin((tick / 30) + 2), (float) Math.sin((tick / 30) + 3), (float) Math.sin((tick / 30) + 4)));
			shader.bind();

			model.render();
		}
	}

	public void exit() {

	}
}
