package gestioneesami.boundary;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import gestioneesami.control.Gestore_Appelli;
import gestioneesami.entity.Appello;
import gestioneesami.entity.Corso;
import gestioneesami.entity.Data_Appello;
import gestioneesami.entity.Docente;
import gestioneesami.entity.Tipo_Appello;

public class BStudente {
	public static Scanner s=new Scanner(System.in);

	public static void main(String[] args) {
		Gestore_Appelli gestore=new Gestore_Appelli();
		Docente doc1=new Docente("Stefano","Barbano");													//inizio database simulato
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
		
		gestore.aggiungi_appello(c1, datef, luoghif, tipof);
		gestore.aggiungi_appello(c1, datez, luoghiz, tipoz);
		gestore.aggiungi_appello(c2, datet, luoghit, tipot);
		
		gestore.aggiungi_appello(c2, datez, luoghiz, tipoz);
		gestore.aggiungi_appello(c3, datet, luoghit, tipot);											//fine database simulato
		
		
		String matricola,nome,cognome;
		System.out.println("Inserire Credenziali");
		System.out.println("Matricola:");
		matricola=s.next();
		System.out.println("Nome:");
		nome=s.next();
		System.out.println("Cognome:");
		cognome=s.next();
		char c;
		loop: while(true) {
		System.out.println("Premere v per visualizzare i corsi con relativi appelli\n");
		System.out.println("Premere p per prenotarti ad un appello\n");
		System.out.println("Premere s per uscire\n");
		switch(c=s.next(".").charAt(0)) {
		case 'v':
			gestore.stampa_appelli();
			break;
		case 'p':
			for(int i=0;i<gestore.lista_corsi.size();i++) {
				System.out.println("N°"+(i+1)+"\n");
				System.out.println(gestore.lista_corsi.get(i).nome_corso);
			}
			int numcors=-1;
			while(numcors<=0||numcors>gestore.lista_corsi.size()) {
				System.out.println("Inserire un numero relativo ad un corso presente\n");
				try {
					numcors=s.nextInt();
				}catch(InputMismatchException e) {
					s.next();
				}
			}
			numcors--;
				if(gestore.lista_corsi.get(numcors).getAppelli().size()>0) {
					DateTimeFormatter formatdata = DateTimeFormatter.ofPattern("dd MM yyyy");
					System.out.println("Sono disponibili i seguenti appelli");
					for(int j=0;j<gestore.lista_corsi.get(numcors).getAppelli().size();j++) {
						ArrayList<Data_Appello> tmpdate=gestore.lista_corsi.get(numcors).getAppelli().get(j).getDate_appello();
						System.out.println("Appello N°"+(j+1)+":\n");
						for(int k=0;k<tmpdate.size();k++) {
							System.out.println((k+1)+"° Data\n");
							System.out.println(tmpdate.get(k).getData().format(formatdata));
							System.out.println(" in luogo "+tmpdate.get(k).getLuogo_svolgimento());
							System.out.println(" PROVA "+tmpdate.get(k).getTipo_appello().name()+"\n");
						}
					
					}
					int numapp=-1;
					while(numapp<=0||numapp>gestore.lista_corsi.get(numcors).getAppelli().size()) {
						System.out.println("Inserire un numero relativo ad un appello presente\n");
						try {
							numapp=s.nextInt();
						}catch(InputMismatchException e) {
							s.next();
						}
						
					}
					gestore.prenota_studente(matricola, nome, cognome, gestore.lista_corsi.get(numcors).getAppelli().get(numapp-1));
					System.out.println("La prenotazione ha avuto successo\n");
				}else
				System.out.println("Non ci sono appelli per questo corso\n");
			break;
		case 's':
			break loop;
		default:
			System.out.println("Inserire carattere valido\n");
			break;

		}
		}
		s.close();
		return;
	}	
}
