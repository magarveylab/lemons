package lemons.data;

import java.util.ArrayList;
import java.util.List;

import lemons.interfaces.IMonomer;
import lemons.interfaces.IPolymer;

import org.openscience.cdk.interfaces.IAtomContainer;

public class Polymer implements IPolymer {

	private List<IMonomer> monomers = new ArrayList<IMonomer>();
	private IAtomContainer molecule;

	public List<IMonomer> monomers() {
		return monomers;
	}
	
	public void addMonomer(IMonomer monomer) {
		monomers.add(monomer);
	}

	public void setMonomers(List<IMonomer> monomers) {
		this.monomers = monomers;
	} 
	
	public IMonomer getFirstMonomer() {
		return monomers.get(0);
	}
	
	public IMonomer getLastMonomer() {
		return monomers.get(monomers.size() - 1);
	}
		
	public IAtomContainer molecule() {
		return molecule;
	}
	
	public void setMolecule(IAtomContainer molecule) {
		this.molecule = molecule;
	}

}
