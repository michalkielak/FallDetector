package pl.kielak.fd.sensors;

import pl.kielak.fd.database.DatabaseManager;
import pl.kielak.fd.database.ProximityMeasure;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class ProximityListener implements SensorEventListener {
	/**
	 * Proximity listner is responsible for collecting events from Proximity
	 * Sensor. It returns distance from an object (in centimeters).
	 * @author Michal Kielak
	 */
	
	private final DatabaseManager db;
	
	public ProximityListener(DatabaseManager db){
		this.db = db;
	}

 	@Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
 		
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
    	long currentTime = System.currentTimeMillis();
	    ProximityMeasure mMeasure = new ProximityMeasure(currentTime,
	    													event.values[0]);
	    db.addProximityMeasure(mMeasure);
    }
	    
}
