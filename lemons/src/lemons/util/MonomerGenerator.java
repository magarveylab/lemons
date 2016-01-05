package lemons.util;

import java.io.IOException;

import lemons.data.Monomer;
import lemons.data.Tag;
import lemons.enums.tags.ScaffoldTags;
import lemons.interfaces.IAminoAcidType;
import lemons.interfaces.IMonomer;
import lemons.interfaces.IMonomerType;
import lemons.interfaces.ITag;
import lemons.io.SmilesIO;
import lemons.util.Cheminformatics.Substrates;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;

/**
 * Generate monomers.
 * 
 * @author michaelskinnider
 *
 */
public class MonomerGenerator {

	public static IMonomer buildMonomer(IMonomerType type) throws IOException,
			CDKException {
		String smiles = type.smiles();
		IAtomContainer molecule = SmilesIO.molecule(smiles);

		// get ketone and alpha carbon
		IAtom extend = Substrates.getExtenderAtom(molecule);
		IAtom begin = Substrates.getStarterAtom(molecule);

		// remove halogens
		ReactionsUtil.removeIodine(molecule);
		ReactionsUtil.removeFluorine(molecule);

		// create residue
		IMonomer monomer = new Monomer(type);
		monomer.setStructure(molecule);

		// tag
		tagMonomer(monomer, extend, begin);
		return monomer;
	}

	public static void tagMonomer(IMonomer monomer, IAtom extendAtom,
			IAtom beginAtom) {
		IMonomerType type = monomer.type();

		if (beginAtom != null) {
			ITag begin = new Tag(ScaffoldTags.BACKBONE_BEGIN, beginAtom);
			monomer.addTag(begin);
			ITag ketone = new Tag(ScaffoldTags.BACKBONE_KETONE, beginAtom);
			monomer.addTag(ketone);
		}

		if (extendAtom != null) {
			ITag extend = new Tag(ScaffoldTags.BACKBONE_EXTEND, extendAtom);
			monomer.addTag(extend);
			
			if (type instanceof IAminoAcidType) {
				ITag nitrogen = new Tag(ScaffoldTags.BACKBONE_NITROGEN, extendAtom);
				monomer.addTag(nitrogen);
			} else {
				ITag alphaCarbon = new Tag(ScaffoldTags.BACKBONE_ALPHA_CARBON,
						extendAtom);
				monomer.addTag(alphaCarbon);
			}
		}
	}

}
