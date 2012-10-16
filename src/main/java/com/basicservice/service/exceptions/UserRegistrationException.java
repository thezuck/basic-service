package com.basicservice.service.exceptions;

/**
 * An exception thrown when a user tries to register with an already existing email
 */

public class UserRegistrationException extends Exception {
	reason why;
	
	public UserRegistrationException(reason why) {
		this.why = why;
	}

	public static enum reason {
		EMAIL_EXISTS,
	}
	
	public reason getReason() {
		return why;
	}
}
