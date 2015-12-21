package lemons.scaffold.reaction;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;

import lemons.data.Reaction;
import lemons.data.ReactionList;
import lemons.data.Tag;
import lemons.enums.Reactions;
import lemons.enums.monomers.ProteinogenicAminoAcids;
import lemons.enums.tags.ReactionTags;
import lemons.enums.tags.ScaffoldTags;
import lemons.interfaces.IMonomer;
import lemons.interfaces.IMonomerType;
import lemons.interfaces.IReaction;
import lemons.interfaces.IReactionList;
import lemons.interfaces.IReactionPlanner;
import lemons.interfaces.IScaffold;
import lemons.interfaces.ITag;
import lemons.interfaces.ITagList;
import lemons.util.RandomUtil;
import lemons.util.ReactionsUtil;
import lemons.util.TagManipulator;
import lemons.util.exception.BadTagException;
import lemons.util.exception.PolymerGenerationException;

/**
 * Oxazole/thiazole, or oxazoline/thiazoline, formation. 
 * 
 * @author michaelskinnider
 *
 */
public class Azole implements IReactionPlanner {

	public void perceive(double numReactions, IScaffold scaffold)
			throws BadTagException {
		IReactionList<IReaction> reactions = new ReactionList();
		IAtomContainer molecule = scaffold.molecule();

		// perceive azoles 
		for (int i = 1; i < scaffold.size() - 1; i += 2) {
			// cannot cyclize on first residue! must keep -COOH for cyclization
			IMonomer first = scaffold.getMonomer(i);
			IMonomer second = scaffold.getMonomer(i+1);
			IMonomerType firstType = first.type();

			// first residue must be a cysteine 
			if (firstType != ProteinogenicAminoAcids.SERINE
					&& firstType != ProteinogenicAminoAcids.CYSTEINE)
				continue;
			
			// second residue must have a ketone 
			boolean hasKetone = false;
			ITag ketoneTag = TagManipulator.getSingleTag(second,
					ScaffoldTags.BACKBONE_KETONE);
			IAtom ketone = ketoneTag.atom();
			for (IBond bond : molecule.getConnectedBondsList(ketone)) {
				if (bond.getOrder() == IBond.Order.DOUBLE
						&& bond.getConnectedAtom(ketone).getSymbol()
								.equals("O"))
					hasKetone = true;
			}
			if (!hasKetone)
				continue;

			// get -SH or -OH 
			IAtom azoleAtom = null;
			IAtomContainer firstStructure = first.structure();
			for (IAtom atom : firstStructure.atoms()) {
				if ((atom.getSymbol().equals("O")
						|| atom.getSymbol().equals("S"))
						&& molecule.getConnectedBondsCount(atom) == 1
						&& molecule.getBondOrderSum(atom) == 1)
					azoleAtom = atom;
			}
			if (azoleAtom == null)
				continue;
			ITag azoleTag = new Tag(ReactionTags.AZOLE_SULFUR_OR_OXYGEN,
					azoleAtom);
			ITag nitrogenTag = TagManipulator.getSingleTag(first,
					ScaffoldTags.BACKBONE_NITROGEN);
			
			// add to monomer
			if (!first.containsTag(ReactionTags.AZOLE_SULFUR_OR_OXYGEN,
					azoleAtom))
				first.addTag(azoleTag);
			
			IReaction reaction = new Reaction(Reactions.AZOLE);
			reaction.addTag(ketoneTag);
			reaction.addTag(nitrogenTag);
			reaction.addTag(azoleTag);
			reactions.add(reaction);
		}
		
		// pick random reactions
		IReactionList<IReaction> random = RandomUtil.pickRandomReactions(
				numReactions, reactions);
		scaffold.addReactions(random);
	}

	public void execute(IReaction reaction, IScaffold scaffold)
			throws PolymerGenerationException, CDKException {
		IAtomContainer molecule = scaffold.molecule();
		ITagList<ITag> tags = reaction.getTags();
		
		ITag ketoneTag = TagManipulator.getSingleTag(tags,
				ScaffoldTags.BACKBONE_KETONE);
		ITag nitrogenTag = TagManipulator.getSingleTag(tags,
				ScaffoldTags.BACKBONE_NITROGEN);
		ITag azoleAtomTag = TagManipulator.getSingleTag(tags,
				ReactionTags.AZOLE_SULFUR_OR_OXYGEN);
		IAtom ketone = ketoneTag.atom();
		IAtom nitrogen = nitrogenTag.atom();
		IAtom azoleAtom = azoleAtomTag.atom();
		
		// add bond between -SH/-OH and ketone
		ReactionsUtil.addBond(azoleAtom, ketone, molecule);
		ReactionsUtil.decrementHydrogenCount(azoleAtom);
		
		// remove ketone =O
		IAtom ketoneOxygen = null;
		for (IAtom atom : molecule.getConnectedAtomsList(ketone))
			if (atom.getSymbol().equals("O")
					&& molecule.getBond(ketone, atom).getOrder() == IBond.Order.DOUBLE)
				ketoneOxygen = atom;
		ReactionsUtil.removeAtom(ketoneOxygen, molecule);

		// add double-bond between nitrogen and ketone 
		ReactionsUtil.setBondOrder(ketone, nitrogen, molecule, IBond.Order.DOUBLE);
		ReactionsUtil.decrementHydrogenCount(nitrogen);
		ReactionsUtil.decrementHydrogenCount(ketone);
	}
	
}
