import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Cliente } from 'src/app/models/cliente';
import { ClienteService } from 'src/app/service/cliente.service';

@Component({
  selector: 'app-nuevo-cliente',
  templateUrl: './nuevo-cliente.component.html',
  styleUrls: ['./nuevo-cliente.component.css']
})
export class NuevoClienteComponent implements OnInit {

  tipoIdentificacion: string;
  numeroIdentificacion: number;
  nombres: string;
  apellidos: string;
  correo: string;
  fechaNacimiento: Date;
  fechaCreacion: Date;

  constructor(private clienteService: ClienteService,
    private toastr: ToastrService,
    private router: Router //para redirigir si hay error
    ) { }

  ngOnInit(): void {
  }

  onCreate(): void{
    const cliente = new Cliente (this.tipoIdentificacion, this.numeroIdentificacion, this.nombres,
      this.apellidos, this.correo, this.fechaNacimiento, this.fechaCreacion);

    this.clienteService.save(cliente).subscribe(
      data =>{
        this.toastr.success('Producto Creado', 'Ok',{
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

  }

}
