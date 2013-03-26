package pl.kielak.fd.database;

public class AccelerometerMeasure extends Measure{
	/**
	 * Record of accelerometer measure, stored in database
	 * Contains measures in 3 axis - X,Y,Z and time (ms)
	 */
	
	private final long time;
	private final float xAccel;
	private final float yAccel;
	private final float zAccel;
	private final float totalAccel;
	
	public AccelerometerMeasure(long time, float xAccel, float yAccel,
											float zAccel, float totalAccel)
	{
		this.time = time;
		this.xAccel = xAccel;
		this.yAccel = yAccel;
		this.zAccel = zAccel;
		this.totalAccel = totalAccel;
	}

	public long getTime() {
		return time;
	}

	public float getxAccel() {
		return xAccel;
	}

	public float getyAccel() {
		return yAccel;
	}

	public float getzAccel() {
		return zAccel;
	}
	
	public float getTotalAccel(){
		return totalAccel;
	}
}