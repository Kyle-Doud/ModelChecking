## Synopsis

At the top of the file there should be a short introduction and/ or overview that explains **what** the project is. This description should match descriptions added for package managers (Gemspec, package.json, etc.)

## Code Example

		Query q = new Query("inflammation eventually exists after inflammatoryAgent > inflammatoryAgentThreshold");
		HypothesisTesting h = new HypothesisTesting("E1", q);
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