package AmazonSESSample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import Structure.Sellter;

public class CampaignRepository {
	public int numberOfCampaign;
	/**
	 * zbior ustawien kampanii wczytanych z pliku campaignSettings.txt
	 */
	public Set<CampaignSettings> campaignsSet = new HashSet<CampaignSettings>();
	
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

	public CampaignRepository() {
		loadCampaignSettings("campaignSettings");
	}
	
	public void loadCampaignSettings(String filePath) {
		try {

            File f = new File("C:\\crawlers\\amazon\\"+filePath+".txt");

            BufferedReader b = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));

            String readLine = "";

            System.out.println("Start loading campaign settings");

            while ((readLine = b.readLine()) != null) {
                String[] parts = readLine.split(";");
                if(parts.length==5) {
                	this.getCampaignsSet().add(new CampaignSettings(parts[0], parts[1], parts[2], parts[3], parts[4]));
                	System.out.println("new campign:  "+readLine);                	
                }else {{{{{{{
                	
                }
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
