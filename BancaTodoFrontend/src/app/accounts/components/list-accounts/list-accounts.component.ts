import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { ToastrService } from 'ngx-toastr';
import Swal from 'sweetalert2';
import { Accounts } from '../../models/accounts';
import { AccountsService } from '../../services/accounts.service';


@Component({
  selector: 'app-list-accounts',
  templateUrl: './list-accounts.component.html',
  styleUrls: ['./list-accounts.component.css'],

})
export class ListAccountsComponent implements OnInit {
  clienteId: number;
  accounts: Accounts[] = [];
  accountSelected: Accounts;
  customerName: any;





  constructor(
    private accountsServices: AccountsService,
    private activatedRoute: ActivatedRoute,
    private toastr: ToastrService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = this.activatedRoute.snapshot.params['id'];
    this.clienteId = id;
    this.cargarAccounts(id);
    this.customerName = localStorage.getItem('customerName');

  }





  cargarAccounts(id: number): void {
    this.accountsServices.listarProductos(id).subscribe(
      (respuesta) => {
        if (respuesta.peticionExitosa) {
          this.accounts = respuesta.datos;
        }
      },
      (err) => {
        Swal.fire(
          'Error!',
          err.error.mensaje,
          'error'
        );
      }
    );
  }

  consignar(clienteId: number, account: Accounts): void {
    if (!(account.estado == 'cancelado')) {
      this.router.navigate(['/cliente/'+clienteId+'/cuenta/'+account.id+'/consignar']);
    } else {
      Swal.fire(
        'Warning!',
        'La cuenta se encuentra cancelada',
        'warning'
      );
    }
  }

  retirar(clienteId: number, account: Accounts): void {
    switch(account.estado){
      case 'cancelado':
        Swal.fire(
          'Warning!',
          'La cuenta se encuentra cancelada',
          'warning'
        );
        break;
      case "inactivo":
        Swal.fire(
          'Warning!',
          'La cuenta se encuentra inactiva',
          'warning'
        );
        break;
      default:
        this.router.navigate(['/cliente/'+clienteId+'/cuenta/'+account.id+'/retirar']);
    }
  }

  transferir(clienteId: number, account: Accounts): void {
    switch(account.estado){
      case 'cancelado':
        Swal.fire(
          'Warning!',
          'La cuenta se encuentra cancelada',
          'warning'
        );
        break;
      case "inactivo":
        Swal.fire(
          'Warning!',
          'La cuenta se encuentra inactiva',
          'warning'
        );
        break;
      default:
        this.router.navigate(['/cliente/'+clienteId+'/cuenta/'+account.id+'/transferencia']);
    }
  }

activar(clienteId: number, accounts: Accounts){
  this.accountsServices.activate(clienteId, accounts).subscribe(
    (respuesta) => {
      if (respuesta.peticionExitosa) {
        if (respuesta.mensaje.charAt(0) == '0') {
          Swal.fire(
            'Success!',
            respuesta.mensaje,
            'success'
          );
          this.cargarAccounts(this.clienteId);
        }else{
          Swal.fire(
            'Warning!',
            respuesta.mensaje,
            'warning'
          );
        }
      }
    },
    (err) => {
      Swal.fire(
        '¡Error!',
        err.error.mensaje,
        'error'
      );
    }
  );
}

desactivar(clienteId: number, accounts: Accounts){
  this.accountsServices.inactivate(clienteId, accounts).subscribe(
    (respuesta) => {
      if (respuesta.peticionExitosa) {
        Swal.fire(
          'Success!',
          respuesta.mensaje,
          'success'
        );
        this.cargarAccounts(this.clienteId);
      }
    },
    (err) => {
      Swal.fire(
        '¡Error!',
        err.error.mensaje,
        'error'
      );
    }
  );
}

cancelar(clienteId: number, accounts: Accounts){

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
      this.accountsServices.cancel(clienteId, accounts).subscribe(
        (respuesta) => {
          if (respuesta.peticionExitosa) {
            if (respuesta.mensaje.charAt(0) == '0') {
              Swal.fire(
                'Success!',
                respuesta.mensaje,
                'success'
              );
              this.cargarAccounts(this.clienteId);
            }else{
              Swal.fire(
                'Warning!',
                respuesta.mensaje,
                'warning'
              );
            }
          }
        },
        (err) => {
          Swal.fire(
            '¡Error!',
            err.error.mensaje,
            'error'
          );
        }
      );
    }
  })



}

@ViewChild("mycheckbox") mycheckbox: { nativeElement: { checked: any; }; };
isChecked(clienteId: number, accounts: Accounts){
   //Check the Console for the Output Weather checkbox is checked or not
   console.log(this.mycheckbox.nativeElement.checked);

   if(accounts.estado==='disabled'){
     this.activar(clienteId, accounts);
     //this.mycheckbox.nativeElement.checked

   }else{
    this.desactivar(clienteId, accounts);
   }
 }

}
