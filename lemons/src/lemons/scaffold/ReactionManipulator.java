package lemons.scaffold;

import java.util.Map;

import org.openscience.cdk.exception.CDKException;

import lemons.Config;
import lemons.interfaces.IReaction;
import lemons.interfaces.IReactionList;
import lemons.interfaces.IReactionType;
import lemons.interfaces.IScaffold;
import lemons.util.RandomUtil;
import lemons.util.exception.BadTagException;

public class ReactionManipulator {
	
	public static void swapReactions(IScaffold scaffold) throws BadTagException, CDKException {
		IReactionList<IReaction> reactions = scaffold.reactions();
		Map<IReactionType, Double> SWAP_REACTIONS = Config.SWAP_REACTIONS;
		for (Map.Entry<IReactionType, Double> entry : SWAP_REACTIONS.entrySet()) {
			IReactionType reaction = entry.getKey();
			Double num = entry.getValue();
			
			// remove reactions
			IReactionList<IReaction> typeReactions = reactions.getReactions(reaction);
			IReactionList<IReaction> randomReactions = RandomUtil
					.pickRandomReactions(num, typeReactions);
			reactions.removeAll(randomReactions);
			
			// add reactions 
			ReactionPerceiver.detectReactions(reaction, num, scaffold);
		}
	}

	public static void addReactions(IScaffold scaffold) throws BadTagException, CDKException {
		Map<IReactionType, Double> ADD_REACTIONS = Config.ADD_REACTIONS;
		for (Map.Entry<IReactionType, Double> entry : ADD_REACTIONS.entrySet()) {
			IReactionType reaction = entry.getKey();
			Double num = entry.getValue();
			
			ReactionPerceiver.detectReactions(reaction, num, scaffold);
		}
	}
	
	public static void detectReactions(IScaffold scaffold) throws BadTagException, CDKException {
		Map<IReactionType, Double> INITIAL_REACTIONS = Config.INITIAL_REACTIONS;
		for (Map.Entry<IReactionType, Double> entry : INITIAL_REACTIONS.entrySet()) {
			IReactionType reaction = entry.getKey();
			Double num = entry.getValue();
			
			ReactionPerceiver.detectReactions(reaction, num, scaffold);
		}
	}

	public static void removeReactions(IScaffold scaffold) throws BadTagException, CDKException {
		IReactionList<IReaction> reactions = scaffold.reactions();
		Map<IReactionType, Double> REMOVE_REACTIONS = Config.REMOVE_REACTIONS;
		for (Map.Entry<IReactionType, Double> entry : REMOVE_REACTIONS.entrySet()) {
			IReactionType type = entry.getKey();
			Double num = entry.getValue();

			IReactionList<IReaction> typeReactions = reactions.getReactions(type);
			IReactionList<IReaction> randomReactions = RandomUtil
					.pickRandomReactions(num, typeReactions);
			reactions.removeAll(randomReactions);
		}
	}

}
