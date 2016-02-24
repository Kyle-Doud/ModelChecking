

public class usecase1 {
	public static void main(String[] args)
	{
		Experiment e = new Experiment("schellingModel", "comparative");
		Objective o = new Objective("SchellingObjective", "COMPARATIVE");
		e.setDescription("A string");
		e.setO(o);
		
		Design design = new Design("SchellingDesign", Design.Types.OTHERS);
		
		Values factorValue= new Values(70);
		Factor f = new Factor("percentLikeNeighbors", Factor.VariableTypes.NONBINARY, factorValue);
		ToParametersXML x = new ToParametersXML();
		x.doit(f);
		Values factorValue1= new Values(2000);
		Factor f1 = new Factor("initialNumAgents", Factor.VariableTypes.NONBINARY, factorValue1);
		x.process(f1);
		Values factorValue2= new Values(50);
		Factor f2 = new Factor("worldWidth", Factor.VariableTypes.NONBINARY, factorValue2);
		x.process(f2);
		Values factorValue3= new Values(50);
		Factor f3 = new Factor("worldHeight", Factor.VariableTypes.NONBINARY, factorValue3);
		x.process(f3);
		Values factorValue4= new Values(100);
		Factor f4 = new Factor("maxDeathAge", Factor.VariableTypes.NONBINARY, factorValue4);
		x.process(f4);
		Values factorValue5= new Values(80);
		Factor f5 = new Factor("minDeathAge", Factor.VariableTypes.NONBINARY, factorValue5);
		x.process(f5);
		
		Response r = new Response("AgentSatisfaction", Response.RTypes.SIMPLE);
		IndependentVariables i = new IndependentVariables(f);
		DependentVariables d = new DependentVariables(r);
		Variables v = new Variables(i, d);
		
		design.setVariables(v);
		
		Values expectedValue = new Values(0.7);
		PerformanceMeasure p = new PerformanceMeasure("SchellingPerformanceMeasure", r,expectedValue);
		
		
		String output = "C:/Users/sritika/Documents/Research Project/Development/SchellingOutput/Output1.txt";
		FileSinkModifier  fm = new FileSinkModifier();
		fm.startModifier(output);
		ExptExecuter ee = new ExptExecuter();
		ee.start();
		System.out.print("Finished");
	}

}
