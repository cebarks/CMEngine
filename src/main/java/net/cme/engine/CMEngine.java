package net.cme.engine;

import net.cme.model.Model;
import net.cme.util.Vector3;
import net.cme.world.Player;
import net.cme.world.World;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CMEngine implements Runnable {

	public static final String TITLE = "CMEngine";
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	public static final Logger LOGGER = LogManager.getLogger(CMEngine.class.getSimpleName());

	private Window window;
	private Camera camera;
	private State state;
	private Player player;

	private World currentWorld;

	private Thread thread;
	private int exitStatus = 0;

	public CMEngine() {
		thread = new Thread(this, "CMEngine-0");
		window = new Window(this, TITLE, WIDTH, HEIGHT, 60, true);
		camera = new Camera(this, new Vector3(0, 0, 0), new Vector3(0, 0, 0), 68, 0.01f, 100f);
		run();
	}

	public void run() {
		state = State.LOADING;

		player = new Player(this);
		Model model = new Model("bunny.obj");

		state = State.RUNNING;
		while (state == State.RUNNING) {
			player.input();
			window.clear();
			camera.render();
			window.update();
			model.render();
		}
		window.destroy();
		camera.destroy();
		System.exit(getExitStatus());
	}

	public void start() {
		thread.start();
	}

	public void stop() {
		state = State.EXITING;
	}

	public void exit(int status) {
		LOGGER.info("Closing under status " + status);
		exitStatus = status;
	}

	public void exitOnError(int status, Exception e) {
		LOGGER.error("Closing with errors under status " + status, e);
		exitStatus = status;
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

	private int getExitStatus() {
		return exitStatus;
	}
}
