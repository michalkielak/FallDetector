package pl.kielak.fd.sensors;

import pl.kielak.fd.database.DatabaseManager;
import pl.kielak.fd.database.GyroscopeMeasure;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class GyroscopeListener implements SensorEventListener {
	/**
	 * Listener of gyroscope. It collect every measure from gyroscope
	 * and put it into table gyroscope in DB.
	 * @author Michal Kielak
	 */
	
	private final DatabaseManager db;
	
	public GyroscopeListener(DatabaseManager db){
		this.db = db;
	}
	
 	@Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    	// can be safely ignored for this demo
    }
    
    @Override
    public void onSensorChanged(SensorEvent event) {
    	long currentTime = System.currentTimeMillis();
	    GyroscopeMeasure mMeasure = new GyroscopeMeasure(
	    		currentTime, event.values[0], event.values[1], 
	    		event.values[2]);
	    
	    db.addGyroMeasure(mMeasure);
    }
	    
}
