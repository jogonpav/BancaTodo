import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Cliente } from 'src/app/cliente/models/cliente';
import { ClienteService } from 'src/app/cliente/services/cliente.service';
import Swal from 'sweetalert2'
import {Location} from '@angular/common'

@Component({
  selector: 'app-nuevo-cliente',
  templateUrl: './nuevo-cliente.component.html',
  styleUrls: ['./nuevo-cliente.component.css']
})
export class NuevoClienteComponent implements OnInit {

  tipoIdentificacion: string;
  numeroIdentificacion: number;
  nombres: string;
  apellidos: string;
  correo: string;
  fechaNacimiento: Date;
  fechaCreacion: Date;


  constructor(private clienteService: ClienteService,
    private toastr: ToastrService,
    private router: Router,
    private location: Location) { }

  ngOnInit(): void {
  }

  onCreate(): void{
    const cliente = new Cliente (this.tipoIdentificacion, this.numeroIdentificacion, this.nombres,
      this.apellidos, this.correo, this.fechaNacimiento, this.fechaCreacion);

    this.clienteService.save(cliente).subscribe(
     respuesta =>{

      if (respuesta.peticionExitosa) {
        if (respuesta.mensaje.charAt(0) == "0") {
          Swal.fire(
            'Success',
            respuesta.mensaje,
            'success'
            );
        }else{
          Swal.fire(
            'Alert',
            respuesta.mensaje,
            'warning'
          );
        }
      }
      this.return();
      }, err =>{
        Swal.fire(
          '¡Error!',
          err.error.mensaje,
          'error'
        );
        this.return();
      }
    )
  }

  return(){
      this.location.back();
  }

}

/*
this.toastr.error(err.error.mensaje,'Fail',{
timeOut:4000, positionClass: 'toast-top-center',
});
this.router.navigate(['/listar']);
*/
