package com.BancaTodo.UserFront.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="productos")
public class ProductoEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="tipo")
	private String tipoCuenta;
	
	@Column(name="numero")
	private int numeroCuenta;
	
	
	@Column(name="estado")
	private String estado;
	
	@Column(name = "fecha_apertura")
	//@Temporal(TemporalType.DATE)
	private LocalDate fechaApertura;
	
	
	@Column(name="saldo")
	private double saldo;
	
	@Column(name = "cliente_id")
	private long clienteId;

	public ProductoEntity() {
	}
	
	

	public ProductoEntity(Long id, String tipoCuenta, int numeroCuenta, String estado, LocalDate fechaApertura, double saldo,
			long clienteId) {
		this.id = id;
		this.tipoCuenta = tipoCuenta;
		this.numeroCuenta = numeroCuenta;
		this.estado = estado;
		this.fechaApertura = fechaApertura;
		this.saldo = saldo;
		this.clienteId = clienteId;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public int getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(int numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public LocalDate getFechaApertura() {
		return fechaApertura;
	}

	public void setFechaApertura(LocalDate fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public long getClienteId() {
		return clienteId;
	}

	public void setClienteId(long clienteId) {
		this.clienteId = clienteId;
	}
	
	
	

}
