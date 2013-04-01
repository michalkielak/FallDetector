package pl.kielak.fd.sensors;

import pl.kielak.fd.database.AccelerometerMeasure;
import pl.kielak.fd.database.DatabaseManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class AccelerometerListener implements SensorEventListener {
	/**
	 * Listener of accelerometer. It collects measures from accelerometer
	 * and put them into database.
	 * @author Michal Kielak
	 */
	
	private final DatabaseManager db;
	
	public AccelerometerListener(DatabaseManager db){
		this.db = db;
	}
	
 	@Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
 		
    }
    
    @Override
    public void onSensorChanged(SensorEvent event) {
    	long currentTime = System.currentTimeMillis();
	    float totalAccel = (float)Math.sqrt(Math.pow(event.values[0],2.0)
	    								+Math.pow(event.values[1],2.0)
	    								+Math.pow(event.values[2],2.0));
	    AccelerometerMeasure mMeasure = new AccelerometerMeasure(
	    		currentTime, event.values[0], event.values[1], 
	    		event.values[2], totalAccel);
	    
	    db.addAccelMeasure(mMeasure);
    }
	    
}
