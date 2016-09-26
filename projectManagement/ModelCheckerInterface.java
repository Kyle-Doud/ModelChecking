package projectManagement;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Comparator;

public class ModelCheckerInterface 
{
	
	/**
	 * createParentDirectoryIfDNE
	 * 
	 * 
	 */
	private void createParentDirectoryIfDNE()
	{
		File theDir = new File(".\\ModelChecking");
		// if the directory does not exist, create it
		if (!theDir.exists()) {
		    try{
		        theDir.mkdir();
		    } 
		    catch(SecurityException se){
		    	System.out.println("ModelCheckerInterface unable to create dir");
		    }        
		}
	}
	
	public void generateFolder(String modelName)
	{
		createParentDirectoryIfDNE();
		int highest = findHighestVersion(modelName);
		highest++;
		createSpinFolder(highest, modelName);
	}
	
	/**
	 * createSpinFolder 
	 * deprecated
	 * @param highest
	 * @param modelName
	 */
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
		
		/**
		 * findHighestVersion
		 * deprecated
		 * @param modelName
		 * @return
		 */
		protected int findHighestVersion(String modelName)
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
//			ArrayList<String> folders = new ArrayList<String>();
//				for(int i = 0; i < directories.length; i++)
//				{
//					if(directories[i].contains(modelName))
//					{
//						folders.add(directories[i]);
//					}
//				}
				Arrays.sort(directories, new Comparator<String>(){
		            @Override
		            public int compare(String o1, String o2) {
		            	String num1 = o1.substring(o1.indexOf('_') + 1);
		            	String num2 = o2.substring(o2.indexOf('_') + 1);
		                if(Integer.parseInt(num1) < Integer.parseInt(num2))
		                {
		                	return -1;
		                }
		                else if(Integer.parseInt(num1) > Integer.parseInt(num2))
		                {
		                	return 1;
		                }
		                return 0;
		            }
				});
				String highestFolder = directories[directories.length - 1];
				highestFolderNum = Integer.parseInt(highestFolder.substring(highestFolder.indexOf('_') + 1));
			}
			return highestFolderNum;

		}

}
