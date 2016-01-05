package lemons.interfaces;

import lemons.util.exception.BadTagException;
import lemons.util.exception.PolymerGenerationException;

import org.openscience.cdk.exception.CDKException;

/**
 * A tailoring reaction which acts on a natural product scaffold.  
 * @author michaelskinnider
 *
 */
public interface IReactionPlanner {

	/**
	 * Detect potential sites at which this tailoring reaction can act.
	 * 
	 * @param numReactions
	 *            the number of times to execute this reaction; a probabilistic
	 *            double (e.g. 0.5 means every 2nd scaffold will have a reaction
	 *            detected)
	 * @param scaffold
	 *            the natural product scaffold to tailor
	 * @throws BadTagException
	 * @throws CDKException
	 */
	public void perceive(double numReactions, IScaffold scaffold) throws BadTagException, CDKException;
	
	/**
	 * Execute this tailoring reaction.
	 * 
	 * @param reaction
	 *            the tailoring reaction which has been detected
	 * @param scaffold
	 *            the natural product scaffold to tailor
	 * @throws PolymerGenerationException
	 * @throws CDKException
	 */
	public void execute(IReaction reaction, IScaffold scaffold)
			throws PolymerGenerationException, CDKException;

}
