package Structure;

public class Recipient extends SendClass {
	public String website;
	public String formaPrawna;
	public String nazwa;
	public String obrot;
	public String zysk;
	public String zatrudnienie;
	public String poziomObrot;
	public String poziomZysk;
	public String poziomZatrudnienie;
	public String imieOdmiana;
	public String plec;
	public String stanowisko;
	public String pkd;
	public String pkdSkrot;

	public synchronized String getWebsite() {
		return website;
	}

	public synchronized void setWebsite(String website) {
		this.website = website;
	}

	public synchronized String getFormaPrawna() {
		return formaPrawna;
	}

	public synchronized void setFormaPrawna(String formaPrawna) {
		this.formaPrawna = formaPrawna;
	}

	public synchronized String getNazwa() {
		return nazwa;
	}

	public synchronized void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public synchronized String getObrot() {
		return obrot;
	}

	public synchronized void setObrot(String obrot) {
		this.obrot = obrot;
	}

	public synchronized String getZysk() {
		return zysk;
	}

	public synchronized void setZysk(String zysk) {
		this.zysk = zysk;
	}

	public synchronized String getZatrudnienie() {
		return zatrudnienie;
	}

	public synchronized void setZatrudnienie(String zatrudnienie) {
		this.zatrudnienie = zatrudnienie;
	}

	public synchronized String getPoziomObrot() {
		return poziomObrot;
	}

	public synchronized void setPoziomObrot(String poziomObrot) {
		this.poziomObrot = poziomObrot;
	}

	public synchronized String getPoziomZysk() {
		return poziomZysk;
	}

	public synchronized void setPoziomZysk(String poziomZysk) {
		this.poziomZysk = poziomZysk;
	}

	public synchronized String getPoziomZatrudnienie() {
		return poziomZatrudnienie;
	}

	public synchronized void setPoziomZatrudnienie(String poziomZatrudnienie) {
		this.poziomZatrudnienie = poziomZatrudnienie;
	}

	public synchronized String getImieOdmiana() {
		return imieOdmiana;
	}

	public synchronized void setImieOdmiana(String imieOdmiana) {
		this.imieOdmiana = imieOdmiana;
	}

	public synchronized String getPlec() {
		return plec;
	}

	public synchronized void setPlec(String plec) {
		this.plec = plec;
	}

	public synchronized String getStanowisko() {
		return stanowisko;
	}

	public synchronized void setStanowisko(String stanowisko) {
		this.stanowisko = stanowisko;
	}

	public synchronized String getPkd() {
		return pkd;
	}

	public synchronized void setPkd(String pkd) {
		this.pkd = pkd;
	}

	public synchronized String getPkdSkrot() {
		return pkdSkrot;
	}

	public synchronized void setPkdSkrot(String pkdSkrot) {
		this.pkdSkrot = pkdSkrot;
	}

	public Recipient(String email, RecipientStatus recipientStatus) {
		super(email, recipientStatus);
		this.plec = "";
	}

	public Recipient(String email, RecipientStatus recipientStatus, String plec) {
		super(email, recipientStatus);
		this.plec = plec;
	}

	public Recipient(String email, RecipientStatus recipientStatus, String plec, String imieOdmiana) {
		super(email, recipientStatus);
		this.plec = plec;
		this.imieOdmiana = imieOdmiana;
	}

	public Recipient(String email, RecipientStatus recipientStatus, String website, String formaPrawna, String nazwa,
			String obrot, String zysk, String zatrudnienie, String poziomObrot, String poziomZysk,
			String poziomZatrudnienie, String imieOdmiana, String plec, String stanowisko, String pkd,
			String pkdSkrot) {
		super(email, recipientStatus);
		this.website = website;
		this.formaPrawna = formaPrawna;
		this.nazwa = nazwa;
		this.obrot = obrot;
		this.zysk = zysk;
		this.zatrudnienie = zatrudnienie;
		this.poziomObrot = poziomObrot;
		this.poziomZysk = poziomZysk;
		this.poziomZatrudnienie = poziomZatrudnienie;
		this.imieOdmiana = imieOdmiana;
		this.plec = plec;
		this.stanowisko = stanowisko;
		this.pkd = pkd;
		this.pkdSkrot = pkdSkrot;
	}

	public Recipient(String email, RecipientStatus recipientStatus, String website, String formaPrawna, String nazwa,
			String obrot, String zysk, String zatrudnienie, String poziomObrot, String poziomZysk,
			String poziomZatrudnienie, String imieOdmiana, String plec, String stanowisko) {
		super(email, recipientStatus);
		this.website = website;
		this.formaPrawna = formaPrawna;
		this.nazwa = nazwa;
		this.obrot = obrot;
		this.zysk = zysk;
		this.zatrudnienie = zatrudnienie;
		this.poziomObrot = poziomObrot;
		this.poziomZysk = poziomZysk;
		this.poziomZatrudnienie = poziomZatrudnienie;
		this.imieOdmiana = imieOdmiana;
		this.plec = plec;
		this.stanowisko = stanowisko;
	}

	@Override
	public synchronized String toString() {
		return "Recipient [website=" + website + ", formaPrawna=" + formaPrawna + ", nazwa=" + nazwa + ", obrot="
				+ obrot + ", zysk=" + zysk + ", zatrudnienie=" + zatrudnienie + ", poziomObrot=" + poziomObrot
				+ ", poziomZysk=" + poziomZysk + ", poziomZatrudnienie=" + poziomZatrudnienie + ", imieOdmiana="
				+ imieOdmiana + ", plec=" + plec + ", stanowisko=" + stanowisko + ", pkd=" + pkd + ", pkdSkrot="
				+ pkdSkrot + ", email=" + email + ", recipientStatus=" + recipientStatus + "]";
	}

}
