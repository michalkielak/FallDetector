package pl.kielak.fd;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.util.Log;

public class FallAlarm{
	/**
	 * Defines action to perform when fall is detected.
	 * @author Michal Kielak
	 */

	private final Context mContext;
	private final Vibrator v;
	private final Timer alarmTimer;
	private final AlarmTask mAlarmTask;
	private final LocationListener mLocationListener;
	private LocationManager mLocationManager;
	private Location mLocation = null;
	
	private String bestProvider;

	public FallAlarm(Context context)
	{
		mAlarmTask = new AlarmTask();
		alarmTimer = new Timer();
		mContext = context;
		v = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
		mLocationManager = (LocationManager)mContext.getSystemService(
													Context.LOCATION_SERVICE);
		
		mLocationListener = new LocationListener(){
			@Override
			public void onLocationChanged(Location location) {
				mLocation = location;
			}
			@Override
			public void onProviderDisabled(String arg0) {}
			@Override
			public void onProviderEnabled(String arg0) {}
			@Override
			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {}
		};
	}
	
	public void performAlarm(){
		getLocation();
		alarmTimer.schedule(mAlarmTask, Settings.NOTIFICATION_SEND_TIME*1000);
		Uri notification = RingtoneManager.getDefaultUri(
											RingtoneManager.TYPE_NOTIFICATION);
		Ringtone r = RingtoneManager.getRingtone(mContext, notification);
		r.play();
		v.vibrate(300);
	}
	
	/**
	 * getLocation method is called when fall is detected. First, it returns
	 * last known location but during alarm it has some time (defined in
	 * settings) to get more accurate position.
	 */
	private void getLocation()
	{
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.NO_REQUIREMENT);
		criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setSpeedRequired(false);
		criteria.setCostAllowed(true);
		
		bestProvider = mLocationManager.getBestProvider(criteria, true);
		mLocation = mLocationManager.getLastKnownLocation(bestProvider);
		try{
		Looper.prepare();
		}catch(RuntimeException e){
			
		}
		mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
	}
	
	private class AlarmTask extends TimerTask{

		@Override
		public void run() {
			mLocationManager.removeUpdates(mLocationListener);
			
			String smsMessage = "Wykryto upadek! Zobacz miejsce upadku: ";
			smsMessage+= "http://maps.google.com/maps?z=18&t=m&q=loc:";
			smsMessage+= String.valueOf(mLocation.getLatitude()) + "+";
			smsMessage+= String.valueOf(mLocation.getLongitude());
			
	        SmsManager smsManager = SmsManager.getDefault();
//	    	smsManager.sendTextMessage(Settings.ALARM_PHONE_NUMBER, null, smsMessage, null, null);

			Log.i("Sended sms ", smsMessage);
			
		}
	}
}