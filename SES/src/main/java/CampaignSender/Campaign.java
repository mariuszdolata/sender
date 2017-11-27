package CampaignSender;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import Structure.Recipient;

public class Campaign implements Runnable {
	public CampaignContent campaignContent;
	public RecipientsRepository recipientsRepository = null;
	public RecipientsRepository testersRepository = null;
	public SMTPConfig sMTPConfig;
	public Logger mailLog = Logger.getLogger("mailLog");
	public Logger mailErr = Logger.getLogger("mailErr");
	public Logger mainLog = Logger.getLogger("mainLog");
	public String campaignName;
	public int thread;

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public CampaignContent getCampaignContent() {
		return campaignContent;
	}

	public void setCampaignContent(CampaignContent campaignContent) {
		this.campaignContent = campaignContent;
	}

	public RecipientsRepository getTestersRepository() {
		return testersRepository;
	}

	public void setTestersRepository(RecipientsRepository testersRepository) {
		this.testersRepository = testersRepository;
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

	public Campaign(CampaignContent campaignContent, RecipientsRepository recipientsRepository, RecipientsRepository testersRepository, SMTPConfig sMTPConfig,
			String campaignName, int thread) {
		mainLog.info("Uruchomienie konstruktora Campaign dla kampanii=" + campaignName);
		this.campaignName = campaignName;
		this.campaignContent = campaignContent;
		this.recipientsRepository = recipientsRepository;
		this.testersRepository = testersRepository;
		this.sMTPConfig = sMTPConfig;
		this.thread = thread;
		try {
			mainLog.info("Start kampanii " + campaignName);
			// startCampaign zostal przeniesiony do metody run!
			// startCampaign();
		} catch (Exception e) {
			mailErr.error("Niudana próba wys³ania do wszystkich wiadomoœci - blad w konstruktorze", e);
			e.printStackTrace();
		}
	}

	public void startCampaign() throws Exception {
		mainLog.trace("Rozpoczecie metody startCampaign() dla kampanii " + this.getCampaignName());
		Properties props = this.getsMTPConfig().getProps();
		int i = 1;
		try {
			/**
			 * petla wysylajaca emaile dopoki sa niewybrani odbiorcy
			 */
			do {
				Recipient recipient = this.recipientsRepository.lockRecipient(thread);
				if(recipient!=null) {
					createEmail(recipient, thread);
				}else {
					mainLog.info("obiorca jest pusty! r="+recipient);
				}
			} while (!this.recipientsRepository.checkRecipientSet(thread));

		} catch (Exception e) {
			mailErr.error("Problem z metod¹ tansport.connect ", e);
			e.printStackTrace();
		} finally {
			mainLog.info("Zakoñczono wysy³kê emaili dla w¹tku i=" + thread);
		}

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
		// MimeMessage msg = new MimeMessage(session);
		MimeMessage msg = null;
		try {

			///// STARA WERSJA
			Properties props = this.getsMTPConfig().getProps();
			// Properties props = System.getProperties();
			String senderEmail = this.campaignContent.getSenderEmail();
			String senderName = this.campaignContent.getSenderName();
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
			msg.setSentDate(new Date());
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
			String subject = personalizeText(this.getCampaignContent().getSubject(), recipient);
			msg.setSubject(subject, "utf-8");
			msg.setContent(body, "text/html; charset=UTF-8");
			// Create a transport.
			Transport transport = session.getTransport();

			// Send the message.
			try {

				System.out.println("Sending... campaign: " + this.getCampaignName() + ", to: " + recipient.getEmail());
				transport.connect(host, smtpUserName, smtpPassword);

				// Send the email.
				transport.sendMessage(msg, msg.getAllRecipients());
				mailLog.info("Email sent!, campaign: " + this.getCampaignName() + ",  sender: "
						+ this.campaignContent.getSenderEmail() + ",   i = " + i + ",   email: "
						+ recipient.getEmail());

			} catch (Exception ex) {
				mailErr.error("The email was not sent,", ex);
				System.out.println("The email was not sent,");
				System.out.println("Error message: " + ex.getMessage());
			} finally {
				// Close and terminate the connection.
				transport.close();
			}

		} catch (Exception e) {
			mailErr.error("Unable to create mail, campaign: " + this.getCampaignName() + ", recipient: "
					+ recipient.getEmail(), e);
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

	public void run() {
		try {
			startCampaign();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
