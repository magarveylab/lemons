package lemons.interfaces;

import java.util.List;

/**
 * A scaffold containing multiple monomers.
 * 
 * @author michaelskinnider
 *
 */
public interface IScaffold {
	
	public List<IMonomerType> monomers();
	
	public void add(IMonomerType monomer);
	
	public void add(List<IMonomerType> monomers);
	
	public void set(int index, IMonomerType monomer);
	
	public int size();
	
	public IFingerprintList<IFingerprint> fingerprints();
	
	public void add(IFingerprint fingerprint);
	
	public void set(IFingerprintList<IFingerprint> fingerprints);

}
