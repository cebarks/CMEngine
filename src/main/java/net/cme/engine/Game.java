package net.cme.engine;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import net.cme.model.Model;
import net.cme.model.Shader;
import net.cme.model.Transform;
import net.cme.util.Vector3;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Game {
	
	public Transform transform;
	public Shader shader;
	public Model model;
	
	private Vector3 position, rotation;
	
	private float tick = 0, speed = 1;
	
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
		
		position = new Vector3(0, 0, 0);
		rotation = new Vector3(0, 0, 0);
		
		Mouse.setGrabbed(true);
	}
	
	private void input() {
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			CMEngine.exit(0);
		}
		
		//if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
		//	float hypotenuse = (speed / 60);
		//	float adjacent = hypotenuse * (float) Math.cos(Math.toRadians(rotation.y));
		//	float opposite = (float) (Math.sin(Math.toRadians(rotation.y)) * hypotenuse);

		//	float newZ = position.z;
		//	float newX = position.x;
			
		//	newZ += adjacent;
		//	newX -= opposite;

		//	position.z = newZ;
		//	position.x = newX;
		//}
		//rotation.x += Mouse.getDY();
		//rotation.y += Mouse.getDX();
	}
	
	public void update() {
		input();
		
		tick += 0.03f;
		
		//TODO This and the input code are commented out because I don't know how to make a fps camera with matrices yet
		//transform.translation = new Vector3(position.x, position.y, position.z);
		//transform.rotation = new Vector3(rotation.x, rotation.y, 0);
		
		transform.translation = new Vector3(1, -1, 5);
		transform.rotation = new Vector3(0, tick * 30, 0);
		transform.scale = new Vector3(1, 1, 1f);

		shader.setUniformMat4("uniformPosition", transform.getProjectedTransformation());
		shader.setUniformVec3("uniformColor", new Vector3((float) Math.sin(tick + 1), (float) Math.sin(tick + 2), (float) Math.sin(tick + 3)));
		shader.bind();
		
		model.render();
	}

	public void exit() {

	}
}
