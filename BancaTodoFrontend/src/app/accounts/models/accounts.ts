export class Accounts {
  id: number;
  tipoCuenta: string;
  numeroCuenta: number;
  estado: string;
  fechaApertura: String;
  saldo: number;
  clienteId: number;

  constructor(
    tipoCuenta: string,
    numeroCuenta: number,
    estado: string,
    fechaApertura: String,
    saldo: number,
    clienteId: number
  ) {
    this.tipoCuenta = tipoCuenta;
    this.numeroCuenta = numeroCuenta;
    this.estado = estado;
    this.fechaApertura = fechaApertura;
    this.saldo = saldo;
    this.clienteId = clienteId;
  }
}
