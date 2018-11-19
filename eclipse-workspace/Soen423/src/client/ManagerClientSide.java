package client;

import java.io.IOException;
import java.net.URL;

import javax.xml.ws.Service;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.namespace.QName;

import repository.LogWriter;
import servers.CenterServerInterface;
import models.Project;

public class ManagerClientSide {

	static CenterServerInterface serverO;
	private static LogWriter log;
	private final static String PATH = "C:\\Users\\Igor3Volf\\git\\SOEN423\\eclipse-workspace\\Soen423\\Client_logs\\"; // descktop

	// private final static String PATH =
	// "C:\\Users\\igor3\\eclipse-workspace\\Soen423\\src\\Client_logs\\";
	// //laptop
	public static void main(String[] args) throws Exception {
		Scanner keyboard = new Scanner(System.in);
		login(keyboard);
	}

	private static void login(Scanner keyboard) throws IOException {
		System.out.println("Hello User, Please enter your ID:[CA|UK|US]#### ");
		String input = keyboard.next();
		if (validUserId(input)) {
			log = new LogWriter(input + "_logs.txt", PATH);
			String location = input.substring(0, 2);
			connectToServer(location);

			options(keyboard, input);
		} else {
			login(keyboard);
		}
	}

	private static void options(Scanner keyboard, String userName) {
		System.out.println();
		System.out.println("Chose one from following :");
		System.out.println("1. Create Manager Record.");
		System.out.println("2. Create Employee Record.");
		System.out.println("3. Get Record Count.");
		System.out.println("4. Edit Record");
		System.out.println("5. Transfer Record");

		System.out.println("0. Exit");
		int choice = keyboard.nextInt();
		if (choice > 0) {
			switch (choice) {
			case 1:
				option1(keyboard, userName);
				break;
			case 2:
				option2(keyboard, userName);
				break;
			case 3:
				option3(keyboard, userName);
				break;
			case 4:
				option4(keyboard, userName);
				break;
			case 5:
				option5(keyboard, userName);
				break;
			case 0:
				System.exit(0);
				break;
			default:
				System.out.println("Invalid Option");
				break;
			}
		}
	}

	private static void option1(Scanner k, String userName) {
		Project p = new Project();
		String firstName, lastName, managerId, mailId, location;

		System.out.println();
		System.out.println("Enter First Name");
		firstName = k.next();
		if (isEmpty(firstName) || !validName(firstName)) {
			System.out.println("Invalid Input");
			option1(k, userName);
			
		}

		System.out.println("Enter Last Name");
		lastName = k.next();
		if (isEmpty(lastName) || !validName(lastName)) {
			System.out.println("Invalid Input");
			option1(k, userName);
		}

		System.out.println("Enter Manager ID [CA|UK|US]####");
		managerId = k.next();
		if (isEmpty(managerId) || !validUserId(managerId)
				|| !allowedLocation(userName, managerId)) {
			System.out.println("Invalid Input");
			option1(k, userName);
		}

		System.out.println("Enter Mail ID");
		mailId = k.next();
		if (isEmpty(mailId) || !validEmail(mailId)) {
			System.out.println("Invalid Input");
			option1(k, userName);
		}

		System.out.println("Enter Project ID P#####");
		String projectId = k.next();
		if (isEmpty(projectId) || !validProjectId(projectId)) {
			System.out.println("Invalid Input");
			option1(k, userName);
		}
		p.setProjectId(projectId);

		System.out.println("Enter Name of the Client");
		String clientName = k.next();
		if (isEmpty(clientName) || !validName(clientName)) {
			System.out.println("Invalid Input");
			option1(k, userName);
		}
		p.setClientName(clientName);

		System.out.println("Enter Name of the Project");
		String projectName = k.next();
		if (isEmpty(projectName)) {
			System.out.println("Invalid Input");
			option1(k, userName);
		}
		p.setProjectName(projectName);

		System.out.println("Enter Location (CA,US,UK)");
		location = k.next();
		if (isEmpty(location) || !allowedLocation(userName, location)) {
			System.out.println("Invalid Input");
			option1(k, userName);
		}
		String message = serverO.createMRecord(userName, firstName, lastName,
				managerId, mailId, p, location);
		log.writeLog(userName, message, location);
		options(k, userName);

	}

	private static void option2(Scanner k, String userName) {
		String firstName, lastName, employeeId, mailId, projectId;

		System.out.println("Enter First Name");
		firstName = k.next();
		if (isEmpty(firstName) || !validName(firstName)) {
			System.out.println("Invalid Input");
			option2(k, userName);
		}

		System.out.println("Enter Last Name");
		lastName = k.next();
		if (isEmpty(lastName) || !validName(lastName)) {
			System.out.println("Invalid Input");
			option2(k, userName);
		}
		System.out.println("Enter Employee ID #####");
		employeeId = k.next();
		if (isEmpty(employeeId) || !validEmpId(employeeId)) {
			System.out.println("Invalid Input");
			option2(k, userName);
		}
		System.out.println("Enter Mail ID");
		mailId = k.next();
		if (isEmpty(mailId) || !validEmail(mailId)) {
			System.out.println("Invalid Input");
			option2(k, userName);
		}
		System.out.println("Enter Project ID P#####");
		projectId = k.next();
		if (isEmpty(projectId) || !validProjectId(projectId)) {
			System.out.println("Invalid Input");
			option2(k, userName);
		}

		String message = serverO.createERecord(userName, firstName, lastName,
				(short) Integer.parseInt(employeeId), mailId, projectId);
		log.writeLog(userName, message, userName.substring(0, 2).toUpperCase());
		options(k, userName);

	}

