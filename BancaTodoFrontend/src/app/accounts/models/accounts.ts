export class Accounts {
  id: number;
  tipoCuenta: string;
  numeroCuenta: number;
  estado: string;
  fechaApertura: String;
  saldo: number;
  clienteId: number;

  constructor(
    id: number,
    tipoCuenta: string,
    numeroCuenta: number,
    estado: string,
    fechaApertura: String,
    saldo: number,
    clienteId: number
  ) {
    this.id = id;
    this.tipoCuenta = tipoCuenta;
    this.numeroCuenta = numeroCuenta;
    this.estado = estado;
    this.fechaApertura = fechaApertura;
    this.saldo = saldo;
    this.clienteId = clienteId;
  }
}
