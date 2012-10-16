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

package com.basicservice.controller;

import org.owasp.appsensor.errors.AppSensorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import com.basicservice.service.MailService;
import com.basicservice.service.MessageLocalizationService;
import com.basicservice.service.UserService;

/**
 * Base controller, will catch all exception and gracefully return an error.
 */

@Controller
@RequestMapping("/api")
public class ApiController {
	@Autowired
	protected UserService userService;
	@Autowired
	protected MailService mailService;
	@Autowired
	protected MessageLocalizationService messageLocalizationService;
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity handleGenericException( Exception error ) {
		try {
			new AppSensorException("ACE1", "Invalid request", "Generic exception occured");
		} catch (Exception e) {
			// AppSensor might throw an exception, but we want to catch it here and stop propagation
		}
		return new ResponseEntity<String>(HttpStatus.SERVICE_UNAVAILABLE);
	}
}
