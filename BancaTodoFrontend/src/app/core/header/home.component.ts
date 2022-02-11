import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { GlobalService } from 'src/app/shared/services/global.service';
import { User } from 'src/app/user/models/user';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  isLogged = false;
  forRegister = false;

  //jwt:string;


  constructor( private router: Router, public globalService: GlobalService) { }

  ngOnInit(): void {
    //console.log(this.jwt);

    //console.log(this.jwt);
  }

  authenticated(user: User){
    this.globalService.user = user;
    this.isLogged = true;
    }

  register(user: User){
    this.globalService.user = user;
    this.forRegister = true;
    }


  logout(): void {
    this.globalService = new GlobalService();
    this.isLogged = false;
    this.forRegister = false;
    //this.router.navigate(['/'])

  }



}
