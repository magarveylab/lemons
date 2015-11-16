package lemons.interfaces;

import java.util.List;

import org.openscience.cdk.interfaces.IAtomContainer;

public interface IPolymer {
	
	public List<IMonomer> monomers();
	
	public void addMonomer(IMonomer monomer);

	public IMonomer getLastMonomer();

	public IMonomer getFirstMonomer();
	
	public void setMonomers(List<IMonomer> monomers);
	
	public IAtomContainer molecule();
	
	public void setMolecule(IAtomContainer molecule);

}
