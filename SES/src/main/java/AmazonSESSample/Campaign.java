package AmazonSESSample;

import java.util.Map.Entry;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import Structure.Recipient;

public class Campaign {
	public CampaignContent campaignContent;
	public RecipientsRepository recipientsRepository;
	public SMTPConfig sMTPConfig;
	public Logger mailingLog = Logger.getLogger("mailing");
	public String campaignName;

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

	public Campaign(CampaignContent campaignContent, RecipientsRepository recipientsRepository, SMTPConfig sMTPConfig, String campaignName) {
		this.campaignName=campaignName;
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
		int i = 1;
		try {
			for (Recipient recipient : this.getRecipientsRepository().getRecipients()) {
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

		for (Recipient recipient : this.getRecipientsRepository().getRecipients()) {

		}
		////////////////////////////////////
	}

	public void createEmail(Recipient recipient, int i) {
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
		// personalizacja tresci
		content = personalizeText(content, recipient);
		System.out.println("=>" + content);
		// MimeMessage msg = new MimeMessage(session);
		MimeMessage msg = null;
		try {

			///// STARA WERSJA
			Properties props = this.getsMTPConfig().getProps();
			// Properties props = System.getProperties();
			String senderEmail = this.campaignContent.getSenderEmail();
			String senderName = this.campaignContent.getSenderName();
			System.out.println("wysylka na email=" + recipient.getEmail());
			String recipientEmail = recipient.getEmail();
			String smtpUserName = this.getsMTPConfig().getSMTP_USERNAME();
			String smtpPassword = this.getsMTPConfig().getSMTP_PASSWORD();
			String configSet = "ConfigSet";
			String host = this.getsMTPConfig().getHOST();
			String body = String.join(System.getProperty("line.separator"), content);
			Session session = Session.getDefaultInstance(props);
			msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(senderEmail, senderName));
			msg.setHeader("Content-Type", "text/plain; charset=UTF-8");
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
			String subject = personalizeText(this.getCampaignContent().getSubject(), recipient);
			msg.setSubject(subject, "utf-8");
			msg.setContent(body, "text/html; charset=UTF-8");
			// Create a transport.
			Transport transport = session.getTransport();

			// Send the message.
			try {

				System.out.println("Sending...");

				// Connect to Amazon SES using the SMTP username and password you specified
				// above.
				transport.connect(host, smtpUserName, smtpPassword);

				// Send the email.
				transport.sendMessage(msg, msg.getAllRecipients());
				System.out.println("Email sent!, i=" + i + ", email=" + recipient.getEmail());
				mailingLog.info("Email sent!, campaign= "+this.campaignContent.getSenderEmail()+"i=" + i + ", email=" + recipient.getEmail());

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

	public String personalizeText(String text, Recipient recipient) {
		String personalized = text;

		personalized = personalized.replaceAll("-XXX_WEBSITE_XXX-", recipient.getWebsite());
		personalized = personalized.replaceAll("-XXX_FORMA_PRAWNA_XXX-", recipient.getFormaPrawna());
		personalized = personalized.replaceAll("-XXX_NAZWA_FIRMY_XXX-", recipient.getNazwa());
		personalized = personalized.replaceAll("-XXX_OBROT_XXX-", recipient.getObrot());
		personalized = personalized.replaceAll("-XXX_ZYSK_XXX-", recipient.getZysk());
		personalized = personalized.replaceAll("-XXX_ZATRUDNIENIE_XXX-", recipient.getZatrudnienie());
		personalized = personalized.replaceAll("-XXX_POZIOM_OBROT_XXX-", recipient.getPoziomObrot());
		personalized = personalized.replaceAll("-XXX_POZIOM_ZYSK_XXX-", recipient.getPoziomZysk());
		personalized = personalized.replaceAll("-XXX_POZIOM_ZATRUDNIENIE_XXX-", recipient.getPoziomZatrudnienie());
		personalized = personalized.replaceAll("-XXX_IMIE_ODMIANA_XXX-", recipient.getImieOdmiana());
		personalized = personalized.replaceAll("-XXX_PLEC_XXX-", recipient.getPlec());
		personalized = personalized.replaceAll("-XXX_STANOWISKO_XXX-", recipient.getStanowisko());
		personalized = personalized.replaceAll("NULL", "");
		personalized = personalized.replaceAll("null", "");
		return personalized;
	}

}
