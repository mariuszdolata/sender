package AmazonSESSample;

import java.util.Map.Entry;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import Structure.Sellter;

public class Campaign {
	public CampaignContent campaignContent;
	public RecipientsRepository recipientsRepository;
	public SMTPConfig sMTPConfig;

	public CampaignContent getCampaignContent() {
		return campaignContent;
	}

	public void setCampaignContent(CampaignContent campaignContent) {
		this.campaignContent = campaignContent;
	}

	public RecipientsRepository getRecipientsRepository() {
		return recipientsRepository;
	}

	public void setRecipientsRepository(RecipientsRepository recipientsRepository) {
		this.recipientsRepository = recipientsRepository;
	}

	public SMTPConfig getsMTPConfig() {
		return sMTPConfig;
	}

	public void setsMTPConfig(SMTPConfig sMTPConfig) {
		this.sMTPConfig = sMTPConfig;
	}

	public Campaign(CampaignContent campaignContent, RecipientsRepository recipientsRepository, SMTPConfig sMTPConfig) {
		this.campaignContent = campaignContent;
		this.recipientsRepository = recipientsRepository;
		this.sMTPConfig = sMTPConfig;
		try {
			startCampaign();
		} catch (Exception e) {
			System.err.println("Niudana próba wys³ania do wszystkich wiadomoœci");
			e.printStackTrace();
		}
	}

	public void startCampaign() throws Exception {
		// this.getRecipientsRepository().showRecipients();
		Properties props = this.getsMTPConfig().getProps();
		System.out.println("PROPERTIES:");
		for (Entry<Object, Object> e : props.entrySet()) {
			System.out.println(e);
		}
		int i=1;
		try {
			for (Sellter recipient : this.getRecipientsRepository().getRecipients()) {
				i++;
				createEmail(recipient, i);
			}

		} catch (Exception e) {
			System.err.print("Problem z metod¹ tansport.connect");
			e.printStackTrace();
		} finally {
			// transport.close();
		}

		//////////////////

		for (Sellter recipient : this.getRecipientsRepository().getRecipients()) {

		}
		////////////////////////////////////
	}

	public void createEmail(Sellter recipient, int i) {
		String content;
		if (recipient.getPlec().contains("K")) {
			content = this.getCampaignContent().getContentFemale();
			// System.out.println("kobieta");
		} else if (recipient.getPlec().contains("M")) {
			content = this.campaignContent.getContentMale();
			// System.out.println("mê¿czyzna");
		} else {
			content = this.campaignContent.getContentUnknown();
			// System.out.println("Bez okreœlenia p³ci");
		}
		// content.replaceAll("null", "");
		content = content.replaceAll("-XXX_IMIE_ODMIANA_XXX-", recipient.getImieOdmiana());
		content = content.replace("null", "");
		System.out.println("=>" + content);
		// MimeMessage msg = new MimeMessage(session);
		MimeMessage msg = null;
		try {/*
				 * msg = new MimeMessage(session); msg.setFrom(new
				 * InternetAddress(this.getCampaignContent().getSenderEmail(),
				 * this.getCampaignContent().getSenderName())); msg.setHeader("Content-Type",
				 * "text/plain; charset=UTF-8"); msg.setRecipient(Message.RecipientType.TO, new
				 * InternetAddress(recipient.getEmail())); String subject =
				 * "Nowi klienci dla "+recipient.getNazwa(); msg.setSubject(subject, "utf-8");
				 * String body = String.join(System.getProperty("line.separator"),content);
				 * msg.setContent(body, "text/html; charset=UTF-8");
				 * System.out.println("msg podczas tworzenia= "+msg.toString());
				 */

			///// STARA WERSJA
			Properties props = this.getsMTPConfig().getProps();
			// Properties props = System.getProperties();
			String FROM = this.campaignContent.getSenderEmail();
			String FROMNAME = this.campaignContent.getSenderName();
			System.out.println("wysylka na email=" + recipient.getEmail());
			String TO = recipient.getEmail();
			String SMTP_USERNAME = this.getsMTPConfig().getSMTP_USERNAME();
			String SMTP_PASSWORD = this.getsMTPConfig().getSMTP_PASSWORD();
			String CONFIGSET = "ConfigSet";
			String HOST = this.getsMTPConfig().getHOST();
			String BODY = String.join(System.getProperty("line.separator"), content);
			Session session = Session.getDefaultInstance(props);
			msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(FROM, FROMNAME));
			msg.setHeader("Content-Type", "text/plain; charset=UTF-8");
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
			String SUBJECT = "Nowi klienci dla "+recipient.getNazwa();
			msg.setSubject(SUBJECT, "utf-8");
			msg.setContent(BODY, "text/html; charset=UTF-8");
			// Create a transport.
			Transport transport = session.getTransport();

			// Send the message.
			try {

				System.out.println("Sending...");

				// Connect to Amazon SES using the SMTP username and password you specified
				// above.
				transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);

				// Send the email.
				transport.sendMessage(msg, msg.getAllRecipients());
				System.out.println("Email sent!, i="+i+", email="+recipient.getEmail());

			} catch (Exception ex) {
				System.out.println("The email was not sent,");
				System.out.println("Error message: " + ex.getMessage());
			} finally {
				// Close and terminate the connection.
				transport.close();
			}

		} catch (Exception e) {
			System.err.println("Nie uda³o siê stworzyæ wiadomoœci");
			e.printStackTrace();
		}
	}

}
