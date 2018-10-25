package Repository;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

import Models.EmployeeRecord;
import Models.ManagerRecord;

public class HashMapper {
	private HashMap<Character, ArrayList<Object>> mapper;

	public HashMapper() {
		mapper = new HashMap<Character, ArrayList<Object>>();
	}

	public void put(String lastName, ManagerRecord o) {
		char key = lastName.toUpperCase().charAt(0);
		if (mapper.containsKey(key)) {
			mapper.get(key).add(o);
		} else {
			ArrayList<Object> al = new ArrayList<Object>();
			al.add(o);
			mapper.put(key, al);
		}		
	}

	public void put(String lastName, EmployeeRecord o) {


		char key = lastName.toUpperCase().charAt(0);
		if (mapper.containsKey(key)) {
			mapper.get(key).add(o);
		} else {
			ArrayList<Object> al = new ArrayList<Object>();
			al.add(o);
			mapper.put(key, al);
		}
		
	}

	public int getCount() {
		int mapSize = 0;
		for (char key : mapper.keySet()) {
			mapSize = mapSize + mapper.get(key).size();
		}
		return mapSize;
	}

	public String edit(String recordId, String fieldName, String newValue) {
		for (Character key : mapper.keySet()) {
			for (int i = 0; i < mapper.get(key).size(); i++) {
				
				if (mapper.get(key).get(i) instanceof ManagerRecord) {
					ManagerRecord record = (ManagerRecord) mapper.get(key).get(i);
					
					if (record.getRecordId().equals(recordId)) {
						return record.assign(fieldName, newValue);
					}

				} else if (mapper.get(key).get(i) instanceof EmployeeRecord) {
					EmployeeRecord record = (EmployeeRecord) mapper.get(key).get(i);

					if (record.getRecordId().equals(recordId)) {
						return record.assign(fieldName, newValue);

					} 
				} 
			}
		}
		return "This Record does not Exists!";
	}
	public void printAll() {

		for (Character key : mapper.keySet()) {
			for (int i = 0; i < mapper.get(key).size(); i++) 
			{
				if (mapper.get(key).get(i) instanceof ManagerRecord) 
				{
					ManagerRecord record = (ManagerRecord) mapper.get(key).get(i);
					System.out.println(record.getRecordId()+" "+record.getFirstName()+" "+record.getLastName()+" "+record.getMailId()+" "
					+record.getLocation()+" "+record.getManagerId());
				}else {
					EmployeeRecord record = (EmployeeRecord) mapper.get(key).get(i);
					System.out.println(record.getRecordId()+" "+record.getFirstName()+" "+record.getLastName()+" "+record.getMailId()+" "
					+record.getProjectId()+" "+record.getEmployeeId());
				}
			}
		}
	}
	public boolean find(String recordId){
		for (Character key : mapper.keySet()) {
			for (int i = 0; i < mapper.get(key).size(); i++) {
				
				if (mapper.get(key).get(i) instanceof ManagerRecord) {
					ManagerRecord record = (ManagerRecord) mapper.get(key).get(i);
					
					if (record.getRecordId().equals(recordId)) {
						return true;
					}

				} else if (mapper.get(key).get(i) instanceof EmployeeRecord) {
					EmployeeRecord record = (EmployeeRecord) mapper.get(key).get(i);

					if (record.getRecordId().equals(recordId)) {
						return true;

					} 
				} 
			}
		}
		return false;		
	}
	
	public String delete(String recordId){
		for (Character key : mapper.keySet()) {
			for (int i = 0; i < mapper.get(key).size(); i++) {
				
				if (mapper.get(key).get(i) instanceof ManagerRecord) {
					ManagerRecord record = (ManagerRecord) mapper.get(key).get(i);
					
					if (record.getRecordId().equals(recordId)) {
						mapper.get(key).remove(i);
						return "This Record was Deleted";
					}

				} else if (mapper.get(key).get(i) instanceof EmployeeRecord) {
					EmployeeRecord record = (EmployeeRecord) mapper.get(key).get(i);
					
					if (record.getRecordId().equals(recordId)) {
						mapper.get(key).remove(i);
						return "This Record was Deleted";
					} 
				} 
			}
		}
		return "This Record does not Exists!";
	}	
	
	public Object extract(String recordId){
		for (Character key : mapper.keySet()) {
			for (int i = 0; i < mapper.get(key).size(); i++) {
				
				if (mapper.get(key).get(i) instanceof ManagerRecord) {
					ManagerRecord record = (ManagerRecord) mapper.get(key).get(i);
					
					if (record.getRecordId().equals(recordId)) {
						return record;
					}

				} else if (mapper.get(key).get(i) instanceof EmployeeRecord) {
					EmployeeRecord record = (EmployeeRecord) mapper.get(key).get(i);

					if (record.getRecordId().equals(recordId)) {
						return record;

					} 
				} 
			}
		}
		return "This Record does not Exists!";		
	}
	
}
