package com.servicenow.impl.login;

import com.servicenow.impl.register.RegisterPage;
import com.servicenow.testapi.ILogin;
/**
 * 
 * @author prasannanjaneyulu padavala 
 *
 */
public class LoginSelenium2Impl implements ILogin {

	@Override
	public boolean login(String sUsername, String sPassword) {
		LoginPage login = new LoginPage();
		return login.login(sUsername, sPassword);
	}

	@Override
	public boolean registerNewAccount(String sLoginname, String sEmail,
			String sPassword) {
		RegisterPage register =  new RegisterPage();
		return register.registerNewAccount(sLoginname, sEmail, sPassword);
	}

	@Override
	public boolean invalidLogin(String username, String password) {
		LoginPage login = new LoginPage();
		return login.invalidLogin(username, password);
	}
}
