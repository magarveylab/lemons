package lemons;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.openscience.cdk.exception.CDKException;

import lemons.enums.Reactions;
import lemons.enums.monomers.*;
import lemons.experiments.Bootstrapper;
import lemons.experiments.Experiment;
import lemons.util.exception.FingerprintGenerationException;
import lemons.util.exception.PolymerGenerationException;

/**
 * LEMONS: Library for the Enumeration of MOdular Natural Structures. 
 * 
 * @author michaelskinnider
 *
 */
public class Main {

	public static void main(String[] args) throws CDKException,
			PolymerGenerationException, IOException,
			FingerprintGenerationException {
		Options help = createHelp();
		Options version = createVersion();
		Options options = createOptions();

		CommandLineParser parser = new DefaultParser();

		try {
			// parse help first
			CommandLine line = parser.parse(help, args, true);
			if (line.hasOption("help")) {
				HelpFormatter formatter = new HelpFormatter();
				String header = "LEMONS: Library for the Enumeration of MOdular Natural Structures\n"
						+ "Generate libraries of hypothetical natural product structures, "
						+ "and use them to compare chemical fingerprints.\n\n";
				String footer = "\nSource available at http://github.com/magarveylab/lemons";
				formatter.printHelp("lemons", header, options, footer, true);
			} else { 
				line = parser.parse(version, args, true);
				if (line.hasOption("version")) {
					System.out.println("LEMONS: version " + Config.VERSION);
				} else {
					line = parser.parse(options, args);
					parseCommandLine(line);
					Bootstrapper.bootstrap(new Experiment());
				}
			} 
		} catch (UnsupportedEncodingException e) {
			handleException(e, "Error: unsupported encoding!");
		} catch (ParseException e) {
			handleException(e, "Error parsing command line arguments!");
		} catch (IllegalArgumentException e) {
			handleException(e, "Error: illegal argument!");
		} catch (Exception e) {
			handleException(e, "Error!");
		} finally {
			System.exit(0);
		}
	}	

	/**
	 * Create the help option.
	 * 
	 * @return newly created help option
	 */
	public static Options createHelp() {
		Options options = new Options();
		Option help = new Option("h", "help", false, "Print this message");
		options.addOption(help);
		return options;
	}
	
	/**
	 * Create the version option.
	 * 
	 * @return newly created version option
	 */
	public static Options createVersion() {
		Options options = new Options();
		Option version = new Option("v", "version", false, "Print the current version and exit");
		options.addOption(version);
		return options;
	}

