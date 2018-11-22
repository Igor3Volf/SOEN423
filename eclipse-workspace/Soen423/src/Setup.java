import models.request.Request;
import models.request.CreateERecordRequest;
import models.request.CreateMRecordRequest;
import servers.ReplicaManager;
import servers.Sequencer;

public class Setup {
	public static void main(String args[]) 
	{
		CreateERecordRequest c= new CreateERecordRequest("","","",0,"","");
		Request req= (Request)c;
		req.setSequenceNum(1234);
		System.out.println("REQ "+req.getSequenceNum());
		System.out.println("CREATE "+c.getSequenceNum());
		
		if(req instanceof CreateERecordRequest) {
			System.out.println("REQ PASS");
		}
		else {
			System.out.println("REQ FAIL");

		}

		if(req instanceof CreateMRecordRequest) {
			System.out.println("REQ PASS");
		}
		else {
			System.out.println("REQ FAIL");

		}

		System.out.println("REQ CLASS "+req.getClass().toString());
		System.out.println("CREATE CLASS "+c.getClass().getSimpleName());
		
	}
}
