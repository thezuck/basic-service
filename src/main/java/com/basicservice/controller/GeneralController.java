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

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.basicservice.service.GeneralService;

/**
 * General purpose controller, use to verify that our basic functionality works
 */

@Controller
@RequestMapping("/api/test")
public class GeneralController extends ApiController {
	private static final Logger LOG = LoggerFactory.getLogger(GeneralController.class);

	@Autowired
	private GeneralService service;

	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> runTest(HttpServletResponse response) {
		LOG.debug("Starting test write to db");
		boolean success = service.initReadWriteTest();
		LOG.debug("DB test write results: " + success);
	   return new ResponseEntity<String>(success ? HttpStatus.OK : HttpStatus.SERVICE_UNAVAILABLE);
	}
}
