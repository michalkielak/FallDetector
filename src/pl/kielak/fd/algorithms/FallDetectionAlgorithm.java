package pl.kielak.fd.algorithms;

import java.util.List;

import pl.kielak.fd.database.Measure;

public interface FallDetectionAlgorithm{
	
	boolean run(List<Measure> measures);
	
}