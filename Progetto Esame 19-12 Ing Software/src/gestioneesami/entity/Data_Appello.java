package gestioneesami.entity;

import java.time.LocalDate;

public class Data_Appello {

	private LocalDate data;
	private String luogo_svolgimento;
	private Tipo_Appello tipo_appello;
	public Data_Appello(LocalDate data, String luogo_svolgimento, Tipo_Appello tipo) {
		this.data = data;
		this.luogo_svolgimento = luogo_svolgimento;
		this.tipo_appello = tipo;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public String getLuogo_svolgimento() {
		return luogo_svolgimento;
	}
	public void setLuogo_svolgimento(String luogo_svolgimento) {
		this.luogo_svolgimento = luogo_svolgimento;
	}
	public Tipo_Appello getTipo_appello() {
		return tipo_appello;
	}
	public void setTipo_appello(Tipo_Appello tipo_appello) {
		this.tipo_appello = tipo_appello;
	}

}