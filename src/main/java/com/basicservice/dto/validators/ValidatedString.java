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

import org.owasp.appsensor.errors.AppSensorException;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Validator;
import org.owasp.esapi.errors.IntrusionException;
import org.owasp.esapi.errors.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An abstract class used to define a validated variable
 * Implementors need to define setConstants and provide the necessary configuration for the validation type 
 */

public abstract class ValidatedString {
	private static final Logger LOG = LoggerFactory.getLogger(ValidatedString.class);

	protected abstract void setConstants();	

	protected String value = null;
	private boolean validated = false;
	protected DataPackage constants = null;

	public ValidatedString() {
		setConstants();
		assert (constants != null);
	}

	public final void setValue(String value) {
		this.value = value;
	}

	public final String getValue() {
		if (validated) return value;
		validateValue();
		return value;
	}

	@Override
	public String toString() {
		return value;
	}
	
	public boolean validateValue() {
		// in this validation we want to approve empty id's (but not crooked ones)
		// exactly right or not there are the only two alternatives
		// this is security validation, we don't make sure the data arrives, only that it is safe to use if 
		// it is provided
		if (validated) return true; // don't want to repeat this action
		validated = true; // validation is done only once, either validate (and sanitize) or nullify the value
		if (value != null && value.length() > 0) {
			try {
				LOG.debug("ValidateValue: preparing to validate: " + value);
				value = doActualValidation();
				LOG.debug("ValidateValue: after validate: " + value);
				return true;
			} catch (ValidationException e) {
				LOG.debug("ValidateValue: got exception: " , e);
				value = null;
				new AppSensorException(constants.ERROR_CODE_VALIDATION_ERROR,
						constants.validationErrorMessage,
						constants.validationErrorLogEntry);
				return false;
			} catch (IntrusionException e) {
				LOG.debug("ValidateValue: got exception: ", e);
				value = null;
				new AppSensorException(constants.ERROR_CODE_INTRUSION_ERROR,
						constants.intrusionErrorMessage,
						constants.intrusionErrorLogEntry);
				return false;
			} catch (Exception e) {
				LOG.debug("ValidateValue: got exception: ", e);
				// any exception while validating should nullify the value since we 
				// can't use it non-validated
				value = null;
				new AppSensorException(constants.ERROR_CODE_VALIDATION_ERROR,
						constants.validationErrorMessage,
						constants.validationErrorLogEntry);
				return false;
			}
		}
		return true;
	}

	/**
	 * @throws ValidationException
	 */
	public String doActualValidation() throws ValidationException {
		Validator instance = ESAPI.validator();
		LOG.debug("doActualValidation: validating [" + value + "] using:" + constants.CODE + " with length: " + constants.LENGTH);
		return instance.getValidInput(constants.CODE, value,
				constants.CODE, constants.LENGTH, false);
	}

	protected static class DataPackage {
		public String CODE;
		public int LENGTH;
		public String ERROR_CODE_VALIDATION_ERROR;
		public String validationErrorMessage;
		public String validationErrorLogEntry;
		public String ERROR_CODE_INTRUSION_ERROR;
		public String intrusionErrorMessage;
		public String intrusionErrorLogEntry;

		/**
		 * @param CODE
		 * @param LENGTH
		 * @param validationErrorMessage
		 * @param validationErrorLogEntry
		 * @param intrusionErrorMessage
		 * @param intrusionErrorLogEntry
		 */
		public DataPackage(String CODE, int LENGTH,
				String ERROR_CODE_VALIDATION_ERROR,
				String validationErrorMessage, String validationErrorLogEntry,
				String ERROR_CODE_INTRUSION_ERROR,
				String intrusionErrorMessage, String intrusionErrorLogEntry) {
			this.CODE = CODE;
			this.LENGTH = LENGTH;
			this.ERROR_CODE_VALIDATION_ERROR = ERROR_CODE_VALIDATION_ERROR;
			this.validationErrorMessage = validationErrorMessage;
			this.validationErrorLogEntry = validationErrorLogEntry;
			this.ERROR_CODE_INTRUSION_ERROR = ERROR_CODE_INTRUSION_ERROR;
			this.intrusionErrorMessage = intrusionErrorMessage;
			this.intrusionErrorLogEntry = intrusionErrorLogEntry;
		}
	}
}
