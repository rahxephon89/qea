package qea.benchmark.crv15;

import static qea.monitoring.impl.translators.TranslatorFactory.event;
import static qea.monitoring.impl.translators.TranslatorFactory.PType.INT;

import java.io.IOException;

import qea.monitoring.impl.CSVFileMonitor;
import qea.monitoring.impl.translators.OfflineTranslator;
import qea.monitoring.impl.translators.TranslatorFactory;
import qea.properties.crv15.offline.OCLR_3;
import qea.structure.impl.other.Verdict;
import qea.structure.intf.QEA;

public class OtherRun {

	public static void main(String[] args) throws IOException{
		
		run_oclr();
		
		
	}
	
	public static void run_oclr() throws IOException{
		//String trace = "/Users/giles/git/crv15_local/Offline/OCLR/bm1_1000000_globally.csv";
		String trace = "/Users/giles/git/crv15_local/Offline/OCLR/bm3_1000000_after.csv";
		//QEA qea = OCLR_3.make_one();
		QEA qea = OCLR_3.make_three();
		//OfflineTranslator t = TranslatorFactory.makeParsingTranslatorWithIgnore(event("c",INT),event("d",INT));
		OfflineTranslator t = TranslatorFactory.makeSelectingDefaultTranslator(event("a"),event("b"),event("c"));
		CSVFileMonitor fm = new CSVFileMonitor(trace, qea, t);
		fm.ignoreHeader();
		Verdict v = fm.monitor();
		System.out.println("Verdict :"+v);
	}
	
}
