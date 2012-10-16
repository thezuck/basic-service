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

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basicservice.domain.GeneralObj;
import com.basicservice.repository.GeneralRepository;

/**
 * A service used to do general actions such as verify basic functionality works (db access)
 */

@Service
public class GeneralService {
	private static final Logger LOG = LoggerFactory.getLogger(GeneralService.class);
	@Autowired
	private GeneralRepository generalRepository;
	
	public boolean initReadWriteTest() {
		try {
			String uuid = Utils.generateRandomUUID();
			GeneralObj obj = new GeneralObj();
			obj.setStringValue(uuid);
			obj.setId(uuid);
			generalRepository.save(obj);
			GeneralObj storedObj = generalRepository.findOne(uuid);
			if (storedObj != null && storedObj.getStringValue().equals(uuid)) {
				generalRepository.delete(uuid);
				return true;
			}
		} catch (Throwable t) {
			LOG.debug("Exception caught while testing db: ", t);
		}
		return false;
	}

	public Map getConstants() {
		Map constantCollection = new HashMap();
		return constantCollection;
	}
}
