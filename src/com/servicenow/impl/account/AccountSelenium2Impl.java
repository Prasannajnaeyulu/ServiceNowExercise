package com.servicenow.impl.account;

import java.util.List;

import com.servicenow.pojo.Sessions;
import com.servicenow.testapi.IAccount;

public class AccountSelenium2Impl implements IAccount {

	@Override
	public boolean changePassword(String newpassword) {
		
		return new ChangePasswordPage().changePassword(newpassword);
	}

	@Override
	public boolean changeSettings(String firstname, String lastname,
			String email, String language) {
		
		return new ChangeSettingsPage().changeSettings(firstname, lastname, email, language);
	}

	@Override
	public List<Sessions> getActiveSessions(String username) {
		SessionsPage sessions = new SessionsPage();
		return sessions.getActiveSessions(username);
	}

	@Override
	public boolean invalidateSession(Sessions session) {
		return new SessionsPage().invalidateSession(session);
	}

	@Override
	public boolean logout() {
		return new SessionsPage().logout(); 
	}

}
