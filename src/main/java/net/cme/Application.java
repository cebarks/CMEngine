package net.cme;

import net.cme.engine.Camera;
import net.cme.engine.Engine;
import net.cme.engine.Window;
import net.cme.game.Player;
import net.cme.mesh.Vertex;
import net.cme.world.Game;
import net.cme.world.World;

public class Application {
	public static void main(String[] args) {
		Engine engine = new Engine();
		
		Window window = new Window();
		Camera camera = new Camera();
		Player player = new Player();
		Game game = new Game();
		World world = new World();
		
		window.create(engine, "hello, world!", 800, 600, 60, true);
		camera.create(engine, new Vertex(0, 0, 0), new Vertex(0, 0, 0), 68, 0.01f, 100f);
		player.create(engine);
		game.create(engine, "this would be where the game is located");
		world.create(game, "test_arena");
		
		engine.setLevel("test_arena");
		
		engine.run();
	}
}
