package lemons.experiments;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openscience.cdk.exception.CDKException;

import lemons.Config;
import lemons.interfaces.IExperiment;
import lemons.util.IOUtil;
import lemons.util.exception.FingerprintGenerationException;
import lemons.util.exception.PolymerGenerationException;

public class Bootstrapper {
	
	public static void bootstrap(IExperiment experiment)
			throws CDKException, PolymerGenerationException, IOException,
			FingerprintGenerationException {
		checkBaseDirectory();
		setWorkingDirectory();
		for (int i = 0; i < Config.BOOTSTRAPS; i++) {
			Config.WORKING_DIRECTORY = Config.TIME_DIRECTORY
					+ File.separator + "Bootstrap_" + (i + 1);
			IOUtil.checkDir(Config.WORKING_DIRECTORY);
			experiment.run();
		}
	}
	
	public static void bootstrap(String title, IExperiment experiment)
			throws CDKException, PolymerGenerationException, IOException,
			FingerprintGenerationException {
		checkBaseDirectory();
		setWorkingDirectory(title);
		for (int i = 0; i < Config.BOOTSTRAPS; i++) {
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
