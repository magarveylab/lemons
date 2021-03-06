package lemons.data;

import java.util.ArrayList;
import java.util.List;

import lemons.interfaces.IMonomer;
import lemons.interfaces.IPolymer;

import org.openscience.cdk.interfaces.IAtomContainer;

/**
 * A generic biological polymer.
 * 
 * @author michaelskinnider
 *
 */
public class Polymer implements IPolymer {

	private List<IMonomer> monomers = new ArrayList<IMonomer>();
	private IAtomContainer molecule;

	public List<IMonomer> monomers() {
		return monomers;
	}

	public void addMonomer(IMonomer monomer) {
		monomers.add(monomer);
	}

	public void addMonomers(List<IMonomer> monomers) {
		this.monomers.addAll(monomers);
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

	public IMonomer getMonomer(int idx) {
		return monomers.get(idx);
	}

	public void setMonomer(int idx, IMonomer monomer) {
		monomers.set(idx, monomer);
	}

	public IAtomContainer molecule() {
		return molecule;
	}

	public void setMolecule(IAtomContainer molecule) {
		this.molecule = molecule;
	}

	public int size() {
		return monomers.size();
	}

}
