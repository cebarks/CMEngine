package net.cme.engine;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import net.cme.model.Model;
import net.cme.model.Shader;
import net.cme.model.Transform;
import net.cme.util.Vector3;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Game {
	
	public Camera camera;
	public Transform transform;
	public Shader shader;
	public Model[] models;
	
	public float tick = 1, speed = 0.1f, test = 0;
	
	public void load() { 
		camera = new Camera(new Vector3(0, 0, -5), new Vector3(0, 0, 1), new Vector3(0, 1, 0));
		camera.rotateX(0);
		camera.rotateY(0);

		transform = new Transform();
		transform.setCamera(camera);
		transform.setProjection(0.01f, 100, 800, 600, 68);

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
		
		Mouse.setGrabbed(true);
	}
	
	private void input() {
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			CMEngine.exit(0);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			camera.move(camera.getForward(), speed);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			camera.move(camera.getBackward(), speed);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			camera.rotateY(-1);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			camera.rotateY(1);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			camera.rotateX(-1);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_E)) {
			camera.rotateX(1);
		}
	}
	
	public void update() {
		input();

		glClearColor(0.5f, 0.5f, 1, 1);
		
		tick++;		
		
		for(int i = 0; i < 10; i++) {
			transform.translation = new Vector3((i - 5), 0, 0);
			transform.scale = new Vector3(0.4f, 0.4f, 0.4f);
			
			shader.setUniformMat4("uniformPosition", transform.getProjectedTransformation());
			
			shader.bind();
			
			if(i % 2 == 0)
				models[Math.abs(i / 2)].render();
		}
	}

	public void exit() {
		for(Model m : models)
			m.destroy();
	}
}
