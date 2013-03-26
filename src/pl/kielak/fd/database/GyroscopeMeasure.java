package pl.kielak.fd.database;

public class GyroscopeMeasure extends Measure{
	/**
	 * Record of gyroscope measure, stored in database
	 * Contains measures in 3 axis - X,Y,Z and time (ms)
	 */
	
	private final long time;
	private final float xSpeed;
	private final float ySpeed;
	private final float zSpeed;
	
	public GyroscopeMeasure(long time, float xSpeed, float ySpeed,
															float zSpeed)
	{
		this.time = time;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.zSpeed = zSpeed;
	}

	public long getTime() {
		return time;
	}

	public float getxSpeed() {
		return xSpeed;
	}

	public float getySpeed() {
		return ySpeed;
	}

	public float getzSpeed() {
		return zSpeed;
	}
	
	
}