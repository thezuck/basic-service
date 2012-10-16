var MENU_ITEMS = {
		'HOME' : 	['home', 'homeMItem'],
		'ADMIN' : 	['admin', 'adminMItem'],
		'CONTACT' : ['contact', 'contactMItem'],
		'ABOUT' : 	['about', 'aboutMItem'],
}
function onPageLoad() {
	bindAjaxCSRFsetup();
	internalTestServerStatus();
	initializeMenus();
}
function bindAjaxCSRFsetup() {
	function csrfSafeMethod(method) {
	    // these HTTP methods do not require CSRF protection
	    return (/^(GET|HEAD|OPTIONS|TRACE)$/.test(method));
	}
	$.ajaxSetup({
	    crossDomain: false, // obviates need for sameOrigin test
	    beforeSend: function(xhr, settings) {
	        if (!csrfSafeMethod(settings.type)) {
	        	csrf_token = getCookie(SERVER_CSRF_COOKIE_NAME);
	        	if (csrf_token) {
	        		xhr.setRequestHeader("X-CSRFToken", csrf_token);
	        	}
	        }
	        if(settings.type.toLowerCase() === "put") {
	        	xhr.setRequestHeader("Content-Length", settings.data.length);
	        	xhr.setRequestHeader("Connection", "close");
	        }
	    }
	});
}
function internalTestServerStatus() {
	$.ajax({
		url: '/api/test',
		type: 'GET',
		dataType: 'json',
		data: {},
		success: function(response) { 
			dataLoadedSuccessfully()
		},
		error: function(response) { 
			dataLoadIssuesEncountered();
			setTimeout("internalTestServerStatus()", 10000);
		} 
	});
}
function initializeMenus() {
	for (menu in MENU_ITEMS) {
		if (menu == ACTIVE_MENU_ITEM) {
			makeActiveItem(document.getElementById(MENU_ITEMS[menu][1]));
		} else {
			showNormalMenuStyle(document.getElementById(MENU_ITEMS[menu][1]));
		}
	}
	setVisibility('div#menuItems', true);	
}
function changeImageState(buttonId, isOn) {
	var currentSrc = document.getElementById(buttonId).src;
	var imageNameClean = currentSrc.substring(0 ,currentSrc.indexOf(".png"));
	if (currentSrc.indexOf("_") > 0) {
		imageNameClean = imageNameClean.substring(0, currentSrc.indexOf("_"));
	}
	if (isOn) {
		document.getElementById(buttonId).src = imageNameClean + "_on.png";
	} else {
		document.getElementById(buttonId).src = imageNameClean + ".png";
	}
}
function showNormalMenuStyle(obj) {
	if (obj.isActiveMenuItem) {
		return;
	} else {
		obj.className='regularMenuItem';
	}
}
function showHoverMenuStyle(obj) {
	if (obj.isActiveMenuItem) {
		return;
	} else {
		obj.className='hoverMenuItem';
	}
}
function showSelectedMenuStyle(obj) {
	obj.className='selectedMenuItem';
}
oldActiveItem = null;
function makeActiveItem(newActiveItem) {
	if (oldActiveItem) {
		oldActiveItem.isActiveMenuItem = false;
		showNormalMenuStyle(oldActiveItem);
	}
	newActiveItem.isActiveMenuItem = true;
	oldActiveItem = newActiveItem;
	showSelectedMenuStyle(newActiveItem);
}
function menuItemClicked(menuItem) {
	makeActiveItem(menuItem);
	for (oneitem in MENU_ITEMS) {
		var obj = MENU_ITEMS[oneitem];
		if (obj[1] == menuItem.id) {
			document.location = '/'+obj[0];
		}
	}
}
function internalLogin(values, dialog, successFunc, errorFunc) {
	$.ajax({
		url: '/api/users/login',
		type: 'POST',
		dataType: 'json',
		data: values,
		success: function(response) {
			successFunc(dialog);
		},
		error: function(response) { 
			errorFunc(dialog, Text.loginFailureMessage_invalidCredentials);
		}
	});
}
function internalRegister(values, dialog, successFunc, errorFunc) {
	$.ajax({
		url: '/api/users/register',
		type: 'POST',
		dataType: 'json',
		data: values,
		success: function(response) { 
			successFunc(dialog);
		},
		error: function(response) { 
			errorFunc(dialog, Text.registerFailureMessage_userExistsFailure);
		}
	});
}
function setVisibility(id, b) {
	$(id).css("visibility", b ? 'visible':'hidden');
}
function setDisplay(id, b) {
	$("#"+id).css("display", b ? 'inline':'none');
}

function contentLoading(flag) {
	setVisibility('div#contentLoadingOverlayWrapper', flag);	
}
function displayIssueMessage(flag) {
	setVisibility('div#issuesMessageOverlay', flag);	
}

