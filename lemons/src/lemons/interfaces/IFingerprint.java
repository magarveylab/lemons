package lemons.interfaces;

import java.util.BitSet;

import lemons.fingerprint.Fingerprinters;

public interface IFingerprint {
	
	public String name();
	
	public BitSet bitset();
	
	public Fingerprinters fingerprinter();

}
