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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.basicservice.domain.User;
import com.basicservice.dto.validators.UUIDValidatedString;
import com.basicservice.service.ConfirmationEmailService;
import com.basicservice.service.Constants;
import com.basicservice.service.GeneralService;
import com.basicservice.service.Utils;

/**
 * Basic controller for managing UI level requests (jsp pages etc.)
 */

@Controller
@RequestMapping("/")
public class AdminController extends ApiController {
	@Autowired // let spring initialize this one
	private GeneralService service;
	@Autowired
	private ConfirmationEmailService confirmationEmailService;


	@RequestMapping({"/", "/home"})
	public ModelAndView homePage(
			HttpServletRequest request,
			HttpServletResponse response
			) {
		Map map = new HashMap();
		prepareContext(map, response);
		return new ModelAndView("basicservice.home", map);
	}
	
	@RequestMapping({"/about"})
	public ModelAndView aboutPage() {
		Map map = new HashMap();
		prepareContext(map);
		return new ModelAndView("basicservice.about", map);
	}
	
	@RequestMapping("/admin")
	public ModelAndView managePage(HttpServletResponse response) {
		Map map = new HashMap();
		prepareContext(map, response);
		return new ModelAndView("basicservice.admin", map);
	}
	
	@RequestMapping("/contact")
	public ModelAndView contactUsPage(HttpServletResponse response) {
		Map map = new HashMap();
		map.put("support", "true");
		prepareContext(map, response);
		return new ModelAndView("basicservice.contact", map);
	}

	private void prepareContext(Map map) {
		prepareContext(map, null);
	}		

	// to the user this seems like an html link, 
	// but it will be generated dynamically like all other calls, with our standard template
	@RequestMapping({"/termsofservice.html"})  
	public ModelAndView termsofservicePage() {
		Map map = new HashMap();
		prepareContext(map);
		return new ModelAndView("basicservice.termsofservice", map);
	}

	@RequestMapping({"/privacypolicy.html"})
	public ModelAndView privacypolicyPage() {
		Map map = new HashMap();
		prepareContext(map);
		return new ModelAndView("basicservice.privacypolicy", map);
	}

	@RequestMapping({"/dmca.html"})
	public ModelAndView dmcaPage() {
		Map map = new HashMap();
		prepareContext(map);
		return new ModelAndView("basicservice.dmca", map);
	}

	@RequestMapping({"/favicon.ico"})
	public String favicon() {
		return "redirect:/resources/images/favicon.ico";
	}

	// TODO: complete csrf functionality
	private void prepareContext(Map map, HttpServletResponse response) {
		Map constants = service.getConstants();
		String csrf_token = "<secret changing key>"; // use SecureRandom to generate a random token
		Locale locale = LocaleContextHolder.getLocale();
		map.put("locale", locale.getLanguage());
		map.put("constants", constants);
		if (response != null) {
			map.put("csrf_token", csrf_token);
			Cookie cookie = new Cookie(Constants.CSRF_COOKIE_NAME, csrf_token);
			cookie.setPath("/");
			cookie.setMaxAge(-1);
			response.addCookie(cookie);
		}
	}
}
