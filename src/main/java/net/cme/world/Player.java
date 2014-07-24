package net.cme.world;

import net.cme.engine.CMEngine;
import net.cme.engine.State;

import org.lwjgl.input.Keyboard;

public class Player {
	private CMEngine engine;

	public Player(CMEngine engine) {
		this.engine = engine;
	}

	public void input() {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			engine.setCurrentState(State.EXITING);
	}
}
