/**
*    This file is part of Basic Service.
*
*    Basic Service is free software: you can redistribute it and/or modify
*    it under the terms of the GNU General Public License as published by
*    the Free Software Foundation, either version 3 of the License, or
*    (at your option) any later version.
*
*    Basic Service is distributed in the hope that it will be useful,
*    but WITHOUT ANY WARRANTY; without even the implied warranty of
*    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*    GNU General Public License for more details.
*
*    You should have received a copy of the GNU General Public License
*    along with Basic Service (See GPL.txt).  If not, see <http://www.gnu.org/licenses/>.
*    
* 	If needed the author is Amir Zucker and can be reached at zucksoft@gmail.com 
*/
package com.basicservice.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.basicservice.service.Utils;

/**
 * The user object holding user related data 
 */

@Document
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class User {
	// Serialized properties
	@Id
	@JsonProperty
	private String id;
	@JsonProperty
	private String name;
	@JsonProperty
	private String email;

	@JsonProperty
	private boolean emailConfirmed;
	
	// Non-Serialized properties
	private Date lastLogin;
	private String sessionId;
	private String password;

	
	public User() {
	}
	
	public User fillInDefaults() {
		id = Utils.generateRandomUUID();
		lastLogin = new Date();
		sessionId = null;
		return this;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	public String getSessionId() {
		return sessionId;
	}
	public String generateSessionId() {
		sessionId = Utils.generateRandomUUID();
		return sessionId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public boolean isEmailConfirmed() {
		return emailConfirmed;
	}

	public void setEmailConfirmed(boolean emailConfirmed) {
		this.emailConfirmed = emailConfirmed;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
