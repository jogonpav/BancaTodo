import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cliente } from 'src/app/cliente/models/cliente';
import { GeneralResponse } from 'src/app/shared/models/general-response';

import { environment } from 'src/environments/environment';
import { User } from '../models/user';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiURL = environment.apiURL;

  URL = this.apiURL + '/users';

  constructor(private httpClient: HttpClient) { }

  public save(user: User): Observable<GeneralResponse<User>> {
    return this.httpClient.post<GeneralResponse<User>>(this.URL, user);
  }

  public detail(userName: string): Observable<GeneralResponse<User>>{
    return this.httpClient.get<GeneralResponse<User>>(this.URL+'?userName='+userName);
  }

  public update(user: User): Observable<GeneralResponse<User>> {
    return this.httpClient.put<GeneralResponse<User>>(this.URL, user);
  }

  login(user: User): Observable<GeneralResponse<User>> {
    return this.httpClient.post<GeneralResponse<User>>(this.URL + '/auth', user);
}
}
