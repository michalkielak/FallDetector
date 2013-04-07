package pl.kielak.fd.algorithms;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import pl.kielak.fd.FallAlarm;
import pl.kielak.fd.Settings;
import pl.kielak.fd.database.AccelerometerMeasure;
import pl.kielak.fd.database.DatabaseManager;
import pl.kielak.fd.database.ProximityMeasure;
import pl.kielak.fd.database.RotationMeasure;

public class AlgorithmsManager {
	/**
	 * Algorithms manager is responsible for activating algorithms, collecting 
	 * appropriate data from database and deliver them to algorithms.
	 * If all algorithms return fall detection, FallAlarm object will be
	 * called.
	 * @author Michal Kielak
	 */

	private final AccelerometerAlgorithm mAccelerometerAlgorithm;
	private final RotationAlgorithm mRotationAlgorithm;
	private final ProximityAlgorithm mProximityAlgorithm;
	private final DatabaseManager db;
	private final Timer algorithmSchedule;
	private final AlgorithmsTask mAlgorithmsTask;
	private final FallAlarm mFallAlarm;
	
	private List<AccelerometerMeasure> mAccelerometerMeasures;
	private List<RotationMeasure> mRotationMeasures;
	private List<ProximityMeasure> mProximityMeasures;
	private long algorithmTimeStart;
	private boolean fallDetected;
	
	public AlgorithmsManager(DatabaseManager db, FallAlarm fallAlarm){
		mAccelerometerAlgorithm = new AccelerometerAlgorithm();
		mRotationAlgorithm = new RotationAlgorithm();
		mProximityAlgorithm = new ProximityAlgorithm();
		mAlgorithmsTask = new AlgorithmsTask();
		mFallAlarm = fallAlarm;
		this.db = db;
		algorithmSchedule = new Timer();
		algorithmSchedule.schedule(mAlgorithmsTask, 0, 
										(long)Settings.ALGORITHM_CYCLE*1000);
	}

	private class AlgorithmsTask extends TimerTask{

		@Override
		public void run() {
			if (fallDetected)
				cancel();
			algorithmTimeStart = System.currentTimeMillis() - 
												1000*Settings.ALGORITHM_WINDOW;
			
			mProximityMeasures = db.getProximityMeasures(algorithmTimeStart);
			fallDetected = mProximityAlgorithm.run(mProximityMeasures);
			
			if (fallDetected){
				mAccelerometerMeasures = db.getAccelerometerMeasures(
															algorithmTimeStart);
				fallDetected = mAccelerometerAlgorithm.run(
														mAccelerometerMeasures);
			}
			
			if (fallDetected){
				mRotationMeasures = db.getRotationMeasures(algorithmTimeStart);
				fallDetected = mRotationAlgorithm.run(mRotationMeasures);
			}
			
		    db.deleteOldMeasures(System.currentTimeMillis() - 15*1000);
		    
			if (fallDetected)
				mFallAlarm.performAlarm();
		}

	}
	
}