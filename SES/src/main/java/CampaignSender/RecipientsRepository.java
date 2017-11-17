package CampaignSender;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import Structure.Recipient;
import Structure.SendClass;

/**
 * Klasa wczytujaca wygenerowane dane dla kampanii
 * 
 * @author mariusz
 *
 */
public class RecipientsRepository {
	public Set<Recipient> recipients;
	public String filePath;

	public Set<Recipient> getRecipients() {
		return recipients;
	}

	public void setRecipients(Set<Recipient> recipients) {
		this.recipients = recipients;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public RecipientsRepository(String filePath) {
		super();
		this.filePath = filePath;
		this.recipients = loadEmailsFromFile(filePath);
		showRecipients();
	}

	public static Set<Recipient> loadEmailsFromFile(String filePath) {

		Set<Recipient> recipients = new HashSet<Recipient>();
		try {

			File f = new File(filePath);

			BufferedReader b = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));

			String readLine = "";

			System.out.println("Start loading recipients.");

			while ((readLine = b.readLine()) != null) {
				System.out.println("new recipients:  " + readLine);
				Recipient nextSellter = mapperSellter(readLine);
				if (nextSellter != null) {
					recipients.add(nextSellter);
					System.out.println("Recipient added: " + nextSellter.toString());
				} else {
					System.out.println("Recipient was skipped" + readLine);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return recipients;
	}

	public static Recipient mapperSellter(String line) {
		Recipient sellter = null;
		String[] fields = line.split(";");
		System.out.println("liczba elementow odbiorcy (13 lub 15), a jest "+fields.length);
		try {
			// sellter = new Recipient(fields[0], false, fields[3], fields[2], fields[1],
			// fields[4]);
			if (fields.length == 15) {
				// dane pochodza z nowego generatora
				sellter = new Recipient(fields[0], false, fields[1], fields[2], fields[3], fields[4], fields[5],
						fields[6], fields[7], fields[8], fields[9], fields[10], fields[11], fields[12], fields[13],
						fields[14]);
				System.out.println(">>>>>>>15>>>>>>>"+sellter.toString());
			} else if (fields.length == 13) {
				// dane pochodza ze starego generatora
				sellter = new Recipient(fields[0], false, fields[1], fields[2], fields[3], fields[4], fields[5],
						fields[6], fields[7], fields[8], fields[9], fields[10], fields[11], fields[12]);
				System.out.println(">>>>13>>>>>>>>>>"+sellter.toString());
			}
			
		} catch (Exception e) {
			System.err.println("Sellter mapping error - out of bound? domain missing?");
			e.printStackTrace();
			try {
				// sellter = new Recipient(fields[0], false, fields[3], fields[2], fields[1],
				// "");
			} catch (Exception ee) {
				System.err.println("Sellter mapping error lvl2 - this recipient was skipped");
				ee.printStackTrace();
			}

		}
		return sellter;
	}

	public void showRecipients() {
		System.out.println("Loaded recipients:");
		for (Recipient s : this.recipients) {
			System.out.println(s.toString());
		}
	}

}
