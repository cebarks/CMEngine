package net.cme.engine;

import org.lwjgl.opengl.Display;

public class Engine {
	private Window window;
	private Camera camera;
	private State state;
	
	public void run() {
		state = State.RUNNING;
		while(state == State.RUNNING) {
			window.clear();
			camera.render();
			window.update();
		}
		exit(0);
	}
	
	public void exit(int status) {
		System.out.println("Closing under status " + status);
		window.destroy();
		camera.destroy();
		System.exit(status);
	}
	
	public void exitOnError(int status, Exception e) {
		System.err.println("Closing with errors under status " + status);
		e.printStackTrace();
		window.destroy();
		camera.destroy();
		System.exit(status);
	}
	
	public void addWindow(Window window) {
		this.window = window;
	}
	
	public void addCamera(Camera camera) {
		this.camera = camera;
	}
}