	/**
	 * Create all other command line options.
	 * 
	 * @return newly created options
	 */
	public static Options createOptions() {
		Options options = new Options();
		
		// construct boolean options 
		Option writeStructures = Option.builder("w").longOpt("write")
				.argName("write")
				.desc("Write generated natural product SMILES to text file")
				.build();
		Option testFingerprints = Option.builder("f").longOpt("fp")
				.argName("fingerprint")
				.desc("Generate fingerprints and rank Tanimoto coefficients for generated structures")
				.build();
		Option terminalCooh = Option.builder("c").longOpt("c")
				.argName("terminal_cooh")
				.desc("Automatically end natural product structures with a C-terminal carboxylic acid")
				.build();
		
		// construct options with one value 
		Option minSize = Option.builder().longOpt("min_size")
				.argName("min scaffold size")
				.desc("The minimum size, in monomers, for the scaffold")
				.hasArg().build();
		Option maxSize = Option.builder().longOpt("max_size")
				.argName("max scaffold size")
				.desc("The maximum size, in monomers, for the scaffold")
				.hasArg().build();
		Option baseDir = Option.builder("dir").longOpt("base_dir")
				.argName("base directory")
				.desc("Directory in which all bootstrap and experiment files will be generated")
				.hasArg().build();
		Option libSize = Option.builder("l").longOpt("lib_size")
				.argName("library size")
				.desc("The size of a single library in one of multiple bootstraps")
				.hasArg().build();
		Option bootstraps = Option.builder("b").longOpt("bootstraps")
				.argName("bootstraps")
				.desc("The number of bootstraps to use per experiment")
				.hasArg().build();
		Option numSwaps = Option.builder().longOpt("swaps")
				.argName("monomer swaps")
				.desc("The number of monomer swaps to execute")
				.hasArg().build();
		Option seed = Option.builder().longOpt("seed")
				.argName("seed")
				.desc("The seed for the random number generator")
				.hasArg().build();

		// construct options with multiple values 
		Option initialMonomers = Option.builder().longOpt("initial_monomers")
				.argName("initial scaffold monomers").desc("Monomers used to construct the initial scaffolds.\n"
						+ "Options: p, proteinogenic amino acids; np, nonproteinogenic amino acids;"
						+ "pk, polyketide monomers; s, starter units"
				).hasArgs().build();
		Option swapMonomers = Option.builder().longOpt("swap_monomers")
				.argName("swap scaffold monomers").desc("Monomers that can be swapped into a modified scaffold.\n"
						+ "Options: p, proteinogenic amino acids; np, nonproteinogenic amino acids;"
						+ "pk, polyketide monomers; s, starter units"
				).hasArgs().build();
		Option initialReactions = Option.builder().longOpt("initial_reactions")
				.argName("initial reactions").desc("Reactions that can be swapped into a modified scaffold.\n"
						+ "Specify the reaction and the number of times that it is executed.\n"
						+ "The execution number can be probabilistic: e.g. 0.33 means the reaction will be "
						+ "executed on 1/3 of  scaffolds.\n"
						+ "Example: --initial_reactions glycosylation 1.5 cyclization 1\n"
						+ "Options: cyclization, azole, glycosylation, n_methylation, halogenation, random.\n"
						+ "This option MUST have an even number of arguments!"
				).hasArgs().build();
		Option addReactions = Option.builder().longOpt("add_reactions")
				.argName("initial reactions").desc("Reactions that can be added to a modified scaffold.\n"
						+ "Usage is the same as initial_reactions."
				).hasArgs().build();
		Option swapReactions = Option.builder().longOpt("swap_reactions")
				.argName("swap reactions").desc("Reactions that can be swapped into a modified scaffold.\n"
						+ "Usage is the same as initial_reactions."
				).hasArgs().build();
		Option removeReactions = Option.builder().longOpt("remove_reactions")
				.argName("remove reactions").desc("Reactions that can be removed from the original scaffold.\n"
						+ "Usage is the same as initial_reactions."
				).hasArgs().build();

		options.addOption(writeStructures);
		options.addOption(testFingerprints);
		options.addOption(minSize);
		options.addOption(maxSize);
		options.addOption(baseDir);
		options.addOption(libSize);
		options.addOption(bootstraps);
		options.addOption(numSwaps);
		options.addOption(seed);
		options.addOption(initialMonomers);
		options.addOption(swapMonomers);
		options.addOption(initialReactions);
		options.addOption(addReactions);
		options.addOption(swapReactions);
		options.addOption(removeReactions);
		options.addOption(terminalCooh);
		
		return options;
	}

