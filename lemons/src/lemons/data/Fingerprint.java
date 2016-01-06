package lemons.data;

import java.util.BitSet;

import lemons.fingerprint.Fingerprinters;
import lemons.interfaces.IFingerprint;

/**
 * A generic chemical fingerprint.
 * 
 * @author michaelskinnider
 *
 */
public class Fingerprint implements IFingerprint {

	private String name;
	private BitSet bitset;
	private Fingerprinters fingerprinter;

	/**
	 * Instantiate a new chemical fingerprint.
	 * 
	 * @param name
	 *            the name of the scaffold which corresponds to this fingerprint
	 * @param bitset
	 *            the bitset of the fingerprint itself
	 * @param fingerprinter
	 *            the fingerprint algorithm used to generate this fingerprint
	 */
	public Fingerprint(String name, BitSet bitset, Fingerprinters fingerprinter) {
		this.name = name;
		this.bitset = bitset;
		this.fingerprinter = fingerprinter;
	}

	public String name() {
		return name;
	}

	public BitSet bitset() {
		return bitset;
	}

	public Fingerprinters fingerprinter() {
		return fingerprinter;
	}

}
