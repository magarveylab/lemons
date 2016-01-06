package lemons.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import lemons.Config;
import lemons.interfaces.IMonomerType;
import lemons.interfaces.IReactionType;

/**
 * Write the configuration of an experiment to a file.
 * 
 * @author michaelskinnider
 *
 */
public class ConfigWriter {
	
	public static void write(String filepath) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(filepath));
		bw.append("LEMONS\n");
		bw.append("-----\n");
		bw.append("Version " + Config.VERSION + "\n");
		
		// append date
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd yyyy, kk:mm:ss");
		bw.append("Wrote config file at " + sdf.format(date) + "\n\n");
				
		// append settings
		bw.append("Settings\n");
		bw.append("-----\n");
		bw.append("Write structures: " + Config.WRITE_STRUCTURES + "\n");
		bw.append("Write fingerprint ranks: " + Config.GET_FINGERPRINTS + "\n");
		bw.append("Minimum scaffold size: " + Config.MIN_SCAFFOLD_SIZE + "\n");
		bw.append("Maximum scaffold size: " + Config.MAX_SCAFFOLD_SIZE + "\n");
		bw.append("Monomer swaps: " + Config.NUM_MONOMER_SWAPS + "\n");
		bw.append("Seed: " + Config.SEED + "\n");
		bw.append("Terminal -COOH: " + Config.TERMINAL_COOH + "\n");
		bw.append("Library size: " + Config.LIBRARY_SIZE + "\n");
		bw.append("Bootstrap size: " + Config.BOOTSTRAPS + "\n");
		bw.append("Working directory: " + Config.WORKING_DIRECTORY + "\n");
		
		// append monomers
		bw.append("\n");
		bw.append("Monomers\n");
		bw.append("-----\n");
		bw.append("Initial:\t");
		for (IMonomerType m : Config.INITIAL_MONOMERS)
			bw.append(m.toString() + " ");
		bw.append("\n");
		bw.append("Swap:\t");
		for (IMonomerType m : Config.SWAP_MONOMERS)
			bw.append(m.toString() + " ");
		bw.append("\n");
		
		// append reactions
		bw.append("\n");
		bw.append("Reactions\n");
		bw.append("-----\n");
		bw.append("Initial:\t");
		for (Map.Entry<IReactionType,Double> entry : Config.INITIAL_REACTIONS.entrySet()) 
			bw.append(entry.getKey().toString() + "-" + entry.getValue() + " ");
		bw.append("\n");
		bw.append("Add:\t");
		for (Map.Entry<IReactionType,Double> entry : Config.ADD_REACTIONS.entrySet()) 
			bw.append(entry.getKey().toString() + ";" + entry.getValue());
		bw.append("\n");
		bw.append("Remove:\t");
		for (Map.Entry<IReactionType,Double> entry : Config.REMOVE_REACTIONS.entrySet()) 
			bw.append(entry.getKey().toString() + ";" + entry.getValue());
		bw.append("\n");
		bw.append("Swap:\t");
		for (Map.Entry<IReactionType,Double> entry : Config.SWAP_REACTIONS.entrySet()) 
			bw.append(entry.getKey().toString() + ";" + entry.getValue());
		bw.append("\n");
		
		bw.close();
	}

}
