package playground.agarwalamit.mixedTraffic.patnaIndia.old;

import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.scenario.ScenarioImpl;
import org.matsim.vis.otfvis.OTFFileWriterFactory;

import playground.ikaddoura.internalizationCar.WelfareAnalysisControlerListener;

public class MyFirstControler {
	
	static final String  outputDir ="/Users/amit/Documents/repos/runs-svn/patnaIndia/run103/";

	public static void main(String[] args) {
		String configFile = outputDir+"/configCarMotorbikeBike.xml";
		Config config = ConfigUtils.loadConfig(configFile);
		final Controler myController = new Controler(config);		
		myController.setOverwriteFiles(true) ;
		myController.setCreateGraphs(true);
		myController.setMobsimFactory(new PatnaQSimFactory()); 
		myController.addSnapshotWriterFactory("otfvis", new OTFFileWriterFactory());
		myController.addControlerListener(new WelfareAnalysisControlerListener((ScenarioImpl)myController.getScenario()));
		myController.setDumpDataAtEnd(true);
		myController.run();
	}
}