package pl.kielak.fd.database;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {
	/**
	 * DatabaseManager is responsible for all operations on db like insert
	 * measure data from sensors, read them or delete.
	 * Database has 2 tables
	 * table accelerometer
	 * TIME (int, PK) | X_ACCEL (real) | Y_ACCEL (real) | Z_ACCEL (real) 
	 * 													| TOTAL_ACCEL (real)
	 * 
	 * table gyroscope
	 * TIME (int, PK) | X_SPEED (real) | Y_SPEED (real) | Z_SPEED (real)
	 * @author Michal Kielak
	 */

    private static final int DATABASE_VERSION = 1;
    
    private static final String DATABASE_NAME = "measureStorage";
    
    private static final String TABLE_ACCELEROMETER = "accelerometer";
    private static final String TABLE_GYROSCOPE = "gyroscope";
    
    //Accelerometer fields
    private static final String KEY_ACCEL_TIME = "time";
    private static final String KEY_ACCEL_X = "x_accel";
    private static final String KEY_ACCEL_Y = "y_accel";
    private static final String KEY_ACCEL_Z = "z_accel";
    private static final String KEY_ACCEL_TOTAL = "total_accel";
    
    //Gyroscope fields
    private static final String KEY_GYRO_TIME = "time";
    private static final String KEY_GYRO_X = "x_speed";
    private static final String KEY_GYRO_Y = "y_speed";
    private static final String KEY_GYRO_Z = "z_speed";
    
    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ACCELEROMETER_TABLE = 
        		"CREATE TABLE " + TABLE_ACCELEROMETER 
        		+ " ( " + KEY_ACCEL_TIME + " INTEGER PRIMARY KEY, " 
        		+ KEY_ACCEL_X + " REAL, "
                + KEY_ACCEL_Y + " REAL, "
        		+ KEY_ACCEL_Z + " REAL, "
        		+ KEY_ACCEL_TOTAL + " REAL );";
        
        String CREATE_GYROSCOPE_TABLE = "CREATE TABLE "
        		+ TABLE_GYROSCOPE+ " ( "
        		+ KEY_GYRO_TIME + " INTEGER PRIMARY KEY, " 
        		+ KEY_GYRO_X + " REAL, "
                + KEY_GYRO_Y + " REAL, "
        		+ KEY_GYRO_Z + " REAL );";
        
        db.execSQL(CREATE_ACCELEROMETER_TABLE);
        db.execSQL(CREATE_GYROSCOPE_TABLE);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCELEROMETER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GYROSCOPE);
 
        // Create tables again
        onCreate(db);
    }
    
    
    public void addAccelMeasure(AccelerometerMeasure accelMeasure) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ACCEL_TIME, accelMeasure.getTime());
        values.put(KEY_ACCEL_X, accelMeasure.getxAccel());
        values.put(KEY_ACCEL_Y, accelMeasure.getyAccel());
        values.put(KEY_ACCEL_Z, accelMeasure.getzAccel());
        values.put(KEY_ACCEL_TOTAL, accelMeasure.getTotalAccel());

        db.insert(TABLE_ACCELEROMETER, null, values);
        db.close();
    }


    public ArrayList<AccelerometerMeasure> getAccelerometerMeasures(long time){
    	
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<AccelerometerMeasure> accelMeasures = 
        									new ArrayList<AccelerometerMeasure>();
 
        Cursor cursor = db.query(TABLE_ACCELEROMETER, new String[] 
        		{ KEY_ACCEL_TIME, KEY_ACCEL_X, KEY_ACCEL_Y, KEY_ACCEL_Z }, 
        		KEY_ACCEL_TIME + ">?", new String[]{ String.valueOf(time)},
        		null, null, null, null);
        
        if (cursor != null)
            cursor.moveToFirst();
        
        while (cursor.isAfterLast()==false){
        	accelMeasures.add(new AccelerometerMeasure(cursor.getInt(0),
                cursor.getFloat(1), cursor.getFloat(2), cursor.getFloat(3),
                cursor.getFloat(4)));
        }
        
        // return contact
        return accelMeasures;
    }
    
    
    public void addGyroMeasure(GyroscopeMeasure accelMeasure) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_GYRO_TIME, accelMeasure.getTime());
        values.put(KEY_GYRO_X, accelMeasure.getxSpeed());
        values.put(KEY_GYRO_Y, accelMeasure.getySpeed());
        values.put(KEY_GYRO_Z, accelMeasure.getzSpeed());

        db.insert(TABLE_GYROSCOPE, null, values);
        db.close();
    }
 

    public ArrayList<GyroscopeMeasure> getGyroscopeMeasures(int beginTime) {
    	
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<GyroscopeMeasure> gyroMeasures = 
        									new ArrayList<GyroscopeMeasure>();
 
        Cursor cursor = db.query(TABLE_GYROSCOPE, new String[] 
        		{ KEY_GYRO_TIME, KEY_GYRO_X, KEY_GYRO_Y, KEY_GYRO_Z }, 
        		KEY_GYRO_TIME + ">?", new String[]{ String.valueOf(beginTime)},
        		null, null, null, null);
        
        if (cursor != null)
            cursor.moveToFirst();
        
        while (cursor.isAfterLast()==false){
        	gyroMeasures.add(new GyroscopeMeasure(cursor.getInt(0),
                cursor.getFloat(1), cursor.getFloat(2), cursor.getFloat(3)));
        }
        
        // return contact
        return gyroMeasures;
    }
    
    public void deleteOldMeasures(long beginTime){
    	 SQLiteDatabase db = this.getWritableDatabase();

    	 db.rawQuery("DELETE FROM " + TABLE_ACCELEROMETER +" WHERE time < "
    			 			+String.valueOf(beginTime), null).moveToFirst() ;
    	 db.rawQuery("DELETE FROM " + TABLE_GYROSCOPE +" WHERE time < " 
    			 			+String.valueOf(beginTime), null).moveToFirst() ;
    	 db.close();
    }
    
}
