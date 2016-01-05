package lemons.interfaces;

import java.io.IOException;

import lemons.util.exception.FingerprintGenerationException;
import lemons.util.exception.PolymerGenerationException;

import org.openscience.cdk.exception.CDKException;

/**
 * A single LEMONS experiment, corresponding to the generation of a single
 * library of hypothetical natural product structures.
 * 
 * @author michaelskinnider
 *
 */
public interface IExperiment {

	/**
	 * Run the experiment, generating natural product structures, swapping
	 * monomers or modifying reactions, and generating fingerprint rank
	 * distributions.
	 * 
	 * @throws CDKException
	 * @throws PolymerGenerationException
	 * @throws IOException
	 * @throws FingerprintGenerationException
	 */
	public void run() throws CDKException, PolymerGenerationException,
			IOException, FingerprintGenerationException;

}
