package projectManagement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

import FSACreation.FSA;
import FSACreation.State;

public class IOManager {
	
	FSA fsa;
	
	public void generateFolderForSpinFiles(String modelName)
	{
		createParentDirectoryIfDNE();
		int highest = findHighestVersion(modelName);
		highest++;
		createSpinFolder(highest, modelName);
	}
	
	private void createSpinFolder(int highest, String modelName)
	{
		File theDir = new File(".\\ModelChecking\\" + modelName + "_" + highest);
		// if the directory does not exist, create it
		if (!theDir.exists()) {
		    try{
		        theDir.mkdir();
		    } 
		    catch(SecurityException se){
		        System.out.println("IOManager unable to create dir");
		    }        
		}
	}
	
	private int findHighestVersion(String modelName)
	{
		int highestFolderNum = 0;
		File file = new File(".\\ModelChecking\\");
		String[] directories = file.list(new FilenameFilter() {
		  @Override
		  public boolean accept(File current, String name) {
		    return new File(current, name).isDirectory();
		  }
		});
		if(directories.length > 0)
		{
		ArrayList<String> folders = new ArrayList<String>();
			for(int i = 0; i < directories.length; i++)
			{
				if(directories[i].contains(modelName))
				{
					folders.add(directories[i]);
				}
			}
			Collections.sort(folders);
			String highestFolder = folders.get(folders.size()-1);
			highestFolderNum = Integer.parseInt(highestFolder.substring(highestFolder.indexOf('_') + 1));
		}
		return highestFolderNum;

	}
	
	private void createParentDirectoryIfDNE()
	{
		File theDir = new File(".\\ModelChecking");
		// if the directory does not exist, create it
		if (!theDir.exists()) {
		    try{
		        theDir.mkdir();
		    } 
		    catch(SecurityException se){
		    	System.out.println("IOManager unable to create dir");
		    }        
		}
	}
	
	public void generatePromelaSource(String modelName, String LTLIn, FSA fsaIn)
	{
		generateFolderForSpinFiles(modelName);
		createPromelaModel(fsaIn);
		addLTLFormula(modelName, LTLIn);//needs to be fixed to not add on top of itself every time
		
	}
	
	private void createPromelaModel(FSA fsaIn)
	{
		this.fsa = fsaIn;
		String ltlDefinitions = defineLTLProperties();
		String mtype = defineMtype();
		String mChannel = defineChannel();
		String states = defineStates();
		
		System.out.print(ltlDefinitions + "\n" + mtype + "\n" + mChannel + "\n" + states + "\n");
	}
	
	private String defineStates() {
		String out = "";
		ArrayList<State> states = fsa.getStates();
		for(State s : states)
		{
			out += defineState(s);
		}
		return out;
	}

	private String defineState(State s) {
		String out = "active proctype state" + s.getIndex() + "()\n"
		+ "{"
			+ "begin: channel?" + s.getTransitions().get(0) + ";"
			+ "end:\n"
		+"}\n";
		return out;
	}

	private String defineChannel() {
		String out = "chan channel = [0] of {mtype}\n";
		return out;
	}

	private String defineMtype() {
		String out = "mtype = {";
		for(int i = 0; i < fsa.getNumStates(); i++)
		{
			out += "s" + i + ", ";
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
