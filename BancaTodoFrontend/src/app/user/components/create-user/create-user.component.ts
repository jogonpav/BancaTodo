import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { User } from '../../models/user';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css']
})
export class CreateUserComponent implements OnInit {

  userName: string;
  password: string;
  jwt: string;
  lastName: string;
  firstName: string;

  constructor(private userService: UserService,
    private toastr: ToastrService,
    private router: Router //para redirigir si hay error
  ) { }

  ngOnInit(): void {
  }
/* this.userName, this.password, this.jwt,
      this.lastName, this.firstName */
  onCreate(): void{
    const user= new User ();

    user.firstName = this.firstName;
    user.lastName = this.lastName;
    user.userName = this.userName;
    user.password = this.password;

    this.userService.save(user).subscribe(
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
      }

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
