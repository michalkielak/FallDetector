package pl.kielak.fd.algorithms;

import java.util.Iterator;
import java.util.List;

import android.util.Log;

import pl.kielak.fd.database.ProximityMeasure;


public class ProximityAlgorithm{
	/**
	 * Proximity algorithm checks whether any object is near phone.
	 * In particular this algorithm is implemented to find out if phone is 
	 * inside pocket.
	 * @author Michal Kielak
	 */
	
	private ProximityMeasure measure;
	
	public ProximityAlgorithm(){}
	
	public boolean run(List<ProximityMeasure> measures) {
		Iterator<ProximityMeasure> i = measures.iterator();

		while (i.hasNext()){
			measure = i.next();
			Log.e("Proximity algorithm", String.valueOf(measure.getDistance()));
			if (measure.getDistance() > 0)
				return false;
		}
		return true;
	}
}
