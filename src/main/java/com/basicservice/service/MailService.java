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

import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * A service for sending emails (using an external service such as Cloudbees-sendGrid or google smtp api)
 */
 
@Service
public class MailService {
	private static final Logger LOG = LoggerFactory.getLogger(MailService.class);
 
	private String smtpHost;
	private String sendGridUsername;
	private String sendGridPassword;
    
    public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public String getSendGridUsername() {
		return sendGridUsername;
	}

	public void setSendGridUsername(String sendGridUsername) {
		this.sendGridUsername = sendGridUsername;
	}

	public String getSendGridPassword() {
		return sendGridPassword;
	}

	public void setSendGridPassword(String sendGridPassword) {
		this.sendGridPassword = sendGridPassword;
	}

	public void sendEmail(String from, String to, String subject, String messageHtml) throws Exception{
        try {
			Properties props = new Properties();
	        props.put("mail.transport.protocol", "smtp");
	        props.put("mail.smtp.host", smtpHost);
	        props.put("mail.smtp.port", 587);
	        props.put("mail.smtp.auth", "true");
	        Authenticator auth = new SMTPAuthenticator();
	        Session mailSession = Session.getDefaultInstance(props, auth);
	        // mailSession.setDebug(true);
	        Transport transport = mailSession.getTransport();
	 
	        MimeMessage message = new MimeMessage(mailSession);
	        Multipart multipart = new MimeMultipart("alternative");
	        
	        BodyPart htmlPart = new MimeBodyPart();
	        htmlPart.setContent(new String(messageHtml.getBytes("UTF8"),"ISO-8859-1"), "text/html");
	        multipart.addBodyPart(htmlPart);
	 
	        message.setContent(multipart);
	        message.setFrom(new InternetAddress(from));
	        message.setSubject(subject, "UTF-8");
	        message.addRecipient(Message.RecipientType.TO,
	             new InternetAddress(to));
	 
	        transport.connect();
	        transport.sendMessage(message,
	            message.getRecipients(Message.RecipientType.TO));
	        transport.close();
        } catch (Exception e) {
        	LOG.debug("Exception while sending email: ", e);
        	throw e;
        }
    }
 
    private class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
           String username = sendGridUsername;
           String password = sendGridPassword;
           return new PasswordAuthentication(username, password);
        }
    }
}