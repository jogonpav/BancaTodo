import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { GlobalService } from 'src/app/shared/services/global.service';
import { UserDto } from '../../models/user-dto';
import { UserService } from '../../services/user.service';

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
    private globalService: GlobalService
  ) { }

  ngOnInit(): void {
    this.userDto.user.userName = this.globalService.user.userName;
  }

  onUpdate(): void {
    this.userService.resetPassword(this.userDto).subscribe(
      (respuesta) => {
        if (respuesta.peticionExitosa) {
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
        this.router.navigate(['/listar']);
      },
      (err) => {
        console.log(err.error.mensaje.charAt(0));
        if (err.error.mensaje.charAt(0) == "1") {
          this.toastr.error(err.error.mensaje, 'Ok', {
            timeOut: 4000,
            positionClass: 'toast-top-center',
          });
        }else{
          this.toastr.error(err.error.mensaje, '¡Info!', {
            timeOut: 4000,
            positionClass: 'toast-top-center',
          });
          this.router.navigate(['/listar']);
        }
      }
    );
  }

  logout(): void {

    this.globalService = new GlobalService();

    this.isLogged = false;
    this.router.navigate(['/listar'])

  }

}
