package CampaignSender;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

public class CampaignContent {

	public String contentMale, contentFemale, contentUnknown; // tresc zaczepki w zaleznosci od plci
	public String senderName;
	public String senderEmail;
	public String contentFilePath;
	public String subject;
	public Logger mailErr = Logger.getLogger("mailErr");
	public Logger mainLog = Logger.getLogger("mainLog");

	public CampaignContent(String contentFilePath, String senderName, String senderEmail, String subject) {
		super();
		this.senderName = senderName;
		this.senderEmail = senderEmail;
		this.contentFilePath = contentFilePath;
		this.contentMale = loadContent(contentFilePath + "_m.txt");
		this.contentFemale = loadContent(contentFilePath + "_f.txt");
		this.contentUnknown = loadContent(contentFilePath + "_u.txt");
		this.subject = subject;
		//this.showContent();
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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
		String content = null;

		try {

			File f = new File(filePath);
			// BufferedReader b = new BufferedReader(new FileReader(f));
			BufferedReader b = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
			String readLine;
			mainLog.info("Pocz�tek wczytywania zaczepki dla kampanii " + filePath);
			while ((readLine = b.readLine()) != null) {
				content += readLine;
			}
			mainLog.info("Koniec wczytywania zaczepki dla kampanii " + filePath);
		} catch (IOException e) {
			mailErr.error("Blad wczytania zaczepki dla kampanii " + filePath, e);
			e.printStackTrace();
		}

		return content;
	}

	public void showContent() {
		System.out.println("Zaczepka �e�ska");
		System.out.println(contentFemale);
		System.out.println("#######################################################################################");
		System.out.println("Zaczepka m�ska");
		System.out.println(contentMale);
		System.out.println("#######################################################################################");
		System.out.println("Zaczepka bezp�ciowa");
		System.out.println(contentUnknown);

	}

}
