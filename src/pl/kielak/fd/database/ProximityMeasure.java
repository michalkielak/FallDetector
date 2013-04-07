package pl.kielak.fd.database;

public class ProximityMeasure extends Measure{
	/**
	 * Record of proximity, stored in database
	 * Contains proximity distance and time of measure
	 */
	
	private final long time;
	private final double distance;
	
	public ProximityMeasure(long time, double distance)
	{
		this.time = time;
		this.distance = distance;
	}

	public long getTime() {
		return time;
	}

	public double getDistance() {
		return distance;
	}
	
}