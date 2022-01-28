export class Cliente {
  id: number;
  tipoIdentificacion: string;
  numeroIdentificacion: number;
  nombres: string;
  apellidos: string;
  correo: string;
  fechaNacimiento: Date;
  fechaCreacion: Date;

  constructor(
    tipoIdentificacion: string,
    numeroIdentificacion: number,
    nombres: string,
    apellidos: string,
    correo: string,
    fechaNacimiento: Date,
    fechaCreacion: Date
  ) {
    this.tipoIdentificacion = tipoIdentificacion;
    this.numeroIdentificacion = numeroIdentificacion;
    this.nombres = nombres;
    this.apellidos = apellidos;
    this.correo = correo;
    this.fechaNacimiento = fechaNacimiento;
    this.fechaCreacion = fechaCreacion;
  }
}
