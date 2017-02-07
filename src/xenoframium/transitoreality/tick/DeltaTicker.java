package xenoframium.transitoreality.tick;

public class DeltaTicker {
	private final Tickable ticker;
	private final long tickDelayMillis;
	
	private long lastTickTime;
	
	public DeltaTicker(long tickDelayMillis, Tickable ticker) {
		this.tickDelayMillis = tickDelayMillis;
		this.ticker = ticker;
		lastTickTime = System.currentTimeMillis();
	}
	
	public void reset() {
		lastTickTime = System.currentTimeMillis();
	}
	
	public void update() {
		long currentTime = System.currentTimeMillis();
		long delta = currentTime - lastTickTime;
		long ticksToRun = delta / tickDelayMillis;
		
		if (ticksToRun > 0) {
			lastTickTime = lastTickTime + ticksToRun * tickDelayMillis;
			for (int i = 0; i < ticksToRun; i++) {
				ticker.tick();
			}
		}
	}
}
