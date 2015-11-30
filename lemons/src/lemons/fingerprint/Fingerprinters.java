package lemons.fingerprint;

import org.openscience.cdk.fingerprint.CircularFingerprinter;
import org.openscience.cdk.fingerprint.EStateFingerprinter;
import org.openscience.cdk.fingerprint.ExtendedFingerprinter;
import org.openscience.cdk.fingerprint.Fingerprinter;
import org.openscience.cdk.fingerprint.GraphOnlyFingerprinter;
import org.openscience.cdk.fingerprint.HybridizationFingerprinter;
import org.openscience.cdk.fingerprint.IFingerprinter;
import org.openscience.cdk.fingerprint.KlekotaRothFingerprinter;
import org.openscience.cdk.fingerprint.LingoFingerprinter;
import org.openscience.cdk.fingerprint.MACCSFingerprinter;
import org.openscience.cdk.fingerprint.PubchemFingerprinter;
import org.openscience.cdk.DefaultChemObjectBuilder;

public enum Fingerprinters {
	
	// substructure 
	MACCS(new MACCSFingerprinter()),
	PUBCHEM(new PubchemFingerprinter(DefaultChemObjectBuilder.getInstance())),
	KLEKOTA_ROTH(new KlekotaRothFingerprinter()),
	E_STATE(new EStateFingerprinter()),

	// topological 
	CDK_EXTENDED(new ExtendedFingerprinter()),
	CDK_GRAPH_ONLY(new GraphOnlyFingerprinter()),
	CDK_HYBRIDIZATION(new HybridizationFingerprinter()),
	CDK_DEFAULT(new Fingerprinter()),

	// circular 
	ECFP0(new CircularFingerprinter(1)),
	ECFP2(new CircularFingerprinter(2)),
	ECFP4(new CircularFingerprinter(3)),
	ECFP6(new CircularFingerprinter(4)),
	FCFP0(new CircularFingerprinter(5)), 
	FCFP2(new CircularFingerprinter(6)),
	FCFP4(new CircularFingerprinter(7)),
	FCFP6(new CircularFingerprinter(8)),
	
	// lexical 
	LINGO(new LingoFingerprinter()),
	;
	
	private final IFingerprinter fingerprinter;
	
	private Fingerprinters(IFingerprinter fingerprinter) {
		this.fingerprinter = fingerprinter;
	}
	
	public IFingerprinter getFingerprinter() {
		return fingerprinter;
	}
	
}
