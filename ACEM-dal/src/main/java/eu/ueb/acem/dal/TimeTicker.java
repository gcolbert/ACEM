package eu.ueb.acem.dal;

public class TimeTicker {

	public static Long tick() {
		return System.currentTimeMillis() / 1000;
	}
	
}
