package projectManagement;

import java.io.IOException;

public class SimulationModelInterface {
	String modelName;
	String parametersFile;
	String modelPath;
	
	public SimulationModelInterface(String modelPathIn, String modelNameIn, String parametersFileIn)
	{
		this.modelName = modelNameIn;
		this.modelPath = modelPathIn;
		this.parametersFile = parametersFileIn;
	}
	
	/**
	 * 
	 * http://repast.sourceforge.net/repast_3/how-to/simstart.html
	 * @param numOfRuns
	 */
	public void runSimulation(int numOfRuns)
	{
		String command = "java -cp " + modelPath + ";c:/repast/lib/repast.jar "
				+ "uchicago.src.sim.engine.SimInit " + modelName; 
		
		try {
			Process p = Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
