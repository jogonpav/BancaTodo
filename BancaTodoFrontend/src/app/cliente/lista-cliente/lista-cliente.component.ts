import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Cliente } from 'src/app/models/cliente';
import { ClienteService } from 'src/app/service/cliente.service';

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
      data => {
        this.clientes = data;
      }, err => {console.log(err)}
    )
  }

  borrar(id: number){
    this.clienteService.delete(id).subscribe(
      data=>{
        this.toastr.success('Cliente Eliminado', 'Ok',{
          timeOut:4000,
          positionClass: 'toast-top-center',
        });

        this.cargarClientes();
      },
      err =>{
        this.toastr.error(err.error.mensaje,'Fail',{
          timeOut:4000, positionClass: 'toast-top-center',
        });
      }
    )
  }



}
