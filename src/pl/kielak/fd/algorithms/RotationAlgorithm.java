package pl.kielak.fd.algorithms;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import android.util.Log;

import pl.kielak.fd.database.RotationMeasure;

public class RotationAlgorithm{ // implements FallDetectionAlgorithm{
	
	/**
	 * Algorithm detects rotation of device. If rotation is greater then
	 * determined threshold - fall detection will be signalized.
	 * @author Michal Kielak
	 */
	
	//phone rotation threshold after fall
	private final float fallDetectionRotation = 60f;

	private RotationMeasure measure;
	private double currentRotation;
	private double xMaxRotation;
	private double yMaxRotation;
	private double zMaxRotation;

	public RotationAlgorithm(){}

	public boolean run(List<RotationMeasure> measures) {
		Iterator<RotationMeasure> i = measures.iterator();
		//table with min and max values of rotations 
		//in each axis:X, Y, Z {min,max}
		double[][] rotations = {{3f,-3f},{3f,-3f},{3f,-3f}};
		while (i.hasNext())
		{
			measure = i.next();
			if (measure.getxRotation() < rotations[0][0])
				rotations[0][0] = measure.getxRotation();
			else if (measure.getxRotation() > rotations[0][1])
				rotations[0][1] = measure.getxRotation();
			
			if (measure.getyRotation() < rotations[1][0])
				rotations[1][0] = measure.getyRotation();
			else if (measure.getyRotation() > rotations[1][1])
				rotations[1][1] = measure.getyRotation();
			
			if (measure.getzRotation() < rotations[2][0])
				rotations[2][0] = measure.getzRotation();
			else if (measure.getzRotation() > rotations[2][1])
				rotations[2][1] = measure.getzRotation();
		}
		xMaxRotation = rotations[0][1]-rotations[0][0];
		yMaxRotation = rotations[1][1]-rotations[1][0];
		zMaxRotation = rotations[2][1]-rotations[2][0];
		
		currentRotation = Math.max(Math.max(xMaxRotation, yMaxRotation),
																zMaxRotation);
		
		Log.e("RotationAlgorithm",Arrays.toString(rotations[0])+
				Arrays.toString(rotations[1])+Arrays.toString(rotations[2]));
		
		if (Math.toDegrees(currentRotation) > fallDetectionRotation)
			return true;
		
		return false;
	}
	
}