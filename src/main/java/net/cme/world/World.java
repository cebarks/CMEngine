package net.cme.world;

public class World {
	private String name;

	public void create(Game game, String name) {
		this.name = name;
		game.addWorld(this);
	}
	
	public String getName() {
		return name;
	}
}
