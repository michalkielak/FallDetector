package pl.kielak.fd.database;

public class RotationMeasure extends Measure{
	/**
	 * Record of rotation, stored in database
	 * Contains measures in 3 axis - X,Y,Z and time (ms)
	 */
	
	private final long time;
	private final double xRotation;
	private final double yRotation;
	private final double zRotation;
	
	public RotationMeasure(long time, double d, double e,
															double f)
	{
		this.time = time;
		this.xRotation = d;
		this.yRotation = e;
		this.zRotation = f;
	}

	public long getTime() {
		return time;
	}

	public double getxRotation() {
		return xRotation;
	}

	public double getyRotation() {
		return yRotation;
	}

	public double getzRotation() {
		return zRotation;
	}
	
	
}