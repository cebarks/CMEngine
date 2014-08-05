package net.cme.engine;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

public class CMEngine implements Runnable {

	public static final String TITLE = "CMEngine";
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	public static final Logger LOGGER = LogManager.getLogger(CMEngine.class);

	private State state;

	private Thread thread;
	
	private Game game;
	
	public CMEngine() {
		thread = new Thread(this, "CMEngine-0");
	}

	public void run() {
		game = new Game();
		
		state = State.LOADING;
		
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle(TITLE);
			Display.setVSyncEnabled(true);
			PixelFormat pixelFormat = new PixelFormat(8, 8, 0, 0);
			ContextAttribs contextAttribs = new ContextAttribs(3, 2).withProfileCore(true).withForwardCompatible(true);
			Display.create(pixelFormat, contextAttribs);
		} catch (LWJGLException e) {
			CMEngine.exitOnError(1, e);
		}
		
		game.load();

		state = State.RUNNING;
		
		while (state == State.RUNNING) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			game.update();
			Display.update();
		}
		
		game.exit();
		
		Display.destroy();
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
}
