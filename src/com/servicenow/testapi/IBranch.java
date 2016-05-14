package com.servicenow.testapi;
/**
 * 
 * @author prasannanjaneyulu padavala 
 *
 */
public interface IBranch {
	public void createNewBranch(String name, String code);
	public boolean searchBranch(String branchname);
	public void editBranch(String branchname);
	public boolean deleteBranch(String branchname);
}
