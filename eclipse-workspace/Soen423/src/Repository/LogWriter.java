package Repository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class LogWriter  
{
	private FileWriter fileWriter;
	private BufferedWriter bufferWriter;
	private PrintWriter in;	
	private String fileName;
	private final static String PATH = "C:\\Users\\Igor3Volf\\git\\SOEN423\\eclipse-workspace\\Soen423\\";
	
	
	public LogWriter(String fileName) throws IOException
	{		
		this.fileName=fileName;
		
		fileWriter = new FileWriter(new File(PATH,fileName),true);
		bufferWriter = new BufferedWriter(fileWriter);
		in = new PrintWriter(bufferWriter);
	}
	 public  void writeLog(String mId, String task)
	 {		 	
		 try {
			 fileWriter = new FileWriter(new File(PATH,fileName),true);
			 bufferWriter = new BufferedWriter(fileWriter);
			 in = new PrintWriter(bufferWriter);		
			 LocalDateTime d = LocalDateTime.now();		 
			 String f = "|%-15s|%-45s|%-30s|\n";
			 in.format(f,mId, task, d.toString());
			 in.println();
			 System.out.print(String.format(f,mId, task, d.toString()));
			 in.flush();

		 }catch(IOException io){
			 io.printStackTrace();
		 }
		  finally {
			  try {
				  fileWriter.close();
				  bufferWriter.close();
				  in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
	}	 
	 
}