	/**
	 * Parse the command line input.
	 * 
	 * @param line
	 *            line to parse
	 * @throws ParseException
	 */
	public static void parseCommandLine(CommandLine line) throws ParseException {
		// parse boolean options
		if (line.hasOption("w")) {
			Config.WRITE_STRUCTURES = true;
		}
		if (line.hasOption("f")) {
			Config.GET_FINGERPRINTS = true;
		}
		
		// parse options with arguments 
		if (line.hasOption("min_size")) {
			String value = line.getOptionValue("min_size");
			Config.MIN_SCAFFOLD_SIZE = Integer.parseInt(value);

		}
		if (line.hasOption("max_size")) {
			String value = line.getOptionValue("max_size");
			Config.MAX_SCAFFOLD_SIZE = Integer.parseInt(value);
		}
		if (line.hasOption("base_dir")) {
			Config.BASE_DIRECTORY = line.getOptionValue("base_dir");
		}
		if (line.hasOption("lib_size")) {
			String value = line.getOptionValue("lib_size");
			Config.LIBRARY_SIZE = Integer.parseInt(value);
		}
		if (line.hasOption("bootstraps")) {
			String value = line.getOptionValue("bootstraps");
			Config.BOOTSTRAPS = Integer.parseInt(value);
		}
		if (line.hasOption("swaps")) {
			String value = line.getOptionValue("swaps");
			Config.NUM_MONOMER_SWAPS = Integer.parseInt(value);
		}
		if (line.hasOption("seed")) {
			String value = line.getOptionValue("seed");
			Config.SEED = Integer.parseInt(value);
		}

		// parse options with multiple arguments
		if (line.hasOption("initial_monomers")) {
			String[] values = line.getOptionValues("initial_monomers");
			for (String v : values) {
				if (!v.equals("np") && !v.equals("p") && !v.equals("pk")
						&& !v.equals("s"))
					throw new IllegalArgumentException(
							"Illegal initial_monomers value: " + v);
				if (v.equals("np"))
					Config.INITIAL_MONOMERS.addAll(Arrays
							.asList(NonProteinogenicAminoAcids.values()));
				if (v.equals("p"))
					Config.INITIAL_MONOMERS.addAll(Arrays
							.asList(ProteinogenicAminoAcids.values()));
				if (v.equals("pk"))
					Config.INITIAL_MONOMERS.addAll(Arrays
							.asList(PolyketideMonomers.values()));
				if (v.equals("s"))
					Config.INITIAL_MONOMERS.addAll(Arrays
							.asList(Starters.values()));
			}
		}
		if (line.hasOption("swap_monomers")) {
			String[] values = line.getOptionValues("swap_monomers");
			for (String v : values) {
				if (!v.equals("np") && !v.equals("p") && !v.equals("pk")
						&& !v.equals("s"))
					throw new IllegalArgumentException(
							"Illegal swap_monomers value: " + v);
				if (v.equals("np"))
					if (v.equals("np"))
						Config.SWAP_MONOMERS.addAll(Arrays
								.asList(NonProteinogenicAminoAcids.values()));
					if (v.equals("p"))
						Config.SWAP_MONOMERS.addAll(Arrays
								.asList(ProteinogenicAminoAcids.values()));
					if (v.equals("pk"))
						Config.SWAP_MONOMERS.addAll(Arrays
								.asList(PolyketideMonomers.values()));
					if (v.equals("s"))
						Config.SWAP_MONOMERS.addAll(Arrays
								.asList(Starters.values()));
			}
		}
		if (line.hasOption("initial_reactions")) {
			String[] values = line.getOptionValues("initial_reactions");
			if (values.length % 2 != 0)
				throw new IllegalArgumentException("Illegal number of arguments for "
						+ "parameter initial_reactions: must be an even number!");
			for (int i = 0; i < values.length - 1; i += 2) {
				String reaction = values[i];
				String num = values[i+1];
				if (getReaction(reaction) == null)
					throw new IllegalArgumentException("Illegal reaction type in initial_reactions: " + reaction);
				Config.INITIAL_REACTIONS.put(getReaction(reaction), Double.parseDouble(num));
			}
		}
		if (line.hasOption("add_reactions")) {
			String[] values = line.getOptionValues("add_reactions");
			if (values.length % 2 != 0)
				throw new IllegalArgumentException("Illegal number of arguments for "
						+ "parameter add_reactions: must be an even number!");
			for (int i = 0; i < values.length - 1; i += 2) {
				String reaction = values[i];
				String num = values[i+1];
				if (getReaction(reaction) == null)
					throw new IllegalArgumentException("Illegal reaction type in add_reactions: " + reaction);
				Config.ADD_REACTIONS.put(getReaction(reaction), Double.parseDouble(num));
			}
		}
		if (line.hasOption("swap_reactions")) {
			String[] values = line.getOptionValues("swap_reactions");
			if (values.length % 2 != 0)
				throw new IllegalArgumentException("Illegal number of arguments for "
						+ "parameter swap_reactions: must be an even number!");
			for (int i = 0; i < values.length - 1; i += 2) {
				String reaction = values[i];
				String num = values[i+1];
				if (getReaction(reaction) == null)
					throw new IllegalArgumentException("Illegal reaction type in swap_reactions: " + reaction);
				Config.SWAP_REACTIONS.put(getReaction(reaction), Double.parseDouble(num));
			}
		}
		if (line.hasOption("remove_reactions")) {
			String[] values = line.getOptionValues("remove_reactions");
			if (values.length % 2 != 0)
				throw new IllegalArgumentException("Illegal number of arguments for "
						+ "parameter remove_reactions: must be an even number!");
			for (int i = 0; i < values.length - 1; i += 2) {
				String reaction = values[i];
				String num = values[i+1];
				if (getReaction(reaction) == null)
					throw new IllegalArgumentException("Illegal reaction type in remove_reactions: " + reaction);
				Config.REMOVE_REACTIONS.put(getReaction(reaction), Double.parseDouble(num));
			}
		}

	}
	
	/**
	 * Get the reaction that corresponds to a string.
	 * 
	 * @param arg
	 *            the string passed into the command line as an argument
	 * @return the reaction, or null if the argument is invalid
	 */
	public static Reactions getReaction(String arg) {
		Reactions reaction = null;
		Reactions[] reactions = Reactions.values();
		for (Reactions r : reactions)
			if (arg.toLowerCase().equals(r.toString().toLowerCase()))
				reaction = r;
		return reaction;
	}
	
	/**
	 * Handle an exception by printing the stack trace to the command line, and
	 * exiting.
	 * 
	 * @param e
	 *            exception
	 * @param message
	 *            message associated with the exception cause
	 */
	public static void handleException(Exception e, String message) {
		System.out.println("[LEMONS] " + message);
		e.printStackTrace();
		System.exit(0);
	}

}
