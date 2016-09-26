
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

import projectManagement.AspectJGenerator;
import projectManagement.PrismInterface;
import projectManagement.SpinInterface;
import projectManagement.SimulationModelInterface;
import DataRecording.DataRecordManager;
import DataRecording.DataType;
import Evidence.HypothesisTesting;
import Evidence.Query;
import FSACreation.FSA;

public class Main {
	public static void main(String[] args)
	{
		Query q = new Query("inflammation eventually exists after inflammatoryAgent > inflammatoryAgentThreshold");
		HypothesisTesting h = new HypothesisTesting("E1", q);
		String LTL = h.toLTL();
//		System.out.println(LTL);
//		String LTL = "deleteme";
		
		//Add FSA code to model
//		String[] inputList = {"step()", "die()"};
//		AspectJGenerator fileMgr = new AspectJGenerator("segregation.SegregationBatch");
//		fileMgr.generate("answers", "Answer1", inputList);
		
		//run model
//		String modelPath = "C:/RepastSimphony-2.3.1/models/Schelling";
//		String modelName = "uchicago.src.sim.schelling.SchellingModel";
//		String parametersPath = "C:/RepastSimphony-2.3.1/models/Schelling/batch/batch_params.xml";
//		SimulationModelInterface schelling = new SimulationModelInterface(modelPath, modelName, parametersPath);
//		schelling.runSimulation(5);
		
		//create markov chain
		int numVars = 3;
		DataRecordManager dataMgr = new DataRecordManager(numVars, "C:/Users/krdou_000/Documents/Repast Workspace/ModelChecking/sampleData.txt");
		ArrayList<DataType[]> matrix = dataMgr.getFullMatrixFromDataFile();
		FSA fsa = new FSA(numVars, dataMgr.getVariableNames());
        fsa.developFSAFromData(matrix);
        
        PrismInterface pi = new PrismInterface("ISHC", LTL, fsa);
        pi.runPrismSource();
        
//		SpinInterface si = new SpinInterface("ISHC", LTL, fsa);
//		si.runPromelaSource("ISHC");
		
	}

}
