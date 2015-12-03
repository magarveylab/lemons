package lemons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lemons.enums.Reactions;
import lemons.enums.monomers.*;
import lemons.interfaces.IMonomerType;
import lemons.interfaces.IReactionType;

public class Config {
	
	public static final String VERSION = "0.0.1-SNAPSHOT";

	public static boolean WRITE_STRUCTURES = false;
	public static boolean GET_FINGERPRINTS = true;
	
	public static int MAX_SCAFFOLD_SIZE = 15;
	public static int MIN_SCAFFOLD_SIZE = 4;
	
	public static String BASE_DIRECTORY = "/Users/michaelskinnider/Desktop/lemons/";
	public static String TIME_DIRECTORY = BASE_DIRECTORY;
	public static String WORKING_DIRECTORY = BASE_DIRECTORY;
	
	public static int LIBRARY_SIZE = 100;
	public static int BOOTSTRAPS = 100;
	
	public static int NUM_MONOMER_SWAPS = 0;
	public static List<IMonomerType> INITIAL_MONOMERS;
	public static List<IMonomerType> SWAP_MONOMERS;
	static {
		INITIAL_MONOMERS = new ArrayList<IMonomerType>();
		SWAP_MONOMERS	 = new ArrayList<IMonomerType>();
	}
	
	public static Map<IReactionType, Double> INITIAL_REACTIONS;
	public static Map<IReactionType, Double> SWAP_REACTIONS;
	public static Map<IReactionType, Double> ADD_REACTIONS;
	public static Map<IReactionType, Double> REMOVE_REACTIONS;
	static { 
		INITIAL_REACTIONS	 = new HashMap<IReactionType, Double>();
		INITIAL_REACTIONS.put(Reactions.CYCLIZATION, 0.0d);

		SWAP_REACTIONS 		= new HashMap<IReactionType, Double>();
		SWAP_REACTIONS.put(Reactions.CYCLIZATION, 0.0d);
		
		ADD_REACTIONS 		= new HashMap<IReactionType, Double>();
		ADD_REACTIONS.put(Reactions.CYCLIZATION, 0.0d);

		REMOVE_REACTIONS 	= new HashMap<IReactionType, Double>();
		REMOVE_REACTIONS.put(Reactions.CYCLIZATION, 0.0d);
	}

}
