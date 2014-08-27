package net.cme.engine;

import java.util.ArrayList;
import java.util.List;

import net.cme.model.Model;
import net.cme.model.Transform;
import net.cme.shader.Shader;
import net.cme.util.Vector;

public abstract class Game {

	public Camera camera;
	public Transform transform;
	public Shader shader;
	public List<Model> models;

	public Game() {
		models = new ArrayList<Model>();

		camera = new Camera(new Vector(0, 0, -5), new Vector(0, 0, 1), new Vector(0, 1, 0));

		transform = new Transform();
		transform.setCamera(camera);
		transform.setProjection(0.01f, 1000, 800, 600, 90);
	}

	public abstract void initialize();

	public abstract void update(float delta);

	public abstract void render();

	public abstract void shutdown();
}
