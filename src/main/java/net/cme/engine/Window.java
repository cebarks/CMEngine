package net.cme.engine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

public class Window {
	private Engine engine;
	private String title;
	private int width, height;
	private int fps;
	private boolean vsync;
	
	public void create(Engine engine, String title, int width, int height, int fps, boolean vsync) {
		engine.addWindow(this);
		
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
		} catch(LWJGLException e) {
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
}
