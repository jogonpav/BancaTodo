import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { GlobalService } from 'src/app/shared/services/global.service';
import { UserDto } from '../../models/user-dto';
import { UserService } from '../../services/user.service';
import {Location} from '@angular/common';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {

  isLogged = true;

  userDto: UserDto = new UserDto;
  confirmNewPassword: string;

  constructor(private userService: UserService,
    private activatedRoute: ActivatedRoute,
    private toastr: ToastrService,
    private router: Router,
    private globalService: GlobalService,
    private location: Location
  ) { }

  ngOnInit(): void {
    this.userDto.user.userName = this.globalService.user.userName;
  }

  onUpdate(): void {
    this.userService.resetPassword(this.userDto).subscribe(
      (respuesta) => {
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

      }
    );
  }

 logout(): void {

    this.globalService = new GlobalService();

    this.isLogged = false;
    this.router.navigate(['/listar'])

  } 

  return(){
    this.location.back();
}

}

 /* if (err.error.mensaje.charAt(0) == "1") {
          this.toastr.error(err.error.mensaje, 'Ok', {
            timeOut: 4000,
            positionClass: 'toast-top-center',
          });
        }else{
          this.toastr.error(err.error.mensaje, '¡Info!', {
            timeOut: 4000,
            positionClass: 'toast-top-center',
          });
          //this.router.navigate(['/listar']);
          this.return();
        } */
