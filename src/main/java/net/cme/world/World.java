package net.cme.world;

import net.cme.engine.CMEngine;

public class World {
	private String name;
	private CMEngine engine;

	public World(CMEngine engine, String name) {
		this.name = name;
		this.engine = engine;
	}
	
	public String getName() {
		return name;
	}
}
