package pl.kielak.fd;

import android.content.Context;
import android.os.Vibrator;

public class FallAlarm{
	
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