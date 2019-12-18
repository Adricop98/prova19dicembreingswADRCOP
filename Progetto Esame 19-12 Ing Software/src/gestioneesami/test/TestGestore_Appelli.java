package gestioneesami.test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gestioneesami.control.Gestore_Appelli;
import gestioneesami.entity.*;

public class TestGestore_Appelli {
	Gestore_Appelli Gestore_Appelli;
	Docente doc_corso;
	Docente doc_corso2;
	LocalDate[] date;
	LocalDate[] date2;
	String[] luoghi;
	String[] luoghi2;
	Tipo_Appello[] tipi;
	Tipo_Appello[] tipi2;

	@Before
	public void setUp() throws Exception {
		Gestore_Appelli=new Gestore_Appelli();
		doc_corso=new Docente("Roberto","Natella");
		doc_corso2=new Docente("Roberto","Pietrantuono");
		date= new LocalDate[] {LocalDate.of(2019, 12, 19)};
		date2=new LocalDate[]  {LocalDate.of(2019, 12, 20),LocalDate.of(2020,1,8)};
		luoghi=new String[] {"SG-II-2"};
		luoghi2=new String[] {"Laboratori Agnano"};
		tipi=new Tipo_Appello[] {Tipo_Appello.ORALE};
		tipi2=new Tipo_Appello[]  {Tipo_Appello.AL_CALCOLATORE,Tipo_Appello.ORALE};
	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void test01Ricerca_prenotazioni() {
		Corso corso=Gestore_Appelli.aggiungi_corso(9, "Ingegneria del Software", doc_corso);
		Corso corso2=new Corso(9,"Sistemi Operativi");
		Appello app=Gestore_Appelli.aggiungi_appello(corso,date,luoghi,tipi);
		Gestore_Appelli.prenota_studente("N46003682", "Adriano", "Coppola", app);
		
		ArrayList<Studente_Prenotato> list=Gestore_Appelli.ricerca_prenotazioni(corso2, null);
		Gestore_Appelli.stampa_prenotazioni(corso2, null);
		
		assertEquals(0,list.size());
	}
	
	@Test
	public void test02Ricerca_prenotazioni() {
		Corso corso=Gestore_Appelli.aggiungi_corso(9, "Ingegneria del Software", doc_corso);
		Corso corso2=Gestore_Appelli.aggiungi_corso(9, "Sistemi Operativi", doc_corso2);
		Appello app=Gestore_Appelli.aggiungi_appello(corso,date,luoghi,tipi);
		Appello app2=Gestore_Appelli.aggiungi_appello(corso2,date2,luoghi2,tipi2);
		Gestore_Appelli.prenota_studente("N46003682", "Adriano", "Coppola", app);
		Gestore_Appelli.prenota_studente("N46000420", "Stefano", "Barbano", app2);
		
		ArrayList<Studente_Prenotato> list=Gestore_Appelli.ricerca_prenotazioni(corso2, app);
		Gestore_Appelli.stampa_prenotazioni(corso2, app);
		
		assertEquals(0,list.size());
	}	

	@Test
	public void test03Ricerca_prenotazioni() {
		Corso corso=Gestore_Appelli.aggiungi_corso(9, "Ingegneria del Software", doc_corso);
		Appello app=Gestore_Appelli.aggiungi_appello(corso,date,luoghi,tipi);
		Gestore_Appelli.prenota_studente("N46003682", "Adriano", "Coppola", app);
		
		Appello app2=new Appello(LocalDate.now(),"Test",Tipo_Appello.DA_STABILIRE);
		
		ArrayList<Studente_Prenotato> list=Gestore_Appelli.ricerca_prenotazioni(corso, app2);
		Gestore_Appelli.stampa_prenotazioni(corso, app2);	
		
		assertEquals(0,list.size());
	}	

	@Test
	public void test04Ricerca_prenotazioni() {
		Corso corso=Gestore_Appelli.aggiungi_corso(9, "Ingegneria del Software", doc_corso);
		Appello app=Gestore_Appelli.aggiungi_appello(corso,date,luoghi,tipi);
		
		ArrayList<Studente_Prenotato> list=Gestore_Appelli.ricerca_prenotazioni(corso, app);
		Gestore_Appelli.stampa_prenotazioni(corso, app);	
		
		assertEquals(0,list.size());
	}		
	
	@Test
	public void test05Ricerca_prenotazioni() {
		Corso corso=Gestore_Appelli.aggiungi_corso(9, "Ingegneria del Software", doc_corso);
		Appello app=Gestore_Appelli.aggiungi_appello(corso,date,luoghi,tipi);
		Gestore_Appelli.prenota_studente("N46003682", "Adriano", "Coppola", app);
		
		ArrayList<Studente_Prenotato> list=Gestore_Appelli.ricerca_prenotazioni(corso, app);
		Gestore_Appelli.stampa_prenotazioni(corso, app);	
		
		assertEquals(1,list.size());
	}
	
	@Test
	public void test06Ricerca_prenotazioni() {
		Corso corso=Gestore_Appelli.aggiungi_corso(9, "Ingegneria del Software", doc_corso);
		Appello app=Gestore_Appelli.aggiungi_appello(corso,date,luoghi,tipi);
		Gestore_Appelli.prenota_studente("N46003682", "Adriano", "Coppola", app);
		Gestore_Appelli.prenota_studente("N46000420", "Stefano", "Barbano", app);
		Gestore_Appelli.prenota_studente("N4600108", "Zoro", "Roronoa", app);
		
		ArrayList<Studente_Prenotato> list=Gestore_Appelli.ricerca_prenotazioni(corso, app);
		Gestore_Appelli.stampa_prenotazioni(corso, app);	
		
		assertEquals(3,list.size());
	}	
}
