package com.servicenow.impl.branch;

import com.servicenow.testapi.IBranch;
/**
 * 
 * @author prasannanjaneyulu padavala 
 *
 */
public class BranchSeleium2Impl implements IBranch{
	BranchPage branchpage = new BranchPage();

	@Override
	public void createNewBranch(String name, String code) {
	  branchpage.createNewBranch(name, code);
	}

	@Override
	public boolean searchBranch(String branchname) {
		return branchpage.searchBranch(branchname);		
	}

	@Override
	public boolean deleteBranch(String branchname) {
		return branchpage.deleteBranch(branchname);
	}

	@Override
	public void editBranch(String branchname) {
		branchpage.editBranch(branchname);
	}

}
