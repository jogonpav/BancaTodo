import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { ToastrService } from 'ngx-toastr';
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
  }
  cargarAccounts(id: number): void {
    this.accountsServices.listarProductos(id).subscribe(
      (respuesta) => {
        if (respuesta.peticionExitosa) {
          this.accounts = respuesta.datos;
          console.log(respuesta.datos);
        }
      },
      (err) => {
        this.toastr.error(err.error.mensaje, 'Fail', {
          timeOut: 4000,
          positionClass: 'toast-top-center',
        });
      }
    );
  }

  consignar(clienteId: number, account: Accounts): void {
    if (!(account.estado == 'cancelado')) {
      this.router.navigate(['/cliente/'+clienteId+'/cuenta/'+account.id+'/consignar']);
    } else {
      this.toastr.warning('La cuenta se encuentra cancelada', '¡info!', {
        timeOut: 4000,
        positionClass: 'toast-top-center',
      });
    }
  }

  retirar(clienteId: number, account: Accounts): void {
    switch(account.estado){
      case 'cancelado':
        this.toastr.warning('La cuenta se encuentra cancelada', '¡info!', {
          timeOut: 4000,
          positionClass: 'toast-top-center',
        });
        break;
      case "inactivo":
        this.toastr.warning('La cuenta se encuentra inactiva', '¡info!', {
          timeOut: 4000,
          positionClass: 'toast-top-center',
        });
        break;
      default:
        this.router.navigate(['/cliente/'+clienteId+'/cuenta/'+account.id+'/retirar']);
    }
  }

  transferir(clienteId: number, account: Accounts): void {
    switch(account.estado){
      case 'cancelado':
        this.toastr.warning('La cuenta se encuentra cancelada', '¡info!', {
          timeOut: 4000,
          positionClass: 'toast-top-center',
        });
        break;
      case "inactivo":
        this.toastr.warning('La cuenta se encuentra inactiva', '¡info!', {
          timeOut: 4000,
          positionClass: 'toast-top-center',
        });
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
          this.toastr.success(respuesta.mensaje, '¡Satisfactoria', {
            timeOut: 4000,
            positionClass: 'toast-top-center',
          });
          this.cargarAccounts(this.clienteId);
        }else{
          this.toastr.warning(respuesta.mensaje, '¡info', {
            timeOut: 4000,
            positionClass: 'toast-top-center',
          });

        }

      }
    },
    (err) => {
      this.toastr.error(err.error.mensaje, 'Fail', {
        timeOut: 4000,
        positionClass: 'toast-top-center',
      });
    }
  );
}

desactivar(clienteId: number, accounts: Accounts){
  this.accountsServices.inactivate(clienteId, accounts).subscribe(
    (respuesta) => {
      if (respuesta.peticionExitosa) {
        this.toastr.success(respuesta.mensaje, '¡Satisfactoria', {
          timeOut: 4000,
          positionClass: 'toast-top-center',
        });
        this.cargarAccounts(this.clienteId);
      }
    },
    (err) => {
      this.toastr.error(err.error.mensaje, 'Fail', {
        timeOut: 4000,
        positionClass: 'toast-top-center',
      });
    }
  );
}

cancelar(clienteId: number, accounts: Accounts){
  this.accountsServices.cancel(clienteId, accounts).subscribe(
    (respuesta) => {
      if (respuesta.peticionExitosa) {
        if (respuesta.mensaje.charAt(0) == '0') {
          this.toastr.success(respuesta.mensaje, '¡Satisfactoria', {
            timeOut: 4000,
            positionClass: 'toast-top-center',
          });
          this.cargarAccounts(this.clienteId);
        }else{
          this.toastr.warning(respuesta.mensaje, '¡info', {
            timeOut: 4000,
            positionClass: 'toast-top-center',
          });

        }

      }
    },
    (err) => {
      this.toastr.error(err.error.mensaje, 'Fail', {
        timeOut: 4000,
        positionClass: 'toast-top-center',
      });
    }
  );
}

}
