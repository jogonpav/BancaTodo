import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
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
          this.toastr.success(respuesta.mensaje, 'Ok', {
            timeOut: 4000,
            positionClass: 'toast-top-center',
          });

        }else{
          this.toastr.warning(respuesta.mensaje, '¡Info!', {
            timeOut: 4000,
            positionClass: 'toast-top-center',
          });
        }
        this.router.navigate(['/cliente/'+this.clienteId +'/detalle']);
      }
      }, err =>{
        this.toastr.error(err.error.mensaje,'Fail2',{
          timeOut:4000, positionClass: 'toast-top-center',
        });
        this.router.navigate(['/cliente/'+this.clienteId +'/detalle']);

      }
    )

  }

}
