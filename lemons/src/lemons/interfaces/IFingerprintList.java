package lemons.interfaces;

import java.util.List;

import lemons.fingerprint.Fingerprinters;
import lemons.util.exception.FingerprintGenerationException;

public interface IFingerprintList<T> extends List<T> {
	
	public IFingerprint getFingerprint(Fingerprinters fingerprinter)
			throws FingerprintGenerationException;

}
