package lemons.util;

import java.io.IOException;

import lemons.data.Polymer;
import lemons.interfaces.IMonomer;
import lemons.interfaces.IMonomerType;
import lemons.interfaces.IPolymer;
import lemons.util.exception.PolymerGenerationException;

import org.openscience.cdk.AtomContainer;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;

public class PolymerGenerator {

	public static IPolymer beginPolymer(IMonomerType type) throws IOException,
			CDKException {
		IPolymer polymer = new Polymer();
		IAtomContainer molecule = new AtomContainer();

		IMonomer monomer = MonomerGenerator.createMonomer(type);
		molecule.add(monomer.structure());

		polymer.setMolecule(molecule);
		polymer.addMonomer(monomer);

		return polymer;
	}

	public static void extendPolymer(IMonomerType type, IPolymer polymer)
			throws IOException, CDKException, PolymerGenerationException {
		IAtomContainer molecule = polymer.molecule();
		IMonomer starter = polymer.getLastMonomer();
		IMonomer extender = MonomerGenerator.createMonomer(type);

		polymer.addMonomer(extender);
		molecule.add(extender.structure());

		ReactionsUtil.addBond(starter.extend(), extender.begin(), molecule);
	}

	public static void finishPolymer(IPolymer polymer) throws CDKException,
			PolymerGenerationException {
		IMonomer first = polymer.getFirstMonomer();
		IAtomContainer molecule = polymer.molecule();
		IAtom atom = first.begin();

		String smiles = "OI";
		ReactionsUtil.functionalize(smiles, atom, molecule);
		
		IMonomer last = polymer.getLastMonomer();
		IAtom extend = last.extend();
		extend.setImplicitHydrogenCount(extend.getImplicitHydrogenCount() + 1);
	}

}
