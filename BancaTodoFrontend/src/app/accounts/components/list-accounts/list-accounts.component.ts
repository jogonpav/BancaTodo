import { ThisReceiver } from '@angular/compiler';
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

  
}
