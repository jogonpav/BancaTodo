import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Transactions } from '../../models/transactions';
import { TransactionsService } from '../../services/transactions.service';

@Component({
  selector: 'app-list-transactions',
  templateUrl: './list-transactions.component.html',
  styleUrls: ['./list-transactions.component.css'],
})
export class ListTransactionsComponent implements OnInit {
  transactions: Transactions[] = [];

  clienteId:number;


  constructor(
    private transactionService: TransactionsService,
    private activatedRoute: ActivatedRoute,
    private toastr: ToastrService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const cuentaId = this.activatedRoute.snapshot.params['id'];
    this.clienteId = this.activatedRoute.snapshot.params['clienteId'];

    this.cargarMovimientos(cuentaId);
  }

  cargarMovimientos(cuentaId: number) {
    this.transactionService.listarMovimientos(cuentaId).subscribe(
      (respuesta) => {
        if (respuesta.peticionExitosa) {
          this.transactions = respuesta.datos;
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

  volver(): void{
    this.router.navigate(['/']);
  }

}
