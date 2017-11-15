package AmazonSESSample;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class AmazonSendClass {
	public int i; // numer watku
	public int j; // numer iteracji

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}
	

	public AmazonSendClass(int i, int j) {
		super();
		this.i = i;
		this.j = j;
		//this.start();
	}

	
	public void start() {
		try {
			System.out.println("Watek nr "+this.getI()+", iteracja nr "+this.getJ()+" START!");
			// Create a Properties object to contain connection configuration information.
			Properties props = System.getProperties();
			props.put("mail.transport.protocol", "smtp");


			String BODY = String.join(System.getProperty("line.separator"),
					"<h1>Dzie�doberek! Mariusz wita odbiorc�w tej wiadomo�ci</h1>", "e-mail nr" + i,
					"<h3>Witam Panie Mariuszu,</h3>",

					"<p>Kontaktuje si� z Pa�stwem w imieniu firmy zajmuj�cej si� generowaniem sprzeda�y dla przedsi�biorstw dzia�aj�cych w obszarze B2B.",
					
					"<p>W zwi�zku z rosn�cym zapotrzebowaniem na nowoczesne metody <B>pozyskiwania klient�w</B>, przygotowali�my rozwi�zanie zwi�kszaj�ce wyniki sprzeda�owe, usprawniaj�ce procesy oraz <U>zmniejszaj�ce koszty</U>. ",
					
					"<p>W czasach gdy kupuj�cy przechodz� samodzielnie nawet 60% procesu zakupowego, (Google and CEB - The Digital Evolution in B2B Marketing) najbardziej istotne jest zidentyfikowanie i zaanga�owanie w rozmowy z klientem, kt�ry ju� podj�� decyzj� o zakupie.", 
					
					"<p>Nasza metodologia dzia�ania u�atwia odnajdywanie zainteresowanych klient�w <B>zwi�kszaj�c znacz�co szanse sprzeda�owe.</B>",
					
					"<p>Czy mog� przedstawi� wi�cej informacji zwi�zanych z pozyskiwaniem nowych klient�w?",
					"<p><p>",
					"<BR>Pozdrawiam,",
					"<BR>Monika Urba�ska",
					"<BR>Key Account Manager",
					"w�tek nr "+i+", iteracja nr "+j,
					"<p>Ten e-mail zostal wyslany przez Mariusza. Jesli nie chcesz jego otrzymywac to wiedz, �e nie interesuje mnie to.");
			// Create a Session object to represent a mail session with the specified
			// properties.
			Session session = Session.getDefaultInstance(props);

			// Create a message with the specified information.
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(FROM, FROMNAME));
			msg.setHeader("Content-Type", "text/plain; charset=UTF-8");
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
			String SUBJECT = "Nowi klienci dla Encore w�tek nr " + i;
			msg.setSubject(SUBJECT, "utf-8");
			msg.setContent(BODY, "text/html; charset=UTF-8");
			System.out.println("msg wzorcowe="+msg.toString());
			// Add a configuration set header. Comment or delete the
			// next line if you are not using a configuration set
			// msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);

			// Create a transport.
			Transport transport = session.getTransport();

			// Send the message.
			try {

				System.out.println("Sending...");

				// Connect to Amazon SES using the SMTP username and password you specified
				// above.
				transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);

				// Send the email.
				transport.sendMessage(msg, msg.getAllRecipients());
				System.out.println("Email sent!, watek=" + i+", iteracja="+j);

			} catch (Exception ex) {
				System.out.println("The email was not sent, watek=" + i+", iteracja="+j);
				System.out.println("Error message: " + ex.getMessage());
			} finally {
				// Close and terminate the connection.
				transport.close();
			}

		} catch (Exception e) {
			System.err.println("blad w klsie AmazonSendClass");
			e.printStackTrace();
		}
		System.out.println("Watek nr "+this.getI()+", iteracja nr "+this.getJ()+" KONIEC!");
	}
}
