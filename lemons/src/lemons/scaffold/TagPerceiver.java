package lemons.scaffold;

import java.util.List;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomType;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import lemons.data.Tag;
import lemons.enums.tags.ReactionTags;
import lemons.interfaces.IMonomer;
import lemons.interfaces.ITag;

public class TagPerceiver {
	
	public static void perceiveHydroxyls(IMonomer monomer, IAtomContainer structure) throws CDKException {
		AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(structure);
		IAtomContainer molecule = monomer.structure();
		for (IAtom atom : molecule.atoms()) {
				if (atom.getSymbol().equals("O")
						&& molecule.getBondOrderSum(atom) == 1) {
					List<IBond> bonds = molecule.getConnectedBondsList(atom);
					if (bonds.size() > 1)
						continue;
					IAtom connectedAtom = bonds.get(0).getConnectedAtom(atom);
					if (connectedAtom.getHybridization() == IAtomType.Hybridization.SP3) {
						ITag tag = new Tag(ReactionTags.SP3_CARBON_HYDROXYL, atom);
						if (!monomer.containsTag(ReactionTags.SP3_CARBON_HYDROXYL, atom))
							monomer.addTag(tag);
					} else if (connectedAtom.getHybridization() == IAtomType.Hybridization.SP2) {
						boolean hasKetone = false;
						for (IAtom atom2 : structure.getConnectedAtomsList(connectedAtom))
						if (atom2.getSymbol().equals("O")
								&& molecule.getBond(connectedAtom, atom2)
										.getOrder() == IBond.Order.DOUBLE)
								hasKetone = true; 
						
						if (hasKetone) {
							ITag tag = new Tag(ReactionTags.CARBOXYLIC_ACID, atom);
							if (!monomer.containsTag(ReactionTags.CARBOXYLIC_ACID, atom))
								monomer.addTag(tag);
						} else {
							ITag tag = new Tag(ReactionTags.SP2_CARBON_HYDROXYL, atom);
							if (!monomer.containsTag(ReactionTags.SP2_CARBON_HYDROXYL, atom))
								monomer.addTag(tag);
						}
					}
				}
		}
	}

}
