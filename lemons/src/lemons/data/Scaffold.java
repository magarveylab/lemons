package lemons.data;

import java.util.List;

import org.openscience.cdk.interfaces.IAtomContainer;

import lemons.interfaces.IFingerprint;
import lemons.interfaces.IFingerprintList;
import lemons.interfaces.IMonomer;
import lemons.interfaces.IPolymer;
import lemons.interfaces.IReaction;
import lemons.interfaces.IReactionList;
import lemons.interfaces.IScaffold;

public class Scaffold implements IScaffold {
	
	private IPolymer polymer = new Polymer();
	private IFingerprintList<IFingerprint> fingerprints = new FingerprintList();
	private IReactionList<IReaction> reactions = new ReactionList();
	
	public List<IMonomer> monomers() {
		return polymer.monomers();
	}
	
	public void addMonomer(IMonomer monomer) {
		polymer.addMonomer(monomer);
	}
	
	public void addMonomers(List<IMonomer> monomers) {
		polymer.addMonomers(monomers);
	}
	
	public void setMonomer(int index, IMonomer monomer) {
		polymer.setMonomer(index, monomer);
	}
	
	public IMonomer getMonomer(int index) {
		return polymer.getMonomer(index);
	}
	
	public int size() {
		return polymer.size();
	}
	
	public IPolymer polymer() {
		return polymer;
	}
	
	public void setPolymer(IPolymer polymer) {
		this.polymer = polymer; 
	}
	
	public IAtomContainer molecule() {
		return polymer.molecule();
	}
	
	public void setMolecule(IAtomContainer molecule) {
		polymer.setMolecule(molecule);
	}
	
	public IFingerprintList<IFingerprint> fingerprints() {
		return fingerprints;
	}
	
	public void addFingerprint(IFingerprint fingerprint) {
		fingerprints.add(fingerprint);
	}
	
	public void setFingerprints(IFingerprintList<IFingerprint> fingerprints) {
		this.fingerprints = fingerprints;
	}
	
	public IReactionList<IReaction> reactions() {
		return reactions;
	}
	
	public void addReaction(IReaction reaction) {
		reactions.add(reaction);
	}
	
	public void addReactions(IReactionList<IReaction> reactions) {
		this.reactions.addAll(reactions);
	}
	
	public void setReactions(IReactionList<IReaction> reactions) {
		this.reactions = reactions;
	}
	
}
