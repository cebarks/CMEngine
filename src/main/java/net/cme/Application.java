package net.cme;

import net.cme.engine.Camera;
import net.cme.engine.Engine;
import net.cme.engine.Window;
import net.cme.mesh.Vertex;
import net.cme.vec.Vector3;

public class Application {
	public static void main(String[] args) {
		Engine engine = new Engine();
		Window window = new Window();
		Camera camera = new Camera();
		
		window.create(engine, "hello, world!", 800, 600, 60, true);
		camera.create(engine, new Vertex(new Vector3(0, 0, 0)), new Vertex(new Vector3(0, 0, 0)), 68, 0.01f, 100f);
		
		engine.run();
	}
}
