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
@Table(name="movimientos")
public class MovimientoEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="tipo_movimiento")
	private String tipoMovimiento;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "saldo_inicial")	
	private double saldoInicial;
	
	@Column(name = "valor")
	private double valor;
	
	@Column(name = "saldo_final")	
	private double saldoFinal;
	
	@Column(name = "tipo_debito")
	private String tipoDebito;
	
	@Column(name = "cuenta_id")
	private long cuentaId;
	
	@Column(name="cuenta_destino")
	private long cuentaDestino;

	@Column(name = "fecha")
	private LocalDate fecha;

	public MovimientoEntity() {
	}

	public MovimientoEntity(String tipoMovimiento, String descripcion, double saldoInicial, double valor, double saldoFinal,
			String tipoDebito, long cuentaId, long cuentaDestino, LocalDate fecha) {
		this.tipoMovimiento = tipoMovimiento;
		this.descripcion = descripcion;
		this.saldoInicial = saldoInicial;
		this.valor = valor;
		this.saldoFinal = saldoFinal;
		this.tipoDebito = tipoDebito;
		this.cuentaId = cuentaId;
		this.cuentaDestino = cuentaDestino;
		this.fecha = fecha;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(double saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public double getSaldoFinal() {
		return saldoFinal;
	}

	public void setSaldoFinal(double saldoFinal) {
		this.saldoFinal = saldoFinal;
	}

	public String getTipoDebito() {
		return tipoDebito;
	}

	public void setTipoDebito(String tipoDebito) {
		this.tipoDebito = tipoDebito;
	}

	public long getCuentaId() {
		return cuentaId;
	}

	public void setCuentaId(long cuentaId) {
		this.cuentaId = cuentaId;
	}

	public long getCuentaDestino() {
		return cuentaDestino;
	}

	public void setCuentaDestino(long cuentaDestino) {
		this.cuentaDestino = cuentaDestino;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}	
	
	
	

}
