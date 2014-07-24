package net.cme.engine;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Window {
	private CMEngine engine;
	private String title;
	private int width, height;
	private int fps;
	private boolean vsync;

	public Window(CMEngine engine, String title, int width, int height, int fps, boolean vsync) {
		this.engine = engine;
		this.title = title;
		this.width = width;
		this.height = height;
		this.fps = fps;
		this.vsync = vsync;

		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(title);
			Display.setVSyncEnabled(vsync);
			Display.create();
		} catch (LWJGLException e) {
			engine.exitOnError(1, e);
		}
	}

	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public void update() {
		Display.sync(fps);
		Display.update();
	}

	public void destroy() {
		Display.destroy();
	}

	public CMEngine getEngine() {
		return engine;
	}

	public String getTitle() {
		return title;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getFps() {
		return fps;
	}

	public boolean isVsync() {
		return vsync;
	}
}
