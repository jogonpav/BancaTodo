package com.BancaTodo.UserFront.dto;

import java.util.Date;


import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class ClienteDto {
	
	private String tipoIdentificacion;	

	private String numeroIdentificacion;
	

	private String nombres;
	

	private String apellidos;
	

	private String correo;
	

	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;
	

	@Temporal(TemporalType.DATE)
	private Date fechaCreacion;


	public ClienteDto() {
	}


	public ClienteDto(String tipoIdentificacion, String numeroIdentificacion, String nombres, String apellidos,
			String correo, Date fechaNacimiento, Date fechaCreacion) {
		this.tipoIdentificacion = tipoIdentificacion;
		this.numeroIdentificacion = numeroIdentificacion;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.correo = correo;
		this.fechaNacimiento = fechaNacimiento;
		this.fechaCreacion = fechaCreacion;
	}


	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}


	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}


	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}


	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}


	public String getNombres() {
		return nombres;
	}


	public void setNombres(String nombres) {
		this.nombres = nombres;
	}


	public String getApellidos() {
		return apellidos;
	}


	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}


	public String getCorreo() {
		return correo;
	}


	public void setCorreo(String correo) {
		this.correo = correo;
	}


	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}


	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}


	public Date getFechaCreacion() {
		return fechaCreacion;
	}


	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	

}
