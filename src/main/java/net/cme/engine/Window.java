package net.cme.engine;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glGetString;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

public class Window {
	private final String title;
	private final int width, height;
	private final int fps;
	private boolean vsync;
	private CMEngine engine;

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
			PixelFormat pixelFormat = new PixelFormat(8, 8, 0, 0);
			ContextAttribs contextAttribs = new ContextAttribs(3, 2).withProfileCore(true).withForwardCompatible(true);
			Display.create(pixelFormat, contextAttribs);
		} catch (LWJGLException e) {
			CMEngine.exitOnError(1, e);
		}

		CMEngine.LOGGER.info(String.format("Running on GLVersion: %s", glGetString(GL_VERSION)));
	}

	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(0.4f, 0.6f, 0.9f, 0f);
	}

	public void update() {
		Display.update();
		Display.sync(fps);
		if (Display.isCloseRequested()) {
			engine.setCurrentState(State.EXITING);
		}
	}

	public void destroy() {
		Display.destroy();
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