	private static void option3(Scanner k, String userName) {
		String message;
		message = serverO.getRecordCounts(userName);
		log.writeLog(userName, message, userName.substring(0, 2).toUpperCase());
		options(k, userName);
	}

	private static void option4(Scanner k, String userName) {
		String recordId, fieldName, newValue;
		System.out.println();
		System.out.println("Enter Record ID: [MR|ER]#####");
		recordId = k.next();
		if (!validRecordId(recordId)) {
			System.out.println("Invalid Input");
			option4(k, userName);
		}
		System.out
				.println("Enter Field Name(mailId, location, projectId, projectName, clientName):");
		fieldName = k.next();
		if (isEmpty(fieldName)) {
			System.out.println("Invalid Input");
			option4(k, userName);
			
		}
		System.out.println("Enter New Value:");
		newValue = k.next();
		if (isEmpty(newValue)) {
			System.out.println("Invalid Input");
			option4(k, userName);
		}
		if (fieldName.equals("location")) {
			if (!allowedLocation(userName, newValue)) {
				System.out.println("Invalid Input");
				option4(k, userName);
			}
		}

		String message = serverO.editRecord(userName, recordId, fieldName,
				newValue);
		log.writeLog(userName, message, userName.substring(0, 2).toUpperCase());

		options(k, userName);
	}

	private static void option5(Scanner k, String userName) {

		String recordId, otherServer;
		System.out.println();
		System.out.println("Enter Record ID: [MR|ER]#####");
		recordId = k.next();
		if (!validRecordId(recordId)) {
			System.out.println("Invalid Input");
			option4(k, userName);
		}
		System.out.println("Enter New Server :");
		otherServer = k.next();
		if (isEmpty(otherServer) || !validServer(userName, otherServer)) {
			System.out.println("Invalid Input");
			option5(k, userName);
		}

		String message = serverO
				.transferRecord(userName, recordId, otherServer);
		log.writeLog(userName, message, userName.substring(0, 2).toUpperCase());

		options(k, userName);
	}

	private static void connectToServer(String location) {

		try {
			if (location.equals("CA")) {
				String name = "Canada";
				URL url = new URL("http://localhost:8080/CenterServer/" + name
						+ "?wsdl");
				QName qName = new QName("http://Servers/",
						"CenterServerService");
				Service service = Service.create(url, qName);
				serverO = service.getPort(CenterServerInterface.class);

			} else if (location.equals("US")) {
				String name = "America";
				URL url = new URL("http://localhost:8080/CenterServer/" + name
						+ "?wsdl");
				QName qName = new QName("http://Servers/",
						"CenterServerService");
				Service service = Service.create(url, qName);
				serverO = service.getPort(CenterServerInterface.class);				

			} else if (location.equals("UK")) {
				String name = "England";
				URL url = new URL("http://localhost:8080/CenterServer/" + name
						+ "?wsdl");
				QName qName = new QName("http://Servers/",
						"CenterServerService");
				Service service = Service.create(url, qName);
				serverO = service.getPort(CenterServerInterface.class);
			} else {
				System.out.println("Wrong Location.");
				System.exit(0);
			}

		} // end try
		catch (Exception e) {
			System.out.println("Exception in ManagerClientSide: " + e);
		}

	}

	private static boolean validUserId(String in) {
		if (in.matches("(^CA(\\d{4})$)|(^UK(\\d{4})$)|(^US(\\d{4})$)"))
			return true;

		else
			return false;
	}

	private static boolean validName(String in) {
		Pattern digit = Pattern.compile("[0-9]");
		Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
		Matcher hasDigit = digit.matcher(in);
		Matcher hasSpecial = special.matcher(in);
		if (hasDigit.find() || hasSpecial.find())
			return false;
		else
			return true;
	}

	private static boolean isEmpty(String in) {
		if (in.isEmpty() || in.length() < 2)
			return true;
		else
			return false;
	}

	private static boolean allowedLocation(String user, String in) {
		if (user.substring(0, 2).equals(in.substring(0, 2)))
			return true;
		else
			return false;

	}

	private static boolean validProjectId(String in) {

		if (in.matches("^P\\d{5}$"))
			return true;

		else
			return false;
	}

	private static boolean validRecordId(String in) {
		if (in.matches("(^MR([1-9]\\d{4})$)|(^ER([1-9]\\d{4})$)"))
			return true;

		else
			return false;
	}

	private static boolean validEmpId(String in) {
		Pattern p = Pattern.compile("^[0-9]{5}$");
		Matcher m = p.matcher(in);
		return m.find();
	}

	private static boolean validEmail(String emailStr) {

		// Took from
		// https://stackoverflow.com/questions/8204680/java-regex-email
		// 10/26/2018
		Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
				"^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}

	private static boolean validServer(String user, String server) {
		Pattern s = Pattern.compile("(CA)|(US)|(UK)");
		Matcher matcher = s.matcher(server);
		if (matcher.find() == false
				|| server.equals(user.substring(0, 2).toUpperCase()))
			return false;
		else
			return true;
	}
}
