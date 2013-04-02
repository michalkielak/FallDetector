package pl.kielak.fd.database;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {
	/**
	 * DatabaseManager is responsible for all operations on db like inserting
	 * measures data, reading them or deleting.
	 * Database has 2 tables
	 * table accelerometer
	 * TIME (int, PK) | X_ACCEL (real) | Y_ACCEL (real) | Z_ACCEL (real) 
	 * 													| TOTAL_ACCEL (real)
	 * 
	 * table rotation
	 * TIME (int, PK) | X_ROT (real) | Y_ROT (real) | Z_ROT (real)
	 * @author Michal Kielak
	 */

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "measureStorage";
    
    private static final String TABLE_ACCELEROMETER = "accelerometer";
    private static final String TABLE_ROTATION = "rotation";
    
    //Accelerometer fields
    private static final String KEY_ACCEL_TIME = "time";
    private static final String KEY_ACCEL_X = "x_accel";
    private static final String KEY_ACCEL_Y = "y_accel";
    private static final String KEY_ACCEL_Z = "z_accel";
    private static final String KEY_ACCEL_TOTAL = "total_accel";
    
    //Rotation fields
    private static final String KEY_ROT_TIME = "time";
    private static final String KEY_ROT_X = "x_rotation";
    private static final String KEY_ROT_Y = "y_rotation";
    private static final String KEY_ROT_Z = "z_rotation";
    
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
        		+ TABLE_ROTATION+ " ( "
        		+ KEY_ROT_TIME + " INTEGER PRIMARY KEY, " 
        		+ KEY_ROT_X + " REAL, "
                + KEY_ROT_Y + " REAL, "
        		+ KEY_ROT_Z + " REAL );";
        
        db.execSQL(CREATE_ACCELEROMETER_TABLE);
        db.execSQL(CREATE_GYROSCOPE_TABLE);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCELEROMETER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROTATION);
 
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
    }


    public ArrayList<AccelerometerMeasure> getAccelerometerMeasures(long time){
    	
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<AccelerometerMeasure> accelMeasures = 
        								new ArrayList<AccelerometerMeasure>();
 
        Cursor cursor = db.query(TABLE_ACCELEROMETER, new String[] 
        		{ KEY_ACCEL_TIME, KEY_ACCEL_X, KEY_ACCEL_Y, KEY_ACCEL_Z,
        		KEY_ACCEL_TOTAL}, KEY_ACCEL_TIME + ">?", 
        		new String[]{ String.valueOf(time)}, null, null, null, null);
        
        if (cursor != null)
            cursor.moveToFirst();

        while (cursor.moveToNext()){
        	accelMeasures.add(new AccelerometerMeasure(cursor.getLong(0),
                cursor.getFloat(1), cursor.getFloat(2), cursor.getFloat(3),
                cursor.getFloat(4)));
        }
        cursor.close();
        return accelMeasures;
    }
    
    
    public void addRotationMeasure(RotationMeasure accelMeasure) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_ROT_TIME, accelMeasure.getTime());
        values.put(KEY_ROT_X, accelMeasure.getxRotation());
        values.put(KEY_ROT_Y, accelMeasure.getyRotation());
        values.put(KEY_ROT_Z, accelMeasure.getzRotation());

        db.insert(TABLE_ROTATION, null, values);
    }
 

    public ArrayList<RotationMeasure> getRotationMeasures(long beginTime) {
    	
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<RotationMeasure> rotationMeasures = 
        									new ArrayList<RotationMeasure>();
 
        Cursor cursor = db.query(TABLE_ROTATION, new String[] 
        		{ KEY_ROT_TIME, KEY_ROT_X, KEY_ROT_Y, KEY_ROT_Z }, 
        		KEY_ROT_TIME + ">?", new String[]{ String.valueOf(beginTime)},
        		null, null, null, null);
        
        if (cursor != null)
            cursor.moveToFirst();
        
        while (cursor.moveToNext()){
        	rotationMeasures.add(new RotationMeasure(cursor.getLong(0),
                cursor.getFloat(1), cursor.getFloat(2), cursor.getFloat(3)));
        }
        
        cursor.close();
        return rotationMeasures;
    }
    
    public void deleteOldMeasures(long beginTime){
    	 SQLiteDatabase db = this.getWritableDatabase();

    	 db.rawQuery("DELETE FROM " + TABLE_ACCELEROMETER +" WHERE time < "
    			 			+String.valueOf(beginTime), null).moveToFirst() ;
    	 db.rawQuery("DELETE FROM " + TABLE_ROTATION +" WHERE time < " 
    			 			+String.valueOf(beginTime), null).moveToFirst() ;
    }
    
}
