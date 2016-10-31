package lemons.util;

import java.io.IOException;
import java.util.BitSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import lemons.data.Fingerprint;
import lemons.fingerprint.Fingerprinters;
import lemons.interfaces.IFingerprint;
import lemons.interfaces.IScaffold;
import lemons.io.SmilesIO;
import lemons.util.exception.FingerprintGenerationException;
import lemons.util.exception.PolymerGenerationException;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.fingerprint.IFingerprinter;
import org.openscience.cdk.interfaces.IAtomContainer;

/**
 * Utilities class for operations on fingerprints.
 * 
 * @author michaelskinnider
 *
 */
public class FingerprintUtil {

	private static final Logger logger = Logger
			.getLogger(FingerprintUtil.class.getName());

	/**
	 * Set fingerprints for a list of hypothetical natural product structures.
	 * 
	 * @param scaffolds
	 *            scaffolds to fingerprint
	 * @throws CDKException
	 * @throws FingerprintGenerationException
	 * @throws PolymerGenerationException
	 * @throws IOException
	 */
	public static void setFingerprints(List<IScaffold> scaffolds)
			throws CDKException, FingerprintGenerationException,
			PolymerGenerationException, IOException {
		// generate fingerprints
		for (int i = 0; i < scaffolds.size(); i++) {
			IScaffold peptide = scaffolds.get(i);

			String name = "Scaffold_" + (i + 1);
			setFingerprints(peptide, name);
		}
	}

	/**
	 * Set fingerprints for a hypothetical natural product structure.
	 * 
	 * @param scaffold
	 *            the scaffold to fingerprint
	 * @param name
	 *            the name of the scaffold
	 * @throws CDKException
	 * @throws FingerprintGenerationException
	 * @throws PolymerGenerationException
	 * @throws IOException
	 */
	public static void setFingerprints(IScaffold scaffold, String name)
			throws CDKException, FingerprintGenerationException,
			PolymerGenerationException, IOException {
		// I/O -- *required* to fix double bond stereochemistry
		String smiles = SmilesIO.smiles(scaffold.molecule());
		IAtomContainer molecule = SmilesIO.molecule(smiles);

		// calculate fingerprints
		for (Fingerprinters fingerprinter : Fingerprinters.values()) {
			IFingerprinter fp = fingerprinter.getFingerprinter();
			try {
				BitSet bitset = fp.getBitFingerprint(molecule).asBitSet();
				IFingerprint fingerprint = new Fingerprint(name, bitset,
						fingerprinter);
				scaffold.addFingerprint(fingerprint);
			} catch (Exception e) {
				logger.log(Level.WARNING,
						"Could not generate fingerprint for molecule " + name);
				IFingerprint fingerprint = new Fingerprint(name, null,
						fingerprinter);
				scaffold.addFingerprint(fingerprint);
			}
		}
	}

}
