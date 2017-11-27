package CampaignSender;

import org.apache.log4j.Logger;

/**
 * Metoda fabrykuj¹ca dla ka¿dej z kampanii.
 * 
 * @author mariusz
 *
 */
public class CampaignFactory implements Runnable {
	/**
	 * Nazwa kampanii wykorzystywana w recipientRepository
	 */
	public String campaignName;
	/**
	 * Sciezka do pliku z danymi dostepowymi SMTP
	 */
	public String smtpConfigFilePath;
	/**
	 * kod zaczepki - mocno rozwojowe
	 */
	public String contentCode;

	/**
	 * Obiekt przechowujacy dane uwierzytelniajace na serwer
	 */
	public SMTPConfig sMTPConfig = null;
	/**
	 * Obiekt przechowujacy odbiorcow emaili. KONIECZNOSC zapewnienia synchronizacji
	 * !
	 */
	public RecipientsRepository recipientsRepository = null;
	
	/**
	 * Adresy testowe dla wysylek oraz zbijajace twarde odbicia
	 */
	public RecipientsRepository testersRepository = null;

	/**
	 * Obiekt przechowujacy aktualna tresc zaczepki
	 */
	public CampaignContent campaignContent = null;
	/**
	 * Obiekt odpowiedzialny za wygenerowanie wiadomosci oraz wysylke z
	 * wykorzystaniem wskazanego serwera
	 */

	public CampaignSettings campaignSettings = null;
	/**
	 * Nadawca
	 */
	public String senderName, senderEmail;

	public Logger testLog = Logger.getLogger("testLog");
	
	public CampaignSettings getCampaignSettings() {
		return campaignSettings;
	}

	public void setCampaignSettings(CampaignSettings campaignSettings) {
		this.campaignSettings = campaignSettings;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public String getSmtpConfigFilePath() {
		return smtpConfigFilePath;
	}

	public void setSmtpConfigFilePath(String smtpConfigFilePath) {
		this.smtpConfigFilePath = smtpConfigFilePath;
	}

	public String getContentCode() {
		return contentCode;
	}

	public void setContentCode(String contentCode) {
		this.contentCode = contentCode;
	}

	public SMTPConfig getsMTPConfig() {
		return sMTPConfig;
	}

	public void setsMTPConfig(SMTPConfig sMTPConfig) {
		this.sMTPConfig = sMTPConfig;
	}

	public RecipientsRepository getRecipientsRepository() {
		return recipientsRepository;
	}

	public void setRecipientsRepository(RecipientsRepository recipientsRepository) {
		this.recipientsRepository = recipientsRepository;
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

	public CampaignFactory(CampaignSettings campaignSettings, RecipientsRepository testersRepository) {
		this.campaignSettings = campaignSettings;
		this.senderName = campaignSettings.getSenderName();
		this.senderEmail = campaignSettings.getSenderEmail();
		this.campaignName = campaignSettings.getCampaignName();
		this.sMTPConfig = new SMTPConfig(
				"C:\\crawlers\\amazon\\smtpconfig\\" + campaignSettings.getSmtpFilePath() + ".properties");
		this.contentCode = campaignSettings.getContentCode();
		this.recipientsRepository = new RecipientsRepository(
				"C:\\crawlers\\amazon\\odbiorcy\\" + this.getCampaignName() + "_test_aa.csv");
		this.campaignContent = new CampaignContent(
				"C:\\crawlers\\amazon\\zaczepki\\" + this.getCampaignName() + "\\" + campaignSettings.getContentCode()
						+ "\\" + this.getCampaignName(),
				this.getSenderName(), this.getSenderEmail(), campaignSettings.getSubject());
		this.testersRepository = testersRepository;
	}

	/**
	 * wywolanie kampanii z uzyciem danych z konstruktora. liczba watkow jest
	 * determinowana przez plik campaignSettings
	 */
	public void run() {
		testLog.info("Rozpoczecie kampanii \" "+this.getCampaignName()+" \" - tworzenie "+this.getCampaignSettings().getNumberOfThreads()+" w¹tków");
		Campaign[] campaigns = new Campaign[this.getCampaignSettings().getNumberOfThreads()];
		Thread[] campaignThreads = new Thread[this.getCampaignSettings().getNumberOfThreads()];
		for (int i = 0; i < this.getCampaignSettings().getNumberOfThreads(); i++) {
			campaigns[i] = new Campaign(this.getCampaignContent(), this.getRecipientsRepository(), this.getTestersRepository(), this.getsMTPConfig(),
					this.getCampaignName(), i, this.campaignSettings);
		}
		for (int i = 0; i < this.getCampaignSettings().getNumberOfThreads(); i++) {
			campaignThreads[i] = new Thread(campaigns[i]);
		}
		for (int i = 0; i < this.getCampaignSettings().getNumberOfThreads(); i++) {
			campaignThreads[i].start();
		}
	}

}
