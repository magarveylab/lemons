package lemons.interfaces;

import java.util.List;

import lemons.fingerprint.Fingerprinters;
import lemons.util.exception.FingerprintGenerationException;

/**
 * A list of chemical fingerprints.
 * 
 * @author michaelskinnider
 *
 * @param <T>
 */
public interface IFingerprintList<T> extends List<T> {

	/**
	 * Get the fingerprint corresponding to a given chemical fingerprint
	 * generation algorithm.
	 * 
	 * @param fingerprinter
	 *            the algorithm used to generate the fingerprint
	 * @return the corresponding chemical fingerprint
	 * @throws FingerprintGenerationException
	 *             if no such fingerprint exists within this list
	 */
	public IFingerprint getFingerprint(Fingerprinters fingerprinter)
			throws FingerprintGenerationException;

}
