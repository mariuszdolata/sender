package AmazonSESSample;

public class AmazonSESSample {



	public static void main(String[] args) throws Exception {

		//Set<Sellter> recipients = new HashSet<Sellter>();
		//recipients = loadEmailsFromFile("C:\\crawlers\\sellter_test1.csv");
		
		
		//SMTPConfig sMTPConfig = new SMTPConfig();
		SMTPConfig sMTPConfig = new SMTPConfig("C:\\crawlers\\amazon\\smtpconfig\\amazon1.properties");
		RecipientsRepository recipientsRepository = new RecipientsRepository("C:\\crawlers\\amazon\\sellter_nasze.csv");
		CampaignContent campaignContent = new CampaignContent("C:\\crawlers\\amazon\\sellter", "Monika Urbañska", "monika.urbanska@your-sellter.pl");
		//CampaignContent campaignContent = new CampaignContent("C:\\crawlers\\amazon\\sellter", "Pawe³ Wieczorek", "pawel.wieczorek@net-biz.com.pl");
		Campaign campaign = new Campaign(campaignContent, recipientsRepository, sMTPConfig);
		int numberOfThreads=1;
		AmazonSendRepository[] repository = new AmazonSendRepository[numberOfThreads];
		Thread[] threads = new Thread[numberOfThreads];
		for (int i = 0; i < numberOfThreads; i++) {
			repository[i]=new AmazonSendRepository(i);
		}
		for (int i = 0; i < numberOfThreads; i++) {
			threads[i] = new  Thread(repository[i]);
		}
		for (int i = 0; i < numberOfThreads; i++) {
			//threads[i].start();  //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<##################################################################
		}
	}
	
}