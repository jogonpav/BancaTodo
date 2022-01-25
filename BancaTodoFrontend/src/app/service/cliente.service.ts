import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cliente } from '../models/cliente';

@Injectable({
  providedIn: 'root',
})
export class ClienteService {
  clienteURL = 'http://localhost:8080/clientes/';

  constructor(private httpCliente: HttpClient) {}

  public lista(): Observable<Cliente[]> {
    return this.httpCliente.get<Cliente[]>(this.clienteURL + '');
  }
  public detail(id:number): Observable<Cliente>{
    return this.httpCliente.get<Cliente>(this.clienteURL +`${id}`);
  }

  public save(cliente: Cliente): Observable<any> {
    return this.httpCliente.post<any>(this.clienteURL + '', cliente);
  }
  public update(id: number, cliente: Cliente): Observable<any>{
    return this.httpCliente.put<any> (this.clienteURL + `${id}/update`,cliente);
  }
  public delete(id: number): Observable<any> {
    return this.httpCliente.delete<any>(this.clienteURL + `delete/${id}`);
  }
}
