package pl.kielak.fd.algorithms;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import android.util.Log;
import pl.kielak.fd.database.AccelerometerMeasure;
import pl.kielak.fd.database.Measure;
import pl.kielak.fd.Settings;


public class AccelerometerAlgorithm implements FallDetectionAlgorithm{
	/**
	 * Implementation of FallDetectionAlgorithm based on accelerometer.
	 * Algorithm detects sudden decrease of total acceleration (free float),
	 * peak (when person hit the ground) and count time without any move.
	 * Constants for every state can be changed.
	 * @author Michal Kielak
	 */
	
	private AccelerometerMeasure measure;
	private AccelerometerMeasure nextMeasure;
	
	private enum States {NORMAL, FREE_FLOAT, PEAK, LYING, FALL_DETECTED};
	
	private final float[] thresholds = {0f, 7f, 16f, 12f, 12f};
	private final float[] minDuration = {0f, 0.1f, 0f, 2f};
	private final float[] maxDuration = {Settings.ALGORITHM_WINDOW, 3f, 0.4f, 
												Settings.ALGORITHM_WINDOW};
	private States state;
	private int stateNo=0;
	private float currentStateTime = 0;
	private float smallest = 10f;
	
	public AccelerometerAlgorithm(){
		state = States.NORMAL;
	}
	
	@Override
	public boolean run(List<AccelerometerMeasure> measures) {
		Iterator<AccelerometerMeasure> i = measures.iterator();
		stateNo=0;
		currentStateTime = 0;
		state = States.NORMAL;
		
		try{
			measure = i.next();
		}catch( NoSuchElementException e ){
			return false;
		}

		while (i.hasNext()){
			nextMeasure = i.next();
			currentStateTime+=(nextMeasure.getTime()-measure.getTime())/1000f;
			
			if (measure.getTotalAccel() < smallest)
				smallest = measure.getTotalAccel();
			
			if ((stateNo==0 || stateNo==2 || stateNo==3) && 
					measure.getTotalAccel() < thresholds[stateNo+1] && 
					minDuration[stateNo] < currentStateTime && 
					currentStateTime < maxDuration[stateNo])
			{
				state = States.values()[++stateNo];
				Log.e("State:", state.toString());
				currentStateTime = 0;
				if (state==States.FALL_DETECTED)
					return true;
			}
			
			else if (stateNo !=0 && stateNo !=2 && stateNo !=3 &&
					measure.getTotalAccel() > thresholds[stateNo+1] && 
					minDuration[stateNo] < currentStateTime && 
					currentStateTime < maxDuration[stateNo])
			{
				state = States.values()[++stateNo];
				Log.e("State:", state.toString());
				currentStateTime = 0;
				if (state==States.FALL_DETECTED)
					return true;
			}
			
			else if(currentStateTime > maxDuration[stateNo])
			{
				state = States.NORMAL;
				stateNo = 0;
				currentStateTime = 0;
			}
			
			measure=nextMeasure;
		}
		smallest = 10f;
		return false;
	}
}
