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

import com.basicservice.dto.StringWrapper;

/**
 * Used by MongoDB repository for reading StringWrappers from db
 * It will take the String value in db and return a StringWrapper with that value
 * Used to read beans which have StringWrapper fields from MongoDB
 */

public class StringWrapperMongoDBReadConverter  implements Converter<String, StringWrapper> {
	public StringWrapper convert(String source) {
		if(source == null) return null;	
		StringWrapper sw = new StringWrapper();
		sw.setValue(source);
		return sw;
	}
}
