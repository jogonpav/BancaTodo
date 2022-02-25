import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Cliente } from 'src/app/cliente/models/cliente';
import { ClienteService } from 'src/app/cliente/services/cliente.service';

@Component({
  selector: 'app-detalle-cliente',
  templateUrl: './detalle-cliente.component.html',
  styleUrls: ['./detalle-cliente.component.css'],
})
export class DetalleClienteComponent implements OnInit {
  cliente: Cliente;

  constructor(
    private clienteService: ClienteService,
    private activatedRoute: ActivatedRoute,
    private toastr: ToastrService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = this.activatedRoute.snapshot.params['id'];

    this.loadDetails(id);

  }

  loadDetails(id: any): void {
    this.clienteService.detail(id).subscribe(
      (respuesta) => {
        if (respuesta.peticionExitosa) {
          if (!(respuesta.mensaje.charAt(0) == '0')) {
            this.toastr.warning(respuesta.mensaje, '¡Info!', {
              timeOut: 4000,
              positionClass: 'toast-top-center',
            });
          }
        }
        this.cliente = respuesta.datos;
        localStorage.setItem('customerName', this.cliente.nombres + ' ' + this.cliente.apellidos)
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

  volver(): void {
    this.router.navigate(['/listar']);
  }
}
//otra manera de recibir datos de otro componente
/*
this.activatedRoute.params.subscribe((params) => {
  console.log(params);
});
*/
