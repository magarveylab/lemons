package lemons;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import lemons.enums.ReactionTypes;
import lemons.enums.monomers.ProteinogenicAminoAcids;
import lemons.interfaces.IMonomerType;
import lemons.interfaces.IReactionType;

public class Config {
	
	public static int MAX_SCAFFOLD_SIZE = 15;
	public static int MIN_SCAFFOLD_SIZE = 4;
	
	public static String BASE_DIRECTORY = "/Users/michaelskinnider/Desktop/lemons/";
	public static String TIME_DIRECTORY = BASE_DIRECTORY;
	public static String WORKING_DIRECTORY = BASE_DIRECTORY;
	
	public static int LIBRARY_SIZE = 100;
	public static int BOOTSTRAPS = 100;
	
	public static IMonomerType[] INITIAL_MONOMERS = ArrayUtils.addAll(
			ProteinogenicAminoAcids.values());
	public static IMonomerType[] SWAP_MONOMERS = ArrayUtils
			.addAll(ProteinogenicAminoAcids.values());
	public static int NUM_MONOMER_SWAPS = 0;
	
	public static Map<IReactionType, Double> INITIAL_REACTIONS;
	public static Map<IReactionType, Double> SWAP_REACTIONS;
	public static Map<IReactionType, Double> ADD_REACTIONS;
	public static Map<IReactionType, Double> REMOVE_REACTIONS;
	static { 
		INITIAL_REACTIONS	 = new HashMap<IReactionType, Double>();
		INITIAL_REACTIONS.put(ReactionTypes.CYCLIZATION, 1.0d);

		SWAP_REACTIONS 		= new HashMap<IReactionType, Double>();
		INITIAL_REACTIONS.put(ReactionTypes.CYCLIZATION, 1.0d);
		
		ADD_REACTIONS 		= new HashMap<IReactionType, Double>();
		REMOVE_REACTIONS 	= new HashMap<IReactionType, Double>();
	
	}

}
