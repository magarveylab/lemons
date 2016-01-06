package lemons.scaffold;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openscience.cdk.exception.CDKException;
import lemons.interfaces.IReaction;
import lemons.interfaces.IReactionPlanner;
import lemons.interfaces.IReactionType;
import lemons.interfaces.IScaffold;
import lemons.io.SmilesIO;
import lemons.util.exception.PolymerGenerationException;

public class ReactionExecutor {

	private static final Logger logger = Logger
			.getLogger(ReactionExecutor.class.getName());

	public static void executeReactions(List<IScaffold> scaffolds)
			throws PolymerGenerationException, CDKException {
		for (IScaffold s : scaffolds)
			executeReactions(s);
	}

	public static void executeReactions(IScaffold scaffold)
			throws PolymerGenerationException, CDKException {
		for (IReaction reaction : scaffold.reactions()) {
			String originalSmiles = SmilesIO.smiles(scaffold.molecule());
			try {
				IReactionType type = reaction.type();
				IReactionPlanner planner = type.planner();
				planner.execute(reaction, scaffold);
				logger.log(Level.INFO,
						"Executed " + type + " reaction, new scaffold: "
								+ SmilesIO.smiles(scaffold.molecule()));
			} catch (IllegalArgumentException e) {
				throw new PolymerGenerationException("Could not execute "
						+ reaction.type()
						+ " reaction on molecule with SMILES " + originalSmiles
						+ ". Underlying exception: " + e.getMessage());
			}
		}
	}

}
