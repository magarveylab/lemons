package lemons;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import lemons.enums.ReactionTypes;
import lemons.enums.monomers.ProteinogenicAminoAcids;
import lemons.interfaces.IMonomerType;
import lemons.interfaces.IReactionType;

public class Config {
	
	public static int MAX_SIZE = 15;
	public static int MIN_SIZE = 4;
	public static String BASE_DIRECTORY = "/Users/michaelskinnider/Desktop/lemons/";
	public static String TIME_DIRECTORY = BASE_DIRECTORY;
	public static String WORKING_DIRECTORY = BASE_DIRECTORY;

	public static IMonomerType[] MONOMERS = ArrayUtils.addAll(
			ProteinogenicAminoAcids.values());
	public static Map<IReactionType, Integer> REACTIONS;
	static {
		REACTIONS = new HashMap<IReactionType, Integer>();
		REACTIONS.put(ReactionTypes.CYCLIZATION, 0);
		REACTIONS.put(ReactionTypes.AZOLE, 0);
	}
	
}
