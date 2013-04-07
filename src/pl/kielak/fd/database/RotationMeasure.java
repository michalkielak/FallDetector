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
	
	public RotationMeasure(long time, double xRotation, double yRotation,
															double zRotation)
	{
		this.time = time;
		this.xRotation = xRotation;
		this.yRotation = yRotation;
		this.zRotation = zRotation;
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