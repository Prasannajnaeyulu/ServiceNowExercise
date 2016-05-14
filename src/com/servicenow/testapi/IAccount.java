package com.servicenow.testapi;

import java.util.List;

import com.servicenow.pojo.Sessions;

public interface IAccount {
	public boolean changeSettings(String firstname, String lastname, String email, String language);
	public boolean changePassword(String newpassword);
	public List<Sessions> getActiveSessions(String username);
	public boolean invalidateSession(Sessions session);
	public boolean logout();
}
