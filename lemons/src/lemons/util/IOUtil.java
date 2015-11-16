package lemons.util;

import java.io.File;

public class IOUtil {
	
	public static void checkDir(String dir) {
		File file = new File(dir);
		if (!file.exists()) 
			file.mkdir();
	}

}
