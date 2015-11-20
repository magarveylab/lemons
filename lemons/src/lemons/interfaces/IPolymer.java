package lemons.interfaces;

import java.util.List;

import org.openscience.cdk.interfaces.IAtomContainer;

public interface IPolymer {
	
	public List<IMonomer> monomers();
	
	public void addMonomer(IMonomer monomer);
	
	public void addMonomers(List<IMonomer> monomers);

	public IMonomer getLastMonomer();

	public IMonomer getFirstMonomer();
	
	public IMonomer getMonomer(int index);

	public void setMonomer(int index, IMonomer monomer);

	public void setMonomers(List<IMonomer> monomers);
	
	public IAtomContainer molecule();
	
	public void setMolecule(IAtomContainer molecule);

	public int size();

}
