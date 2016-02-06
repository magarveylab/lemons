package lemons;

import java.io.IOException;
import java.util.Arrays;

import lemons.enums.Reactions;
import lemons.enums.monomers.*;
import lemons.experiments.Bootstrapper;
import lemons.experiments.Experiment;
import lemons.util.exception.FingerprintGenerationException;
import lemons.util.exception.PolymerGenerationException;

import org.openscience.cdk.exception.CDKException;

public class Test {
	
	public static void main(String[] args) throws CDKException,
			PolymerGenerationException, IOException,
			FingerprintGenerationException {		
		// set dir 
		if (args.length > 0)
			Config.BASE_DIRECTORY = args[0];
		
		test();
	}

	public static void test() throws CDKException, PolymerGenerationException,
			IOException, FingerprintGenerationException {
		// for bug testing

		Config.INITIAL_MONOMERS.addAll(Arrays.asList(ProteinogenicAminoAcids.values()));
//		Config.INITIAL_MONOMERS.addAll(Arrays.asList(NonProteinogenicAminoAcids.values()));
//		Config.INITIAL_MONOMERS.addAll(Arrays.asList(PolyketideMonomers.values()));
//		Config.INITIAL_MONOMERS.addAll(Arrays.asList(Starters.values()));
//		Config.SWAP_MONOMERS.addAll(Arrays.asList(ProteinogenicAminoAcids.values()));
//		Config.SWAP_MONOMERS.addAll(Arrays.asList(NonProteinogenicAminoAcids.values()));
//		Config.SWAP_MONOMERS.addAll(Arrays.asList(Starters.values()));

		Config.MIN_SCAFFOLD_SIZE = 4;
		Config.MIN_SCAFFOLD_SIZE = 15;
		
		Config.NUM_MONOMER_SWAPS = 1;
		Config.BOOTSTRAPS = 1;
		Config.LIBRARY_SIZE = 100;
		
		Config.WRITE_STRUCTURES = true;
		Config.GET_FINGERPRINTS = false;
	
		Config.SEED = 12345;

		Config.ADD_REACTIONS.put(Reactions.RANDOM, 1.0d);
		
		//	Config.INITIAL_REACTIONS.put(Reactions.AZOLE, 1.0d);
//		Config.INITIAL_REACTIONS.put(Reactions.CYCLIZATION, 1.0d);
		
		Bootstrapper.bootstrap(new Experiment());
	}

}
