import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { GlobalService } from 'src/app/shared/services/global.service';
import { User } from '../../models/user';
import { UserService } from '../../services/user.service';
import {Location} from '@angular/common'


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
    private router: Router,
    private globalService: GlobalService,
    private location: Location

  ) { }

  ngOnInit(): void {
  }

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
      this.return();
        //this.router.navigate(['/login']);
      }, err =>{
        this.toastr.error(err.error.mensaje,'Fail',{
          timeOut:4000, positionClass: 'toast-top-center',
        });
        //this.router.navigate(['/listar']);
        this.return();

      }
    )

  }

  return(){
    if (this.globalService.user.jwt != ""){
      //this.router.navigate(['/listar']);
      this.location.back();
    }else{
      this.router.navigate(['/']);
      window.location.reload();
    }
  }

}
