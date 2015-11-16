package lemons.data;

import lemons.interfaces.IMonomer;
import lemons.interfaces.IMonomerType;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;

/**
 * A biological monomer.
 * 
 * @author michaelskinnider
 *
 */
public class Monomer implements IMonomer {

	private IMonomerType type;
	private IAtom begin;
	private IAtom extend;
	private IAtomContainer structure;

	/**
	 * Instantiate a new biological monomer.
	 * 
	 * @param type
	 *            the type of the monomer (e.g., threonine or methylmalonate)
	 */
	public Monomer(IMonomerType type) {
		this.type = type;
	}

	public IMonomerType type() {
		return type;
	}

	public void setType(IMonomerType type) {
		this.type = type;
	}
	
	public IAtom begin() {
		return begin;
	}
	
	public void setBegin(IAtom begin) {
		this.begin = begin;
	}
	
	public IAtom extend() {
		return extend;
	}
	
	public void setExtend(IAtom extend) {
		this.extend = extend;
	}
	
	/**
	 * Get the entire structure of this monomer.
	 * @return	the monomer's structure
	 */
	public IAtomContainer structure() {
		return structure;
	}
	
	/**
	 * Set the structure of this monomer.
	 * @param structure	the monomer structure
	 */
	public void setStructure(IAtomContainer structure) {
		this.structure = structure;
	}

}
