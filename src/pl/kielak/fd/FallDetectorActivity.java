package pl.kielak.fd;

import java.util.Vector;

import pl.kielak.fd.algorithms.AlgorithmsManager;
import pl.kielak.fd.database.DatabaseManager;
import pl.kielak.fd.sensors.AccelerometerListener;
import pl.kielak.fd.sensors.GyroscopeListener;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FallDetectorActivity extends Activity{

	private SensorManager mSensorManager;
	private AccelerometerListener mAccelerometerListener;
	private GyroscopeListener mGyroscopeListener;
	private AlgorithmsManager mAlgorithmsManager;
	private FallAlarm mFallAlarm;
	
	private DatabaseManager db;
	
	public int currentFPS = 0;
    public long start = 0;
    public int fps = 0;
    double lastValue = 5d;

	private Sensor mAccelerometer;
	private Sensor mGyroscope;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
  
    	db = new DatabaseManager(this.getApplicationContext());
    	mFallAlarm = new FallAlarm(this.getApplicationContext());
    	
    	mAccelerometerListener = new AccelerometerListener(db);
    	mGyroscopeListener = new GyroscopeListener(db);
        mSensorManager = (SensorManager) getSystemService(
        												Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(
        											Sensor.TYPE_ACCELEROMETER);
        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorManager.registerListener(mAccelerometerListener, mAccelerometer,
        									SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(mGyroscopeListener, mGyroscope,
        									SensorManager.SENSOR_DELAY_GAME);
        
        mAlgorithmsManager = new AlgorithmsManager(db, mFallAlarm);
        
    }
    
    protected void onResume() {
    	super.onResume();
        mSensorManager.registerListener(mAccelerometerListener, mAccelerometer,
				SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(mGyroscopeListener, mGyroscope,
				SensorManager.SENSOR_DELAY_GAME);
    }
    
    protected void onPause() {
    	super.onPause();
//    	mSensorManager.unregisterListener(mAccelerometerListener);
    }
    
   
    
    
    
}