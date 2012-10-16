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

import com.basicservice.domain.User;
import com.basicservice.dto.validators.UUIDValidatedString;
import com.basicservice.repository.UserRepository;
import com.basicservice.service.exceptions.UserRegistrationException;

/**
 * A service for user related operations
 */

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public String save(User user) {
		User newUser = userRepository.save(user);
		return newUser.getId();
	}

	public void delete(String userId) {
		userRepository.delete(userId);
	}
	
	public User get(String userId) {
		return userRepository.findOne(userId);
	}
	
	public User update(User user) {
		return userRepository.save(user);
	}

	/**
	 * @param userIdCookie
	 * @return
	 */
	public User getUserFromValidatedSessionId(UUIDValidatedString id) {
		return id != null ? getUserFromSessionId(id.getValue()) : null;
	}
	
	/**
	 * @param userId
	 * @return
	 */
	public User getUserFromSessionId(String sessionId) {
		return sessionId != null ? userRepository.findBySessionId(sessionId) : null;
	}

	public User login(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}

	public User register(String name, String email, String password) throws UserRegistrationException {
		User user = userRepository.findByEmail(email);
		if (user != null) {
			throw new UserRegistrationException(UserRegistrationException.reason.EMAIL_EXISTS);
		}
		user = new User().fillInDefaults();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		user.generateSessionId();
		return user;
	}
}
