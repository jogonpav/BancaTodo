import { transition } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Accounts } from 'src/app/accounts/models/accounts';
import { AccountsService } from 'src/app/accounts/services/accounts.service';
import Swal from 'sweetalert2';
import { Transaction } from '../../models/transaction';
import { TransactionsService } from '../../services/transactions.service';

@Component({
  selector: 'app-transferir-components',
  templateUrl: './transferir-components.component.html',
  styleUrls: ['./transferir-components.component.css']
})
export class TransferirComponentsComponent implements OnInit {

  account: Accounts;
  clienteId: number;
  cuentaId: number;

  //Atributos Detalles de la cuenta
  tipoCuenta: string;
  numeroCuenta: number;
  estado: string;
  fechaApertura: string;
  saldo: number;

  //Datos Entre cuentas
  accountsBetween: Accounts [] = [];
  idAccountBetweenTarget: number;
  valorAccountBetween: number;
  descripcionAccountBetween: string;

  //Datos Otras cuentas
  otherAccounts: Accounts [] = [];
  idOtherAccountTarget: number;
  valorOtherAccount: number;
  descripcionOtherAccount: string;

  //Atributos adicionales de movimiento
  tipoMovimiento: string;
  saldoInicial: number;
  saldoFinal: number;
  tipoDebito: string;
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

    this.clienteId = consClienteId;
    this.cuentaId = consCuentaId;

    this.verDetallesCuenta();

    this.verCuentaCliente();
    this.verOtrasCuentas();
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

  verCuentaCliente() {
    this.accountService.getOtherProductosByCliente(this.cuentaId, this.clienteId).subscribe(
      (respuesta) => {
        if (respuesta.peticionExitosa) {
          if (respuesta.mensaje.charAt(0) == '0') {
            this.accountsBetween = respuesta.datos;
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
 verOtrasCuentas() {
  this.accountService.getProductosOtherClientes(this.clienteId).subscribe(
    (respuesta) => {
      if (respuesta.peticionExitosa) {
        if (respuesta.mensaje.charAt(0) == '0') {
          this.otherAccounts = respuesta.datos;
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

  transferirEntreCuentas(): void {
    const transactionAccountsBetween = new Transaction(
      this.tipoMovimiento,
      this.descripcionAccountBetween,
      this.saldoInicial,
      this.valorAccountBetween,
      this.saldoFinal,
      this.tipoDebito,
      this.cuentaId,
      this.idAccountBetweenTarget,
      this.fecha
    );

    this.transactionService.transferir(this.cuentaId, this.idAccountBetweenTarget, transactionAccountsBetween).subscribe(
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

  transferirOherCeuntas(): void {
  const transactionOtherAccount = new Transaction(
    this.tipoMovimiento,
    this.descripcionOtherAccount,
    this.saldoInicial,
    this.valorOtherAccount,
    this.saldoFinal,
    this.tipoDebito,
    this.cuentaId,
    this.idOtherAccountTarget,
    this.fecha
  );

    this.transactionService.transferir(this.cuentaId, this.idOtherAccountTarget, transactionOtherAccount).subscribe(
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
