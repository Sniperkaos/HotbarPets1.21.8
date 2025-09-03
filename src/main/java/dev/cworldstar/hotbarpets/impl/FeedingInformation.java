package dev.cworldstar.hotbarpets.impl;

public class FeedingInformation {
	private long lastFeedTime = System.currentTimeMillis();
	private long nextFeedTime = 0L;
	
	public FeedingInformation(int delayInSeconds) {
		nextFeedTime = lastFeedTime + (delayInSeconds * 1000);
	}
	
	public boolean isFeedTime() {
		return System.currentTimeMillis() >= nextFeedTime;
	}
	
	public void reset(int delayInSeconds) {
		lastFeedTime = System.currentTimeMillis();
		nextFeedTime = lastFeedTime + (delayInSeconds * 1000);
	}
}
