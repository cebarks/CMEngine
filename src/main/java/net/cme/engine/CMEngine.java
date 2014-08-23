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
	public final String title;
	public final int width;
	public final int height;

	public boolean isRunning = false;

	public static final Logger LOGGER = LogManager.getLogger(CMEngine.class);

	public Game game;

	private Thread thread;
	private int fps;

	public CMEngine(Game game, String title, int width, int height) {
		this.title = String.format("CMEngine %s | %s", Version.getFullVersion(), title);
		this.game = game;
		this.width = width;
		this.height = height;

		thread = new Thread(this, "CMEngine-0");
	}

	public void run() {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(title);
			Display.setVSyncEnabled(true);
			PixelFormat pixelFormat = new PixelFormat(8, 8, 0, 8);
			ContextAttribs contextAttribs = new ContextAttribs(3, 2).withProfileCore(true).withForwardCompatible(true);
			Display.create(pixelFormat, contextAttribs);
		} catch (LWJGLException e) {
			CMEngine.exitOnError(1, e);
		}

		isRunning = true;

		game.initialize();

		long lastFrame = System.nanoTime();
		final long goalRate = 1000000000 / 60;
		long lastFPS = System.nanoTime();
		int frames = 0;

		while (isRunning) {
			long currentTime = System.nanoTime();
			float delta = (currentTime - lastFrame) / (float) goalRate;
			float frameTime = (currentTime - lastFrame) / 1000000;
			lastFrame = currentTime;

			frames++;

			if (currentTime - lastFPS >= 1000000000) {
				fps = frames;
				frames = 0;
				lastFPS = currentTime;
				//Display.setTitle("Delta: " + delta + " FPS: " + fps + " FrameTime: " + frameTime);
			}

			game.update(delta);

			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glEnable(GL_DEPTH_TEST);
			game.render();

			Display.update();
		}

		game.shutdown();

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
