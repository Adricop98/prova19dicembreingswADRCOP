package gestioneesami.control;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import gestioneesami.entity.*;

public class Gestore_Appelli {
	
	public  ArrayList<Corso> lista_corsi=new ArrayList<Corso>();
	

	public  Corso aggiungi_corso(int num_cred,String nom_corso,Docente doc_corso) {				//crea un corso e vi assegna 
		Corso nuovo_corso=new Corso(num_cred,nom_corso);												//un docente preesistente nel sistema
		nuovo_corso.setDocente_corso(doc_corso);
		lista_corsi.add(nuovo_corso);
		return nuovo_corso;
	}
	
	public  Appello aggiungi_appello(Corso corso,LocalDate data[], String luogo_svolgimento[], Tipo_Appello tipo[]) {
		luogo_svolgimento=raffina_input_luogo(data,luogo_svolgimento);
		tipo=raffina_input_tipo(data,tipo);
		ordina_date(data,luogo_svolgimento,tipo,0,data.length-1);
		DateTimeFormatter formatdata = DateTimeFormatter.ofPattern("dd MM yyyy");    //per un valore data nel caso in cui
		CharSequence Errordate="01 01 0001";										//viene creato un oggetto non aggiunto alla lista
		if(lista_corsi.contains(corso)) {
			int indTCorso=lista_corsi.indexOf(corso);
			
			for(int x=0;x<lista_corsi.get(indTCorso).getAppelli().size();x++) {
				
				ArrayList<Data_Appello> datetmp=lista_corsi.get(indTCorso).getAppelli().get(x).getDate_appello();
				if((datetmp.get(datetmp.size()-1).getData().isAfter(data[0])) && (datetmp.get(datetmp.size()-1).getData().isEqual(data[0]))) {
					System.out.println("Impossibile inserire appello, scegliere prima data dopo il periodo di date di appelli gia presenti\n");
					return new Appello(LocalDate.parse(Errordate, formatdata),"Errore, Appello non esistente",Tipo_Appello.DA_STABILIRE);			//controlla se le date da inserire
				}																																	//sono coerenti con quelle di un appello 
				if((datetmp.get(0).getData().isBefore(data[data.length-1])) && (datetmp.get(0).getData().isEqual(data[data.length-1]))) {			//già presente nel sistema															
					System.out.println("Impossibile inserire appello, scegliere ultima data prima del periodo di date di appelli gia presenti\n");
					return new Appello(LocalDate.parse(Errordate, formatdata),"Errore, Appello non esistente",Tipo_Appello.DA_STABILIRE);
				}
				if((datetmp.get(0).getData().isEqual(data[0])) && (datetmp.get(datetmp.size()-1).getData().isEqual(data[data.length-1]))) {																
					System.out.println("Impossibile inserire appello, coincidenza con date di appelli gia presenti\n");
					return new Appello(LocalDate.parse(Errordate, formatdata),"Errore, Appello non esistente",Tipo_Appello.DA_STABILIRE);
				}
			} 
			lista_corsi.get(indTCorso).getAppelli().add(new Appello(data[0],luogo_svolgimento[0],tipo[0]));
			int indNAppello=lista_corsi.get(indTCorso).getAppelli().size()-1;
			lista_corsi.get(indTCorso).getAppelli().get(indNAppello).setId_appello(UUID.nameUUIDFromBytes((corso.nome_corso+data[0].toString()).getBytes()).toString());		//crea id sulla base del nome del
																																												//corso e della prima data
			

			
			for(int i=1;i<data.length;i++) {
					lista_corsi.get(indTCorso).getAppelli().get(indNAppello).addData(data[i],luogo_svolgimento[i],tipo[i]);
				}
			return lista_corsi.get(indTCorso).getAppelli().get(indNAppello);
		}
		return new Appello(LocalDate.parse(Errordate, formatdata),"Errore, Appello non esistente",Tipo_Appello.DA_STABILIRE);
	}
	
	
	public  void stampa_appelli() {
		DateTimeFormatter formatdata = DateTimeFormatter.ofPattern("dd LLLL yyyy");
		for(int i=0;i<lista_corsi.size();i++) {
			Corso stmcorso=lista_corsi.get(i);														//stampa tutte le informazioni a riguardo
			Docente stmdoccorso=stmcorso.getDocente_corso();										//di tutti gli appelli
			ArrayList<Appello> stmappcorso=stmcorso.getAppelli();
			System.out.println("CORSO:"+stmcorso.nome_corso+"\nCFU:"+stmcorso.num_crediti);
			System.out.println("\nIl corso è sostenuto dal docente: "+stmdoccorso.nome_doc+" "+stmdoccorso.cognome_doc);
			if(stmappcorso.size()!=0){	
				System.out.println("\nGli appelli d'esame per il corso sono i seguenti\n");
				for(int j=0;j<stmappcorso.size();j++) {
					ArrayList<Data_Appello> tmpdate=stmappcorso.get(j).getDate_appello();
					System.out.println("Appello N°"+(j+1)+":\n");
					for(int k=0;k<tmpdate.size();k++) {
						System.out.println((k+1)+"° Data\n");
						System.out.println(tmpdate.get(k).getData().format(formatdata));
						System.out.println(" in luogo "+tmpdate.get(k).getLuogo_svolgimento());
						System.out.println(" PROVA "+tmpdate.get(k).getTipo_appello().name()+"\n");
					}
					
				}
			}
		}
	}
	
