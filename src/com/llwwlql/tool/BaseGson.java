package com.llwwlql.tool;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BaseGson{

	private Gson gson;
	
	/**
	 * @return the gson
	 */
	public Gson getGson() {
		return gson;
	}

	/**
	 * @param gson the gson to set
	 */
	public void setGson(Gson gson) {
		this.gson = gson;
	}
	public void userGson()
	{
		gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
			
			public boolean shouldSkipField(FieldAttributes f) {
				// TODO Auto-generated method stub
				return f.getName().contains("contestusers") 
						| f.getName().contains("email") 
						| f.getName().contains("password") 
						| f.getName().contains("userBlog") 
						| f.getName().contains("problemRating")
						| f.getName().contains("vjudgeuser")
						| f.getName().contains("hduuser")
						| f.getName().contains("pojuser")
						| f.getName().contains("logs");
			}
			
			public boolean shouldSkipClass(Class<?> arg0) {
				// TODO Auto-generated method stub
				return false;
			}
		}).create();
	}
	public void contestGson()
	{
		gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
			
			public boolean shouldSkipField(FieldAttributes f) {
				// TODO Auto-generated method stub
				return f.getName().contains("contestusers");
			}
			
			public boolean shouldSkipClass(Class<?> arg0) {
				// TODO Auto-generated method stub
				return false;
			}
		}).create();
	}
	public void userInfoGson()
	{
		gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
			public boolean shouldSkipField(FieldAttributes f) {
				// TODO Auto-generated method stub
				return f.getName().contains("contestusers") | f.getName().contains("logs");
			}
			
			public boolean shouldSkipClass(Class<?> arg0) {
				// TODO Auto-generated method stub
				return false;
			}
		}).create();
	}
}
