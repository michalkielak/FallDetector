package pl.kielak.fd;

public class Settings{
	/**
	 * Settings class. Used to make code more readable and collect major
	 * constants in one place.
	 * @author Michal Kielak
	 */
	
	//used when converting nanoseconds to seconds
	public static long ONE_BILION = 1000000000;
	
	//used when converting miliseconds to seconds
	public static long ONE_MILION = 1000000;
	
	//window of time in which algorithm analyze data from sensors
	public static int ALGORITHM_WINDOW = 5;
	
	//this variable determine how often (in seconds) algorithms computing 
	//														should be performed
	public static float ALGORITHM_CYCLE = 1f;
}