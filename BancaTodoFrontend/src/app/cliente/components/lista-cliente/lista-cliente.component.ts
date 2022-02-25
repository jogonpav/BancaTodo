import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Cliente } from 'src/app/cliente/models/cliente';
import { ClienteService } from 'src/app/cliente/services/cliente.service';
import Swal from 'sweetalert2';


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

    //console.log(localStorage.getItem('tutorial'));
  }

  cargarClientes(): void{

    this.clienteService.lista().subscribe(
      respuesta => {
        if (respuesta.peticionExitosa){
          this.clientes = respuesta.datos;
          //localStorage.setItem('tutorial', 'Como utilizar el LocalStorage en Angular');
        }

      }, err => {
        Swal.fire(
          'Error!',
          err.error.mensaje,
          'error'
        );
      }
    )
  }

  borrar(id: number){


    Swal.fire({
      title: 'Are you sure?',
      text: "You won't be able to revert this!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
      if (result.isConfirmed) {
        this.clienteService.delete(id).subscribe(
          respuesta=>{
            if (respuesta.peticionExitosa) {

              if (respuesta.mensaje.charAt(0) == "0") {
                Swal.fire(
                  'Deleted!',
                  'User has been deleted.',
                  'success'
                );
                this.cargarClientes();
              }else{
                Swal.fire({
                  icon: 'error',
                  title: '¡Info!',
                  text: respuesta.mensaje,
                })
              }
            }
          },
          err =>{
            Swal.fire({
              icon: 'error',
              title: '¡Error!',
              text: err.error.mensaje,
            })

          }
        )
      }
    });
  }
}
