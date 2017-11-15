package AmazonSESSample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CampaignContent {
	
	public String contentMale, contentFemale, contentUnknown; //tresc zaczepki w zaleznosci od plci
	public String senderName;
	public String senderEmail;
	public String contentFilePath;
	
	
	
	public CampaignContent(String contentFilePath, String senderName, String senderEmail) {
		super();
		this.senderName = senderName;
		this.senderEmail = senderEmail;
		this.contentFilePath = contentFilePath;
		this.contentMale = loadContent(contentFilePath+"_m.txt");
		this.contentFemale = loadContent(contentFilePath+"_f.txt");
		this.contentUnknown = loadContent(contentFilePath+"_u.txt");
		this.showContent();
	}
	public String getContentFilePath() {
		return contentFilePath;
	}
	public void setContentFilePath(String contentFilePath) {
		this.contentFilePath = contentFilePath;
	}
	public String getContentMale() {
		return contentMale;
	}
	public void setContentMale(String contentMale) {
		this.contentMale = contentMale;
	}
	public String getContentFemale() {
		return contentFemale;
	}
	public void setContentFemale(String contentFemale) {
		this.contentFemale = contentFemale;
	}
	public String getContentUnknown() {
		return contentUnknown;
	}
	public void setContentUnknown(String contentUnknown) {
		this.contentUnknown = contentUnknown;
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
	
	public String loadContent(String filePath) {
		String content=null;
		
		try {

            File f = new File(filePath);
           // BufferedReader b = new BufferedReader(new FileReader(f));
            BufferedReader b = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
            String readLine;
            System.out.println("Start loadingcontent.");
            while ((readLine = b.readLine()) != null) {
                content+=readLine;
            }
            System.out.print("Zakoñczono wczytywanie zaczepki z pliku "+filePath);
        } catch (IOException e) {
        	System.err.println("Unable to load content from file: "+filePath);
            e.printStackTrace();
        }
		
		return content;
	}
	
	public void showContent() {
		System.out.println("Zaczepka ¿eñska");
		System.out.println(contentFemale);
		System.out.println("#######################################################################################");
		System.out.println("Zaczepka mêska");
		System.out.println(contentMale);
		System.out.println("#######################################################################################");
		System.out.println("Zaczepka bezp³ciowa");
		System.out.println(contentUnknown);
		
	}
	
	

}
