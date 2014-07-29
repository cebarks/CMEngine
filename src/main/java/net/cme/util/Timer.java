package net.cme.util;

public class Timer {

	private final long startTime;

	private Timer() {
		startTime = System.currentTimeMillis();
	}

	public static Timer start() {
		return new Timer();
	}
	
	public long stop() {
		return System.currentTimeMillis() - startTime;
	}
}
