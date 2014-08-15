package net.cme.engine;
import static org.lwjgl.opengl.GL11.glClearColor;
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
	public Model model;
	
	public float tick = 1, x = 0, y = 0;
	
	public boolean isProjected = false;
	
	public void load() { 
		transform = new Transform();
		transform.setProjection(0.1f, 1000, 800, 600, 70);

		shader = new Shader();
		shader.addProgram("basicVertex.glsl", GL_VERTEX_SHADER);
		shader.addProgram("basicFragment.glsl", GL_FRAGMENT_SHADER);
		shader.compileShader();
		
		shader.addUniform("uniformPosition");
		shader.addUniform("uniformColor");

		model = new Model("triangle.obj");
		model.setShader(shader);
		model.bufferData();
	}
	
	private void input() {
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			CMEngine.exit(0);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			y += 0.01f;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			y -= 0.01f;
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
		
		while(Keyboard.next()) {
			if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
				if(isProjected)
					isProjected = false;
				else 
					isProjected = true;
			}
		}
	}
	
	public void update() {
		input();
		tick++;
		
		glClearColor((float) Math.sin((tick / 30)), (float) Math.sin((tick / 30) + 1), (float) Math.sin((tick / 30) + 2), 1);
		
		transform.translation = new Vector3(x, y, 0.2f);
		transform.rotation = new Vector3(0, x * 300, 0);
		transform.scale = new Vector3(0.05f, 0.05f, 0.05f);
		
		if(isProjected)
			shader.setUniformMat4("uniformPosition", transform.getProjectedTransformation());
		else
			shader.setUniformMat4("uniformPosition", transform.getTransformation());
		
		shader.setUniformVec3("uniformColor", new Vector3((float) Math.sin((tick / 30) - 4) + 1, (float) Math.sin((tick / 30) + 5) + 1, (float) Math.sin((tick / 30) + 6) + 1));
		shader.bind();
		
		model.render();
	}

	public void exit() {
		model.destroy();
	}
}
