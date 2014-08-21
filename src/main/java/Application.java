import net.cme.engine.CMEngine;
import net.cme.test.TestGame;

public class Application {
	public static void main(String[] args) {
		CMEngine engine = new CMEngine(new TestGame(), "CMEngine v0.1 Test Game", 800, 600);
		engine.start();
	}
}
