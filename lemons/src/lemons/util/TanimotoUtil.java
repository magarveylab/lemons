package lemons.util;

import java.util.BitSet;

import lemons.data.TanimotoCoefficient;
import lemons.interfaces.IFingerprint;
import lemons.interfaces.ITanimotoCoefficient;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.similarity.Tanimoto;

/**
 * Utilities class for operations involving the Tanimoto coefficient.
 * 
 * @author michaelskinnider
 *
 */
public class TanimotoUtil {
	
	/**
	 * Calculate the Tanimoto coefficient between two chemical fingerprints.
	 * 
	 * @param fp1
	 *            the first fingerprint
	 * @param fp2
	 *            the second fingerprint
	 * @return the Tanimoto coefficient (in a package including the names of
	 *         both scaffolds)
	 * @throws CDKException
	 */
	public static ITanimotoCoefficient get(IFingerprint fp1, IFingerprint fp2)
			throws CDKException {
		double value = TanimotoUtil.calculate(fp1, fp2);
		String name1 = fp1.name();
		String name2 = fp2.name();
		ITanimotoCoefficient tc = new TanimotoCoefficient(name1, name2, value); 
		return tc;
	}
	
	private static double calculate(IFingerprint fp1, IFingerprint fp2)
			throws CDKException {
		BitSet b1 = fp1.bitset();
		BitSet b2 = fp2.bitset();
		return Tanimoto.calculate(b1, b2);
	}
	
}
