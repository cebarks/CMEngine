package net.cme.world;

public class World {
	private final String name;
	private Game game;
	
	public World(String name) {
		this.name = name;
	}
	
	public void create(Game game) {
		game.addWorld(this);
	}
	
	public String getName() {
		return name;
	}
}
