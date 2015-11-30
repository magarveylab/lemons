package lemons.util;

import java.util.BitSet;
import java.util.List;

import lemons.data.Fingerprint;
import lemons.fingerprint.Fingerprinters;
import lemons.interfaces.IFingerprint;
import lemons.interfaces.IScaffold;
import lemons.util.exception.FingerprintGenerationException;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.fingerprint.IFingerprinter;
import org.openscience.cdk.interfaces.IAtomContainer;

public class FingerprintUtil {

	public static void setFingerprints(List<IScaffold> scaffolds)
			throws CDKException, FingerprintGenerationException {
		// generate fingerprints
		for (int i = 0; i < scaffolds.size(); i++) {
			IScaffold peptide = scaffolds.get(i);
			String name = "Scaffold_" + (i + 1);
			setFingerprints(peptide, name);
		}
	}

	public static void setFingerprints(IScaffold scaffold, String name)
			throws CDKException, FingerprintGenerationException {
		// calculate fingerprints
		IAtomContainer molecule = scaffold.molecule();
		for (Fingerprinters fingerprinter : Fingerprinters.values()) {
			IFingerprinter fp = fingerprinter.getFingerprinter();
			try {
				BitSet bitset = fp.getBitFingerprint(molecule).asBitSet();
				IFingerprint fingerprint = new Fingerprint(name, bitset,
						fingerprinter);
				scaffold.addFingerprint(fingerprint);
			} catch (Exception e) {
				throw e;
			//	throw new FingerprintGenerationException(
			//			"Could not generate fingerprint for molecule" + name);
			}
		}
	}

}
