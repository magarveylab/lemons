package lemons.scaffold;

import java.util.List;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomType;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import lemons.data.Tag;
import lemons.data.TagList;
import lemons.enums.tags.ReactionTags;
import lemons.interfaces.IMonomer;
import lemons.interfaces.ITag;
import lemons.interfaces.ITagList;

public class TagPerceiver {
	
	public static ITagList<ITag> perceiveHydroxyls(IMonomer monomer, IAtomContainer structure) throws CDKException {
		ITagList<ITag> tags = new TagList();
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
						tags.add(tag);
					} else if (connectedAtom.getHybridization() == IAtomType.Hybridization.SP2) {
						// do this
					}
				}
		}
		return tags;
	}

}
