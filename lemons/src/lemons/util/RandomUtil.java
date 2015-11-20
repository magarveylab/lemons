package lemons.util;

import java.util.Random;

import lemons.enums.monomers.ProteinogenicAminoAcids;
import lemons.interfaces.IMonomerType;
import lemons.interfaces.ITag;
import lemons.interfaces.ITagList;
import lemons.util.exception.BadTagException;

public class RandomUtil {

	public static int randomInt(int min, int max) {
		Random rand = new Random();
		int size = rand.nextInt((max - min) + 1) + min;
		return size;
	}
	
	public static IMonomerType getRandomProteinogenicAminoAcid() {
		ProteinogenicAminoAcids[] aas = ProteinogenicAminoAcids.values();
		int index = randomInt(0, aas.length - 1);
		IMonomerType monomer = aas[index];
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

}
