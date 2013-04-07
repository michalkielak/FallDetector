package pl.kielak.fd;

import java.util.Vector;

import pl.kielak.fd.algorithms.AlgorithmsManager;
import pl.kielak.fd.database.DatabaseManager;
import pl.kielak.fd.sensors.AccelerometerListener;
import pl.kielak.fd.sensors.ProximityListener;
import pl.kielak.fd.sensors.RotationListener;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;

public class FallDetectorActivity extends Activity{

	private SensorManager mSensorManager;
	private AccelerometerListener mAccelerometerListener;
	private RotationListener mRotationListener;
	private ProximityListener mProximityListener;
	private AlgorithmsManager mAlgorithmsManager;
	private FallAlarm mFallAlarm;
	
	private DatabaseManager db;
	
	public int currentFPS = 0;
    public long start = 0;
    public int fps = 0;
    double lastValue = 5d;

	private Sensor mAccelerometer;
	private Sensor mGyroscope;
	private Sensor mProximity;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

    	db = new DatabaseManager(this.getApplicationContext());
    	mFallAlarm = new FallAlarm(this.getApplicationContext());
    	mAlgorithmsManager = new AlgorithmsManager(db, mFallAlarm);
    	mAccelerometerListener = new AccelerometerListener(db);
    	mProximityListener = new ProximityListener(db);

        mSensorManager = (SensorManager) getSystemService(
        												Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(
        											Sensor.TYPE_ACCELEROMETER);

        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorManager.registerListener(mAccelerometerListener, mAccelerometer,
        									SensorManager.SENSOR_DELAY_GAME);

        mSensorManager.registerListener(mProximityListener, mProximity,
        									SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onResume() {
    	super.onResume();
        mSensorManager.registerListener(mAccelerometerListener, mAccelerometer,
				SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(mRotationListener, mGyroscope,
				SensorManager.SENSOR_DELAY_GAME);
    }
    
    protected void onPause() {
    	super.onPause();
//    	mSensorManager.unregisterListener(mAccelerometerListener);
    }

}