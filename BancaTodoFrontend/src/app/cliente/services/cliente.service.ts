import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cliente } from '../models/cliente';
import { GeneralResponse } from '../../shared/models/general-response';

@Injectable({
  providedIn: 'root',
})
export class ClienteService {
  clienteURL = 'http://localhost:8080/clientes/';

  constructor(private httpCliente: HttpClient) {}

   public lista(): Observable<GeneralResponse<Cliente[]>> {
    return this.httpCliente.get<GeneralResponse<Cliente[]>>(this.clienteURL + '');
  }


  public detail(id:number): Observable<GeneralResponse<Cliente>>{
    return this.httpCliente.get<GeneralResponse<Cliente>>(this.clienteURL +`${id}`);
  }

  public save(cliente: Cliente): Observable<GeneralResponse<Cliente>> {
    return this.httpCliente.post<GeneralResponse<Cliente>>(this.clienteURL + '', cliente);
  }
  public update(id: number, cliente: Cliente): Observable<GeneralResponse<Cliente>>{
    return this.httpCliente.put<GeneralResponse<Cliente>> (this.clienteURL + `${id}/update`,cliente);
  }
  public delete(id: number): Observable<any> {
    return this.httpCliente.delete<any>(this.clienteURL + `${id}/delete`);
  }
}
