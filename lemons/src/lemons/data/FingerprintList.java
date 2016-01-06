package lemons.data;

import java.util.ArrayList;
import java.util.Iterator;

import lemons.fingerprint.Fingerprinters;
import lemons.interfaces.IFingerprint;
import lemons.interfaces.IFingerprintList;
import lemons.util.exception.FingerprintGenerationException;

/**
 * A generic list of chemical fingerprints.
 * 
 * @author michaelskinnider
 *
 */
public class FingerprintList extends ArrayList<IFingerprint> implements
		IFingerprintList<IFingerprint> {

	private static final long serialVersionUID = -2896195724076412726L;

	@Override
	public IFingerprint getFingerprint(Fingerprinters fingerprinter)
			throws FingerprintGenerationException {
		IFingerprint fingerprint = null;
		Iterator<IFingerprint> itr = this.iterator();
		while (itr.hasNext()) {
			IFingerprint f = itr.next();
			if (f.fingerprinter() == fingerprinter)
				fingerprint = f;
		}
		if (fingerprint == null)
			throw new FingerprintGenerationException("Failed to generate "
					+ fingerprinter.toString() + " fingerprint");
		return fingerprint;
	}

}
