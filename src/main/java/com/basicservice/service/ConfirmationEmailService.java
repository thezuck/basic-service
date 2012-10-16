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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basicservice.domain.ConfirmationString;
import com.basicservice.dto.validators.UUIDValidatedString;
import com.basicservice.repository.ConfirmationRepository;

/**
 * A service used to save and retrieve email confirmation keys for users
 */

@Service
public class ConfirmationEmailService {
	@Autowired
	private ConfirmationRepository repository;

	public ConfirmationString getConfirmationString(String userId) {
		ConfirmationString cs = new ConfirmationString();
		cs.setUserId(userId);
		cs.setKey(Utils.generateRandomUUID());
		repository.save(cs);
		return cs;
	}

	public String confirmEmail(UUIDValidatedString key) {
		ConfirmationString cs = repository.findOne(key.getValue());
		if (cs != null) {
			repository.delete(cs);
			return cs.getUserId();
		} else {
			return null;
		}
	}
}
