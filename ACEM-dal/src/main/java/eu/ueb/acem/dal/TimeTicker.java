package eu.ueb.acem.dal;

public class TimeTicker {

	public static long tick() {
		return System.currentTimeMillis() / 1000L;
	}
	
}
