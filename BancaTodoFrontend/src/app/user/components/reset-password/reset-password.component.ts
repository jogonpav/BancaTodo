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

  userDto: UserDto = new UserDto;
  confirmNewPassword: string;

  constructor(private userService: UserService,
    private activatedRoute: ActivatedRoute,
    private toastr: ToastrService,
    private router: Router,
    private globalService: GlobalService
  ) { }

  ngOnInit(): void {
    console.log(this.globalService.user.userName);
    this.userDto.user.userName = this.globalService.user.userName;

    console.log('username: '+ this.globalService.user.userName);
  }

  onUpdate(): void {
    /* this.userService.update(this.userDto).subscribe(
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
    ); */
  }

}
