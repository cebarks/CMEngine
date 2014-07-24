package net.cme.game;

import net.cme.engine.Engine;
import net.cme.engine.State;

import org.lwjgl.input.Keyboard;

public class Player {
	private Engine engine;
	
	public Player(Engine engine) {
		this.engine = engine;
		engine.addPlayer(this);
	}
	
	public void input() {
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) engine.setState(State.EXITING);
	}
}
