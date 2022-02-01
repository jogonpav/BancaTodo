import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GeneralResponse } from 'src/app/shared/models/general-response';
import { Transaction } from '../models/transaction';

@Injectable({
  providedIn: 'root'
})
export class TransactionsService {

  productoURL = 'http://localhost:8080/productos';

  constructor(private httpClient: HttpClient) { }

  public listarMovimientos(cuentaId:number): Observable<GeneralResponse<Transaction[]>>{
    return this.httpClient.get<GeneralResponse<Transaction[]>>(this.productoURL+"/"+`${cuentaId}`+"/movimientos");
  }

  public consignar(cuentaId:number, transaction: Transaction): Observable<GeneralResponse<number>>{
    return this.httpClient.post<GeneralResponse<number>>(this.productoURL+"/"+`${cuentaId}`+"/consignar",transaction);
  }
  public retirar(cuentaId:number, transaction: Transaction): Observable<GeneralResponse<number>>{
    return this.httpClient.post<GeneralResponse<number>>(this.productoURL+"/"+`${cuentaId}`+"/retirar",transaction);
  }

  public transferir(cuentaId:number, idCuentaDestino:number, movimientoOrigen: Transaction): Observable<GeneralResponse<number>>{
    return this.httpClient.post<GeneralResponse<number>>(this.productoURL+"/"+`${cuentaId}`+"/transferencia?idCuentaDestino="+`${idCuentaDestino}`,movimientoOrigen);
  }


}
