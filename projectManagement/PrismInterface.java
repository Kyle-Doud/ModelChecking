package projectManagement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import DataRecording.DataRecordManager;
import FSACreation.MarkovChain;
import FSACreation.State;
import FSACreation.Transition;

public class PrismInterface extends ModelCheckerInterface 
{
	String modelName, LTL;
	MarkovChain markovChain;
	DataRecordManager dataMgr;

	public PrismInterface(String modelNameIn, String LTLIn, MarkovChain fsaIn, DataRecordManager drm)
	{
		this.modelName = modelNameIn;
		this.LTL = LTLIn;
		this.markovChain = fsaIn;
		this.dataMgr = drm;
		generateFolder(modelName);
		createPrismModel();
		createLTLModel();
	}

	public void runPrismSource()
	{
		try{
			String cdCmd = "cd " + System.getProperty("user.dir") + "\\ModelChecking\\" + modelName + "_" + findHighestVersion(modelName) + "\\";
			String prismCmd = "prism " + modelName + ".pm" + " " + modelName + ".props -prop 1 -ex";
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
		for(State s : markovChain.getStates())
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
		for(int i = 0; i < markovChain.getVarNames().length; i++)
		{
			sb.append(markovChain.getVarNames()[i] + " : " + dataMgr.getVariableTypes().get(markovChain.getVarNames()[i]) + ";\n");
		}
		sb.append("\n");

		//		sb.append("s : [0..");//this should be broken down
		//		sb.append(Integer.toString(fsa.getNumStates()));
		//		sb.append("] init 0;\n\n");
		return sb.toString();
	}

	private String processState(State s)
	{
		String conditions = defineConditions(s);
		String transitions = defineTransitions(s);
		return conditions + transitions;
	}
	
	private String defineTransitions(State s)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(" -> ");
		ArrayList<Transition> t = s.getTransitions();
		for(int i = 0; i < t.size(); i++)//for each transition
		{
			if(i < t.size() - 1)
			{
				sb.append("" + t.get(i).getWeight() + " : (");
				State tos = t.get(i).getToState();
				for (int j = 0; j < tos.getValues().length; j++)
				{
					if(j < tos.getValues().length - 1)
					{
						sb.append(tos.getIdentifiers()[j] + "'=" + tos.getValues()[j] + ")&(");
					}
					else
					{
						sb.append(tos.getIdentifiers()[j] + "'=" + tos.getValues()[j] + ") + ");
					}
				}
			}
			else
			{
				sb.append("" + t.get(i).getWeight() + " : (");
				State tos = t.get(i).getToState();
				for (int j = 0; j < tos.getValues().length; j++)
				{
					if(j < tos.getValues().length - 1)
					{
						sb.append(tos.getIdentifiers()[j] + "'=" + tos.getValues()[j] + ")&(");
					}
					else
					{
						sb.append(tos.getIdentifiers()[j] + "'=" + tos.getValues()[j] + ")");
					}
				}
				sb.append(";\n");
			}
		}
		return sb.toString();
	}
	
	private String defineConditions(State s)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("[] ");
		ArrayList<String> stateConditions = new ArrayList<String>();
		
		ArrayList<Integer> conditions = markovChain.classifyStateByConditions(s);
		for(int i = 0; i < conditions.size(); i++)
		{
			stateConditions.add(markovChain.getConditions()[conditions.get(i)].toString());
		}
		
		ArrayList<String> nonConditionVariables = findNonConditionVariables(stateConditions);
		for(String str : nonConditionVariables)
		{
			stateConditions.add(str + " = " + s.getValue(str));
		}

		for (int i = 0; i < stateConditions.size(); i++)
		{
			if(i < stateConditions.size() - 1)
			{
				sb.append(stateConditions.get(i) + " & ");
			}
			else
			{
				sb.append(stateConditions.get(i));
			}
		}
		return sb.toString();
	}
	
	private ArrayList<String> findNonConditionVariables(ArrayList<String> stateConditions)
	{
		ArrayList<String> nonConditionVariables = new ArrayList<String>();
		String[] varnames = markovChain.getVarNames();
		for(int i = 0; i < varnames.length; i++)//find the variables that are not involved in any conditions
		{
			if(!isInCondition(stateConditions, varnames[i]))
			{
				nonConditionVariables.add(varnames[i]);
			}
		}
		return nonConditionVariables;
	}
	
	private boolean isInCondition(ArrayList<String> stateConditions, String varName)
	{
		for(String str : stateConditions)
		{
			if(str.contains(varName))
			{
				return true;
			}
		}
		return false;
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
