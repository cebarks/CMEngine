package net.cme.engine;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import net.cme.model.Model;
<<<<<<< HEAD
import net.cme.model.Shader;
=======
>>>>>>> 2578798d5d2f4aaa983099f0898a655351286083
import net.cme.util.Vector3;
import net.cme.world.Player;
import net.cme.world.World;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CMEngine implements Runnable {

	public static final String TITLE = "CMEngine";
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	public static final Logger LOGGER = LogManager.getLogger(CMEngine.class);

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
		camera = new Camera(new Vector3(0, 0, 0), new Vector3(0, 0, 0), 68, 0.01f, 1000f);
		window = new Window(TITLE, WIDTH, HEIGHT, 60, true);
		player = new Player(this);
<<<<<<< HEAD
		
		Model model = new Model("bunny.obj");
		
		Shader shader = new Shader();
		shader.addProgram("basicVertex.glsl", GL_VERTEX_SHADER);
		shader.addProgram("basicFragment.glsl", GL_FRAGMENT_SHADER);
		shader.compileShader();
		
		model.bindShader(shader);
		
=======

		// Shader shader = new Shader();
		// shader.addVertexShader(shader.loadShaderSource("basicVertex.glsl"));
		// shader.compile();

		// model.bindShader(shader);

		Model model = null;

		try {
			model = Model.loadModel("bunny.obj");
		} catch (Exception e) {
			LOGGER.error("Error loading OBJ model.", e);
		}

>>>>>>> 2578798d5d2f4aaa983099f0898a655351286083
		state = State.RUNNING;
		while (state == State.RUNNING) {
			player.input();
			window.clear();
			camera.render();
			window.update();
		}
		
		window.destroy();
		camera.destroy();
		exit(0);
	}

	public void start() {
		thread.start();
	}

	public void stop() {
		state = State.EXITING;
	}

	public static void exit(int status) {
		LOGGER.info("Closing under status " + status);
		System.exit(status);
	}

	public static void exitOnError(int status, Exception e) {
		LOGGER.fatal("Closing with errors under status " + status, e);
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
