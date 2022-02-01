import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cliente } from 'src/app/cliente/models/cliente';
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

  public detail(cuentaId:number): Observable<GeneralResponse<Accounts>>{
    return this.httpClient.get<GeneralResponse<Accounts>>(this.productoURL+"/"+`${cuentaId}`);
  }

  public getOtherProductosByCliente(idCuenta: number, idCliente:number): Observable<GeneralResponse<Accounts[]>>{
    return this.httpClient.get<GeneralResponse<Accounts[]>>(this.productoURL+"/"+`${idCuenta}`+"/cliente/"+`${idCliente}`);
  }

  public getProductosOtherClientes(idCliente:number): Observable<GeneralResponse<Accounts[]>>{
    return this.httpClient.get<GeneralResponse<Accounts[]>>(this.productoURL+"/cliente/"+`${idCliente}`+"/distintc");
  }

  public add(clienteId:number, cuenta:Accounts): Observable<GeneralResponse<Accounts>>{
    return this.httpClient.post<GeneralResponse<Accounts>>(this.productoURL+"?clienteId="+`${clienteId}`,cuenta);
  }

  public activate(clienteId:number, cuenta:Accounts): Observable<GeneralResponse<Accounts>>{
    return this.httpClient.put<GeneralResponse<Accounts>>(this.productoURL+"/"+`${clienteId}`+"/activate", cuenta);
  }
  public inactivate(clienteId:number, cuenta:Accounts): Observable<GeneralResponse<Accounts>>{
    return this.httpClient.put<GeneralResponse<Accounts>>(this.productoURL+"/"+`${clienteId}`+"/inactivate", cuenta);
  }
  public cancel(clienteId:number, cuenta:Accounts): Observable<GeneralResponse<Accounts>>{
    return this.httpClient.put<GeneralResponse<Accounts>>(this.productoURL+"/"+`${clienteId}`+"/cancel", cuenta);
  }


}
