package com.llwwlql.tool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class Property {
	
	public static String[] getProperty(Map<String,Object> map){
		Iterator it = map.keySet().iterator();
		String[] property = new String[map.size()];
		for(int i=0;it.hasNext();i++)
		{
			property[i]=it.next().toString();
		}
		return property;
	}
	
	public static Object[] getValue(Map<String,Object> map){
		Iterator it = map.keySet().iterator();
		Object[] value = new Object[map.size()];
		Object key;
		for(int i=0;it.hasNext();i++)
		{
			key = it.next();
			value[i]=map.get(key);
		}
		
		return value;
	}
	
}
