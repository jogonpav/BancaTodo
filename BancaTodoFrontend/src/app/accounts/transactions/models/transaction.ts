export class Transaction {
  id: number;
  tipoMovimiento: string;
  descripcion: string;
  saldoInicial: number;
  valor:number;
  saldoFinal: number;
  tipoDebito: string;
  cuentaId: number;
  cuentaDestino: number;
  fecha: Date;

  constructor(

    tipoMovimiento: string,
    descripcion: string,
    saldoInicial: number,
    valor:number,
    saldoFinal: number,
    tipoDebito: string,
    cuentaId: number,
    cuentaDestino: number,
    fecha: Date
  ) {

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
}
