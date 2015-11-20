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

	public static void bootstrap(IExperiment experiment, int bootstraps)
			throws CDKException, PolymerGenerationException, IOException,
			FingerprintGenerationException {
		setWorkingDirectory();
		for (int i = 0; i < bootstraps; i++) {
			Config.WORKING_DIRECTORY = Config.TIME_DIRECTORY
					+ File.separator + "Bootstrap_" + (i + 1);
			IOUtil.checkDir(Config.WORKING_DIRECTORY);
			experiment.run();
		}
	}
	
	public static void setWorkingDirectory() {
		// check base dir 
		IOUtil.checkDir(Config.BASE_DIRECTORY);
		
		// set working directory 
		Date initDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HHmm-ss");
		String date = sdf.format(initDate);
		Config.TIME_DIRECTORY = Config.BASE_DIRECTORY + File.separator + date;
		IOUtil.checkDir(Config.TIME_DIRECTORY);
	}
	
}
