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

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.owasp.appsensor.errors.AppSensorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriTemplate;

import com.basicservice.domain.ConfirmationString;
import com.basicservice.domain.User;
import com.basicservice.dto.validators.EmailValidatedString;
import com.basicservice.dto.validators.UUIDValidatedString;
import com.basicservice.service.ConfirmationEmailService;
import com.basicservice.service.Constants;
import com.basicservice.service.GeneralService;
import com.basicservice.service.Utils;
import com.basicservice.service.exceptions.UserRegistrationException;

/**
 * User controller, manages login/register and other user actions
 */

@Controller
@RequestMapping("/api/users")
public class UserController extends ApiController {
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private GeneralService generalService;
	@Autowired
	private ConfirmationEmailService confirmationEmailService;

	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public User findLoggedinUser(
			@CookieValue(value=Constants.AUTHENTICATED_USER_ID_COOKIE, required=false) UUIDValidatedString userIdCookie, 
			HttpServletResponse response) {
		User user = userService.getUserFromValidatedSessionId(userIdCookie);
		if (user != null) {
			return user;
		} else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	}

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ResponseEntity<String> login(HttpServletRequest request, 
			@RequestParam(value="email") EmailValidatedString email, 
			@RequestParam(value="password") String password
			) {
			if (email == null || email.getValue() == null || password == null) {
				return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
			}
			User user = userService.login(email.getValue(), password);
			LOG.debug("Got login request: email:" + email.getValue() + ", pass:" + password);
			
			if (user == null) { 
				try {
					new AppSensorException("AF1", "Authentication Failure", "Authentication Failure detected");
				} catch (Exception e) {
					// AppSensor might throw an exception, but we want to catch it here and stop propagation
				}
				return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
			} else 
			if (! user.isEmailConfirmed()) {
				// let the user know that she can't login until she confirms the email
				return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
			}

			user.setLastLogin(new Date());

			LOG.debug("Preparing to save user to db");
			userService.save(user);
			final HttpHeaders headers = new HttpHeaders();
			headers.add("Set-Cookie", Constants.AUTHENTICATED_USER_ID_COOKIE + "=" + user.getSessionId() + "; Path=/");
			return new ResponseEntity<String>(headers, HttpStatus.OK);
	}	
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public ResponseEntity<String> register(HttpServletRequest request, 
			@RequestParam(value="name") String name,
			@RequestParam(value="email") EmailValidatedString email, 
			@RequestParam(value="password") String password
			) {
		if (email == null || email.getValue() == null || password == null) {
			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
		}
		LOG.debug("Got register request: email:" + email.getValue() + ", pass:" + password + ", name:" + name);
		
		User user = null;
		try {
			user = userService.register(name, email.getValue(), password);
		} catch (UserRegistrationException e) {
			if (e.getReason() == UserRegistrationException.reason.EMAIL_EXISTS) {
				return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
			}
		}
		if (user == null) { 
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOG.debug("Preparing to save user to db");
		final String id = userService.save(user);
		if (id != null) {
			String from = Constants.REGISTRATION_FROM_ADDRESS;
			String to = email.getValue();
			Locale locale = LocaleContextHolder.getLocale();
			String subject = messageLocalizationService.getMessage(Constants.MLS_REGISTRATION_CONFIRMATION_SUBJECT, locale);
			Object[] args = new String[1];
			String path = Utils.getBasicPath(request);
			ConfirmationString cs = confirmationEmailService.getConfirmationString(user.getId());
			URI uri = new UriTemplate("{requestUrl}/api/users/registrationConfirmation/?key={key}").expand(path, cs.getKey());
			args[0] = uri.toASCIIString();
			String messageHtml = messageLocalizationService.getMessage(Constants.MLS_REGISTRATION_CONFIRMATION_HTML, args, locale);
			try {
				mailService.sendEmail(from, to, subject, messageHtml);
			} catch (Exception e) {
				LOG.debug("Failed to send confirmation email to: " + to, e);
			}
		}
		final HttpHeaders headers = new HttpHeaders();
		headers.add("Set-Cookie", Constants.AUTHENTICATED_USER_ID_COOKIE + "=" + id + "; Path=/");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}	

	@RequestMapping(value="/registrationConfirmation", method=RequestMethod.GET)
	public ResponseEntity<String> confirmEmail(
			HttpServletRequest request, 
			@RequestParam(value="key") UUIDValidatedString key,
			HttpServletResponse response
			) {
		String validatdKey = Utils.getValueFromValidatedString(key);
		if (validatdKey != null) {
			String userId = confirmationEmailService.confirmEmail(key);
			if (userId != null) {
				User user = userService.get(userId);
				user.setEmailConfirmed(true);
				userService.save(user);
			}
		}
		String uri = Utils.getBasicPath(request);
		try {
			response.sendRedirect(response.encodeRedirectURL(uri));
		} catch (IOException e) {
		}
		return new ResponseEntity<String>(HttpStatus.OK);		
	}	
	
}
