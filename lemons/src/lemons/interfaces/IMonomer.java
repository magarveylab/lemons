package lemons.interfaces;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;

/**
 * A peptide or polyketide monomer.
 * 
 * @author michaelskinnider
 *
 */
public interface IMonomer {

	public IMonomerType type();
	
	public void setType(IMonomerType type);
	
	public IAtomContainer structure();
	
	public void setStructure(IAtomContainer structure);
	
	public IAtom begin();
	
	public void setBegin(IAtom begin);
	
	public IAtom extend();
	
	public void setExtend(IAtom extend);

}
