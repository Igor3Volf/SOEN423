package Repository;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

import Models.EmployeeRecord;
import Models.ManagerRecord;

public class HashMapper 
{
	HashMap<Character, ArrayList<Object>> mapper;
	
	public HashMapper()
	{
		mapper = new HashMap<Character, ArrayList<Object>>();		
	}
	public void put(String lastName, ManagerRecord o) {
		
		char key = lastName.toUpperCase().charAt(0);
		if(mapper.containsKey(key)) {
			mapper.get(key).add(o);
		}else {			
			ArrayList<Object> al = new ArrayList<Object>();
			al.add(o);
			mapper.put(key,al);
		}	
		
	}
	public void put(String lastName, EmployeeRecord o) {
		
		char key = lastName.toUpperCase().charAt(0);
		if(mapper.containsKey(key)) {
			mapper.get(key).add(o);
		}else {			
			ArrayList<Object> al = new ArrayList<Object>();
			al.add(o);
			mapper.put(key,al);
		}		
		
	}
	public int getCount() {
		int mapSize=0;
		for(char key: mapper.keySet()) {
			mapSize= mapSize + mapper.get(key).size();
		}
		return mapSize;
	}
	public String edit(String recordId, String fieldName, String newValue) 
	{
		for(Character key: mapper.keySet()) 
		{
			for(int i=0; i<mapper.get(key).size(); i++)
			{
				
				if(mapper.get(key).get(i) instanceof ManagerRecord) 
				{
					ManagerRecord record = (ManagerRecord)mapper.get(key).get(i);
					
					if(record.getRecordId().equals(recordId)) 
					{
						return record.assign(fieldName, newValue);
					}
					else
					{
						return "This record does not exist.";
					}
					
				}
				else if(mapper.get(key).get(i) instanceof EmployeeRecord)
				{
					EmployeeRecord record = (EmployeeRecord)mapper.get(key).get(i);
					if(record.getRecordId().equals(recordId)) 
					{
						return record.assign(fieldName, newValue);

					}else
					{
						return "This record does not exist.";
					}

				}else
				{
					return "Invalid Input.";
				}
			}			
		}
		return "Something";
	}
}
