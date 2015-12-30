package lemons.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import lemons.data.Polymer;
import lemons.interfaces.IMonomer;
import lemons.interfaces.IMonomerType;
import lemons.interfaces.IPolymer;
import lemons.io.SmilesIO;
import lemons.util.exception.PolymerGenerationException;

import org.openscience.cdk.AtomContainer;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;

public class PolymerGenerator {
	
	private static final Logger logger = Logger.getLogger(PolymerGenerator.class.getName());
	
	public static IPolymer deepCopyPolymer(IPolymer original)
			throws CDKException, PolymerGenerationException, IOException {
		List<IMonomer> monomersCopy = new ArrayList<IMonomer>();
		for (IMonomer originalMonomer : original.monomers()) {
			IMonomerType originalMonomerType = originalMonomer.type();
			IMonomer monomerCopy = MonomerGenerator.buildMonomer(originalMonomerType); 
			monomersCopy.add(monomerCopy);
		}
		IPolymer copy = buildPolymer(monomersCopy);
		return copy;
	}

	public static IPolymer buildPolymer(List<IMonomer> monomers)
			throws CDKException, PolymerGenerationException, IOException {
		int i = 0;
		IPolymer polymer = null;
		while (i < monomers.size()) {
			IMonomer monomer = monomers.get(i);
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
		
		return polymer;
	}

	public static IPolymer beginPolymer(IMonomer monomer) throws IOException,
			CDKException {
		IPolymer polymer = new Polymer();
		IAtomContainer molecule = new AtomContainer();

		molecule.add(monomer.structure());
		polymer.setMolecule(molecule);
		
		polymer.addMonomer(monomer);
		return polymer;
	}

	public static void extendPolymer(IMonomer monomer, IPolymer polymer)
			throws IOException, CDKException, PolymerGenerationException {
		IAtomContainer molecule = polymer.molecule();
		IMonomer starter = polymer.getLastMonomer();

		polymer.addMonomer(monomer);
		molecule.add(monomer.structure());

		if (starter.extend() == null) 
			throw new PolymerGenerationException("Could not extend residue " + starter.type());
		
		try {
			ReactionsUtil.addBond(starter.extend(), monomer.begin(), molecule);
		} catch (Exception e) {
			throw new PolymerGenerationException(
					"Couldn't extend polymer with new residue "
							+ monomer.type() + " and last residue "
							+ starter.type() + "\nFull message: "
							+ e.getLocalizedMessage());
		}
	}

	public static void finishPolymer(IPolymer polymer) throws CDKException,
			PolymerGenerationException {
		IMonomer first = polymer.getFirstMonomer();
		IAtomContainer molecule = polymer.molecule();
		IAtom atom = first.begin();

		String smiles = "OI";
		ReactionsUtil.functionalize(smiles, atom, molecule);
		
		IMonomer last = polymer.getLastMonomer();
		if (last.hasExtend()) {
			IAtom extend = last.extend();
			double bondOrderSum = molecule.getBondOrderSum(extend);
			if (extend.getSymbol().equals("N")) {
				while (bondOrderSum + extend.getImplicitHydrogenCount() < 3)
					extend.setImplicitHydrogenCount(extend.getImplicitHydrogenCount() + 1);
			} else if (extend.getSymbol().equals("C")) {
				while (bondOrderSum + extend.getImplicitHydrogenCount() < 4)
					extend.setImplicitHydrogenCount(extend.getImplicitHydrogenCount() + 1);
			}
		}
	}

}
