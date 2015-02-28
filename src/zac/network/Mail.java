package zac.network;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mail {

	final static Logger logger = LoggerFactory.getLogger(Mail.class);

	private String _subject;
	private String _content;
	private ArrayList<String> _to;
	private SmtpSettings _smtp;
	
	@SuppressWarnings("unused")
	private Mail(){};
	
	public Mail(SmtpSettings smtp, String subject, String content){
		_to = new ArrayList<String>();
		_smtp = smtp;
	}
	
	public void send() {

		try {
			Properties props = System.getProperties();
			props.put("mail.smtp.starttls.enable", _smtp.starttls_enable);
			props.put("mail.smtp.host", _smtp.host);
			props.put("mail.smtp.user", _smtp.from);
			props.put("mail.smtp.password", _smtp.pwd);
			props.put("mail.smtp.port", _smtp.port);
			props.put("mail.smtp.auth", _smtp.auth);

			Session session = Session.getDefaultInstance(props);
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(_smtp.from));
			InternetAddress[] toAddress = new InternetAddress[_to.size()];

			// To get the array of addresses
			for (int i = 0; i < _to.size(); i++) {
				toAddress[i] = new InternetAddress(_to.get(i));
			}

			for (int i = 0; i < toAddress.length; i++) {
				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
			}

			message.setSubject(_subject);
			message.setContent("<h1>" + _subject + "</h1>\n" + _content,
					"text/html");
			Transport transport = session.getTransport("smtp");
			transport.connect(_smtp.host, _smtp.from, _smtp.pwd);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
