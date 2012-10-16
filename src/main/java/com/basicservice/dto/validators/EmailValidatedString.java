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
package com.basicservice.dto.validators;

/**
 * An Email validation field element
 * Usage:
 * @RequestParam(value="email") EmailValidatedString email  // this field will be read from the request and validated 
 */

public class EmailValidatedString extends ValidatedString{
	private final static DataPackage EmailValidatedStringConstants = 
			new DataPackage(
					"Email",100,
					"VE2", "Invalid Email", "Validation failed on Email",
					"IE2", "Invalid Email", "Possible intrusion detected while parsing Email");
	public EmailValidatedString() {
		super();
	}
	
	@Override
	protected void setConstants() {
		super.constants = EmailValidatedStringConstants;
	}
}
