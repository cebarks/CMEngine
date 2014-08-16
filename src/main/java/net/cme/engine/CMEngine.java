package net.cme.engine;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

public class CMEngine implements Runnable {

	public static final String TITLE = "CMEngine v.01";
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	public boolean isRunning = false, isWireframe = false;
	
	public static final Logger LOGGER = LogManager.getLogger(CMEngine.class);
	
	public Game game = new Game();
	
	private Thread thread;

	public CMEngine() {
		thread = new Thread(this, "CMEngine-0");
	}

	public void run() {
		
		isRunning = true;

		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle(TITLE);
			Display.setVSyncEnabled(true);
			PixelFormat pixelFormat = new PixelFormat(8, 8, 0, 8);
			ContextAttribs contextAttribs = new ContextAttribs(3, 2).withProfileCore(true).withForwardCompatible(true);
			Display.create(pixelFormat, contextAttribs);
		} catch (LWJGLException e) {
			CMEngine.exitOnError(1, e);
		}
		
		game.load();

		while (isRunning == true) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glEnable(GL_DEPTH_TEST);
			
			game.update();
			
			Display.update();
			Display.sync(60);
		}
		
		game.exit();
		
		Display.destroy();
		exit(0);
	}

	public void start() {
		thread.start();
	}

	public static void exit(int status) {
		LOGGER.info("Closing under status " + status);
		System.exit(status);
	}

	public static void exitOnError(int status, Exception e) {
		LOGGER.fatal("Closing with errors under status " + status, e);
		System.exit(status);
	}
}
