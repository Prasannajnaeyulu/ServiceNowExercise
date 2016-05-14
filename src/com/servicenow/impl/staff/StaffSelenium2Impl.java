package com.servicenow.impl.staff;

import com.servicenow.testapi.IStaff;
/**
 * 
 * @author prasannanjaneyulu padavala 
 *
 */
public class StaffSelenium2Impl implements IStaff{
	
	StaffPage spage = new StaffPage();

	@Override
	public void createNewStaff(String name, String branch) {
		spage.createNewStaff(name, branch);
	}

	@Override
	public boolean searchStaff(String staffname) {
		return spage.searchStaff(staffname);
	}

	@Override
	public boolean deleteStaff(String staffname) {
		return spage.deleteStaff(staffname);
	}

	@Override
	public void editStaff(String staffname) {
		spage.editStaff(staffname);		
	}

}
