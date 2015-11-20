package lemons.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lemons.Config;
import lemons.fingerprint.Fingerprinters;
import lemons.interfaces.ITanimotoCoefficient;
import lemons.interfaces.ITanimotoCoefficientList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class ExperimentWriter {
	
	public static void writeRow(ITanimotoCoefficient tc,
			ITanimotoCoefficientList<ITanimotoCoefficient> tcList,
			Fingerprinters fp) throws IOException {
		// get file 
		File file = new File(Config.WORKING_DIRECTORY + File.separator + fp.toString() + ".csv");

		// check file exists
		boolean exists = file.exists();
		
		// create file writer
		CSVFormat csvFileFormat = CSVFormat.EXCEL;
		CSVPrinter cw = new CSVPrinter(new FileWriter(file, true),
				csvFileFormat);
		
		// write header, if not already written 
		if (!exists) {
			List<String> header = new ArrayList<String>();
			header.add("Name");
			header.add("Value");
			header.add("Rank");
			cw.printRecord(header);
		}
		
		// append row
		List<String> row = new ArrayList<String>();
		row.add(tc.name1());
		row.add(tc.value() + "");
		row.add((tcList.indexOf(tc) + 1) + "");
		cw.printRecord(row);
		
		cw.close();
	}

}
