package gestioneesami.entity;

import java.util.ArrayList;

public class Corso {
	public int num_crediti;
	public String nome_corso;
	private Docente docente_corso;
	private ArrayList<Appello> appelli;

	public Corso(int num_crediti, String nome_corso) {
		this.num_crediti = num_crediti;
		this.nome_corso = nome_corso;
		this.appelli=new ArrayList<Appello>();
	}

	public Docente getDocente_corso() {
		return docente_corso;
	}

	public void setDocente_corso(Docente docente_corso) {
		this.docente_corso = docente_corso;
	}
	
	public ArrayList<Appello> getAppelli() {
		return appelli;
	}

	public void setAppelli(ArrayList<Appello> appelli) {
		this.appelli = appelli;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj==null) {
			return false;
		}
		if(!(obj instanceof Corso)) {
			return false;
		}
		Corso tmp=(Corso) obj;
		if(tmp.nome_corso==this.nome_corso) {
			return true;
		}
		return false;
	}
	


}