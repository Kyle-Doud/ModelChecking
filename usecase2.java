import Evidence.HypothesisTesting;
import Evidence.Query;




public class usecase2 {
	public static void main(String[] args)
	{
		Query q = new Query("lipopolysaccharide > 0 leads to metabolicCapacity(-1) after start()");
		HypothesisTesting h = new HypothesisTesting("Response", q);
		System.out.println(h.toLTL());
		
		q = new Query("x>12 precedes die( )");
		h = new HypothesisTesting("Precedence", q);
		System.out.println(h.toLTL());
		
		q = new Query("LookingForNewSite is absent after step() until AgentSatisfaction<0.7 ");
		h = new HypothesisTesting("Absence", q);
		System.out.println(h.toLTL());
		
		q = new Query("LookingForNewSite precedes AgentSatisfaction < 0.7");
		h = new HypothesisTesting("Precedence", q);
		System.out.println(h.toLTL());
		
		q = new Query("step( ) always exists between Agent(int numofAgents) and die( )");
		h = new HypothesisTesting("Universality", q);
		System.out.println(h.toLTL());
		
		q = new Query("step( ) leads to lookingForNewSite before die( )");
		h = new HypothesisTesting("Response", q);
		System.out.println(h.toLTL());
	}

}
