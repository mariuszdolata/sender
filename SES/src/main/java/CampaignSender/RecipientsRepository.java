package CampaignSender;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

import Sample.Sample;
import Structure.Recipient;
import Structure.RecipientStatus;
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
	public Logger testLog = Logger.getLogger("testLog");
	public Logger testLogCollision = Logger.getLogger("testLogCollision");

	public synchronized Set<Recipient> getRecipients() {
		return recipients;
	}

	public synchronized void setRecipients(Set<Recipient> recipients) {
		this.recipients = recipients;
	}

	public synchronized String getFilePath() {
		return filePath;
	}

	public synchronized void setFilePath(String filePath) {
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
				//System.out.println("new recipients:  " + readLine);
				Recipient nextSellter = mapperSellter(readLine);
				if (nextSellter != null) {
					recipients.add(nextSellter);
					//System.out.println("Recipient added: " + nextSellter.toString());
				} else {
					//System.out.println("Recipient was skipped" + readLine);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return recipients;
	}

	/**
	 * UWAGA, koniecznosc ujednolicenia mapowania !!!! Obecna forma jest tylko
	 * przejsciowa!
	 * 
	 * @param line
	 * @return
	 */
	public static Recipient mapperSellter(String line) {
		Recipient sellter = null;
		String[] fields = line.split(";");
		//char c = '?';
		//fields[0]=fields[0].replaceAll(String.valueOf(c), "");
//		byte[] bytes = fields[0].getBytes();
//		for(byte b:bytes) {
//			System.out.println(fields[0]+"==>"+b);
//		}
		//System.out.println("liczba elementow odbiorcy (13 lub 15), a jest " + fields.length);
		try {
			// sellter = new Recipient(fields[0], false, fields[3], fields[2], fields[1],
			// fields[4]);
			if (fields.length == 15) {
				// dane pochodza z nowego generatora
				sellter = new Recipient(fields[0], Structure.RecipientStatus.READY, fields[1], fields[2], fields[3],
						fields[4], fields[5], fields[6], fields[7], fields[8], fields[9], fields[10], fields[11],
						fields[12], fields[13], fields[14]);
			//	System.out.println(">>>>>>>15>>>>>>>" + sellter.toString());
			} else if (fields.length == 13) {
				// dane pochodza ze starego generatora
				sellter = new Recipient(fields[0], Structure.RecipientStatus.READY, fields[1], fields[2], fields[3],
						fields[4], fields[5], fields[6], fields[7], fields[8], fields[9], fields[10], fields[11],
						fields[12]);
		//		System.out.println(">>>>13>>>>>>>>>>" + sellter.toString());
			} else if (fields.length == 1) {
				sellter = new Recipient(fields[0], Structure.RecipientStatus.READY);
			} else if (fields.length == 2) {
				sellter = new Recipient(fields[0], Structure.RecipientStatus.READY, fields[1]);
			} else if (fields.length == 3) {
				sellter = new Recipient(fields[0], Structure.RecipientStatus.READY, fields[1], fields[2]);
			}

		} catch (Exception e) {
		//	System.err.println("Sellter mapping error - out of bound? domain missing?");
			e.printStackTrace();
			try {
				// sellter = new Recipient(fields[0], false, fields[3], fields[2], fields[1],
				// "");
			} catch (Exception ee) {
		//		System.err.println("Sellter mapping error lvl2 - this recipient was skipped");
				ee.printStackTrace();
			}

		}
		return sellter;
	}

	public synchronized void showRecipients() {
		System.out.println("Loaded recipients:");
		for (Recipient s : this.recipients) {
			System.out.println(s.toString());
		}
	}

	/**
	 * Metoda znajduje pierwszego nieuzytego odbiorcê i go zwraca
	 * 
	 * @param thread
	 * @return
	 */
	public synchronized Recipient lockRecipient(int thread) {
		Recipient recipient = null;
		for (Iterator<Recipient> it = this.recipients.iterator(); it.hasNext();) {
			Recipient returnedRecipient = it.next();
			//testLog.info("i = "+thread+", iteracja recipient " + r.toString());
			if (returnedRecipient.getRecipientStatus() == RecipientStatus.READY) {
				testLog.info("i = "+thread+", znalezono nieu¿ytego odbiorcê " + returnedRecipient.toString());
				this.recipients.remove(returnedRecipient);
				returnedRecipient.setRecipientStatus(RecipientStatus.LOCKED);
				this.recipients.add(returnedRecipient);
				testLog.info("i = "+thread+", u¿yto odbiorcê " + returnedRecipient.toString());
				recipient = returnedRecipient;
				break;
			}
		}
		return recipient;
	}

	/**
	 * metoda zamieniajaca status odbiorcy z LOCKED na USED
	 * @param recipient
	 * @param thread
	 */
	public synchronized void setStatusRecipient(Recipient recipient, int thread) {
		for(Iterator<Recipient> it = this.recipients.iterator();it.hasNext();) {
			Recipient r = it.next();
			if(r==recipient) {
				if(r.getRecipientStatus()==RecipientStatus.LOCKED) {
					testLog.info("i = "+thread + "zwolnienie blokady dla r = "+r.toString());
					this.recipients.remove(recipient);
					recipient.setRecipientStatus(RecipientStatus.USED);
					this.recipients.add(recipient);
					testLog.info("i = "+thread + " odbiorca uzyty r="+recipient.toString());
				}else if(r.getRecipientStatus()==RecipientStatus.USED) {
					testLogCollision.info("i = "+thread + "Kolizja dla r " + recipient);
				}else {
					testLogCollision.info("i = " + thread + "status nieznany dla r="+recipient.toString());
				}
			}
		}
	}

	/**
	 * Metoda sprawdzajaca czy jeszcze komus mozna wyslac emaile
	 * @param thread
	 * @return
	 */
	public synchronized boolean checkRecipientSet(int thread) {
		boolean isDone=true; 
		for(Iterator<Recipient> it=this.recipients.iterator();it.hasNext();) {
			Recipient r = it.next();
			if(r.getRecipientStatus()==RecipientStatus.READY) {
				isDone=false;
			}
		}
		if(isDone) testLog.info("i = "+thread+", zbiór skoñczony");
		return isDone;			
	}
}
