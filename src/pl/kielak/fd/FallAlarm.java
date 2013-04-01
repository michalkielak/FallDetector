package pl.kielak.fd;

import android.content.Context;
import android.os.Vibrator;

public class FallAlarm{
	/**
	 * Defines action to perform when fall is detected.
	 * @author Michal Kielak
	 */
	
	private final Context mContext;
	private final Vibrator v;
	
	public FallAlarm(Context context)
	{
		mContext = context;
		v = (Vibrator) mContext.getSystemService(mContext.VIBRATOR_SERVICE);
	}
	
	public void performAlarm(){
		v.vibrate(300);
	}
}