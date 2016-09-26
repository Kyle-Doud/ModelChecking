package experimentDesign;

public class ExptExecuter {
	/**
	 * Runs the RePast experiment with the parameters specified by the user
	 * Updates the result in the output location specified by the user
	 */
	public static void start () {
		String fileLocation = "C:/Users/sritika/Documents/Research Project/Development/RepastSimphony/Schelling/Schelling.rs"; //"C:/RepastSimphony-2.2/models/Schelling/Schelling.rs"; 
		String []args = new String[]{"-params","C:/Users/sritika/Documents/Fall 2015/MDE/ReferenceImpl/ReferenceImplementation1/src/parameters1.xml", fileLocation};
		//repast.simphony.runtime.RepastMain.main(args);
		System.out.println("Finish!");
	}
	
	

}
