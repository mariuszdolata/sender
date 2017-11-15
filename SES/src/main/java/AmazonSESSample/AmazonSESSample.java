package AmazonSESSample;

public class AmazonSESSample {



	public static void main(String[] args) throws Exception {

		/**
		 * Dcelowe wywolanie kampanii
		 */
		CampaignRepository campaignRepository = new CampaignRepository();
		
		/**
		 * testowe wywolanie kapanii
		 */
//		SMTPConfig sMTPConfig = new SMTPConfig("C:\\crawlers\\amazon\\smtpconfig\\amazon1.properties");
//		RecipientsRepository recipientsRepository = new RecipientsRepository("C:\\crawlers\\amazon\\sellter_nasze.csv");
//		CampaignContent campaignContent = new CampaignContent("C:\\crawlers\\amazon\\sellter", "Monika Urbañska", "monika.urbanska@your-sellter.pl");
//		Campaign campaign = new Campaign(campaignContent, recipientsRepository, sMTPConfig);
		
		
		
		/**
		 * wielowatkowe wywolanie kampanii - do poprawienia
		 */
//		int numberOfThreads=1;
//		AmazonSendRepository[] repository = new AmazonSendRepository[numberOfThreads];
//		Thread[] threads = new Thread[numberOfThreads];
//		for (int i = 0; i < numberOfThreads; i++) {
//			repository[i]=new AmazonSendRepository(i);
//		}
//		for (int i = 0; i < numberOfThreads; i++) {
//			threads[i] = new  Thread(repository[i]);
//		}
//		for (int i = 0; i < numberOfThreads; i++) {
//			//threads[i].start();  //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<##################################################################
//		}
	}
	
}