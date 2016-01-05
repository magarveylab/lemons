package lemons.interfaces;

import java.util.BitSet;

import lemons.fingerprint.Fingerprinters;

/**
 * A chemical fingerprint.
 * 
 * @author michaelskinnider
 *
 */
public interface IFingerprint {

	/**
	 * Get the name of the molecule described by this chemical fingerprint.
	 * 
	 * @return the name of the molecule
	 */
	public String name();

	/**
	 * Get the bitset corresponding to the chemical fingerprint itself.
	 * 
	 * @return the bitset of the fingerprint
	 */
	public BitSet bitset();

	/**
	 * Get the fingerprint algorithm which was used to generate this individual
	 * fingerprint.
	 * 
	 * @return the fingerprint algorithm used to generate this fingerprint
	 */
	public Fingerprinters fingerprinter();

}
