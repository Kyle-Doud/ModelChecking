package projectManagement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import FSACreation.MarkovChain;
import FSACreation.State;

public class SpinInterface extends ModelCheckerInterface
{
	MarkovChain markovChain;
	
	public SpinInterface(String modelName, String LTLIn, MarkovChain fsaIn)
	{
		generateFolder(modelName);
		createPromelaModel(fsaIn);
		addLTLFormula(modelName, LTLIn);//needs to be fixed to not add on top of itself every time
	}
	
	/**
	 * createPromelaModel
	 * deprecated
	 * @param fsaIn
	 */
	private void createPromelaModel(MarkovChain fsaIn)
	{
		this.markovChain = fsaIn;
		String ltlDefinitions = defineLTLProperties();
		String mtype = defineMtype();
		String mChannel = defineChannel();
		String states = defineStates();
		
		System.out.print(ltlDefinitions + "\n" + mtype + "\n" + mChannel + "\n" + states + "\n");
	}
	
	/**
	 * defineStates
	 * deprecated
	 * @return
	 */
	private String defineStates() {
		String out = "";
		ArrayList<State> states = markovChain.getStates();
		for(State s : states)
		{
			out += defineState(s);
		}
		return out;
	}

	/**
	 * defineStates
	 * deprecated
	 * @param s
	 * @return
	 */
	private String defineState(State s) {
		String out = "active proctype state" + s.getIndex() + "()\n"
		+ "{\n"
			+ "begin: channel?s" + s.getTransitions().get(0).getToState().getIndex() + ";\n"
			+ "end:\n"
		+"}\n";
		return out;
	}

	/**
	 * defineChannel
	 * deprecated
	 * @return
	 */
	private String defineChannel() {
		String out = "chan channel = [0] of {mtype}\n";
		return out;
	}

	/**
	 * defineMtype
	 * deprecated
	 * @return
	 */
	private String defineMtype() {
		String out = "mtype = {";
		for(int i = 0; i < markovChain.getNumStates(); i++)
		{
			if(i == markovChain.getNumStates() - 1)
			{
				out += "s" + i;
			}
			else
			{
				out += "s" + i + ", ";
			}
		}
		out += "}\n";
		return out;
	}

	private String defineLTLProperties() {
		//String out = "#define p (" + fsa.state.transition.getEvent() + ")\n";//which state??
		return " ";
	}

	private void addLTLFormula(String modelName, String LTLIn)
	{
		try
		{
			File file = new File(".\\ModelChecking\\" + modelName + "_" + findHighestVersion(modelName) + "\\" + modelName + ".pml");
			FileWriter fileWritter = new FileWriter(file,true);
	        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
	        bufferWritter.write("\n" + LTLIn);
	        bufferWritter.close();
		}
		catch(IOException e)
		{
			System.out.println("IOException in IOManager");
		}
	}
	
	/**
	 * runPromelaSource
	 * deprecated
	 * @param modelName
	 */
	public void runPromelaSource(String modelName)
	{
		try{
	        String cdCmd = "cd " + System.getProperty("user.dir") + "\\ModelChecking\\" + modelName + "_" + findHighestVersion(modelName) + "\\";
	        String spinCmd = "spin -a " + modelName + ".pml";
	        String gccCmd = "gcc -o " + modelName + ".exe pan.c";
	        String exeCmd = modelName + ".exe -a";
			ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", 
										cdCmd + " && " + spinCmd + " && " + gccCmd + " && " + exeCmd);
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

}
