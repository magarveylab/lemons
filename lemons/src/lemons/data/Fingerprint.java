package lemons.data;

import java.util.BitSet;

import lemons.fingerprint.Fingerprinters;
import lemons.interfaces.IFingerprint;

public class Fingerprint implements IFingerprint {
	
	private String name;
	private BitSet bitset;
	private Fingerprinters fingerprinter;
	
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
