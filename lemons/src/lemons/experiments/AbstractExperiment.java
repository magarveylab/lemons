package lemons.experiments;

import lemons.interfaces.IExperiment;

public abstract class AbstractExperiment implements IExperiment {

	protected int numSamples;
	
	public AbstractExperiment(int numSamples) {
		this.numSamples = numSamples;
	}
	
}
