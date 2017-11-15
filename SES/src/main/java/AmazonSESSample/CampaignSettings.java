package AmazonSESSample;

public class CampaignSettings {
	public String campaignName;
	public String smtpFilePath;
	public String contentCode;
	public String senderName;
	public String senderEmail;
	public String getCampaignName() {
		return campaignName;
	}
	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}
	public String getSmtpFilePath() {
		return smtpFilePath;
	}
	public void setSmtpFilePath(String smtpFilePath) {
		this.smtpFilePath = smtpFilePath;
	}
	public String getContentCode() {
		return contentCode;
	}
	public void setContentCode(String contentCode) {
		this.contentCode = contentCode;
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
	public CampaignSettings(String campaignName, String smtpFilePath, String contentCode, String senderName,
			String senderEmail) {
		super();
		this.campaignName = campaignName;
		this.smtpFilePath = smtpFilePath;
		this.contentCode = contentCode;
		this.senderName = senderName;
		this.senderEmail = senderEmail;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((campaignName == null) ? 0 : campaignName.hashCode());
		result = prime * result + ((contentCode == null) ? 0 : contentCode.hashCode());
		result = prime * result + ((senderEmail == null) ? 0 : senderEmail.hashCode());
		result = prime * result + ((senderName == null) ? 0 : senderName.hashCode());
		result = prime * result + ((smtpFilePath == null) ? 0 : smtpFilePath.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CampaignSettings other = (CampaignSettings) obj;
		if (campaignName == null) {
			if (other.campaignName != null)
				return false;
		} else if (!campaignName.equals(other.campaignName))
			return false;
		if (contentCode == null) {
			if (other.contentCode != null)
				return false;
		} else if (!contentCode.equals(other.contentCode))
			return false;
		if (senderEmail == null) {
			if (other.senderEmail != null)
				return false;
		} else if (!senderEmail.equals(other.senderEmail))
			return false;
		if (senderName == null) {
			if (other.senderName != null)
				return false;
		} else if (!senderName.equals(other.senderName))
			return false;
		if (smtpFilePath == null) {
			if (other.smtpFilePath != null)
				return false;
		} else if (!smtpFilePath.equals(other.smtpFilePath))
			return false;
		return true;
	}
	
	

}
