package dev.cworldstar.hotbarpets.impl;

/**
 * Holds information for the {@link TimedFeeder}. Effectively uses the same
 * implementation as message delay.
 * @author cworldstar
 *
 */
public class FeedingInformation {
	private long lastFeedTime = System.currentTimeMillis();
	private long nextFeedTime = 0L;
	
	/**
	 * Creates a new instance of FeedingInformation with a delay of T/1000, where
	 * T is the delay in milliseconds.
	 * @param delayInSeconds The delay in seconds.
	 */
	public FeedingInformation(int delayInSeconds) {
		nextFeedTime = lastFeedTime + (delayInSeconds * 1000);
	}
	
	public boolean isFeedTime() {
		return System.currentTimeMillis() >= nextFeedTime;
	}
	
	/**
	 * Does not change nextFeedTime to 0, insteads resets it to 
	 * {@code #lastFeedTime + (delayInSeconds * 1000)}
	 * Equal to {@code new FeedingInformation(int delay)}, except 
	 * it does not create a new instance.
	 * @param delayInSeconds The delay in seconds.
	 */
	public void reset(int delayInSeconds) {
		lastFeedTime = System.currentTimeMillis();
		nextFeedTime = lastFeedTime + (delayInSeconds * 1000);
	}

	/**
	 * 
	 * @return The remaining ticks before the next feeding operation
	 * can be performed.
	 */
	public int getRemainingTicks() {
		return (int) (nextFeedTime - System.currentTimeMillis()) / 1000;
	}
}
