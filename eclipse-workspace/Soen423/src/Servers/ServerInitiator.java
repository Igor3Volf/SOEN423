package Servers;
import Models.EmployeeRecord;
import Models.ManagerRecord;
import Models.Project;
import Repository.IdGenerator;
import Servers.CenterServerInterface;

import java.io.*;

import javax.xml.ws.Endpoint;

/**
 * 
 * @author Ihar Volkau
 */

public class ServerInitiator {
	private final static String PATH = "C:\\Users\\Igor3Volf\\git\\SOEN423\\eclipse-workspace\\Soen423\\Server_logs\\"; // desktop

	// private final static String PATH =
	// "C:\\Users\\igor3\\eclipse-workspace\\Soen423\\src\\Server_logs\\";
	// //laptop

	public static void main(String args[]) {
		
			(new IdGenerator(5555)).start();		

			try {
				CenterServer csCA = new CenterServer("Canadian", PATH, 2001);
				CenterServer csUS = new CenterServer("American", PATH, 2002);
				CenterServer csUK = new CenterServer("English", PATH, 2003);
				
				Endpoint endpointCA = Endpoint.publish("http://localhost:8080/CenterServer/Canada", csCA);
				Endpoint endpointUS = Endpoint.publish("http://localhost:8080/CenterServer/America", csUS);
				Endpoint endpointUK = Endpoint.publish("http://localhost:8080/CenterServer/England", csUK);
				
				System.out.println(endpointCA.isPublished());
				System.out.println(endpointUS.isPublished());
				System.out.println(endpointUK.isPublished());
				
				preset(csCA,csUS,csUK);


			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

	}

		private static void preset(CenterServer ca, CenterServer us, CenterServer uk) {
			
			 System.out.println("-------------BEGINNING OF PRESET--------------");

			Project p1CA = new Project("P0001",
					"Blenz Coffee", "Coffee Machine Optimization");
			Project p2CA = new Project("P0002",
					"Bluenotes", "Web Setup");
			Project p3CA = new Project("P0003",
					"Boeing Canada", "Network Setup");
			Project p4CA = new Project("P0004",
					"Bombardier Inc.", "Supplier Web Modification");

			 ca.createMRecord("Server Manager", "Vanessa", "Luke",
					"CA0001", "v.duk@gmail.com", p1CA, "CA");

			 ca.createMRecord("Server Manager", "Trent", "Turner",
					"CA0002", "t.tur@gmail.com", p2CA, "CA");

			 ca.createMRecord("Server Manager", "Luka", "Callahan",
					"CA0003", "l.cal@gmail.com", p3CA, "CA");

			 ca.createMRecord("Server Manager", "Carmen", "Lalentine",
					"CA0004", "c.val@yahoo.com", p4CA, "CA");

			 ca.createERecord("Server Manager", "Maliyah", "Garza",
					(short) 1, "m.gar@gmail.com", "P00001");

			 ca.createERecord("Server Manager", "Jaliyah", "Lucas",
					(short) 2, "j.luc@gmail.com", "P00002");

			 ca.createERecord("Server Manager", "Olivia", "Henson",
					(short) 3, "o.hen@gmail.com", "P00003");

			 ca.createERecord("Server Manager", "Tyrese", "Sawyer",
					(short) 4, "t.saw@hotmail.com", "P00004");

			Project p1US = new Project("P0005", "Walmart",
					"Electro Cashier Setup");
			Project p2US = new Project("P0006",
					"Berkshire Hathaway", "Server Setup");
			Project p3US = new Project("P0007",
					"Apple Inc.", "New Server");
			Project p4US = new Project("P0008",
					"ExxonMobil", "Car Network");

			 us.createMRecord("Server Manager", "Giancarlo", "Pennington",
					"US0005", "g.pen@yahoo.com", p1US, "US");

			 us.createMRecord("Server Manager", "Keira", "Bennett",
					"US0006", "k.ben@gmail.com", p2US, "US");

			 us.createMRecord("Server Manager", "Barbara", "Wiggins",
					"US0007", "b.wig@gmail.com", p3US, "US");

			 us.createMRecord("Server Manager", "Gustavo", "Skinner",
					"US0008", "g.ski@yahoo.com", p4US, "US");

			 us.createERecord("Server Manager", "Garrett", "Khan",
					(short) 5, "g.khan@gmail.com", "P00005");

			 us.createERecord("Server Manager", "Amanda", "Kim",
					(short) 6, "a.kim@gmail.com", "P00006");

			 us.createERecord("Server Manager", "Athena", "Moody",
					(short) 7, "a.moo@gmail.com", "P00007");

			 us.createERecord("Server Manager", "Adrien", "Owens",
					(short) 8, "a.owe@hotmail.com", "P00008");

			Project p1UK = new Project("P0009",
					"Prudential", "Software Install");
			Project p2UK = new Project("P00010", "HSBC",
					"New Bank Server");
			Project p3UK = new Project("P00011", "Tesco",
					"Network Configuration");
			Project p4UK = new Project("P00012", "Aviva",
					"Website Setup");

			 uk.createMRecord("Server Manager", "Asa", "Woodard",
					"UK0009", "a.woo@gmail.com", p1UK, "UK");

			 uk.createMRecord("Server Manager", "Cory", "Harvey",
					"UK0010", "c.har@gmail.com", p2UK, "UK");

			 uk.createMRecord("Server Manager", "Marina", "Sanford",
					"UK0011", "m.san@gmail.com", p3UK, "UK");

			 uk.createMRecord("Server Manager", "Lamar", "Fritz",
					"UK0012", "l.fri@gmail.com", p4UK, "UK");

			 uk.createERecord("Server Manager", "Avery", "Castro",
					(short) 9, "a.cas@gmail.com", "P00009");

			 uk.createERecord("Server Manager", "Maxim", "House",
					(short) 10, "m.hou@gmail.com", "P00010");

			 uk.createERecord("Server Manager", "Rory", "Vang",
					(short) 11, "r.yang@gmail.com", "P00011");

			 uk.createERecord("Server Manager", "Reese", "Marquez",
					(short) 12, "r.mar@hotmail.com", "P00012");
			 
			 ca.getRecordCounts("Server Manager");
			 us.getRecordCounts("Server Manager");
			 uk.getRecordCounts("Server Manager");
			 /* 
			   
			 testing transfer correct
			 
			 ca.transferRecord("CASystem", "MR10003", "UK");
			 ca.getRecordCounts("Server Manager");
			 uk.getRecordCounts("Server Manager");
			 us.transferRecord("USSystem", "MR10003", "CA");
			 uk.transferRecord("UKSystem", "MR10003", "US");
			 us.transferRecord("US2System", "MR10003", "CA");
			 ca.getRecordCounts("Server Manager");
			 
			 
			 EmployeeRecord er = new EmployeeRecord("fnTest","lnTest",11111,"fail@mail.id","ER10022","P00010");			 
			 ca.addToMap(er);
			 ca.getRecordCounts("Fail Test Manager");

			 checking for duplicate in other server transfer correct
			 ca.transferRecord("CASystem", "ER10022", "UK");
			 ca.getRecordCounts("Fail Test Manager");
			 */
			 System.out.println("-------------END OF PRESET------------");
			 
			 
			
		}

}