package lemons;

import java.io.IOException;

import org.openscience.cdk.exception.CDKException;

import lemons.enums.ReactionTypes;
import lemons.enums.monomers.ProteinogenicAminoAcids;
import lemons.enums.monomers.PolyketideMonomers;
import lemons.experiments.Bootstrapper;
import lemons.experiments.Experiment;
import lemons.util.exception.FingerprintGenerationException;
import lemons.util.exception.PolymerGenerationException;

public class Main {

	public static void main(String[] args) throws CDKException,
			PolymerGenerationException, IOException,
			FingerprintGenerationException {
		// set dir
		if (args.length > 0)
			Config.BASE_DIRECTORY = args[0];

		Config.INITIAL_MONOMERS = ProteinogenicAminoAcids.values();
		Config.SWAP_MONOMERS = ProteinogenicAminoAcids.values();
		Config.NUM_MONOMER_SWAPS = 1;
//		Config.INITIAL_REACTIONS.put(ReactionTypes.CYCLIZATION, 1.0d);
		Bootstrapper.bootstrap(new Experiment());
		
//		runPaperExperiments();
	}

	public static void runPaperExperiments() throws CDKException,
			PolymerGenerationException, IOException,
			FingerprintGenerationException {
		// test linear, proteinogenic peptides with 1, 2, 3, 4, and 5 swaps 
		Config.INITIAL_MONOMERS = ProteinogenicAminoAcids.values();
		Config.SWAP_MONOMERS = ProteinogenicAminoAcids.values();
		Config.NUM_MONOMER_SWAPS = 1;
		Bootstrapper.bootstrap("Peptides_linear_100_1", new Experiment());

		Config.NUM_MONOMER_SWAPS = 2;
		Bootstrapper.bootstrap("Peptides_linear_100_2", new Experiment());

		Config.NUM_MONOMER_SWAPS = 3;
		Bootstrapper.bootstrap("Peptides_linear_100_3", new Experiment());
		
		Config.NUM_MONOMER_SWAPS = 4;
		Bootstrapper.bootstrap("Peptides_linear_100_4", new Experiment());

		Config.NUM_MONOMER_SWAPS = 5;
		Bootstrapper.bootstrap("Peptides_linear_100_5", new Experiment());

		// test linear polyketides with 1, 2, 3, 4, and 5 swaps 
		Config.INITIAL_MONOMERS = PolyketideMonomers.values();
		Config.SWAP_MONOMERS = PolyketideMonomers.values();
		Config.NUM_MONOMER_SWAPS = 1;
		Bootstrapper.bootstrap("Polyketides_linear_100_1", new Experiment());
		
		Config.NUM_MONOMER_SWAPS = 2;
		Bootstrapper.bootstrap("Polyketides_linear_100_2", new Experiment());
		
		Config.NUM_MONOMER_SWAPS = 3;
		Bootstrapper.bootstrap("Polyketides_linear_100_3", new Experiment());

		Config.NUM_MONOMER_SWAPS = 4;
		Bootstrapper.bootstrap("Polyketides_linear_100_4", new Experiment());
		
		Config.NUM_MONOMER_SWAPS = 5;
		Bootstrapper.bootstrap("Polyketides_linear_100_5", new Experiment());

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
