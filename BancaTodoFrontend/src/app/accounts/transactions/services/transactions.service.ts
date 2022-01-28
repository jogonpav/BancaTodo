import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GeneralResponse } from 'src/app/shared/models/general-response';
import { Transactions } from '../models/transactions';

@Injectable({
  providedIn: 'root'
})
export class TransactionsService {

  productoURL = 'http://localhost:8080/productos';

  constructor(private httpClient: HttpClient) { }

  public listarMovimientos(cuentaId:number): Observable<GeneralResponse<Transactions[]>>{
    return this.httpClient.get<GeneralResponse<Transactions[]>>(this.productoURL+"/"+`${cuentaId}`+"/movimientos");
  }
}
