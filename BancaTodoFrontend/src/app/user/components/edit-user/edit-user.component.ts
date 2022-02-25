import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { GlobalService } from 'src/app/shared/services/global.service';
import { User } from '../../models/user';
import { UserService } from '../../services/user.service';
import {Location} from '@angular/common'
import Swal from 'sweetalert2';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {

  user: User;

  constructor( private userService: UserService,
    private activatedRoute: ActivatedRoute,
    private toastr: ToastrService,
    private router: Router,
    private globalService: GlobalService,
    private location: Location
    ) { }

  ngOnInit(): void {

    this.userService.detail(this.globalService.user.userName).subscribe(
      (respuesta) => {
        if (respuesta.peticionExitosa) {
          this.user = respuesta.datos;
          console.log(this.user);
        }
      },
      (err) => {
        this.toastr.error(err.error.mensaje, 'Fail', {
          timeOut: 4000,
          positionClass: 'toast-top-center',
        });
        //this.router.navigate(['/listar']);
        this.return();
      }
    );

  }

  onUpdate(): void {
    this.userService.update(this.user).subscribe(
      (respuesta) => {
        if (respuesta.peticionExitosa) {
          console.log(respuesta.mensaje.charAt(0));
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
        }
        //this.router.navigate(['/listar']);
        this.return();
      },
      (err) => {
        Swal.fire(
          'Error!',
          err.error.mensaje,
          'error'
        );
        //this.router.navigate(['/listar']);
        this.return();
      }
    );
  }

  return(){
    this.location.back();
}

}
