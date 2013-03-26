package pl.kielak.fd.algorithms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import android.content.Context;
import android.os.Vibrator;
import android.widget.Toast;
import pl.kielak.fd.database.AccelerometerMeasure;
import pl.kielak.fd.database.Measure;
import pl.kielak.fd.Settings;


public class AccelerometerAlgorithm implements FallDetectionAlgorithm{

//	private boolean freeFall = false;
//	private double freeFallTime = 0;
//	private double lastFreeFallTime = 0;
//	private final double freeFallThreshold = 1;
//	private final double freeFallTimeThreshold = 0.2;
//	
//	private boolean peak = false;
//	private final double peakThreshold = 19;
//	private double fps;
	
	private AccelerometerMeasure measure;
	private AccelerometerMeasure nextMeasure;
	
	private enum States {NORMAL, FREE_FLOAT, PEAK, LYING, FALL_DETECTED};
	
	private float[] thresholds = {0f, 7f, 18f, 12f};
	private float[] minDuration = {0f, 0.2f, 0f, 2f};
	private float[] maxDuration = {Settings.ALGORITHM_WINDOW, 3f, 0.4f, 
													Settings.ALGORITHM_WINDOW};
	private States state;
	private float currentStateTime = 0;
	
	public AccelerometerAlgorithm(){
		state = States.NORMAL;
	}
	
	@Override
	public boolean run(List<AccelerometerMeasure> measures) {
		Iterator<AccelerometerMeasure> i = measures.iterator();
		
		try{
			measure = i.next();
		}catch( NoSuchElementException e ){
			return false;
		}
		
		for (; i.hasNext();){
			nextMeasure = i.next();
			currentStateTime+=(nextMeasure.getTime()-measure.getTime())/
															Settings.ONE_BILION;
			
			if (measure.getTotalAccel() > thresholds[state.ordinal()+1] && 
						minDuration[state.ordinal()] < currentStateTime && 
						currentStateTime < maxDuration[state.ordinal()])
			{
				state = States.values()[state.ordinal()+1];
				
				if (state==States.FALL_DETECTED)
					return true;
			}
		}
		return false;
	}
}
//	
//		currentFPS++;
//		if(System.currentTimeMillis() - start >= 1000) {
//		    fps = currentFPS;
//		    currentFPS = 0;
//		    start = System.currentTimeMillis();
//		}
//		
//		
//		//begining of free fall
//		if (values[3] < freeFallThreshold && !freeFall){
//			freeFall = true;
//			freeFallTime = 0;
//		
//		}
//		//free fall continuation
//		else if(values[3] < freeFallThreshold && freeFall){
//			freeFallTime+=1d/fps;
//		}
//		//end of free fall; counting time
//		else if(freeFall && values[3] > freeFallThreshold && values[3] < peakThreshold)
//		{
//			lastFreeFallTime = 1/fps;
//		}
//		//counting time from last free fall
//		else if(lastFreeFallTime > 0){
//			lastFreeFallTime += 1/fps;
//		}
//		
//		else if(values[3] > peakThreshold && freeFall){
//			peak = true;
//			freeFall = false;
//			lastFreeFallTime = 0;
//			Toast.makeText(getApplicationContext(), "Fall detected", Toast.LENGTH_LONG).show();
//			// Get instance of Vibrator from current Context
//			Vibrator mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//			 
//			// Vibrate for 300 milliseconds
//			mVibrator.vibrate(1000);
//			
//		}
//		
//		else if (peak && values[3] < peakThreshold){
//			peak = false;
//			freeFall = false;
//		}
//		return false;
//	}
//
//}