package lemons.util;

import java.io.File;

/**
 * Utilities class for simple I/O operations.
 * 
 * @author michaelskinnider
 *
 */
public class IOUtil {

	/**
	 * Make sure a directory exists, and create the directory if it does not
	 * exist.
	 * 
	 * @param dir
	 *            directory to check
	 */
	public static void checkDir(String dir) {
		File file = new File(dir);
		if (!file.exists())
			file.mkdir();
	}

}
