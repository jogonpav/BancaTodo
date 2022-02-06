import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GeneralResponse } from 'src/app/shared/models/general-response';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  URL = 'http://localhost:8080/users/';

  constructor(private httpClient: HttpClient) { }

  login(user: User): Observable<GeneralResponse<User>> {
    return this.httpClient.post<GeneralResponse<User>>(this.URL + 'auth', user);
}
}
