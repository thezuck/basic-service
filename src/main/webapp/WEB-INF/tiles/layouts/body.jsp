<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
	<div id="overTheTopStrip" class="overTheTopStrip">
		<tiles:insertAttribute name="country_selection" />
	</div>
	<div id="topStrip" class="topStrip">
		<div class="logo"><img src="/resources/images/logo.png" height="60px"/></div>
		
		
		<div class="login">
			<button id="login"><spring:message code="button.login"/></button>&nbsp;
			<script>
				$("#login").button();
				$("#login").click(function() { showLogin();});
			</script>
			<button id="register"><spring:message code="button.register"/></button>
			<script>
				$("#register").button();
				$("#register").click(function() { showRegister();});
			</script>
		</div>
		<div id="menuItems" class="menuItems">
			<div class="menuItem" id="homeMItem" onclick="menuItemClicked(this);" onmouseout="showNormalMenuStyle(this);" onmousemove="showHoverMenuStyle(this);"><spring:message code="menuitem.home"/></div>
			<div class="menuItem" id="adminMItem" onclick="menuItemClicked(this);" onmouseout="showNormalMenuStyle(this);" onmousemove="showHoverMenuStyle(this);"><spring:message code="menuitem.admin"/></div>
			<div class="menuItem" id="contactMItem" onclick="menuItemClicked(this);" onmouseout="showNormalMenuStyle(this);" onmousemove="showHoverMenuStyle(this);"><spring:message code="menuitem.contact"/></div>
			<div class="menuItem" id="aboutMItem" onclick="menuItemClicked(this);" onmouseout="showNormalMenuStyle(this);" onmousemove="showHoverMenuStyle(this);"><spring:message code="menuitem.about"/></div>
		</div>
	</div>
	<div id="centralStrip" class="centralStrip">
		<div id="dialogs">
			<div id="dialog-form-template" class="dialog-form" title="{dialog header 1}" style="display:none;">
			    <p id="validateTips" class="validateTips">{dialog messages}</p>
			    <form>
			    <fieldset>
			        <label id="labelTemplate" class="fieldSetContent" for="{fieldName}">{fieldLabel}</label>
			        <input id="inputTextTemplate" type="text" name="{fieldName}" class="fieldSetContent text ui-widget-content ui-corner-all" />
			        <input id="inputPasswordTemplate" type="password" name="fieldName}" class="fieldSetContent text ui-widget-content ui-corner-all" />
			    </fieldset>
			    </form>
			</div>
		</div>
		<tiles:insertAttribute name="central_strip" />
		<div class="contentLoadingOverlayWrapper" id="contentLoadingOverlayWrapper">
			<div class="contentLoadingOverlay" id="contentLoadingOverlay">
				<img src="/resources/images/loading.gif" />
			</div>
			<div class="issuesMessageOverlay" id="issuesMessageOverlay">
				<spring:message code="messages.error.connectionIssues"/>
			</div>
		</div>
	</div>
	<div id="bottomStrip" class="bottomStrip">
		<div class="policiesLink" id="policiesLink"><a href="/privacypolicy.html"><spring:message code="button.privacypolicy"/></a>&nbsp;&nbsp;<a href="/termsofservice.html"><spring:message code="button.serviceterms"/></a></div>
	</div>
</div>