function dataLoadIssuesEncountered() {
	displayIssueMessage(true);
	contentLoading(true);
	setTimeout("internalTestServerStatus()", 5000);
}
function dataLoadedSuccessfully() {
	contentLoading(false);
	displayIssueMessage(false);
}
function getCookie(name) {
	var cookies = document.cookie.split('; ');
	for (var i = 0, parts; (parts = cookies[i] && cookies[i].split('=')); i++) {
		if (parts[0] == name) return parts[1];
	}
	return null;
}
function login(email, password) {
	internalLogin(email, password);
}
var NAME_VALIDATION= {
		minSize:3,
		maxSize:60,
		regex: null, 
		message: null
}
var EMAIL_VALIDATION= {
		minSize:7,
		maxSize:80,
        // From jquery.validate.js (by joern), contributed by Scott Gonzalez: http://projects.scottsplayground.com/email_address_validation/
		regex: /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i, 
		message: Text.dialogInvalidEmailField
	}
var PASSWORD_VALIDATION= {
		minSize:5,
		maxSize:13,
		regex: /^([0-9a-zA-Z!@#$%^&*)])+$/, 
		message:Text.dialogInvalidPasswordField
	}

var registerDialogParams = {
		dialogName: Text.registrationDialogHeader,
		acceptButton: Text.registrationDialogAcceptButton,
		initialDialogMessage: Text.registrationDialogInitialMessage,
		fields: [
		         ["name", Text.dialogField_Name, "text", NAME_VALIDATION],
		         ["email", Text.dialogField_Email, "text", EMAIL_VALIDATION],
		         ["password", Text.dialogField_Password, "password", PASSWORD_VALIDATION]
		        ],
        acceptFunction: function (data, dialog, successFunc, errorFunc) {
        	acceptRegister(data, dialog, successFunc, errorFunc);
        }
	}
var loginDialogParams = {
		dialogName: Text.loginDialogHeader,
		acceptButton: Text.loginDialogAcceptButton,
		initialDialogMessage: Text.loginDialogInitialMessage,
		fields: [
		         ["email", Text.dialogField_Email, "text", EMAIL_VALIDATION],
		         ["password", Text.dialogField_Password, "password", PASSWORD_VALIDATION]
		        ],
		acceptFunction: function (data, dialog, successFunc, errorFunc) {
			acceptLogin(data, dialog, successFunc, errorFunc);
		}
}
function showLogin() {
	openDialog("login-dialog", loginDialogParams);
}
function acceptLogin(values, dialog, successFunc, errorFunc) {
	internalLogin(values, dialog, successFunc, errorFunc);
	return false;
}
function showRegister() {
	openDialog("register-dialog", registerDialogParams);
}
function acceptRegister(values, dialog, successFunc, errorFunc) {
	internalRegister(values, dialog, successFunc, errorFunc);
	return false;
}
function openDialog(dialogName, params) {
	var theDialog = $("#"+dialogName);
	var allFields = $( new Array() );
	var allLabels = $( new Array() );
	if (theDialog.length == 0) {
		theDialog = cloneObject("dialog-form-template", dialogName);
		for (i = 0; i < params.fields.length; i++) {
			labelClone = cloneSpecificObject(theDialog.find("#labelTemplate"), dialogName + "labelfield" + i); 
			if ("text" == params.fields[i][2]) {
				inputClone = cloneSpecificObject(theDialog.find("#inputTextTemplate"), dialogName + "inputfield" + i);
			} else
			if ("password" == params.fields[i][2]) {
				inputClone = cloneSpecificObject(theDialog.find("#inputPasswordTemplate"), dialogName + "inputfield" + i);
			}
			labelClone.attr("for", params.fields[i][0]);
			labelClone.text(params.fields[i][1]);
			inputClone.attr("name", params.fields[i][0]);
			inputClone.data("validation", params.fields[i][3]);
			allFields = allFields.add(inputClone );
			allLabels = allLabels.add(labelClone );
		}
		theDialog.attr("title", params.dialogName);
		theDialog.find("#validateTips").text(params.initialDialogMessage);
		theDialog.find("#validateTips").data("initialMessage", params.initialDialogMessage);
		theDialog.find("#labelTemplate").remove();
		theDialog.find("#inputTextTemplate").remove();
		theDialog.find("#inputPasswordTemplate").remove();
		theDialog.css("display", "inline");
		theDialog.data("allFields", allFields);
		theDialog.data("allLabels", allLabels);
	}
	
	function updateTips( tips, message ) {
		tips
		.text( message )
		.addClass( "ui-state-highlight" );
		setTimeout(function() {
			tips.removeClass( "ui-state-highlight", 1500 );
		}, 500 );
	}
	function checkLength( o, fieldName, min, max, parentDialog) {
		if ( o.val().length > max || o.val().length < min ) {
			o.addClass( "ui-state-error" );
			var tip = Text.dialogInvalidFieldLength.replace("{1}", fieldName).replace("{2}", min).replace("{3}", max);
			var tips = parentDialog.find("#validateTips");
			updateTips( tips, tip);
			return false;
		} else {
			return true;
		}
	}
	function checkRegexp( o, regexp, message, parentDialog) {
		if ( !( regexp.test( o.val() ) ) ) {
			o.addClass( "ui-state-error" );
			var tips = parentDialog.find("#validateTips");
			updateTips( tips, message);
			return false;
		} else {
			return true;
		}
	}
    theDialog.dialog({
        autoOpen: true,
        height: 400,
        width: 450,
        modal: true,
        buttons: [
            {
            	text:params.acceptButton,
            	click:function() {
	                var bValid = true;
	                var allFields = $(this).data("allFields");
	                var allLabels = $(this).data("allLabels");
	                allFields.removeClass( "ui-state-error" );
	                for (i = 0; i < allFields.length; i++) {
	                	var oneField = $(allFields[i]);
	                	var oneLabel = $(allLabels[i]);
	                	var validationObj = oneField.data("validation");
	                	bValid = bValid && checkLength( oneField , oneLabel.text(), validationObj.minSize, validationObj.maxSize, $( this ));
	                	if (validationObj.regex != null) {
	                		bValid = bValid && checkRegexp( oneField,  validationObj.regex, validationObj.message, $( this ));
	                	}
	                }
	                if ( bValid ) {
	                	var data = new Object();
	                	for (i=0;i<allFields.length;i++) { 
	                		var name	= allFields[i].name;
	                		var value	= allFields[i].value;
	                		data[name] = value;
                		}
	                	var successFunc = function (dialog) {
	                		dialog.dialog( "close" );
	                	}
	                	var errorFunc = function (dialog, message) {
	            			var tips = dialog.find("#validateTips");
	            			updateTips( tips, message);
	                	}

	                	var shouldClose = params.acceptFunction(data, $(this), successFunc, errorFunc);
	                	if (shouldClose) {
	                		$( this ).dialog( "close" );
	                	}
	                }
	            }
            },
            {
            	text:Text.dialogButon_Cancel,
            	click: function() {
			                $( this ).dialog( "close" );
			            }
    		}
        ],
        close: function() {
            var allFields = $(this).data("allFields");
            allFields.val( "" ).removeClass( "ui-state-error" );
            var tips = $(this).find("#validateTips");
            tips.text(tips.data("initialMessage"));
        },
    });
}
function cloneObject(templateName, cloneId) {
	var template = $('div#'+templateName);
	return cloneSpecificObject(template, cloneId);
}
function cloneSpecificObject(templateObj, cloneId) {
	var newObj = templateObj.clone();
	newObj.attr("id", cloneId);
	templateObj.parent().append(newObj);
	return newObj;
}
$(document).ready(function() {
	createDropDown();
	
	var $dropTrigger = $(".dropdown dt a");
	var $languageList = $(".dropdown dd ul");
	
	$dropTrigger.toggle(function() {
		$languageList.slideDown(200);
		$dropTrigger.addClass("active");
	}, function() {
		$languageList.slideUp(200);
		$(this).removeAttr("class");
	});

	$(document).bind('click', function(e) {
		var $clicked = $(e.target);
		if (! $clicked.parents().hasClass("dropdown"))
			$languageList.slideUp(200);
			$dropTrigger.removeAttr("class");
	});

	$(".dropdown dd ul li a").click(function() {
		var clickedValue = $(this).parent().attr("class");
		var clickedTitle = $(this).find("em").html();
		$("#target dt").removeClass().addClass(clickedValue);
		$("#target dt em").html(clickedTitle);
		$languageList.hide();
		$dropTrigger.removeAttr("class");
	});
});

function createDropDown(){
	var $form = $("div#country-select form");
	$form.hide();
	var source = $("#country-options");
	source.removeAttr("autocomplete");
	var selected = source.find("option:selected");
	var options = $("option", source);
	$("#country-select").append('<dl id="target" class="dropdown"></dl>')
	$("#target").append('<dt class="' + selected.val() + '"><a href="#"><span class="flag"></span><em>' + selected.text() + '</em></a></dt>')
	$("#target").append('<dd><ul></ul></dd>')
	options.each(function(){
		$("#target dd ul").append('<li class="' + $(this).val() + '"><a href="' + $(this).attr("title") + '"><span class="flag"></span><em>' + $(this).text() + '</em></a></li>');
		});
}
