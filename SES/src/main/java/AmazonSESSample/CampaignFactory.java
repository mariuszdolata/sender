package AmazonSESSample;

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
	 * Obiekt przechowujacy odbiorcow emaili
	 */
	public RecipientsRepository recipientsRepository = null;

	/**
	 * Obiekt przechowujacy aktualna tresc zaczepki
	 */
	public CampaignContent campaignContent = null;
	/**
	 * Obiekt odpowiedzialny za wygenerowanie wiadomosci oraz wysylke z
	 * wykorzystaniem wskazanego serwera
	 */
	public Campaign campaign = null;
	
	/**
	 * Nadawca
	 */
	public String senderName, senderEmail;

	
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

	public Campaign getCampaign() {
		return campaign;
	}

	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}
	
//	public CampaignFactory(String campaigName, String smtpFilePath, String contentCode, String senderName, String senderEmail) {
//		this.senderName=senderName;
//		this.senderEmail=senderEmail;
//		this.campaignName = campaigName;
//		this.sMTPConfig=new SMTPConfig(smtpFilePath);
//		this.contentCode=contentCode;
//		this.recipientsRepository = new RecipientsRepository("C:\\crawlers\\amazon\\"+this.getCampaignName()+"_test_aa.csv");
//		this.campaignContent = new CampaignContent("C:\\crawlers\\amazon\\"+this.getCampaignName(), this.getSenderName(), this.getSenderEmail());
//		
//		
//	}
	public CampaignFactory(CampaignSettings campaignSettings) {
		this.senderName = campaignSettings.getSenderName();
		this.senderEmail = campaignSettings.getSenderEmail();
		this.campaignName = campaignSettings.getCampaignName();
		this.sMTPConfig = new SMTPConfig("C:\\crawlers\\amazon\\smtpconfig\\"+campaignSettings.getSmtpFilePath()+".properties");
		this.contentCode = campaignSettings.getContentCode();
		this.recipientsRepository = new RecipientsRepository("C:\\crawlers\\amazon\\odbiorcy\\"+this.getCampaignName()+"_test_aa.csv");
		this.campaignContent = new CampaignContent("C:\\crawlers\\amazon\\zaczepki\\"+this.getCampaignName()+"\\"+campaignSettings.getContentCode()+"\\"+this.getCampaignName(), this.getSenderName(), this.getSenderEmail(), campaignSettings.getSubject());
	}

	/**
	 * wywolanie kampanii z uzyciem danych z konstruktora.
	 */
	public void run() {
		// TODO Auto-generated method stub
		this.campaign = new Campaign(this.getCampaignContent(), this.getRecipientsRepository(), this.getsMTPConfig(), this.getCampaignName());
	}

}
