package lemons.data;

import java.util.ArrayList;
import java.util.List;

import lemons.interfaces.IFingerprint;
import lemons.interfaces.IFingerprintList;
import lemons.interfaces.IMonomerType;
import lemons.interfaces.IScaffold;

public class Scaffold implements IScaffold {
	
	private List<IMonomerType> monomers = new ArrayList<IMonomerType>();
	private IFingerprintList<IFingerprint> fingerprints = new FingerprintList();
	
	public List<IMonomerType> monomers() {
		return monomers;
	}
	
	public void add(IMonomerType monomer) {
		monomers.add(monomer);
	}
	
	public void add(List<IMonomerType> monomers) {
		this.monomers.addAll(monomers);
	}
	
	public void set(int index, IMonomerType monomer) {
		monomers.set(index, monomer);
	}
	
	public int size() {
		return monomers.size();
	}
	
	public IFingerprintList<IFingerprint> fingerprints() {
		return fingerprints;
	}
	
	public void add(IFingerprint fingerprint) {
		fingerprints.add(fingerprint);
	}
	
	public void set(IFingerprintList<IFingerprint> fingerprints) {
		this.fingerprints = fingerprints;
	}
	
}
