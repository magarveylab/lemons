package lemons;

import java.io.IOException;

import org.openscience.cdk.exception.CDKException;

import lemons.experiments.LinearPeptideTest;
import lemons.util.exception.FingerprintGenerationException;
import lemons.util.exception.PolymerGenerationException;

public class Main {

	public static void main(String[] args) throws CDKException,
			PolymerGenerationException, IOException,
			FingerprintGenerationException {
		LinearPeptideTest lpt = new LinearPeptideTest();
		lpt.testProteinogenicPeptides(1, 10);
	}

}
