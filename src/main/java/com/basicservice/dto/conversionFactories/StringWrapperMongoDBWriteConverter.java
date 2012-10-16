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
import com.basicservice.service.Utils;

/**
 * Used by MongoDB repository for writing StringWrappers to db
 * It will take the StringWrapper value and write the string value to DB
 * Used to write beans which have StringWrapper fields to MongoDB
 */

public class StringWrapperMongoDBWriteConverter  implements Converter<StringWrapper, String> {
	public String convert(StringWrapper source) {
	    return Utils.getStringValueIfNotNull(source);
	}
}
