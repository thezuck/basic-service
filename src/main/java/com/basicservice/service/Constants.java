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
package com.basicservice.service;

/**
 * A class to hold all the constants in the system
 */

public class Constants {
	public static final String AUTHENTICATED_USER_ID_COOKIE = "TS_ENC_AUTH_UID";
	public static final int ONE_YEAR_COOKIE = 60*60*24*365;
	public static final String REGISTRATION_FROM_ADDRESS = "registration@basicservice.com";
	public static final String SUPPORT_BASICSERVICE = "support@basicservice.com";	
	public static final String CSRF_COOKIE_NAME = "CSRF_KEY";
	public static final String MLS_REGISTRATION_CONFIRMATION_SUBJECT = "email.registrationConfirmation.subject";	
	public static final String MLS_REGISTRATION_CONFIRMATION_HTML = "email.registrationConfirmation.html";
}

