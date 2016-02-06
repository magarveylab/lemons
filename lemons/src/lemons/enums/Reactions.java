package lemons.enums;

import lemons.interfaces.IReactionPlanner;
import lemons.interfaces.IReactionType;
import lemons.scaffold.reaction.*;

/**
 * Tailoring reactions that can be executed within LEMONS.
 * 
 * @author michaelskinnider
 *
 */
public enum Reactions implements IReactionType {

	CYCLIZATION(new Cyclization()), 
	AZOLE(new Azole()), 
	GLYCOSYLATION(new Glycosylation()), 
	N_METHYLATION(new NMethylation()),
	HALOGENATION(new Halogenation()),
	RANDOM(new RandomBondReaction()),
	;
	
	private IReactionPlanner planner;
	
	private Reactions(IReactionPlanner planner) {
		this.planner = planner;
	}
	
	public IReactionPlanner planner() {
		return planner;
	}

}
