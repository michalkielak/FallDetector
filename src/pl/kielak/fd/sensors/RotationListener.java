package pl.kielak.fd.sensors;

import pl.kielak.fd.database.DatabaseManager;
import pl.kielak.fd.database.RotationMeasure;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class RotationListener implements SensorEventListener {
	/**
	 * Listener of gyroscope. It collect every measure from gyroscope
	 * and put it into table gyroscope in DB.
	 * @author Michal Kielak
	 */
	
	private final DatabaseManager db;
	private final SensorManager mSensorManager;
	
	public RotationListener(DatabaseManager db, SensorManager sensorManager){
		this.db = db;
		this.mSensorManager = sensorManager;
	}
	
 	@Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    	// can be safely ignored for this demo
    }
    
    @Override
    public void onSensorChanged(SensorEvent event) {
    	long currentTime = System.currentTimeMillis();
    	
	    RotationMeasure mMeasure = new RotationMeasure(currentTime, 
	    		event.values[0], event.values[1],
	    		event.values[2]);
	    
//	    db.addRotationMeasure(mMeasure);
    }

}
