package CampaignSender;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

/**
 * Klasa odpowiedzialna za wczytanie listy kampanii pliku oraz stworzenie
 * osobnego w¹tku dla ka¿dej z kampanii.
 */
public class CampaignRepository {
	public int numberOfCampaign;
	/**
	 * zbior ustawien kampanii wczytanych z pliku campaignSettings.txt
	 */
	public Set<CampaignSettings> campaignsSet = new HashSet<CampaignSettings>();
	public List<?> campaignList;
	public Logger testLog = Logger.getLogger("testLog");

	public int getNumberOfCampaign() {
		return numberOfCampaign;
	}

	public void setNumberOfCampaign(int numberOfCampaign) {
		this.numberOfCampaign = numberOfCampaign;
	}

	public Set<CampaignSettings> getCampaignsSet() {
		return campaignsSet;
	}

	public void setCampaignsSet(Set<CampaignSettings> campaignsSet) {
		this.campaignsSet = campaignsSet;
	}

	public List<?> getCampaignList() {
		return campaignList;
	}

	public void setCampaignList(List<?> campaignList) {
		this.campaignList = campaignList;
	}

	public CampaignRepository() {
		loadCampaignSettings("campaignSettings");
		// konwersja do listy na potrzeby tworzenia nowych watkow
		this.campaignList = campaignsSet.stream().collect(Collectors.toList());
		// liczba watkow do wygenerowania
		this.numberOfCampaign = this.getCampaignsSet().size();
		if (campaignList.size() > 0) {
			createCampaignFactory(campaignList);
		} else {
			System.err.println("Campaign list is empty.");
		}
	}

	/**
	 * Metoda odpowiedzialna za wczytanie listy kampanii przeznaczonej do wysylki
	 * 
	 * @param filePath
	 */
	public void loadCampaignSettings(String filePath) {
		try {

			File f = new File("C:\\crawlers\\amazon\\" + filePath + ".txt");

			BufferedReader b = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));

			String readLine = "";

			System.out.println("Start loading campaign settings");

			while ((readLine = b.readLine()) != null) {
				System.out.println(readLine);
				String[] parts = readLine.split(";");
				System.out.println("liczba emementow to " + parts.length);
				if (parts.length == 8) {
					// wykrywa kampanie aktywne
					if (parts[5].contains("yes")) {
						this.getCampaignsSet().add(new CampaignSettings(parts[0], parts[1], parts[2], parts[3],
								parts[4], parts[6], parts[7]));
						System.out.println("new campign:  " + readLine);
					} else if (parts[5].contains("no")) {
						System.out.println("Skipped campaign: " + parts[1]);
					} else {
						System.out.println("niesklasyfikowano = " + parts[5]);
					}
				} else {
					System.err.println("Unable load settings for campaign - check syntax");
				}

			}
			System.out.println("Loading process is complited");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metoda tworz¹ca osobne w¹tki dla ka¿dej z kampanii
	 */
	public void createCampaignFactory(List<?> campaignList) {
		testLog.info("ZNALEZIONO "+campaignList.size()+" KAMPANII");
		CampaignFactory[] campaignFactory = new CampaignFactory[campaignList.size()];
		Thread[] threads = new Thread[campaignList.size()];
		for (int i = 0; i < campaignList.size(); i++) {
			campaignFactory[i] = new CampaignFactory((CampaignSettings) campaignList.get(i));
		}
		for (int i = 0; i < campaignList.size(); i++) {
			threads[i] = new Thread(campaignFactory[i]);
		}
		for (int i = 0; i < campaignList.size(); i++) {
			threads[i].start();
		}

	}
}
