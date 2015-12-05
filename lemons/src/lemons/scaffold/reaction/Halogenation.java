package lemons.scaffold.reaction;

import java.util.Random;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;

import lemons.data.Reaction;
import lemons.data.ReactionList;
import lemons.data.Tag;
import lemons.data.TagList;
import lemons.enums.Reactions;
import lemons.enums.tags.ReactionTags;
import lemons.enums.tags.ScaffoldTags;
import lemons.interfaces.IMonomer;
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
 * Halogenation (i.e. chlorination or bromination, with equal probability) of
 * random aromatic carbons or non-backbone, non-O-/N-methyl SP3 carbons.
 * 
 * @author michaelskinnider
 *
 */
public class Halogenation implements IReactionPlanner {

	public void perceive(double numReactions, IScaffold scaffold)
			throws BadTagException {
		// perceive carbons
		ITagList<ITag> carbons = new TagList();
		IAtomContainer molecule = scaffold.molecule();
		for (IMonomer monomer : scaffold.monomers()) {
			IAtomContainer structure = monomer.structure();
			for (IAtom atom : structure.atoms()) {
				if (atom.getSymbol().equals("C")) {
					// not backbone
					ITagList<ITag> tags = monomer.getTags(atom);
					if (tags.contains(ScaffoldTags.BACKBONE_ALPHA_CARBON)
							|| tags.contains(ScaffoldTags.BACKBONE_KETONE))
						continue;

					// must be bonded to at least one carbon
					boolean hasCarbon = false;
					for (IAtom atom2 : molecule.getConnectedAtomsList(atom))
						if (atom2.getSymbol().equals("C"))
							hasCarbon = true;
					if (!hasCarbon)
						continue;

					// check that a new bond can be added
					if (molecule.getBondOrderSum(atom) >= 4)
						continue;

					ITag tag = new Tag(ReactionTags.HALOGENATION_CARBON, atom);
					carbons.add(tag);
					
					// add to monomer
					if (!monomer.containsTag(ReactionTags.HALOGENATION_CARBON,
							atom))
						monomer.addTag(tag);
				}
			}
		}

		// create reactions
		IReactionList<IReaction> reactions = new ReactionList();
		for (ITag carbon : carbons) {
			IReaction reaction = new Reaction(Reactions.HALOGENATION);
			
			// set smiles
			String smiles = null;
			double p = new Random().nextDouble();
			if (p < 0.5) {
				smiles = "ICl";
			} else {
				smiles = "IBr";
			}
			reaction.setSmiles(smiles);
			
			reaction.addTag(carbon);
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
		ITag nitrogen = TagManipulator.getSingleTag(tags,
				ReactionTags.HALOGENATION_CARBON);

		ReactionsUtil.functionalize(reaction.smiles(), nitrogen.atom(), molecule);
	}

}
