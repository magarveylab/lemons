package lemons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lemons.interfaces.IMonomerType;
import lemons.interfaces.IReactionType;

/**
 * Configuration for a LEMONS experiment.
 * 
 * @author michaelskinnider
 *
 */
public class Config {

	/**
	 * The version of LEMONS which is being run.
	 */
	public static final String VERSION = "0.0.1-SNAPSHOT";

	/**
	 * If true, writes all generated structures (both original and swapped
	 * scaffolds) to a file.
	 */
	public static boolean WRITE_STRUCTURES = false;

	/**
	 * If true, generates fingerprint rank distributions and writes them to
	 * files.
	 */
	public static boolean GET_FINGERPRINTS = false;

	/**
	 * The maximum number of monomers in generated natural product scaffolds.
	 */
	public static int MAX_SCAFFOLD_SIZE = 15;

	/**
	 * The minimum number of monomers in generated natural product scaffolds.
	 */
	public static int MIN_SCAFFOLD_SIZE = 4;

	/**
	 * Automatically end a structure with a C-terminal carboxylic acid. 
	 */
	public static boolean TERMINAL_COOH = false;
	
	/**
	 * The directory in which to write all subdirectories and files generated by
	 * a LEMONS experiment.
	 */
	public static String BASE_DIRECTORY = "/Users/michaelskinnider/Desktop/lemons/";
	public static String TIME_DIRECTORY = BASE_DIRECTORY;
	public static String WORKING_DIRECTORY = BASE_DIRECTORY;

	/**
	 * The size of the libraries to be generated in each LEMONS experiment.
	 */
	public static int LIBRARY_SIZE = 100;

	/**
	 * The number of bootstraps to perform.
	 */
	public static int BOOTSTRAPS = 100;
	
	/**
	 * The seed for the random number generator. 
	 */
	public static int SEED = -1;

	/**
	 * The number of monomers to swap between original and swapped scaffolds.
	 */
	public static int NUM_MONOMER_SWAPS = 0;

	/**
	 * The monomers to be used in the construction of the initial scaffolds.
	 */
	public static List<IMonomerType> INITIAL_MONOMERS;

	/**
	 * The monomers to be used in swapping monomers in the initial scaffolds to
	 * create swapped scaffolds.
	 */
	public static List<IMonomerType> SWAP_MONOMERS;
	static {
		INITIAL_MONOMERS = new ArrayList<IMonomerType>();
		SWAP_MONOMERS = new ArrayList<IMonomerType>();
	}

	/**
	 * Reactions to execute on the initial set of scaffolds. 
	 */
	public static Map<IReactionType, Double> INITIAL_REACTIONS;
	
	/**
	 * Reactions to be executed on the initial set of scaffolds, then executed
	 * again on the swapped scaffolds at a different site: e.g. for
	 * macrocyclization, LEMONS will attempt to modify the residue which is
	 * macrocyclized.
	 */
	public static Map<IReactionType, Double> SWAP_REACTIONS;
	
	/**
	 * Reactions to add to the swapped scaffolds. 
	 */
	public static Map<IReactionType, Double> ADD_REACTIONS;
	
	/**
	 * Reactions to be removed from the initial scaffolds. 
	 */
	public static Map<IReactionType, Double> REMOVE_REACTIONS;
	static {
		INITIAL_REACTIONS 	= new HashMap<IReactionType, Double>();
		SWAP_REACTIONS 		= new HashMap<IReactionType, Double>();
		ADD_REACTIONS 		= new HashMap<IReactionType, Double>();
		REMOVE_REACTIONS 	= new HashMap<IReactionType, Double>();
	}

}
