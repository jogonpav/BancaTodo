import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { GlobalService } from 'src/app/shared/services/global.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  jwt: String;
  constructor( private router: Router, public globalService: GlobalService) { }

  ngOnInit(): void {
    this.jwt = this.globalService.user.jwt;
    console.log(this.jwt);
  }

  logout(): void {

    this.globalService = new GlobalService();
    this.router.navigate(['/'])

  }



}
