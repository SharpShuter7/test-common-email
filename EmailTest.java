package org.apache.commons.mail;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.subethamail.smtp.server.Session;

public class EmailTest {

	public static final String[] TEST_EMAILS = { "ab@bc.com", "a.b@c.org", 
			"abcdefghijklmnopqrst@abcdefghijklmnopqrst.com.bd"};
	
	/* Concrete Email class for testing */
	private EmailConcrete email;

	@Before
	public void setUpEmailTest() throws Exception {
		
		email = new EmailConcrete();
		
	}
	
	@After
	public void tearDownEmailTest() throws Exception {
		
		
		
	}
	
	@Test
	public void testAddBcc() throws Exception {
		
		email.addBcc(TEST_EMAILS);
		
		assertEquals(3, email.getBccAddresses().size());
		
	}

	@Test
	public void testAddCc() throws Exception {
		
		email.addCc(TEST_EMAILS[0]);
		
		assertEquals(1, email.getCcAddresses().size());
		
	}

	@Test
	public void testAddHeader() throws Exception { //test when properly used
		
		email.addHeader("Bob", "Professor");
		
		assertEquals("Professor", email.headers.get("Bob"));
		
	}

	@Test (expected = IllegalArgumentException.class) //show the exception was thrown for null name
	public void testAddHeaderBadName() throws Exception {
		
		email.addHeader(null, "Value");
		
		assertEquals("Value", email.headers.get(null));
		
	}

	@Test
	public void testAddReplyTo() throws Exception {
		
		email.addReplyTo(TEST_EMAILS[1], "John");
		
		assertEquals( 1, email.getReplyToAddresses().size());
		
	}

	@Test
	public void testBuildMimeMessage() throws EmailException {
		
		Date sentDate = new Date(03/17/2021);
		
		Properties properties = new Properties();  
		
		email.setHostName("localhost");
		email.setSmtpPort(1234);
		email.setDebug(false);
		
		Authenticator newAuthenticator = new DefaultAuthenticator("abc@a.org", "password");
		email.setAuthenticator(newAuthenticator);
		
		email.setSSLOnConnect(true);
		email.setBounceAddress("test@ac.org");
		
		javax.mail.Session session = javax.mail.Session.getInstance(properties,null);  
		
		email.setSentDate(sentDate);
		email.addBcc(TEST_EMAILS[0]);
		email.addCc(TEST_EMAILS[1]);
		email.addHeader("Bob", "Professor");
		email.setFrom(TEST_EMAILS[2]);
		email.addReplyTo(TEST_EMAILS[1], "John");
		email.setSocketConnectionTimeout(3);
		email.setBounceAddress(TEST_EMAILS[0]);
		email.addTo("a@bc.org");
		email.setCharset("ISO-8859-1");
		email.setContent("test content", "text/plain");
		email.setSubject("mysubject");
		
		email.buildMimeMessage();
		
	}
}