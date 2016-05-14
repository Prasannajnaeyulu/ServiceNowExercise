package com.servicenow.testapi;
/**
 * 
 * @author prasannanjaneyulu padavala 
 *
 */

public interface ILogin {
	public boolean login(String sUsername, String sPassword);
	public boolean invalidLogin(String sUsername, String sPassword);
	public boolean registerNewAccount(String sLoginname, String sEmail,String sPassword);
}
