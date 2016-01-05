package lemons;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import lemons.experiments.Bootstrapper;
import lemons.experiments.Experiment;
import lemons.interfaces.IScaffold;
import lemons.io.SmilesReader;
import lemons.util.FingerprintUtil;
import lemons.util.IOUtil;
import lemons.util.exception.FingerprintGenerationException;
import lemons.util.exception.PolymerGenerationException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.openscience.cdk.exception.CDKException;

public class EntryForChris {

	public static void main(String[] args) throws CDKException,
	PolymerGenerationException, IOException,
	FingerprintGenerationException {
		Options help = Main.createHelp();
		Options version = Main.createVersion();
		Options options = Main.createOptions();

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
					Main.parseCommandLine(line);
					
					// check directories 
					Bootstrapper.checkBaseDirectory();
					// Bootstrapper.setWorkingDirectory();
//					
//					// write config file
//					String config = Config.TIME_DIRECTORY
//							+ File.separator + "config.txt";
//					ConfigWriter.write(config);
				
					// run experiment
					Config.WORKING_DIRECTORY = Config.BASE_DIRECTORY
								+ File.separator + "Bootstrap_" + Config.BOOTSTRAPS;
						IOUtil.checkDir(Config.WORKING_DIRECTORY);
						System.out.println(Config.WORKING_DIRECTORY);
					// read scaffolds
					List<IScaffold> scaffolds = SmilesReader
							.read(Config.WORKING_DIRECTORY + File.separator
									+ "scaffolds.tsv");
					List<IScaffold> swapScaffolds = SmilesReader
							.read(Config.WORKING_DIRECTORY + File.separator
									+ "swap_scaffolds.tsv");
					
					// generate fingerprints
					if (Config.GET_FINGERPRINTS) {
						FingerprintUtil.setFingerprints(scaffolds);
						FingerprintUtil.setFingerprints(swapScaffolds);

						// calculate all Tanimoto coefficients
						Experiment.calculateRanks(scaffolds, swapScaffolds);
					}
				}
			}
			
		} catch (UnsupportedEncodingException e) {
			Main.handleException(e, "Error: unsupported encoding!");
		} catch (ParseException e) {
			Main.handleException(e, "Error parsing command line arguments!");
		} catch (IllegalArgumentException e) {
			Main.handleException(e, "Error: must specify input sequence file!");
		} catch (Exception e) {
			Main.handleException(e, "Error!");
		} finally {
			System.exit(0);
		}
	}	

}
