<%@ page import="com.basicservice.service.Constants" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
<script type="text/javascript">
ACTIVE_MENU_ITEM = '<tiles:insertAttribute name="active_menu_item" />';
var Text = {
<c:forEach var="entry" items="${constants}">
	${entry.key}: "${entry.value}",
</c:forEach>
	dialogInvalidFieldLength: "<spring:message code="messages.dialog.dialogInvalidFieldLength"/>",
	dialogInvalidEmailField: "<spring:message code="messages.dialog.dialogInvalidEmailField"/>",
	dialogInvalidPasswordField: "<spring:message code="messages.dialog.dialogInvalidPasswordField"/>",
	dialogField_Name: "<spring:message code="dialog.dialogField_Name"/>",
	dialogField_Email: "<spring:message code="dialog.dialogField_Email"/>",
	dialogField_Password: "<spring:message code="dialog.dialogField_Password"/>",
	dialogButon_Cancel: "<spring:message code="dialog.cancel"/>",
	
	registrationDialogHeader: "<spring:message code="messages.dialog.registrationDialogHeader"/>",
	registrationDialogAcceptButton: "<spring:message code="messages.dialog.registrationDialogAcceptButton"/>",
	registrationDialogInitialMessage: "<spring:message code="messages.dialog.registrationDialogInitialMessage"/>",
	
	loginDialogAcceptButton: "<spring:message code="messages.dialog.loginDialogAcceptButton"/>",
	loginDialogHeader: "<spring:message code="messages.dialog.loginDialogHeader"/>",
	loginDialogInitialMessage: "<spring:message code="messages.dialog.loginDialogInitialMessage"/>",

	loginFailureMessage_invalidCredentials: "<spring:message code="messages.error.login.invalidCredentials"/>",
	registerFailureMessage_userExistsFailure: "<spring:message code="messages.error.registration.userExistsFailure"/>",
}
</script>
<script language="javascript" src="/resources/js/jquery.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/css/main.css" />
<link rel="stylesheet" type="text/css" href="/resources/css/<tiles:insertAttribute name="cssDefinitionsFile" />" />
<link type="text/css" href="/resources/css/jquery-ui-1.9.0.custom.css"
	rel="stylesheet" />
<style>
	.ui-dialog { direction: <spring:message code="direction"/>; }
</style>
<script src="/resources/js/jquery-ui-1.9.0.custom.min.js"></script>
<script src="/resources/js/main.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:insertAttribute name="title" /></title>
<script>
var csrf_token = '${csrf_token}';
var SERVER_CSRF_COOKIE_NAME = '<%=Constants.CSRF_COOKIE_NAME%>';
function pageLoadCustomActions(){}
$(document).ready(function(){
	onPageLoad();
	pageLoadCustomActions();
});
</script>
</head>
<body>
