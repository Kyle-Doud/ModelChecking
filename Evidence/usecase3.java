package Evidence;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;


public class usecase3 {
	public static void main(String[] args)
	{
		String promelaFilePath = args[0];
		Query q = new Query("(seen < N) || (tour > MAX) always exists");
		//Query q = new Query("x>12 precedes die( )");
		HypothesisTesting h = new HypothesisTesting("Temporal Hypothesis", q);
		String LTL = h.toLTL();
		System.out.println(LTL);
        
		try{
			File file =new File("C:\\Users\\krdou_000\\Dropbox\\Research\\LTL\\salesman1.pml");
			FileWriter fileWritter = new FileWriter(file,true);
	        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
	        bufferWritter.write("\n" + LTL);
	        bufferWritter.close();
	        
			ProcessBuilder builder = new ProcessBuilder(
		            "cmd.exe", "/c", "cd \"C:\\Users\\krdou_000\\Dropbox\\Research\\LTL\" && spin -a salesman1.pml && gcc -o salesman1.exe pan.c && salesman1.exe -a");
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
