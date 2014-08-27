package net.cme.test;

import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import net.cme.engine.CMEngine;
import net.cme.engine.Game;
import net.cme.model.Model;
import net.cme.shader.Shader;
import net.cme.util.Vector;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class TestGame extends Game {
	public float tick = 1, speed = 0.1f, test = 0;

	public void initialize() {
		shader = new Shader();
		shader.addProgram("basicVertex.glsl", GL_VERTEX_SHADER);
		shader.addProgram("basicFragment.glsl", GL_FRAGMENT_SHADER);
		shader.compileShader();

		shader.addUniform("uniformPosition");

		Model cube = new Model("cube.obj");
		cube.setShader(shader);
		cube.bufferData();
		models.add(cube);

		Model cow = new Model("cow.obj");
		cow.setShader(shader);
		cow.bufferData();
		models.add(cow);

		Model bunny = new Model("bunny.obj");
		bunny.setShader(shader);
		bunny.bufferData();
		models.add(bunny);

		Model playerModel = new Model("playermodel.obj");

		playerModel.setShader(shader);
		playerModel.bufferData();
		models.add(playerModel);

		Model triangle = new Model("triangle.obj");
		triangle.setShader(shader);
		triangle.bufferData();
		models.add(triangle);

		Mouse.setGrabbed(true);
	}

	public void update(float delta) {
		input(delta);

		tick++;
	}

	@Override
	public void render() {
		glClearColor(0.5f, 0.5f, 0.5f, 1);

		for (int i = 0; i < models.size(); i++) {
			transform.translation = new Vector(i * 10, 0, 0);
			transform.rotation = new Vector(0, 90, 0);
			shader.setUniformMat4("uniformPosition", transform.getProjectedTransformation());

			shader.bind();
			models.get(i).render();
		}
	}

	@Override
	public void shutdown() {
		for (Model m : models)
			m.destroy();
	}

	private void input(float delta) {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			CMEngine.exit(0);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			camera.move(camera.getForward(), speed * delta);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			camera.move(camera.getBackward(), speed * delta);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			camera.move(camera.getLeft(), speed * delta);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			camera.move(camera.getRight(), speed * delta);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			camera.move(camera.getUp(), speed * delta);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			camera.move(camera.getDown(), speed * delta);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_M))
			Mouse.setGrabbed(!Mouse.isGrabbed());

		if (Mouse.isGrabbed()) {
			camera.rotateX(Mouse.getDY() / 2);
			camera.rotateY(Mouse.getDX() / 2);
		}
	}

	public static void main(String[] args) {
		CMEngine engine = new CMEngine(new TestGame(), "TestGame", 800, 600);
		engine.start();
	}
}
