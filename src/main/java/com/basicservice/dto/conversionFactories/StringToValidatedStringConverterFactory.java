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
package com.basicservice.dto.conversionFactories;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import com.basicservice.dto.validators.ValidatedString;

/**
 * A converter class used by Spring to convert Strings to ValidatedStrings
 * Used when a ValidatedString is used in a controller and the value passed in 
 * is a String 
 */

public class StringToValidatedStringConverterFactory implements
		ConverterFactory<String, ValidatedString> {

	public <T extends ValidatedString> Converter<String, T> getConverter(
			Class<T> targetType) {
		return new StringToValidatedStringConverter(targetType);
	}

	private final class StringToValidatedStringConverter<T extends ValidatedString>
			implements Converter<String, T> {

		private Class<T> stringValidationClass;

		public StringToValidatedStringConverter(Class<T> stringValidationClass) {
			this.stringValidationClass = stringValidationClass;
		}

		public T convert(String source) {
			try {
				T svci = stringValidationClass.newInstance();
				svci.setValue(source);
				return svci;
			} catch (InstantiationException e) {
				return null;
			} catch (IllegalAccessException e) {
				return null;
			}
		}
	}
}