	public  void prenota_studente(String matricola,String nome,String cognome,Appello app) {				//aggiunge studente
		Studente_Prenotato newstud=new Studente_Prenotato(matricola,nome,cognome);								//a lista dei prenotati	
		app.getStudenti_appello().add(newstud);																	//ad un appello
	}	
	
	public  ArrayList<Studente_Prenotato> ricerca_prenotazioni(Corso corso,Appello app){
		if(lista_corsi.contains(corso)) {
			int indexCorso=lista_corsi.indexOf(corso);
			if(lista_corsi.get(indexCorso).getAppelli().contains(app)) {											//restituisce arraylist
				int indexAppello=lista_corsi.get(indexCorso).getAppelli().indexOf(app);								//con studenti prenotati
				return lista_corsi.get(indexCorso).getAppelli().get(indexAppello).getStudenti_appello();			//all'appello
			}

		}
		return new ArrayList<Studente_Prenotato>();
	}
	

	public  void stampa_prenotazioni(Corso Corso,Appello app) {                              
		 if(lista_corsi.contains(Corso)){
			 int indexCorso=lista_corsi.indexOf(Corso);
			if(lista_corsi.get(indexCorso).getAppelli().contains(app)) {
				System.out.println("\nAppello di "+lista_corsi.get(indexCorso).nome_corso);							//stampa tutte le prenotazioni
				ArrayList<Studente_Prenotato> arraystud=ricerca_prenotazioni(lista_corsi.get(indexCorso),app);		//di un appello
				if(arraystud.size()>0) {																	
				System.out.println("\n Gli Studenti prenotati all'appello sono:\n");
						for(int k=0;k<arraystud.size();k++) {
							Studente_Prenotato tmpstud=arraystud.get(k);
							System.out.println("Studente "+tmpstud.matricola);
							System.out.println(" "+tmpstud.getCognome()+" "+tmpstud.getNome());
					}
				}else {
					System.out.println("\nNon ci sono studenti prenotati all'appello\n");
				}
				return;
			}
			System.out.println("L'appello richiesto non è presente nel sistema");
			return;
		}
		System.out.println("Il corso richiesto non è presente nel sistema");
		return;
	}
	
	
	private  void ordina_date(LocalDate data[], String luogo_svolgimento[], Tipo_Appello tipo[],int pri,int ult) {
		if(pri<ult) {
			int pivot=partiziona(data,luogo_svolgimento,tipo,pri,ult);                  //algoritmo quicksort per ordinare le date
			ordina_date(data,luogo_svolgimento,tipo,pri,pivot-1);						//in input
			ordina_date(data,luogo_svolgimento,tipo,pivot+1,ult);
		}
	}
	
	private  int partiziona(LocalDate data[], String luogo_svolgimento[], Tipo_Appello tipo[],int pri,int ult) {
		LocalDate pivot=data[ult];
		int i=(pri-1);
		LocalDate tmpData;																//funzione per lo swap e il funzionamento
		String tmpString;																//di ordina_date
		Tipo_Appello tmpTipapp;
		
		for(int j=pri;j<ult;j++) {
			if(data[j].isBefore(pivot)) {
				i++;
				tmpData=data[i];
				tmpString=luogo_svolgimento[i];
				tmpTipapp=tipo[i];
				data[i]=data[j];
				luogo_svolgimento[i]=luogo_svolgimento[j];
				tipo[i]=tipo[j];
				data[j]=tmpData;
				luogo_svolgimento[j]=tmpString;
				tipo[j]=tmpTipapp;
			}
		}
		tmpData=data[i+1];
		tmpString=luogo_svolgimento[i+1];
		tmpTipapp=tipo[i+1];
		data[i+1]=data[ult];
		luogo_svolgimento[i+1]=luogo_svolgimento[ult];
		tipo[i+1]=tipo[ult];
		data[ult]=tmpData;
		luogo_svolgimento[ult]=tmpString;
		tipo[ult]=tmpTipapp;		
		return i+1;
	}


	private  String[] raffina_input_luogo(LocalDate data[], String luogo_svolgimento[]) {
		int lungdata=data.length;
		int lungluoghi=luogo_svolgimento.length;										//evita eccezioni come indexoutofbounds
		if(lungluoghi<lungdata) {														//sulla nozione che l'input ad aggiungi appello
			luogo_svolgimento=Arrays.copyOf(luogo_svolgimento, data.length);			//accetterà date senza luoghi ma scarterà
			for(int i=lungluoghi;i<lungdata;i++) {										//luoghi senza date, metterà un luogo fittizio
				luogo_svolgimento[i]="DA STABILIRE";									//che potrà essere modificato con una eventuale
			}																			//funzione di modifica dell'appello
		}
		return luogo_svolgimento;
	}
	private  Tipo_Appello[] raffina_input_tipo(LocalDate data[],Tipo_Appello tipo[]) {
		int lungdata=data.length;														//stessa funzione di raffina input luogo
		int lungtipo=tipo.length;														//ma per i tipi
		if(lungtipo<lungdata) {
			tipo=Arrays.copyOf(tipo, data.length);
			for(int i=lungtipo;i<lungdata;i++) {
				tipo[i]=Tipo_Appello.DA_STABILIRE;			
			}
		}
		return tipo;
	}
}

