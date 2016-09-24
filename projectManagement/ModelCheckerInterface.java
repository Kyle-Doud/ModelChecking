package projectManagement;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;

public class ModelCheckerInterface 
{
	
	/**
	 * createParentDirectoryIfDNE
	 * deprecated
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

}
