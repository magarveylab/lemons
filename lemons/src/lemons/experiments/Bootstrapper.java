package lemons.experiments;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openscience.cdk.exception.CDKException;

import lemons.Config;
import lemons.interfaces.IExperiment;
import lemons.io.ConfigWriter;
import lemons.util.exception.FingerprintGenerationException;
import lemons.util.exception.PolymerGenerationException;

import skinnider.utilities.io.IOUtil;

public class Bootstrapper {
	
	private static final Logger logger = Logger.getLogger(Bootstrapper.class.getName());
	
	public static void bootstrap(IExperiment experiment)
			throws CDKException, PolymerGenerationException, IOException,
			FingerprintGenerationException {
		// check directories 
		checkBaseDirectory();
		setWorkingDirectory();
		
		// write config file
		String config = Config.TIME_DIRECTORY
				+ File.separator + "config.txt";
		ConfigWriter.write(config);
	
		// run experiments
		for (int i = 0; i < Config.BOOTSTRAPS; i++) {
			logger.log(Level.INFO, "Running bootstrap " + i);
			Config.WORKING_DIRECTORY = Config.TIME_DIRECTORY
					+ File.separator + "Bootstrap_" + (i + 1);
			IOUtil.checkDir(Config.WORKING_DIRECTORY);
			experiment.run();
			logger.log(Level.INFO, "Finished bootstrap " + (i+1));
		}
	}
	
	public static void bootstrap(String title, IExperiment experiment)
			throws CDKException, PolymerGenerationException, IOException,
			FingerprintGenerationException {
		// check directories 
		checkBaseDirectory();
		setWorkingDirectory(title);
		
		// write config file
		String config = Config.BASE_DIRECTORY + File.separator
				+ title + File.separator + "config.txt";
		ConfigWriter.write(config);
	
		// run experiments
		for (int i = 0; i < Config.BOOTSTRAPS; i++) {
			logger.log(Level.INFO, "Running bootstrap " + i);
			Config.WORKING_DIRECTORY = Config.BASE_DIRECTORY + File.separator
					+ title + File.separator + "Bootstrap_" + (i + 1);
			IOUtil.checkDir(Config.WORKING_DIRECTORY);
			experiment.run();
		}
	}
	
	public static void setWorkingDirectory() {
		// set working directory 
		Date initDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HHmm-ss");
		String date = sdf.format(initDate);
		Config.TIME_DIRECTORY = Config.BASE_DIRECTORY + File.separator + date;
		IOUtil.checkDir(Config.TIME_DIRECTORY);
	}
	
	public static void setWorkingDirectory(String title) {
		// set working directory 
		String dir = Config.BASE_DIRECTORY + File.separator
				+ title;
		IOUtil.checkDir(dir);
	}
	
	public static void checkBaseDirectory() {
		// check base dir 
		IOUtil.checkDir(Config.BASE_DIRECTORY);
	}
	
}
