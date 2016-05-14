package com.servicenow.testapi;
/**
 * 
 * @author prasannanjaneyulu padavala 
 *
 */
public interface IStaff {
	public void createNewStaff(String name, String branch);
	public boolean searchStaff(String staffname);
	public void editStaff(String staffname);
	public boolean deleteStaff(String staffname);
}
