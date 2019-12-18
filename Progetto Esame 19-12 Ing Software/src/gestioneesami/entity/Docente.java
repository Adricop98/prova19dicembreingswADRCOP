package gestioneesami.entity;

import java.time.LocalDate;
import java.util.UUID;

public class Docente {
	private String id_doc;
	public String nome_doc;
	public String cognome_doc;

	public Docente(String nome_doc, String cognome_doc) {
		this.nome_doc = nome_doc;
		this.cognome_doc = cognome_doc;
		this.id_doc=UUID.nameUUIDFromBytes((nome_doc+cognome_doc+LocalDate.now().toString()).getBytes()).toString();
	}

}