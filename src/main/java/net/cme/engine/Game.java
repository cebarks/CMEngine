package net.cme.engine;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import net.cme.model.Model;
import net.cme.model.Shader;
import net.cme.model.Transform;
import net.cme.util.Vector3;

import org.lwjgl.input.Keyboard;

public class Game {
	
	public Transform transform;
	public Shader shader;
	public Model[] models;
	
	public float tick = 1, x = 0, y = 0;
	
	public void load() { 
		transform = new Transform();
		transform.setProjection(0.1f, 1000, 800, 600, 70);

		shader = new Shader();
		shader.addProgram("basicVertex.glsl", GL_VERTEX_SHADER);
		shader.addProgram("basicFragment.glsl", GL_FRAGMENT_SHADER);
		shader.compileShader();
		
		shader.addUniform("uniformPosition");

		models = new Model[5];
		
		models[0] = new Model("bunny.obj");
		models[0].setShader(shader);
		models[0].bufferData();
		
		models[1] = new Model("cow.obj");
		models[1].setShader(shader);
		models[1].bufferData();
		
		models[2] = new Model("cube.obj");
		models[2].setShader(shader);
		models[2].bufferData();
		
		models[3] = new Model("playermodel.obj");
		models[3].setShader(shader);
		models[3].bufferData();
		
		models[4] = new Model("triangle.obj");
		models[4].setShader(shader);
		models[4].bufferData();
	}
	
	private void input() {
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			CMEngine.exit(0);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			y += 0.1f;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			y -= 0.1f;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			x -= 0.01f;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			x += 0.01f;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_R)) {
			y = 0;
			x = 0;
		}
	}
	
	public void update() {
		input();

		tick++;		
		
		for(int i = 0; i < 10; i++) {
			transform.translation = new Vector3(((i - 5) * 2) + 2, y - 1, 10);
			transform.rotation = new Vector3(0, x * 300, 0);
			transform.scale = new Vector3(0.4f, 0.4f, 0.4f);
			
			shader.setUniformMat4("uniformPosition", transform.getProjectedTransformation());
			
			shader.bind();
			
			if(i % 2 == 0)
				models[Math.abs(i / 2)].render();
		}
	}

	public void exit() {
		
	}
}
