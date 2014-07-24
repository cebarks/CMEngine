package net.cme.engine;

import net.cme.game.Player;
import net.cme.world.Game;
import net.cme.world.World;

public class Engine {
	private Game game;
	private Window window;
	private Camera camera;
	private State state;
	private Player player;
	
	private World currentWorld;
	
	public void run() {
		state = State.RUNNING;
		while(state == State.RUNNING) {
			player.input();
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
	
	public void setState(State state) {
		this.state = state;
	}
	
	public State getState() {
		return state;
	}
	
	public void addGame(Game game) {
		this.game = game;
	}
	
	public void addWindow(Window window) {
		this.window = window;
	}
	
	public void addCamera(Camera camera) {
		this.camera = camera;
	}
	
	public void addPlayer(Player player) {
		this.player = player;
	}
}
