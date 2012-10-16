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
package com.basicservice.dto.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.basicservice.dto.StringWrapper;
import com.basicservice.dto.validators.ValidatedString;

/**
 * An input validation handler which is called by @InputValidation annotation.
 * Triggers a validation of the value by the defined constraint, assigning the output
 * to the input string 
 */

public class InputConstraintValidator implements ConstraintValidator<InputValidation, StringWrapper> {
	Class<? extends ValidatedString> cvs;
	
	public void initialize(InputValidation constraintAnnotation) {
		cvs = constraintAnnotation.inputValidator();
	}
	
	public boolean isValid(StringWrapper value, ConstraintValidatorContext context) {
		if (value == null) return true;  // nothing to validate if it's null, no biz logic here only security
		
		ValidatedString instance;
		try {
			instance = cvs.newInstance();
		} catch (InstantiationException e) {
			return false;
		} catch (IllegalAccessException e) {
			return false;
		}
		instance.setValue(value.getValue());
		boolean valid = instance.validateValue();
		if (!valid) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("message")
			.addNode(value.getValue())
			.addConstraintViolation();
		}
		value.setValue(instance.getValue());
		return valid;
	}
}
