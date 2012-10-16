<%-- 
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
 --%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="country-select" class="country-select">
  <form action="javascript:void()">
    <select id="country-options" name="country-options">
      <option <c:if test='${locale == "en"}'>selected="selected"</c:if> title="?lang=en" value="en"><spring:message code="menu.languageSelection.en"/></option>
      <option <c:if test='${locale == "iw"}'>selected="selected"</c:if> title="?lang=iw" value="iw"><spring:message code="menu.languageSelection.iw"/></option>
    </select>
  </form>
</div>	
