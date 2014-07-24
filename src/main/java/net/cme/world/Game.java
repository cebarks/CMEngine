package net.cme.world;

import java.util.ArrayList;
import java.util.List;

import net.cme.engine.Engine;

public class Game {
	private List<World> worlds;

	public void create(Engine engine, String location) {
		worlds = new ArrayList<World>();
		//TODO Loads world here
		engine.addGame(this);
	}

	public void addWorld(World world) {
		if (worlds != null) {
			worlds.add(world);
		} else {
			worlds = new ArrayList<World>();
			worlds.add(world);
		}
	}

	public World getWorld(String name) {
		for (World world : worlds)
			if (world.getName() == name)
				return world;

		return null;
	}
}
