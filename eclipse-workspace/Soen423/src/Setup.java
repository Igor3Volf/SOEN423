import servers.ReplicaManager;
import servers.Sequencer;


public class Setup {
public static void main(String args[]) {
		
	ReplicaManager r;
		try {
			r = new ReplicaManager();
			(new Sequencer()).start();
			r.start();		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
