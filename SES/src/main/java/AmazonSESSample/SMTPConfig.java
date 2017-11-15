package AmazonSESSample;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map.Entry;
import java.util.Properties;

public class SMTPConfig {
	public Properties props;// = System.getProperties();
	public int PORT;
	public String SMTP_USERNAME;
	public String SMTP_PASSWORD;
	public String CONFIGSET;
	public String HOST;
	


	/**
	 * Konstruktor wyrzucaj¹cy dane dostêpowe do zewnetrznego pliku
	 * @param filePath
	 */
	public SMTPConfig(String filePath) {
		//props = System.getProperties();
		props = new Properties();
		InputStream inputStream=null;
		try {
			inputStream=new FileInputStream(filePath);
			this.props.load(inputStream);
			printProps(this.props);
			System.out.println("SMPT config - loaded");
		}catch(IOException ex) {
			ex.printStackTrace();
		}finally {
			printProps(this.props);
			if(inputStream!=null) {
				try {
					inputStream.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
			
			System.out.println(this.props.getProperty("mail.smtp.port"));
			this.PORT=Integer.parseInt(this.props.getProperty("mail.smtp.port"));
			this.HOST=this.props.getProperty("smtp_host");
			this.SMTP_USERNAME=this.getProps().getProperty("smtp_username");
			this.SMTP_PASSWORD=this.getProps().getProperty("smtp_password");
			this.CONFIGSET=this.getProps().getProperty("smtp_configset");
		}
	}
	public void printProps(Properties props) {
        for(Entry<Object, Object> e : props.entrySet()) {
            System.out.print("prop:");
        	System.out.println(e);
        }
    }

	public Properties getProps() {
		return props;
	}

	public void setProps(Properties props) {
		this.props = props;
	}

	public int getPORT() {
		return PORT;
	}

	public void setPORT(int pORT) {
		PORT = pORT;
	}

	public String getSMTP_USERNAME() {
		return SMTP_USERNAME;
	}

	public void setSMTP_USERNAME(String sMTP_USERNAME) {
		SMTP_USERNAME = sMTP_USERNAME;
	}

	public String getSMTP_PASSWORD() {
		return SMTP_PASSWORD;
	}

	public void setSMTP_PASSWORD(String sMTP_PASSWORD) {
		SMTP_PASSWORD = sMTP_PASSWORD;
	}

	public String getCONFIGSET() {
		return CONFIGSET;
	}

	public void setCONFIGSET(String cONFIGSET) {
		CONFIGSET = cONFIGSET;
	}

	public String getHOST() {
		return HOST;
	}

	public void setHOST(String hOST) {
		HOST = hOST;
	}

}
