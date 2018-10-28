package Repository;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class LogWriter {
	private FileWriter fileWriter;
	private BufferedWriter bufferWriter;
	private PrintWriter in;
	private String fileName;
	private String path = "";
    
	public LogWriter(String fileName, String path) throws IOException 
	{
		this.fileName = fileName;
		this.path = path;
		fileWriter = new FileWriter(new File(path, fileName), true);
		bufferWriter = new BufferedWriter(fileWriter);
		in = new PrintWriter(bufferWriter);

	}	
	public void writeLog(String mId, String task, String location) {		
			File file= new File(path, fileName);
			try {
				fileWriter = new FileWriter(file, true);
				bufferWriter = new BufferedWriter(fileWriter);
				in = new PrintWriter(bufferWriter);				
					LocalDateTime d = LocalDateTime.now();					
					String f = "|%-15s|%-45s|%-30s|%-10s|\n";
					in.format(f, mId, task, d.toString(), location);
					in.println();
					//System.out.print(String.format(f, mId, task, d.toString()));
					in.flush();
					fileWriter.close();
					bufferWriter.close();
					in.close();
				
			} catch (IOException io) {
				io.printStackTrace();
			}		
	}

}
