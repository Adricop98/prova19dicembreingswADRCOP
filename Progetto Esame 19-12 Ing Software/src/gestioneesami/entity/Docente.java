package gestioneesami.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.UUID;

public class Docente {
	private String id_doc;
	public String nome_doc;
	public String cognome_doc;
	private ArrayList<Corso> corsi;

	public Docente(String nome_doc, String cognome_doc) {
		this.nome_doc = nome_doc;
		this.cognome_doc = cognome_doc;
		this.corsi=new ArrayList<Corso>();
		this.id_doc=UUID.nameUUIDFromBytes((nome_doc+cognome_doc+LocalDate.now().toString()).getBytes()).toString();

	}

	public ArrayList<Corso> getCorsi() {
		return corsi;
	}

	public void setCorsi(ArrayList<Corso> corsi) {
		this.corsi = corsi;
	}
}