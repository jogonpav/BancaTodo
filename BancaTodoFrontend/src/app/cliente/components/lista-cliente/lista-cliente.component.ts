import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Cliente } from 'src/app/cliente/models/cliente';
import { ClienteService } from 'src/app/cliente/services/cliente.service';


@Component({
  selector: 'app-lista-cliente',
  templateUrl: './lista-cliente.component.html',
  styleUrls: ['./lista-cliente.component.css']
})
export class ListaClienteComponent implements OnInit {

  clientes: Cliente[] =[];

  constructor(private clienteService: ClienteService,
    private toastr: ToastrService) { }

  ngOnInit(): void {
    this.cargarClientes();
  }

  cargarClientes(): void{
    this.clienteService.lista().subscribe(
      respuesta => {
        if (respuesta.peticionExitosa){
          this.clientes = respuesta.datos;
        }

      }, err => {console.log(err.error.mensaje)}
    )
  }


  borrar(id: number){
    this.clienteService.delete(id).subscribe(
      respuesta=>{
        if (respuesta.peticionExitosa) {
          console.log(respuesta.mensaje.charAt(0));
          if (respuesta.mensaje.charAt(0) == "0") {
            this.toastr.success(respuesta.mensaje, '¡Satisfactoria!', {
              timeOut: 4000,
              positionClass: 'toast-top-center',
            });
            this.cargarClientes();
          }else{
            this.toastr.warning(respuesta.mensaje, '¡Info!', {
              timeOut: 4000,
              positionClass: 'toast-top-center',
            });
          }
        }
      },
      err =>{
        this.toastr.error(err.error.mensaje,'Fail',{
          timeOut:4000, positionClass: 'toast-top-center',
        });
      }
    )
  }
}
