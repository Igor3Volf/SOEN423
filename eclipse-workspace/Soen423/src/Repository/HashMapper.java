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
	public void put(String lastName, ArrayList<Object> o) {
		
		char key = lastName.toUpperCase().charAt(0);
		if(mapper.containsKey(key)) {
			mapper.get(key).add(o.get(0));
		}else {			
			mapper.put(key,o);
		}	
		
	}
	public int getCount() {
		int mapSize=0;
		for(char key: mapper.keySet()) {
			mapSize= mapSize + mapper.get(key).size();
		}
		return mapSize;
	}
}
