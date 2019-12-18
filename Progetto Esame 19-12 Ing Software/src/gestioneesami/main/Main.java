package gestioneesami.main;

import gestioneesami.control.*;
import java.time.LocalDate;
import java.util.Scanner;

import gestioneesami.control.Gestore_Appelli;
import gestioneesami.entity.*;

public class Main {

	public static void main(String[] args) {
		Gestore_Appelli gestore=new Gestore_Appelli();
		Docente doc1=new Docente("Stefano","Barbano");
		Docente doc2=new Docente("Adriano","Coppola");
		Docente doc3=new Docente("Andrea","Olandese");
		
		Corso c1=gestore.aggiungi_corso(9, "Fondamenti di Sistemi Dinamici", doc1);
		Corso c2=gestore.aggiungi_corso(6, "Analisi II", doc2);
		Corso c3=gestore.aggiungi_corso(2, "Programmazione I", doc3);
		
		LocalDate[] datef= new LocalDate[] {LocalDate.of(2020, 4, 20),LocalDate.of(2019, 4, 19)};
		LocalDate[] datez= new LocalDate[] {LocalDate.of(2020, 12, 14)};
		LocalDate[] datet= new LocalDate[] {LocalDate.of(2022, 5, 23),LocalDate.of(2024, 9, 3),LocalDate.of(2070, 6, 9)};
		
		Tipo_Appello[] tipof= new Tipo_Appello[] {Tipo_Appello.SCRITTA,Tipo_Appello.ORALE};
		Tipo_Appello[] tipoz= new Tipo_Appello[] {Tipo_Appello.ORALE};
		Tipo_Appello[] tipot= new Tipo_Appello[] {Tipo_Appello.SCRITTA,Tipo_Appello.ORALE};
		
		String[] luoghif=new String[] {"SG I 2","SG I 2"};
		String[] luoghiz=new String[] {"SG T 3"};
		String[] luoghit=new String[] {"SG II 1","SG I 3","Laboratori Agnano"};
		
		Appello a1=gestore.aggiungi_appello(c1, datef, luoghif, tipof);
		Appello a3=gestore.aggiungi_appello(c1, datef, luoghif, tipof);
		Appello a2=gestore.aggiungi_appello(c1, datez, luoghiz, tipoz);
		
		gestore.aggiungi_appello(c2, datez, luoghiz, tipoz);
		gestore.aggiungi_appello(c3, datet, luoghit, tipot);
		Scanner s=new Scanner(System.in);
		
		gestore.stampa_appelli();
		
		gestore.prenota_studente("N46003692", "Giorno","Giovanna",a1);
		gestore.prenota_studente("N46006666", "Bruno","Bucciarati",a1);
		gestore.prenota_studente("N46003692", "Giorno","Giovanna",a2);
		gestore.prenota_studente("N46000420", "Gabriele","Romano",a1);
		
		gestore.stampa_prenotazioni(c1,a1);
		gestore.stampa_prenotazioni(c1,a2);
		gestore.stampa_prenotazioni(c1,a3);
	}

}
