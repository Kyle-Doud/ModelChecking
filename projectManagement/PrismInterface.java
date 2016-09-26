package projectManagement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import FSACreation.FSA;
import FSACreation.State;
import FSACreation.Transition;

public class PrismInterface extends ModelCheckerInterface 
{
	String modelName, LTL;
	FSA fsa;
	
	public PrismInterface(String modelNameIn, String LTLIn, FSA fsaIn)
	{
		this.modelName = modelNameIn;
		this.LTL = LTLIn;
		this.fsa = fsaIn;
		generateFolder(modelName);
		createPrismModel();
		createLTLModel();
	}
	
	public void runPrismSource()
	{
		try{
	        String cdCmd = "cd " + System.getProperty("user.dir") + "\\ModelChecking\\" + modelName + "_" + findHighestVersion(modelName) + "\\";
	        String prismCmd = "prism " + modelName + ".pm" + " " + modelName + ".props -prop 1";
			ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", cdCmd + " && " + prismCmd);
	        builder.redirectErrorStream(true);
	        Process p = builder.start();
	        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        String line;
	        while (true) {
	            line = r.readLine();
	            if (line == null) { break; }
	            System.out.println(line);
	        }
		        
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void createPrismModel()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(writeMetaInfo());
		for(State s : fsa.getStates())
		{
			sb.append(processState(s));
		}
		sb.append("\n\nendmodule");
		
		try
		{
			File file = new File(".\\ModelChecking\\" + modelName + "_" + findHighestVersion(modelName) + "\\" + modelName + ".pm");
			FileWriter fileWritter = new FileWriter(file,true);
	        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
	        bufferWritter.write(sb.toString());
	        bufferWritter.close();
		}
		catch(IOException e)
		{
			System.out.println("IOException in IOManager");
		}
	}
	
	private String writeMetaInfo()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("dtmc\n\nmodule ");
		sb.append(modelName);
		sb.append("\n\n");
		sb.append("s : [0..");
		sb.append(Integer.toString(fsa.getNumStates()));
		sb.append("] init 0;\n\n");
		return sb.toString();
	}
	
	private String processState(State s)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("[] s=" + s.getIndex() + " -> ");
		ArrayList<Transition> t = s.getTransitions();
		for(int i = 0; i < t.size(); i++)
		{
			if(i < t.size() - 1)
			{
				sb.append("" + t.get(i).getWeight() + " : (s'=" + t.get(i).getToState().getIndex() + ") + ");
			}
			else
			{
				sb.append("" + t.get(i).getWeight() + " : (s'=" + t.get(i).getToState().getIndex() + ");\n");
			}
			
		}
		return sb.toString();
	}
	
	private void createLTLModel()
	{
		try
		{
			File file = new File(".\\ModelChecking\\" + modelName + "_" + findHighestVersion(modelName) + "\\" + modelName + ".props");
			FileWriter fileWritter = new FileWriter(file,true);
	        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
	        bufferWritter.write(LTL);
	        bufferWritter.close();
		}
		catch(IOException e)
		{
			System.out.println("IOException in IOManager");
		}
	}
}
