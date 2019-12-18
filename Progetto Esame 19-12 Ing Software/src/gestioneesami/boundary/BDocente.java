package gestioneesami.boundary;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Scanner;

import gestioneesami.control.Gestore_Appelli;
import gestioneesami.entity.Appello;
import gestioneesami.entity.Corso;
import gestioneesami.entity.Data_Appello;
import gestioneesami.entity.Docente;
import gestioneesami.entity.Tipo_Appello;

public class BDocente {																				//assumo che questo boundary sia collegato ad un sistema di login dove va immesso un id e password
																									// e che quindi il sistema sappia che professore è collegato
	public static Scanner s=new Scanner(System.in);
	
	public static void main(String[] args) {
		Gestore_Appelli gestore=new Gestore_Appelli();	
		Docente doc1=new Docente("Roberto","Natella");												//inizio database simulato
		Docente doc2=new Docente("Emanuele","Haus");
		Docente doc3=new Docente("Adriano","Coppola");
		
		LocalDate[] datef= new LocalDate[] {LocalDate.of(2020, 4, 20),LocalDate.of(2019, 4, 19)};
		Tipo_Appello[] tipof= new Tipo_Appello[] {Tipo_Appello.SCRITTA,Tipo_Appello.ORALE};
		String[] luoghif=new String[] {"SG I 2","SG I 2"};
		
		Corso c1=gestore.aggiungi_corso(9, "Ingegneria del Software", doc1);
		Corso c2=gestore.aggiungi_corso(6, "Analisi II", doc2);	
		Corso c3=gestore.aggiungi_corso(9, "Sistemi Operativi", doc1);
		Corso c4=gestore.aggiungi_corso(6, "Metodi", doc2);	
		Appello a1=gestore.aggiungi_appello(c1, datef, luoghif, tipof);	
		
		gestore.prenota_studente("N46003692", "Giorno","Giovanna",a1);
		gestore.prenota_studente("N46006666", "Bruno","Bucciarati",a1);								//fine database simulato

		System.out.println("Utente Collegato:Prof."+doc1.cognome_doc+" "+doc1.nome_doc);
		
		char c;
		loop: while(true) {
		System.out.println("Premere v per visualizzare gli studenti prenotati ad un appello\n");
		System.out.println("Premere a per aggiungere un appello\n");
		System.out.println("Premere s per uscire\n");
		switch(c=s.next(".").charAt(0)) {
		case 'v':
			System.out.println("Scegli corso:\n");
			if(doc1.getCorsi().size()>0) {
				for(int i=0;i<doc1.getCorsi().size();i++) {
					System.out.println("N°"+(i+1)+"\n");
					System.out.println(doc1.getCorsi().get(i).nome_corso); 
				}
				int numcor=-1;
				while(numcor<=0||numcor>doc1.getCorsi().size()) {
					System.out.println("Inserire un numero relativo ad un corso presente\n");
					try {
						numcor=s.nextInt();
					}catch(InputMismatchException e) {
						s.next();
					}
					
				}
				numcor--;
				if(doc1.getCorsi().get(numcor).getAppelli().size()>0) {
					DateTimeFormatter formatdata = DateTimeFormatter.ofPattern("dd MM yyyy");
					for(int j=0;j<doc1.getCorsi().get(numcor).getAppelli().size();j++) {
						ArrayList<Data_Appello> tmpdate=doc1.getCorsi().get(numcor).getAppelli().get(j).getDate_appello();
						System.out.println("Appello N°"+(j+1)+":\n");
						for(int k=0;k<tmpdate.size();k++) {
							System.out.println((k+1)+"° Data\n");
							System.out.println(tmpdate.get(k).getData().format(formatdata));
							System.out.println(" in luogo "+tmpdate.get(k).getLuogo_svolgimento());
							System.out.println(" PROVA "+tmpdate.get(k).getTipo_appello().name()+"\n");
						}
					}
					int numapp=-1;
					while(numapp<=0||numapp>doc1.getCorsi().get(numcor).getAppelli().size()) {
						System.out.println("Inserire un numero relativo ad un appello presente\n");
						try {
							numapp=s.nextInt();
						}catch(InputMismatchException e) {
							s.next();
						}
					}
					numapp--;
					gestore.stampa_prenotazioni(doc1.getCorsi().get(numcor), doc1.getCorsi().get(numcor).getAppelli().get(numapp));
				}else
					System.out.println("Non sono ancora presenti appelli per questo corso");

			}else
				System.out.println("Non ti è stato assegnato un corso\n");
			break;
		case 'a':
			if(doc1.getCorsi().size()>0) {
				System.out.println("Scegli corso:\n");
				for(int i=0;i<doc1.getCorsi().size();i++) {
					System.out.println("N°"+(i+1)+"\n");
					System.out.println(doc1.getCorsi().get(i).nome_corso);
				}
				int numcor=-1;
				while(numcor<=0||numcor>doc1.getCorsi().size()) {
					System.out.println("Inserire un numero relativo ad un corso presente\n");
					try {
						numcor=s.nextInt();
					}catch(InputMismatchException e) {
						s.next();
					}
					
				}
				numcor--;
				int numdate=-1;
				while(numdate<=0||numdate>3) {
					System.out.println("Quante date vuoi inserire per questo appello?(MAX 3)\n");
					try {
						numdate=s.nextInt();
					}catch(InputMismatchException e) {
						s.next();
					}
				}
				LocalDate[] date=new LocalDate[numdate];
				String[] luoghi=new String[numdate];
				Tipo_Appello[] tipi=new Tipo_Appello[numdate];
				ArrayList<Tipo_Appello> arraysceltatipi=new ArrayList<Tipo_Appello>();
				arraysceltatipi.add(Tipo_Appello.ORALE);
				arraysceltatipi.add(Tipo_Appello.SCRITTA);
				arraysceltatipi.add(Tipo_Appello.AL_CALCOLATORE);
					for(int i=0;i<numdate;i++) {
						int anno=-1;
						while(anno<=2019||anno>3000) {
							System.out.println("Inserire anno della "+(i+1)+"° data(viene accettato nell'intervallo 2019-3000)\n");
							try {
								anno=s.nextInt();
							}catch(InputMismatchException e) {
								s.next();
							}
						}
						int mese=-1;
						while(mese<1||mese>12) {
							System.out.println("Inserire mese della "+(i+1)+"° data(viene accettato nell'intervallo 1-12)\n");
							try {
								mese=s.nextInt();
							}catch(InputMismatchException e) {
								s.next();
								}
						}
						int giorno=-1;
						while(giorno<1||giorno>LocalDate.of(anno,mese, 1).lengthOfMonth()) {
							System.out.println("Inserire giorno della "+(i+1)+"° data(viene accettato un numero coerente al mese)\n");
							try {
								giorno=s.nextInt();
								}catch(InputMismatchException e) {
									s.next();
								}		
						}
						date[i]=LocalDate.of(anno, mese, giorno);
						System.out.println("Inserire luogo dello svolgimento della "+(i+1)+"° data(tutto attaccato)");
						luoghi[i]=s.next();
						System.out.println("Inserire tipo di appello della "+(i+1)+"° data");
						int sceglitip=-1;
						while(sceglitip<1||sceglitip>arraysceltatipi.size()) {
							System.out.println("Inserire tipo di appello della "+(i+1)+"° data selezionando tramite numero uno dei seguenti\n");
							for(int k=0;k<arraysceltatipi.size();k++) {
								System.out.println((k+1)+":PROVA "+arraysceltatipi.get(k).toString());
							}
							try {
								sceglitip=s.nextInt();
								}catch(InputMismatchException e) {
									s.next();
								}		
						}
						tipi[i]=arraysceltatipi.get(sceglitip-1);
						arraysceltatipi.remove(sceglitip-1);
							}
					gestore.aggiungi_appello(doc1.getCorsi().get(numcor), date, luoghi, tipi);
					System.out.println("Appello aggiunto con successo");
			}else
				System.out.println("Non ti è stato assegnato un corso\n");
			break;
		case 's':
			break loop;
		default:
			System.out.println("Inserire carattere valido\n");
			break;

		}
		}
		s.close();
	}

}
