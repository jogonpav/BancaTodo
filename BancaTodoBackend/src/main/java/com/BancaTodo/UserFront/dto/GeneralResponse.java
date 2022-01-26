package com.BancaTodo.UserFront.dto; 

public class GeneralResponse<T> {
	
	private T datos;
	private String mensaje;
	private boolean peticionExitosa;
	private int codigoError;


	public T getDatos() {
		return datos;
	}

	public void setDatos(T datos) {
		this.datos = datos;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	

	public boolean isPeticionExitosa() {
		return peticionExitosa;
	}

	public void setPeticionExitosa(boolean existeError) {
		this.peticionExitosa = existeError;
	}

}
