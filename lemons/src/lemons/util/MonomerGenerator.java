package lemons.util;

import java.io.IOException;

import lemons.data.Monomer; 
import lemons.interfaces.IMonomer;
import lemons.interfaces.IMonomerType;
import lemons.io.SmilesIO;
import lemons.util.Chemoinformatics.Atoms;
import lemons.util.Chemoinformatics.Bonds;
import lemons.util.Chemoinformatics.Substrates;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;

/**
 * Generate monomers.
 * @author michaelskinnider
 *
 */
public class MonomerGenerator {
	
	public static IMonomer createMonomer(IMonomerType type) throws IOException, 
			CDKException {
		String smiles = type.smiles();
		IAtomContainer molecule = SmilesIO.molecule(smiles);
		
		// get ketone and alpha carbon
		IAtom extend = Substrates.getExtenderAtom(molecule);
		IAtom begin = Substrates.getStarterAtom(molecule);

		// remove halogens
		removeIodine(molecule);
		removeFluorine(molecule);

		// create residue
		IMonomer monomer = new Monomer(type);
		monomer.setBegin(begin);
		monomer.setExtend(extend);
		monomer.setStructure(molecule);
		
		return monomer;
	}
	
	/**
	 * Remove a fluorine atom from this container.
	 * @param container	the container to remove fluorine from
	 */
	public static void removeFluorine(IAtomContainer container) {
		IAtom fluorine = Atoms.getFluorine(container);
		if (fluorine != null) {
			IBond starterFluorineBond = Bonds.getFluorineBond(container);
			container.removeBond(starterFluorineBond);
			container.removeAtom(fluorine);
		}
	}
	
	/**
	 * Remove an iodine atom from this container.
	 * @param container	the container to remove iodine from
	 */
	public static void removeIodine(IAtomContainer container) {
		IAtom iodine = Atoms.getIodine(container);
		if (iodine != null) {
			IBond extenderIodineBond = Bonds.getIodineBond(container);
			container.removeBond(extenderIodineBond);
			container.removeAtom(iodine);
		}
	}
	
}
