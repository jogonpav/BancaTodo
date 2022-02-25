package com.BankFor.UserBack.entity;

import java.time.LocalDate;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="productos")
public class ProductoEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="tipo")
	private String tipoCuenta;
	
	@Column(name="numeroCuenta")
	private String numeroCuenta;
	
	
	@Column(name="estado")
	private String estado;
	
	@Column(name = "fecha_apertura")
	//@Temporal(TemporalType.DATE)
	private LocalDate fechaApertura;
	
	
	@Column(name="saldo")
	private Double saldo;
	
	@Column(name = "cliente_id")
	private Long clienteId;

	public ProductoEntity() {
	}
	
	

	public ProductoEntity(String tipoCuenta, String numeroCuenta, String estado, LocalDate fechaApertura, Double saldo,
			Long clienteId) {
		
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

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
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

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}
	
	
	

}
