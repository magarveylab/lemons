package lemons.util;

import java.util.List;
import java.util.Random;

import lemons.Config;
import lemons.data.ReactionList;
import lemons.interfaces.IMonomerType;
import lemons.interfaces.IReaction;
import lemons.interfaces.IReactionList;
import lemons.interfaces.ITag;
import lemons.interfaces.ITagList;
import lemons.util.exception.BadTagException;

/**
 * Utilities class for operations involving random numbers.
 * 
 * @author michaelskinnider
 *
 */
public class RandomUtil {
	
	/**
	 * A random number generator which may optionally be seeded through the command line. 
	 */
	private static Random rand;
	static { 
		if (Config.SEED > 0) {
			rand = new Random(Config.SEED); 
		} else {
			rand = new Random();
		}
	}

	/**
	 * Generate a random integer between two bounds, inclusive.
	 * 
	 * @param min
	 *            the minimum value of the random integer
	 * @param max
	 *            the maximum value of the random integer
	 * @return a random integer within the specified bounds
	 */
	public static int randomInt(int min, int max) {
		int size = rand.nextInt((max - min) + 1) + min;
		return size;
	}
	
	/**
	 * Get a random monomer from a list of monomers.
	 * 
	 * @param monomers
	 *            list of monomers to select from
	 * @return a random monomer from the given list
	 */
	public static IMonomerType getRandomMonomer(List<IMonomerType> monomers) {
		int index = randomInt(0, monomers.size() - 1);
		IMonomerType monomer = monomers.get(index);
		return monomer;
	}
	
	/**
	 * Get a random monomer from an array of monomers.
	 * 
	 * @param monomers
	 *            array of monomers to select from
	 * @return a random monomer from the given array
	 */
	public static IMonomerType getRandomMonomer(IMonomerType[] monomers) {
		int index = randomInt(0, monomers.length - 1);
		IMonomerType monomer = monomers[index];
		return monomer;
	}
	
	/**
	 * Get a random tag from a list of tags.
	 * 
	 * @param tags
	 *            list of tags to select from
	 * @return a random tag from the given list
	 * @throws BadTagException
	 *             if the tag list is empty
	 */
	public static ITag getRandomTag(ITagList<ITag> tags) throws BadTagException {
		int size = tags.size();
		if (size == 0)
			throw new BadTagException("Error: could not "
					+ "get random tag from empty list!");
		int idx = randomInt(0, size - 1);
		return tags.get(idx);
	}

	/**
	 * Pick a random set of reactions probabilistically from a set of reactions.
	 * 
	 * @param num
	 *            the number of reactions to select, as a double
	 * @param allReactions
	 *            the complete set of reactions to choose from
	 * @return an empty list if the original list is empty; the complete list,
	 *         if its size is less than the given number of reactions to select;
	 *         or a probabilistic selection of reactions, if neither of the
	 *         above is true
	 */
	public static IReactionList<IReaction> pickRandomReactions(double num,
			IReactionList<IReaction> allReactions) {
		IReactionList<IReaction> randomReactions = new ReactionList();
		if (allReactions.size() == 0) {
			return randomReactions;
		} else if (num >= allReactions.size()) {
			randomReactions.addAll(allReactions);
			return randomReactions;
		} else {
			// EXAMPLE: if probability is 2.33: remove 2 random reactions ... 
			boolean[] used = new boolean[allReactions.size()];
			for (int k = 0; k < Math.floor(num); k++) {
				int r = -1;
				while (r == -1 || used[r] == true)
					r = RandomUtil.randomInt(0, allReactions.size() - 1);
				used[r] = true; 
				IReaction reaction = allReactions.get(r);
				randomReactions.add(reaction);
			}
			
			// ... plus remove another one 0.33% of the time
			double probability = num - Math.floor(num);
			double randomDouble = new Random().nextDouble();
			if (randomDouble < probability) {
				int r = -1;
				while (r == -1 || used[r] == true)
					r = RandomUtil.randomInt(0, allReactions.size() - 1);
				used[r] = true; 
				IReaction reaction = allReactions.get(r);
				randomReactions.add(reaction);
			}
			return randomReactions;
		}
	}

}
