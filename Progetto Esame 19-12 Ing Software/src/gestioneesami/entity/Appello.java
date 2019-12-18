package gestioneesami.entity;

import java.time.LocalDate;
import java.util.ArrayList;

public class Appello {	

	private String id_appello;
	private ArrayList<Data_Appello> date_appello;
	private ArrayList<Studente_Prenotato> studenti_appello;

	public Appello(LocalDate data_appello,String luogo_data,Tipo_Appello tipo) {
		this.date_appello=new ArrayList<Data_Appello>();
		this.studenti_appello= new ArrayList<Studente_Prenotato>();
		Data_Appello pdata=new Data_Appello(data_appello,luogo_data,tipo);
		this.date_appello.add(pdata);
	}

	public void addData(LocalDate data_appello,String luogo_data,Tipo_Appello tipo) {   //mettere nel gestore

		Data_Appello ndata=new Data_Appello(data_appello,luogo_data,tipo);
		this.date_appello.add(ndata);
	}

	public ArrayList<Data_Appello> getDate_appello() {
		return date_appello;
	}

	public void setDate_appello(ArrayList<Data_Appello> date_appello) {
		this.date_appello = date_appello;
	}

	public ArrayList<Studente_Prenotato> getStudenti_appello() {
		return studenti_appello;
	}

	public void setStudenti_appello(ArrayList<Studente_Prenotato> studenti_appello) {
		this.studenti_appello = studenti_appello;
	}

	public String getId_appello() {
		return id_appello;
	}

	public void setId_appello(String id_appello) {
		this.id_appello = id_appello;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj==null) {
			return false;
		}
		if(!(obj instanceof Appello)) {
			return false;
		}
		Appello tmp=(Appello) obj;
		if(tmp.id_appello==this.id_appello) {
			return true;
		}
		return false;
	}
	
}