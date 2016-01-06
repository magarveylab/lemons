package lemons.scaffold.reaction;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;

import lemons.data.Reaction;
import lemons.data.ReactionList;
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
import lemons.scaffold.TagPerceiver;
import lemons.util.RandomUtil;
import lemons.util.ReactionsUtil;
import lemons.util.TagManipulator;
import lemons.util.exception.BadTagException;
import lemons.util.exception.PolymerGenerationException;

/**
 * Macrocyclization of the terminal -COOH with either an aliphatic, SP3
 * carbon-bound hydroxyl or the N-terminal nitrogen atom.
 * 
 * @author michaelskinnider
 *
 */
public class Cyclization implements IReactionPlanner {
	
	private static final Logger logger = Logger.getLogger(Cyclization.class.getName());

	public void perceive(double numReactions, IScaffold scaffold)
			throws BadTagException, CDKException {
		// length check
		if (scaffold.size() < 4)
			return;

		// get C-terminus
		IMonomer last = scaffold.getMonomer(0);
		ITag cTerminus = TagManipulator.getSingleTag(last,
				ScaffoldTags.BACKBONE_KETONE);

		// perceive -OH tags
		ITagList<ITag> hydroxyls = new TagList();
		for (int i = 3; i < scaffold.size(); i++) {
			IMonomer monomer = scaffold.getMonomer(i);
			TagPerceiver.perceiveHydroxyls(monomer, scaffold.molecule());
			hydroxyls.addAll(monomer.getTags(ReactionTags.SP3_CARBON_HYDROXYL));
		}

		// create reactions
		IReactionList<IReaction> reactions = new ReactionList();
		for (ITag hydroxyl : hydroxyls) {
			IReaction reaction = new Reaction(Reactions.CYCLIZATION);
			reaction.addTag(cTerminus);
			reaction.addTag(hydroxyl);
			reactions.add(reaction);
		}

		// get N-terminus
		IMonomer first = scaffold.getMonomer(scaffold.size() - 1);
		if (first.getTags().contains(ScaffoldTags.BACKBONE_NITROGEN)) {
			ITag nitrogen = TagManipulator.getSingleTag(first,
					ScaffoldTags.BACKBONE_NITROGEN);
			IReaction reaction = new Reaction(Reactions.CYCLIZATION);
			reaction.addTag(cTerminus);
			reaction.addTag(nitrogen);
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
		ITag ketone = TagManipulator.getSingleTag(tags,
				ScaffoldTags.BACKBONE_KETONE);
		ITag cyclization = null;
		if (tags.contains(ScaffoldTags.BACKBONE_NITROGEN)) {
			cyclization = TagManipulator.getSingleTag(tags,
					ScaffoldTags.BACKBONE_NITROGEN);
		} else {
			cyclization = TagManipulator.getSingleTag(tags,
					ReactionTags.SP3_CARBON_HYDROXYL);
		}
		IAtom ketoneAtom = ketone.atom();
		IAtom cyclizationAtom = cyclization.atom();
		
		if (molecule.getConnectedBondsCount(cyclizationAtom) > 1
				|| cyclizationAtom.getImplicitHydrogenCount() == 0) {
			logger.log(Level.INFO, "Could not cyclize an atom which already has more than one bond");
			return;
		}

		// correct valency of cyclization atom
		int valency = cyclizationAtom.getValency();
		int bondOrderSum = (int) molecule.getBondOrderSum(cyclizationAtom)
				+ cyclizationAtom.getImplicitHydrogenCount() + 1; 
				// +1 because a bond is about to be added)
		if (valency != bondOrderSum) {
			int remainder = valency - bondOrderSum;
			cyclizationAtom.setImplicitHydrogenCount(cyclizationAtom
					.getImplicitHydrogenCount() + remainder);
		}

		ReactionsUtil.removeAlcohol(ketoneAtom, molecule);
		ReactionsUtil.addBond(cyclizationAtom, ketoneAtom, molecule);

	}

}
