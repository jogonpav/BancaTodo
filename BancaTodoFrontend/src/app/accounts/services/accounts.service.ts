import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GeneralResponse } from 'src/app/shared/models/general-response';
import { Accounts } from '../models/accounts';

@Injectable({
  providedIn: 'root'
})
export class AccountsService {

  productoURL = 'http://localhost:8080/productos';

  constructor(private httpClient: HttpClient) { }

  public listarProductos(clienteId:number): Observable<GeneralResponse<Accounts[]>>{
    return this.httpClient.get<GeneralResponse<Accounts[]>>(this.productoURL+"?clienteId="+`${clienteId}`);
  }
}
