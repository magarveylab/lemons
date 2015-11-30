package lemons;

import java.io.IOException;

import org.openscience.cdk.exception.CDKException;

import lemons.experiments.Bootstrapper;
import lemons.experiments.impl.PeptideExperiment;
import lemons.experiments.impl.PolyketideExperiment;
import lemons.util.exception.FingerprintGenerationException;
import lemons.util.exception.PolymerGenerationException;

public class Main {

	public static void main(String[] args) throws CDKException,
			PolymerGenerationException, IOException,
			FingerprintGenerationException {
		// set dir
		if (args.length > 0)
			Config.BASE_DIRECTORY = args[0];
		
		runPaperExperiments();
		
	//	Config.REACTIONS.put(ReactionTypes.CYCLIZATION, 1);
	//	Bootstrapper.bootstrap("Peptides_linear_1swap", new PeptideExperiment(10, 1), 2);
		
	//	PolyketideExperiment lpt1 = new PolyketideExperiment(100, 3);
	//	Bootstrapper.bootstrap(lpt1, 1);
	}

	public static void runPaperExperiments() throws CDKException,
			PolymerGenerationException, IOException,
			FingerprintGenerationException {
		// test linear peptides with 1, 2, 3, 4, and 5 swaps 
		Bootstrapper.bootstrap("Peptides_linear_100_1", new PeptideExperiment(1));
		Bootstrapper.bootstrap("Peptides_linear_100_2", new PeptideExperiment(2));
		Bootstrapper.bootstrap("Peptides_linear_100_3", new PeptideExperiment(3));
		Bootstrapper.bootstrap("Peptides_linear_100_4", new PeptideExperiment(4));
		Bootstrapper.bootstrap("Peptides_linear_100_5", new PeptideExperiment(5));

		// test linear polyketides with 1, 2, 3, 4, and 5 swaps 
		Bootstrapper.bootstrap("Polyketides_linear_100_1", new PolyketideExperiment(1));
		Bootstrapper.bootstrap("Polyketides_linear_100_2", new PolyketideExperiment(2));
		Bootstrapper.bootstrap("Polyketides_linear_100_3", new PolyketideExperiment(3));
		Bootstrapper.bootstrap("Polyketides_linear_100_4", new PolyketideExperiment(4));
		Bootstrapper.bootstrap("Polyketides_linear_100_5", new PolyketideExperiment(5));

		// test cyclic/branched peptides with 1, 2, 3, 4, and 5 swaps 
	/*	Config.REACTIONS.put(ReactionTypes.CYCLIZATION, 1);
		Bootstrapper.bootstrap(new PeptideExperiment(100, 1), 1000);
		Bootstrapper.bootstrap(new PeptideExperiment(100, 2), 1000);
		Bootstrapper.bootstrap(new PeptideExperiment(100, 3), 1000);
		Bootstrapper.bootstrap(new PeptideExperiment(100, 4), 1000);
		Bootstrapper.bootstrap(new PeptideExperiment(100, 5), 1000);
		Bootstrapper.bootstrap(new PolyketideExperiment(100, 1), 1000);
		Bootstrapper.bootstrap(new PolyketideExperiment(100, 2), 1000);
		Bootstrapper.bootstrap(new PolyketideExperiment(100, 3), 1000);
		Bootstrapper.bootstrap(new PolyketideExperiment(100, 4), 1000);
		Bootstrapper.bootstrap(new PolyketideExperiment(100, 5), 1000);*/

		// test tailoring reactions
		
		// O-glycosylation
		// oxazole/thiazole formation 
		// N-methylation 
		// halogenation 
		
	}
	
}
