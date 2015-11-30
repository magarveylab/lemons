package lemons.util;

import java.util.Random;

import lemons.data.ReactionList;
import lemons.interfaces.IMonomerType;
import lemons.interfaces.IReaction;
import lemons.interfaces.IReactionList;
import lemons.interfaces.ITag;
import lemons.interfaces.ITagList;
import lemons.util.exception.BadTagException;

public class RandomUtil {

	public static int randomInt(int min, int max) {
		Random rand = new Random();
		int size = rand.nextInt((max - min) + 1) + min;
		return size;
	}
	
	public static IMonomerType getRandomMonomer(IMonomerType[] monomers) {
		int index = randomInt(0, monomers.length - 1);
		IMonomerType monomer = monomers[index];
		return monomer;
	}
	
	public static ITag getRandomTag(ITagList<ITag> tags) throws BadTagException {
		int size = tags.size();
		if (size == 0)
			throw new BadTagException("Error: could not "
					+ "get random tag from empty list!");
		int idx = randomInt(0, size - 1);
		return tags.get(idx);
	}

	public static IReactionList<IReaction> pickRandomReactions(double num,
			IReactionList<IReaction> allReactions) {
		IReactionList<IReaction> randomReactions = new ReactionList();
		if (allReactions.size() == 0) {
			return randomReactions;
		} else if (num >= allReactions.size()) {
			randomReactions.addAll(allReactions);
			return randomReactions;
		} else {
			// if probability is 2.33: remove 2 random reactions,
			// plus remove another one 0.33% of the time
			for (int k = 0; k < Math.floor(num); k++) {
				int r = RandomUtil.randomInt(0, allReactions.size());
				IReaction reaction = allReactions.get(r);
				randomReactions.add(reaction);
			}
			double probability = num - Math.floor(num);
			double randomProbability = new Random().nextDouble();
			if (randomProbability > probability) {
				int r = RandomUtil.randomInt(0, allReactions.size());
				IReaction reaction = allReactions.get(r);
				randomReactions.add(reaction);
			}
			return randomReactions;
		}
	}

}
