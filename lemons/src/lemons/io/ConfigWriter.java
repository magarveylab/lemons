package lemons.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import lemons.Config;
import lemons.enums.monomers.NonProteinogenicAminoAcids;
import lemons.enums.monomers.PolyketideMonomers;
import lemons.enums.monomers.ProteinogenicAminoAcids;
import lemons.enums.monomers.Starters;
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
		bw.append(getCommandLineOptionsString() + "\n");
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
		for (Map.Entry<IReactionType, Double> entry : Config.INITIAL_REACTIONS
				.entrySet())
			bw.append(entry.getKey().toString() + "-" + entry.getValue() + " ");
		bw.append("\n");
		bw.append("Add:\t");
		for (Map.Entry<IReactionType, Double> entry : Config.ADD_REACTIONS
				.entrySet())
			bw.append(entry.getKey().toString() + "-" + entry.getValue());
		bw.append("\n");
		bw.append("Remove:\t");
		for (Map.Entry<IReactionType, Double> entry : Config.REMOVE_REACTIONS
				.entrySet())
			bw.append(entry.getKey().toString() + "-" + entry.getValue());
		bw.append("\n");
		bw.append("Swap:\t");
		for (Map.Entry<IReactionType, Double> entry : Config.SWAP_REACTIONS
				.entrySet())
			bw.append(entry.getKey().toString() + "-" + entry.getValue());
		bw.append("\n");

		bw.close();
	}

	public static String getCommandLineOptionsString() {
		StringBuffer sb = new StringBuffer();
		sb.append("java -jar lemons.jar");
		if (Config.WRITE_STRUCTURES)
			sb.append(" -w");
		if (Config.GET_FINGERPRINTS)
			sb.append(" -f");
		if (Config.SEED != -1)
			sb.append(" -seed " + Config.SEED);
		sb.append(" -min_size " + Config.MIN_SCAFFOLD_SIZE);
		sb.append(" -max_size " + Config.MAX_SCAFFOLD_SIZE);
		sb.append(" -lib_size " + Config.LIBRARY_SIZE);
		sb.append(" -bootstraps " + Config.BOOTSTRAPS);
		sb.append(" -swaps " + Config.NUM_MONOMER_SWAPS);
		sb.append(" -initial_monomers");
		if (Config.INITIAL_MONOMERS.contains(ProteinogenicAminoAcids.ALANINE))
			sb.append(" p");
		if (Config.INITIAL_MONOMERS
				.contains(NonProteinogenicAminoAcids._2_4_DIAMINO_BUTYRIC_ACID))
			sb.append(" np");
		if (Config.INITIAL_MONOMERS.contains(PolyketideMonomers.MALONATE))
			sb.append(" pk");
		if (Config.INITIAL_MONOMERS
				.contains(Starters._2_3_DIHYDROXYBENZOIC_ACID))
			sb.append(" s");
		sb.append(" -swap_monomers");
		if (Config.SWAP_MONOMERS.contains(ProteinogenicAminoAcids.ALANINE))
			sb.append(" p");
		if (Config.SWAP_MONOMERS
				.contains(NonProteinogenicAminoAcids._2_4_DIAMINO_BUTYRIC_ACID))
			sb.append(" np");
		if (Config.SWAP_MONOMERS.contains(PolyketideMonomers.MALONATE))
			sb.append(" pk");
		if (Config.SWAP_MONOMERS.contains(Starters._2_3_DIHYDROXYBENZOIC_ACID))
			sb.append(" s");
		if (Config.INITIAL_REACTIONS.size() > 0) {
			sb.append(" -initial_reactions");
			for (Map.Entry<IReactionType, Double> entry : Config.INITIAL_REACTIONS
					.entrySet())
				sb.append(" " + entry.getKey().toString().toLowerCase() + " "
						+ entry.getValue());
		}
		if (Config.SWAP_REACTIONS.size() > 0) {
			sb.append(" -swap_reactions");
			for (Map.Entry<IReactionType, Double> entry : Config.SWAP_REACTIONS
					.entrySet())
				sb.append(" " + entry.getKey().toString().toLowerCase() + " "
						+ entry.getValue());
		}
		if (Config.ADD_REACTIONS.size() > 0) {
			sb.append(" -add_reactions");
			for (Map.Entry<IReactionType, Double> entry : Config.ADD_REACTIONS
					.entrySet())
				sb.append(" " + entry.getKey().toString().toLowerCase() + " "
						+ entry.getValue());
		}
		if (Config.REMOVE_REACTIONS.size() > 0) {
			sb.append(" -remove_reactions");
			for (Map.Entry<IReactionType, Double> entry : Config.REMOVE_REACTIONS
					.entrySet())
				sb.append(" " + entry.getKey().toString().toLowerCase() + " "
						+ entry.getValue());
		}
		sb.append(" -base_dir " + Config.BASE_DIRECTORY);
		return sb.toString();
	}

}
