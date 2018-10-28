package Servers;

import CorbaModule.ManagerInterface;
import CorbaModule.ManagerInterfaceHelper;
import Repository.IdGenerator;

import java.io.*;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

/**
 * 
 * @author Ihar Volkau
 */

public class ServerInitiatorCorba {
	private final static String PATH = "C:\\Users\\Igor3Volf\\git\\SOEN423\\eclipse-workspace\\Soen423\\Server_logs\\"; // desktop

	// private final static String PATH =
	// "C:\\Users\\igor3\\eclipse-workspace\\Soen423\\src\\Server_logs\\";
	// //laptop

	public static void main(String args[]) {
		try {

			// create and initialize the ORB
			ORB orb = ORB.init(args, null);
			// get a reference to the root POA
			POA rootpoa = POAHelper.narrow(orb
					.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();
			
			(new IdGenerator(5555)).start();
			CenterServer csCA = new CenterServer("Canadian", PATH, 2001);
			CenterServer csUS = new CenterServer("American", PATH, 2002);
			CenterServer csUK = new CenterServer("English", PATH, 2003);

			csCA.setORB(orb);
			csUS.setORB(orb);
			csUK.setORB(orb);

			org.omg.CORBA.Object refCA = rootpoa.servant_to_reference(csCA);
			ManagerInterface hrefCA = ManagerInterfaceHelper.narrow(refCA);

			org.omg.CORBA.Object refUS = rootpoa.servant_to_reference(csUS);
			ManagerInterface hrefUS = ManagerInterfaceHelper.narrow(refUS);

			org.omg.CORBA.Object refUK = rootpoa.servant_to_reference(csUK);
			ManagerInterface hrefUK = ManagerInterfaceHelper.narrow(refUK);

			org.omg.CORBA.Object objRef = orb
					.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			// bind the Object References in Naming

			String name1 = "Canada";
			NameComponent path[] = ncRef.to_name(name1);
			ncRef.rebind(path, hrefCA);
			String name2 = "America";
			NameComponent path2[] = ncRef.to_name(name2);
			ncRef.rebind(path2, hrefUS);
			String name3 = "England";
			NameComponent path3[] = ncRef.to_name(name3);
			ncRef.rebind(path3, hrefUK);

			System.out.println("CA Server is started!");

			System.out.println("US Server is started!");

			System.out.println("UK Server is started!");
			preset(csCA, csUS, csUK);

			orb.run();
			System.out.print("");
		} catch (InvalidName | AdapterInactive | IOException
				| org.omg.CosNaming.NamingContextPackage.InvalidName | NotFound
				| CannotProceed | ServantNotActive | WrongPolicy e) {
			e.printStackTrace();
		}

	}

	private static void preset(CenterServer ca, CenterServer us, CenterServer uk) {
		 System.out.println("-------------BEGINNING OF PRESET--------------");

		CorbaModule.Project p1CA = new CorbaModule.Project("P0001",
				"Blenz Coffee", "Coffee Machine Optimization");
		CorbaModule.Project p2CA = new CorbaModule.Project("P0002",
				"Bluenotes", "Web Setup");
		CorbaModule.Project p3CA = new CorbaModule.Project("P0003",
				"Boeing Canada", "Network Setup");
		CorbaModule.Project p4CA = new CorbaModule.Project("P0004",
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

		CorbaModule.Project p1US = new CorbaModule.Project("P0005", "Walmart",
				"Electro Cashier Setup");
		CorbaModule.Project p2US = new CorbaModule.Project("P0006",
				"Berkshire Hathaway", "Server Setup");
		CorbaModule.Project p3US = new CorbaModule.Project("P0007",
				"Apple Inc.", "New Server");
		CorbaModule.Project p4US = new CorbaModule.Project("P0008",
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

		CorbaModule.Project p1UK = new CorbaModule.Project("P0009",
				"Prudential", "Software Install");
		CorbaModule.Project p2UK = new CorbaModule.Project("P00010", "HSBC",
				"New Bank Server");
		CorbaModule.Project p3UK = new CorbaModule.Project("P00011", "Tesco",
				"Network Configuration");
		CorbaModule.Project p4UK = new CorbaModule.Project("P00012", "Aviva",
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
		 ca.transferRecord("CASystem", "MR10003", "UK");
		 ca.getRecordCounts("Server Manager");
		 uk.getRecordCounts("Server Manager");
		 us.transferRecord("USSystem", "MR10003", "CA");
		 uk.transferRecord("UKSystem", "MR10003", "US");
		 us.transferRecord("US2System", "MR10003", "CA");
		 ca.getRecordCounts("Server Manager");
		 System.out.println("-------------END OF PRESET------------");
		
	}

}