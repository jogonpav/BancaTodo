import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import Swal from 'sweetalert2';
import { Accounts } from '../../models/accounts';
import { AccountsService } from '../../services/accounts.service';

@Component({
  selector: 'app-create-accounts',
  templateUrl: './create-accounts.component.html',
  styleUrls: ['./create-accounts.component.css']
})
export class CreateAccountsComponent implements OnInit {

  tipoCuenta: string;
  numeroCuenta: number;
  estado: string;
  fechaApertura: String;
  saldo: number;
  clienteId: number;



  constructor(private cuentaService: AccountsService,
    private toastr: ToastrService,
    private activatedRoute: ActivatedRoute,
    private router: Router //para redirigir si hay error
    ) { }



  ngOnInit(): void {
    this.clienteId = this.activatedRoute.snapshot.params['clienteId'];
  }

  onCreate(): void{
    this.saldo =0;


    const cuenta = new Accounts ( this.tipoCuenta,
      this.numeroCuenta,
      this.estado,
      this.fechaApertura,
      this.saldo,
      this.clienteId);


    this.cuentaService.add(this.clienteId,cuenta).subscribe(
     respuesta =>{
      if (respuesta.peticionExitosa) {
        if (respuesta.mensaje.charAt(0) == "0") {
          Swal.fire(
            'Success!',
            respuesta.mensaje,
            'success'
          );
        }else{
          Swal.fire(
            'Warning!',
            respuesta.mensaje,
            'warning'
          );
        }
        this.router.navigate(['/cliente/'+this.clienteId +'/detalle']);
      }
      }, err =>{
        Swal.fire(
          'Error!',
          err.error.mensaje,
          'error'
        );
        this.router.navigate(['/cliente/'+this.clienteId +'/detalle']);
      }
    )

  }

}
