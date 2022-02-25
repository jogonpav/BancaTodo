import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Accounts } from 'src/app/accounts/models/accounts';
import { AccountsService } from 'src/app/accounts/services/accounts.service';
import Swal from 'sweetalert2';
import { Transaction } from '../../models/transaction';
import { TransactionsService } from '../../services/transactions.service';

@Component({
  selector: 'app-retirar-components',
  templateUrl: './retirar-components.component.html',
  styleUrls: ['./retirar-components.component.css']
})
export class RetirarComponentsComponent implements OnInit { account: Accounts;
  clienteId: number;

  tipoCuenta: string;
  numeroCuenta: number;
  estado: string;
  fechaApertura: String;
  saldo: number;


  tipoMovimiento: string;
  descripcion: string;
  saldoInicial: number;
  valor: number;
  saldoFinal: number;
  tipoDebito: string;
  cuentaId: number;
  cuentaDestino: number;
  fecha: Date;

  constructor(
    private accountService: AccountsService,
    private transactionService: TransactionsService,
    private activatedRoute: ActivatedRoute,
    private toastr: ToastrService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const consClienteId = this.activatedRoute.snapshot.params['clienteId'];
    const consCuentaId = this.activatedRoute.snapshot.params['cuentaId'];

    console.log(consClienteId);
    this.clienteId = consClienteId;
    this.cuentaId = consCuentaId;

    this.verDetallesCuenta();
  }

  verDetallesCuenta() {
    this.accountService.detail(this.cuentaId).subscribe(
      (respuesta) => {
        if (respuesta.peticionExitosa) {
          if (respuesta.mensaje.charAt(0) == '0') {
            /* this.toastr.success(respuesta.mensaje, '¡Satisfactorio!', {
              timeOut: 4000,
              positionClass: 'toast-top-center',
            }); */
            this.account = respuesta.datos;
            this.tipoCuenta = this.account.tipoCuenta;
            this.numeroCuenta = this.account.numeroCuenta;
            this.estado = this.account.estado;
            this.saldo = this.account.saldo;

          } else {
            this.toastr.warning(respuesta.mensaje, '¡Info!', {
              timeOut: 4000,
              positionClass: 'toast-top-center',
            });
            this.volver();
          }
        }
      },
      (err) => {
        this.toastr.error(err.error.mensaje, 'Fail', {
          timeOut: 4000,
          positionClass: 'toast-top-center',
        });
        this.volver();
      }
    );
  }

  retirar(): void {
    const transaction = new Transaction(
      this.tipoMovimiento,
      this.descripcion,
      this.saldoInicial,
      this.valor,
      this.saldoFinal,
      this.tipoDebito,
      this.cuentaId,
      this.cuentaDestino,
      this.fecha
    );

    this.transactionService.retirar(this.cuentaId, transaction).subscribe(
      (respuestaTransaction) => {
        if (respuestaTransaction.peticionExitosa) {
          if (respuestaTransaction.mensaje.charAt(0) == '0') {
            Swal.fire(
              'Success!',
              respuestaTransaction.mensaje,
              'success'
            );
            this.router.navigate([
              '/cliente/' +
                this.clienteId +
                '/cuenta/' +
                this.cuentaId +
                '/movimiento',
            ]);
          } else {
            Swal.fire(
              'Warning!',
              respuestaTransaction.mensaje,
              'warning'
            );
          }
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

  volver(): void {
    this.router.navigate(['/cliente/' + this.clienteId + '/detalle']);
  }

}
