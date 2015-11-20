package lemons.interfaces;

import java.io.IOException;

import lemons.util.exception.FingerprintGenerationException;
import lemons.util.exception.PolymerGenerationException;

import org.openscience.cdk.exception.CDKException;

public interface IExperiment {

	public void run() throws CDKException, PolymerGenerationException,
			IOException, FingerprintGenerationException;

}
