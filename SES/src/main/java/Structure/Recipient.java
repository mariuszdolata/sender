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

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getFormaPrawna() {
		return formaPrawna;
	}

	public void setFormaPrawna(String formaPrawna) {
		this.formaPrawna = formaPrawna;
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public String getObrot() {
		return obrot;
	}

	public void setObrot(String obrot) {
		this.obrot = obrot;
	}

	public String getZysk() {
		return zysk;
	}

	public void setZysk(String zysk) {
		this.zysk = zysk;
	}

	public String getZatrudnienie() {
		return zatrudnienie;
	}

	public void setZatrudnienie(String zatrudnienie) {
		this.zatrudnienie = zatrudnienie;
	}

	public String getPoziomObrot() {
		return poziomObrot;
	}

	public void setPoziomObrot(String poziomObrot) {
		this.poziomObrot = poziomObrot;
	}

	public String getPoziomZysk() {
		return poziomZysk;
	}

	public void setPoziomZysk(String poziomZysk) {
		this.poziomZysk = poziomZysk;
	}

	public String getPoziomZatrudnienie() {
		return poziomZatrudnienie;
	}

	public void setPoziomZatrudnienie(String poziomZatrudnienie) {
		this.poziomZatrudnienie = poziomZatrudnienie;
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

	public String getStanowisko() {
		return stanowisko;
	}

	public void setStanowisko(String stanowisko) {
		this.stanowisko = stanowisko;
	}

	public String getPkd() {
		return pkd;
	}

	public void setPkd(String pkd) {
		this.pkd = pkd;
	}

	public String getPkdSkrot() {
		return pkdSkrot;
	}

	public void setPkdSkrot(String pkdSkrot) {
		this.pkdSkrot = pkdSkrot;
	}

	public Recipient(String email, boolean status) {
		super(email, status);
		this.plec="";
	}
	public Recipient(String email, boolean status, String plec) {
		super(email, status);
		this.plec=plec;
	}
	public Recipient(String email, boolean status, String plec, String imieOdmiana) {
		super(email,status);
		this.plec=plec;
		this.imieOdmiana=imieOdmiana;
	}
	public Recipient(String email, boolean status, String website, String formaPrawna, String nazwa, String obrot,
			String zysk, String zatrudnienie, String poziomObrot, String poziomZysk, String poziomZatrudnienie,
			String imieOdmiana, String plec, String stanowisko, String pkd, String pkdSkrot) {
		super(email, status);
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

	public Recipient(String email, boolean status, String website, String formaPrawna, String nazwa, String obrot,
			String zysk, String zatrudnienie, String poziomObrot, String poziomZysk, String poziomZatrudnienie,
			String imieOdmiana, String plec, String stanowisko) {
		super(email, status);
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
	public String toString() {
		return "Recipient [website=" + website + ", formaPrawna=" + formaPrawna + ", nazwa=" + nazwa + ", obrot="
				+ obrot + ", zysk=" + zysk + ", zatrudnienie=" + zatrudnienie + ", poziomObrot=" + poziomObrot
				+ ", poziomZysk=" + poziomZysk + ", poziomZatrudnienie=" + poziomZatrudnienie + ", imieOdmiana="
				+ imieOdmiana + ", plec=" + plec + ", stanowisko=" + stanowisko + ", email=" + email + "]";
	}

}
