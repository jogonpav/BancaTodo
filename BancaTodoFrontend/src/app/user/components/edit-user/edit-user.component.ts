import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { GlobalService } from 'src/app/shared/services/global.service';
import { User } from '../../models/user';
import { UserService } from '../../services/user.service';

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
    private globalService: GlobalService
    ) { }

  ngOnInit(): void {

    console.log(this.globalService.user.userName);
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
        this.router.navigate(['/listar']);
      }
    );

  }

  onUpdate(): void {
    this.userService.update(this.user).subscribe(
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
        this.router.navigate(['/listar']);
      },
      (err) => {
        this.toastr.error(err.error.mensaje, 'Fail', {
          timeOut: 4000,
          positionClass: 'toast-top-center',
        });
        this.router.navigate(['/listar']);
      }
    );
  }

}
