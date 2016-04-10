
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

import projectManagement.FileGenerator;
import projectManagement.IOManager;
import projectManagement.ModelInterface;
import DataRecording.DataRecordManager;
import DataRecording.DataType;
import Evidence.HypothesisTesting;
import Evidence.Query;
import FSACreation.FSA;

public class Main {
	public static void main(String[] args)
	{
//		Query q = new Query("inflammation occurs after inflammatoryAgent > inflammatoryAgentThreshold");
//		HypothesisTesting h = new HypothesisTesting("E1", q);
//		String LTL = h.toLTL();
//		System.out.println(LTL);
//		
//		
//		//Add FSA code to model
//		String[] inputList = {};
//		FileGenerator fileMgr = new FileGenerator("segregation.SegregationBatch");
//		fileMgr.generate("answers", "Answer1", inputList);
//		
//		//run model
//		String modelPath = "C:/RepastSimphony-2.3.1/models/Schelling";
//		String modelName = "uchicago.src.sim.schelling.SchellingModel";
//		String parametersPath = "C:/RepastSimphony-2.3.1/models/Schelling/batch/batch_params.xml";
//		ModelInterface schelling = new ModelInterface(modelPath, modelName, parametersPath);
//		schelling.runSimulation(5);
		
		//create FSA
		int numVars = 3;
		DataRecordManager dataMgr = new DataRecordManager(numVars, "C:/Users/krdou_000/Documents/Repast Workspace/ModelChecking/sampleData.txt");
		ArrayList<DataType[]> matrix = dataMgr.getFullMatrixFromDataFile();
		FSA fsa = new FSA(numVars, dataMgr.getVariableNames());
        fsa.developFSAFromData(matrix);
        
//		IOManager iom = new IOManager();
//		iom.generatePromelaSource("ISHC", LTL, fsa);
//		iom.runPromelaSource("ISHC");
		
	}

}
