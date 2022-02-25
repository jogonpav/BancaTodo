import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { GlobalService } from 'src/app/shared/services/global.service';
import { User } from '../../models/user';
import { UserService } from '../../services/user.service';
import {Location} from '@angular/common'
import Swal from 'sweetalert2';


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
      this.return();
        //this.router.navigate(['/login']);
      }, err =>{

        Swal.fire(
          'Error!',
          err.error.mensaje,
          'error'
        );
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
