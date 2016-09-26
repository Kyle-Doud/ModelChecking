## Synopsis

This program is used to process input from a domain specific language for domain specific simulation experiments.  The program takes a set of evidences and hypotheses from the DSL and creates an observer of the simulation to record data related to these evidence/hypotheses.  It converts the simulation data to a discrete time markov chain which is validated using a probabilistic model checker.

## Code Example

		Property p = new Property("inflammation eventually exists after inflammatoryAgent > inflammatoryAgentThreshold");
		HypothesisTesting h = new HypothesisTesting("E1", p);
		String LTL = h.toLTL();
		
		//Add FSA code to model
		String[] inputList = {"step()", "die()"};
		AspectJGenerator fileMgr = new AspectJGenerator("segregation.SegregationBatch");
		fileMgr.generate("answers", "Answer1", inputList);
		
		//run model
		String modelPath = "C:/RepastSimphony-2.3.1/models/Schelling";
		String modelName = "uchicago.src.sim.schelling.SchellingModel";
		String parametersPath = "C:/RepastSimphony-2.3.1/models/Schelling/batch/batch_params.xml";
		SimulationModelInterface schelling = new SimulationModelInterface(modelPath, modelName, parametersPath);
		schelling.runSimulation(5);
		
		//create markov chain
		int numVars = 3;
		DataRecordManager dataMgr = new DataRecordManager(numVars, "C:/Users/krdou_000/Documents/Repast Workspace/ModelChecking/sampleData.txt");
		ArrayList<DataType[]> matrix = dataMgr.getFullMatrixFromDataFile();
		FSA fsa = new FSA(numVars, dataMgr.getVariableNames());
        fsa.developFSAFromData(matrix);
        
        PrismInterface pi = new PrismInterface("ISHC", LTL, fsa);
        pi.runPrismSource();

## Motivation

Automating model verification for experimental simulation models based on input from a domain specific language.

## Installation

Dependencies: 

PRISM Model Checker 
http://www.prismmodelchecker.org/manual/InstallingPRISM/Instructions#source