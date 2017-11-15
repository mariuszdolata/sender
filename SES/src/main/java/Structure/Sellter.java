package Structure;

public class Sellter extends SendClass {
	public String nazwa;
	public String imieOdmiana;
	public String plec;
	public String website;

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public String getImieOdmiana() {
		return imieOdmiana;
	}

	public void setImieOdmiana(String imieOdmiana) {
		this.imieOdmiana = imieOdmiana;
	}

	public String getPlec() {
		return plec;
	}

	public void setPlec(String plec) {
		this.plec = plec;
	}

	public Sellter(String email, boolean status, String nazwa, String imieOdmiana, String plec, String website) {
		super(email, status);
		this.nazwa = nazwa;
		this.imieOdmiana = imieOdmiana;
		this.plec = plec;
		this.website = website;
	}

	@Override
	public String toString() {
		return "Sellter [email=" + email + ", nazwa=" + nazwa + ", imieOdmiana=" + imieOdmiana + ", plec=" + plec
				+ ", website=" + website + "]";
	}
	

}
