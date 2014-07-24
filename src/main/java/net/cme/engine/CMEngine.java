package net.cme.engine;

import net.cme.model.Model;
import net.cme.util.Shader;
import net.cme.util.Vector3;
import net.cme.world.Player;
import net.cme.world.World;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CMEngine implements Runnable {

	public static final String TITLE = "CMEngine";
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	public static final Logger LOGGER = LogManager.getLogger();

	private Window window;
	private Camera camera;
	private State state;
	private Player player;

	private World currentWorld;

	private Thread thread;

	public CMEngine() {
		thread = new Thread(this, "CMEngine-0");
	}

	public void run() {
		state = State.LOADING;
		camera = new Camera(this, new Vector3(0, 0, 0), new Vector3(0, 0, 0), 68, 0.01f, 100f);
		window = new Window(this, TITLE, WIDTH, HEIGHT, 60, true);
		player = new Player(this);
		
		Shader shader = new Shader();
		shader.addVertexShader(shader.loadShaderSource("basicVertex.glsl"));
		shader.compile();
		
		Model model = new Model("bunny.obj");
		model.bindShader(shader);
		
		state = State.RUNNING;
		while (state == State.RUNNING) {
			player.input();
			window.clear();
			camera.render();
			model.render();
			window.update();
		}
		window.destroy();
		camera.destroy();
	}

	public void start() {
		thread.start();
	}

	public void stop() {
		state = State.EXITING;
	}

	public void exit(int status) {
		LOGGER.info("Closing under status " + status);
		System.exit(status);
	}

	public static void exitOnError(int status, Exception e) {
		LOGGER.error("Closing with errors under status " + status, e);
		System.exit(status);
	}

	public void setCurrentState(State state) {
		this.state = state;
	}

	public State getState() {
		return state;
	}

	public World getWorld() {
		return currentWorld;
	}
}
