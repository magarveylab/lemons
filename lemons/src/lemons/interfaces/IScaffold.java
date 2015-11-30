package lemons.interfaces;

import java.util.List;

import org.openscience.cdk.interfaces.IAtomContainer;

/**
 * A scaffold containing multiple monomers.
 * 
 * @author michaelskinnider
 *
 */
public interface IScaffold {
	
	public IAtomContainer molecule();
	
	public void setMolecule(IAtomContainer molecule);
	
	public List<IMonomer> monomers();
	
	public void addMonomer(IMonomer monomer);
	
	public void addMonomers(List<IMonomer> monomers);
	
	public void setMonomer(int index, IMonomer monomer);
	
	public IMonomer getMonomer(int index);
	
	public IPolymer polymer();
	
	public void setPolymer(IPolymer polymer);
	
	public int size();
	
	public IFingerprintList<IFingerprint> fingerprints();
	
	public void addFingerprint(IFingerprint fingerprint);
	
	public void setFingerprints(IFingerprintList<IFingerprint> fingerprints);
	
	public IReactionList<IReaction> reactions();
	
	public void addReaction(IReaction reaction);

	public void addReactions(IReactionList<IReaction> reactions);
	
	public void setReactions(IReactionList<IReaction> reactions);
	
}
