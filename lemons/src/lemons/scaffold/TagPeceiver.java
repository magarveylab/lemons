package lemons.scaffold;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;

import lemons.data.Tag;
import lemons.data.TagList;
import lemons.enums.tags.AtomTags;
import lemons.interfaces.IMonomer;
import lemons.interfaces.ITag;
import lemons.interfaces.ITagList;

public class TagPeceiver {
	
	public static ITagList<ITag> perceiveHydroxyls(IMonomer monomer) {
		ITagList<ITag> tags = new TagList();
		IAtomContainer molecule = monomer.structure();
		for (IAtom atom : molecule.atoms()) {
				if (atom.getSymbol().equals("O")
						&& molecule.getBondOrderSum(atom) == 1) {
					ITag tag = new Tag(AtomTags.HYDROXYL, atom);
					monomer.addTag(tag);
					tags.add(tag);
				}
		}
		return tags;
	}

}
