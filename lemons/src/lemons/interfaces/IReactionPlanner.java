package lemons.interfaces;

import lemons.util.exception.BadTagException;
import lemons.util.exception.PolymerGenerationException;

import org.openscience.cdk.exception.CDKException;

public interface IReactionPlanner {

	public void perceive(double numReactions, IScaffold scaffold) throws BadTagException, CDKException;
	
	public void execute(IReaction reaction, IScaffold scaffold) throws PolymerGenerationException, CDKException;
	
}
