import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { GlobalService } from 'src/app/shared/services/global.service';
import { User } from 'src/app/user/models/user';
import { UserService } from 'src/app/user/services/user.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  @Output() login: EventEmitter<User> = new EventEmitter();

  form: FormGroup;

  constructor( private userService: UserService,
    private formBuilder: FormBuilder,
    private toastr: ToastrService,
    private router: Router,
    private globalService: GlobalService) { }

  ngOnInit(): void {
    this.initForm();
  }

    initForm() {
      this.form = this.formBuilder.group({
        userName: new FormControl(),
        password: new FormControl()
      });
    }

    signIn() {
      const userName = this.form.controls['userName'].value;
      const password = this.form.controls['password'].value;

      if (userName && password) {
        const user: User = new User();
        user.userName = userName;
        user.password = password;

        this.userService.login(user).subscribe( respuesta => {
          if (respuesta.peticionExitosa) {

            this.login.emit(respuesta.datos);
            this.toastr.success(respuesta.mensaje,'OK',{
              timeOut:4000, positionClass: 'toast-top-center',
            });
            //const token: string =  respuesta.datos.jwt;
            this.globalService.user = respuesta.datos;


            //console.log(token);
            this.router.navigate(['/listar']);
          }
        }, err => {
          this.toastr.error(err.error.mensaje,'Fail',{
            timeOut:4000, positionClass: 'toast-top-center',
          });
        });
      } else {
        this.toastr.warning('Escriba usuario y contraseña', '¡Info!',{
          timeOut:4000, positionClass: 'toast-top-center',
        });
      }
    }



}
