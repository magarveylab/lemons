package lemons.util;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import lemons.interfaces.IMonomerType;
import lemons.interfaces.IPolymer;
import lemons.interfaces.IScaffold;
import lemons.io.SmilesIO;
import lemons.util.exception.PolymerGenerationException;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;

public class SmilesUtil {
	
	private static final Logger logger = Logger.getLogger(SmilesUtil.class.getName());

	public static IAtomContainer toMolecule(IScaffold scaffold)
			throws CDKException, PolymerGenerationException, IOException {
		int i = 0;
		List<IMonomerType> monomers = scaffold.monomers();
		IPolymer polymer = null;
		while (i < monomers.size()) {
			IMonomerType monomer = monomers.get(i);
			if (i == 0) {
				polymer = PolymerGenerator.beginPolymer(monomer);
			} else {
				PolymerGenerator.extendPolymer(monomer, polymer);
			}
			i++;
		}
		PolymerGenerator.finishPolymer(polymer);
		
		IAtomContainer molecule = polymer.molecule();
		
		String smiles = SmilesIO.smiles(molecule);		
		logger.log(Level.INFO, "Generated new scaffold with SMILES " + smiles);
		return molecule;
	}
	
}
