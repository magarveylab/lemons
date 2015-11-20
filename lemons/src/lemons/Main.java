package lemons;

import java.io.IOException;
import org.openscience.cdk.exception.CDKException;

import lemons.experiments.Bootstrapper;
import lemons.experiments.PeptideExperiment;
import lemons.util.exception.FingerprintGenerationException;
import lemons.util.exception.PolymerGenerationException;

public class Main {

	public static void main(String[] args) throws CDKException,
			PolymerGenerationException, IOException,
			FingerprintGenerationException {
		// test linear peptides with 1 swap 
		PeptideExperiment lpt1 = new PeptideExperiment(10);
		Bootstrapper.bootstrap(lpt1, 1);
		
		// now bootstrap 
		Bootstrapper.bootstrap(lpt1, 10);
	}

}
