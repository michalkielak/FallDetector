package pl.kielak.fd.algorithms;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import pl.kielak.fd.FallAlarm;
import pl.kielak.fd.Settings;
import pl.kielak.fd.database.AccelerometerMeasure;
import pl.kielak.fd.database.DatabaseManager;
import pl.kielak.fd.database.GyroscopeMeasure;

public class AlgorithmsManager {
	
	private final AccelerometerAlgorithm mAccelerometerAlgorithm;
	private final GyroscopeAlgorithm mGyroscopeAlgorithm;
	private final DatabaseManager db;
	private final Timer algorithmSchedule;
	private final AlgorithmsTask mAlgorithmsTask;
	private final FallAlarm mFallAlarm;
	
	private List<AccelerometerMeasure> mAccelerometerMeasures;
	private List<GyroscopeMeasure> mGyroscopeMeasures;
	private long algorithmTimeStart;
	private boolean fallDetected;
	
	public AlgorithmsManager(DatabaseManager db, FallAlarm fallAlarm){
		mAccelerometerAlgorithm = new AccelerometerAlgorithm();
		mGyroscopeAlgorithm = new GyroscopeAlgorithm();
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
			long algorithmTimeStart = (System.currentTimeMillis()
						*Settings.ONE_MILION) - (Settings.ALGORITHM_WINDOW
						*Settings.ONE_BILION);
			mAccelerometerMeasures = db.getAccelerometerMeasures(
															algorithmTimeStart);
			
			fallDetected = mAccelerometerAlgorithm.run(mAccelerometerMeasures);
			if (fallDetected)
				mFallAlarm.performAlarm();
		}
		
	}
	
}