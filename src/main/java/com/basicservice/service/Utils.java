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

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.owasp.esapi.reference.crypto.JavaEncryptor;

import com.basicservice.dto.validators.ValidatedString;

/**
 * A utility class for static convenience methods
 */

public class Utils {
	private static final Logger LOG = LoggerFactory.getLogger(Utils.class);
		
	public static void generateESAPIKeys() throws Exception {
		JavaEncryptor.main(new String[0]);
	}

	public static String getBasicPath(HttpServletRequest req) {
	    String scheme = req.getScheme();             // http
	    String serverName = req.getServerName();     // hostname.com
	    int serverPort = req.getServerPort();        // 80

	    return scheme+"://"+serverName+":"+serverPort + "/";
	}
	
	public static String getValueFromValidatedString(
			ValidatedString vs) {
		return  vs != null ? vs.getValue() : null;
	}

	public static String generateRandomUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	public static String getStringValueIfNotNull(Object o) {
		if (o == null) return null;
		return o.toString();
	}
}
