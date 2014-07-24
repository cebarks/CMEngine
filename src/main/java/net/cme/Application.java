package net.cme;

import net.cme.engine.Camera;
import net.cme.engine.Engine;
import net.cme.engine.Window;
import net.cme.game.Player;
import net.cme.vec.Vector3;
import net.cme.world.Game;
import net.cme.world.World;

public class Application {
	public static void main(String[] args) {
		
		Engine engine = new Engine();
		new Window(engine, "hello, world!", 800, 600, 60, true);
		new Camera(engine, new Vector3(0, 0, 0), new Vector3(0, 0, 0), 68, 0.01f, 100f);
		new Player(engine);
		
		Game game = new Game(engine, "this would be where the game is located");
		new World(game, "test_arena");
		
		engine.setLevel("test_arena");
		engine.run();
	}
}
