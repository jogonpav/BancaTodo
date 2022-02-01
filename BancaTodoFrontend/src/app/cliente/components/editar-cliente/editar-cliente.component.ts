import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Cliente } from 'src/app/cliente/models/cliente';
import { ClienteService } from 'src/app/cliente/services/cliente.service';

@Component({
  selector: 'app-editar-cliente',
  templateUrl: './editar-cliente.component.html',
  styleUrls: ['./editar-cliente.component.css'],
})
export class EditarClienteComponent implements OnInit {

  cliente: Cliente;

  dataIdentificacion = ['Cédula Ciudadanía','Pasaporte','Cédula Extranjera']
  valorSeleccion: string;

  comparacion(item1:any, item2:any) {
    return item1 && item2 ? item1 === item2 : item1 === item2;
  }

  constructor(
    private clienteService: ClienteService,
    private activatedRoute: ActivatedRoute,
    private toastr: ToastrService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = this.activatedRoute.snapshot.params['id'];
    this.clienteService.detail(id).subscribe(
      (respuesta) => {
        if (respuesta.peticionExitosa) {
          this.cliente = respuesta.datos;
          this.valorSeleccion = this.cliente.tipoIdentificacion;
        }
      },
      (err) => {
        this.toastr.error(err.error.mensaje, 'Fail', {
          timeOut: 4000,
          positionClass: 'toast-top-center',
        });
        this.router.navigate(['/']);
      }
    );


  }

  onUpdate(): void {
    const id = this.activatedRoute.snapshot.params['id'];
    this.clienteService.update(id, this.cliente).subscribe(
      (respuesta) => {
        if (respuesta.peticionExitosa) {
          console.log(respuesta.mensaje.charAt(0));
          if (respuesta.mensaje.charAt(0) == "0") {
            this.toastr.success(respuesta.mensaje, 'Ok', {
              timeOut: 4000,
              positionClass: 'toast-top-center',
            });
          }else{
            this.toastr.success(respuesta.mensaje, '¡Info!', {
              timeOut: 4000,
              positionClass: 'toast-top-center',
            });

          }
        }
        this.router.navigate(['/']);
      },
      (err) => {
        this.toastr.error(err.error.mensaje, 'Fail', {
          timeOut: 4000,
          positionClass: 'toast-top-center',
        });
        this.router.navigate(['/']);
      }
    );
  }
  /* onUpdate(): void{
    const id = this.activatedRoute.snapshot.params['id'];
    this.clienteService.update(id, this.cliente).subscribe(
      data =>{
        this.toastr.success('Producto Actualizado', 'Ok',{
          timeOut:4000,
          positionClass: 'toast-top-center',
        });
        this.router.navigate(['/']);

      }, err =>{
        this.toastr.error(err.error.mensaje,'Fail',{
          timeOut:4000, positionClass: 'toast-top-center',
        });
        this.router.navigate(['/']);

      }
    )

} */
}
