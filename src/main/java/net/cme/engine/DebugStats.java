package net.cme.engine;

public class DebugStats {
	public final CMEngine engine;

	public int fps;

	public float delta;

	public long frameTime;

	public DebugStats(CMEngine engine) {
		this.engine = engine;
	}
}
