package lemons.scaffold.reaction;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;

import lemons.data.Reaction;
import lemons.data.ReactionList;
import lemons.data.TagList;
import lemons.interfaces.IMonomer;
import lemons.interfaces.IReaction;
import lemons.interfaces.IReactionList;
import lemons.interfaces.IReactionPlanner;
import lemons.interfaces.IScaffold;
import lemons.interfaces.ITag;
import lemons.interfaces.ITagList;
import lemons.util.RandomUtil;
import lemons.util.exception.BadTagException;
import lemons.util.exception.PolymerGenerationException;

/**
 * A template for the addition of new tailoring reactions to LEMONS. Modify this
 * file by adding code for the detection of potential reaction sites in the
 * perceive method, and the execution of the tailoring reaction in the execute
 * method.
 * 
 * @author michaelskinnider
 *
 */
public class ReactionTemplate implements IReactionPlanner {

	@SuppressWarnings("unused")
	@Override
	public void perceive(double numReactions, IScaffold scaffold)
			throws BadTagException, CDKException {
		// if the reaction you wish to add involves a single atom on the
		// scaffold, such as halogenation or glycosylation, create a single list
		// of atom tags
		ITagList<ITag> tagList = new TagList();
		for (IMonomer monomer : scaffold.monomers()) {
			// perceive tags for each monomer here
			// add tags to TagList
		}

		// now, convert each tag
		IReactionList<IReaction> reactions = new ReactionList();
		for (ITag tag : tagList) {
			IReaction reaction = new Reaction(null); // replace null with your
														// reaction type
			reaction.addTag(tag);
			reactions.add(reaction);
		}

		// finally, pick random reactions
		IReactionList<IReaction> random = RandomUtil.pickRandomReactions(
				numReactions, reactions);
		scaffold.addReactions(random);
	}

	@SuppressWarnings("unused")
	@Override
	public void execute(IReaction reaction, IScaffold scaffold)
			throws PolymerGenerationException, CDKException {
		IAtomContainer molecule = scaffold.molecule();
		ITagList<ITag> tags = reaction.getTags();

		// execution of the reaction will depend based on the reaction type.
		// for example: get the tag you assigned in the perceive method from the
		// tag list using the TagManipulator.getSingleTag method,
		// and functionalize it with an iodine-tagged functional group using the
		// ReactionsUtil.functionalize method.
	}

}
