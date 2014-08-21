package net.cme.engine;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import net.cme.model.Model;
import net.cme.model.Transform;
import net.cme.shader.Shader;
import net.cme.util.Vector;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Game {
	
	public Camera camera;
	public Transform transform;
	public Shader shader;
	public Model[] models;
	
	public float tick = 1, speed = 0.1f, test = 0;
	
	public void load() { 
		camera = new Camera(new Vector(0, 0, -5), new Vector(0, 0, 1), new Vector(0, 1, 0));

		transform = new Transform();
		transform.setCamera(camera);
		transform.setProjection(0.01f, 1000, 800, 600, 90);

		shader = new Shader();
		shader.addProgram("basicVertex.glsl", GL_VERTEX_SHADER);
		shader.addProgram("basicFragment.glsl", GL_FRAGMENT_SHADER);
		shader.compileShader();
		
		shader.addUniform("uniformPosition");

		models = new Model[5];
		
		models[0] = new Model("cube.obj");
		models[0].setShader(shader);
		models[0].bufferData();
		
		models[1] = new Model("cow.obj");
		models[1].setShader(shader);
		models[1].bufferData();
		
		models[2] = new Model("bunny.obj");
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
			camera.move(camera.getLeft(), speed);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			camera.move(camera.getRight(), speed);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			camera.move(camera.getUp(), speed);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			camera.move(camera.getDown(), speed);
		}
		
		camera.rotateX(Mouse.getDY() / 2);
		camera.rotateY(Mouse.getDX() / 2);	
	}
	
	public void update() {
		input();

		glClearColor(0.5f, 0.5f, 0.5f, 1);
		
		tick++;		
		
		for(int i = 0; i < 5; i++) {
			transform.translation = new Vector(i * 10, 0, 0);
			transform.rotation = new Vector(0, 90, 0);
			
			shader.setUniformMat4("uniformPosition", transform.getProjectedTransformation());
			
			shader.bind();
			
			models[i].render();	
		}
	}

	public void exit() {
		for(Model m : models) m.destroy();
	}
}
