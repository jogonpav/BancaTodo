import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListTransactionsComponent } from './accounts/transactions/components/list-transactions/list-transactions.component';
import { DetalleClienteComponent } from './cliente/components/detalle-cliente/detalle-cliente.component';
import { EditarClienteComponent } from './cliente/components/editar-cliente/editar-cliente.component';
import { ListaClienteComponent } from './cliente/components/lista-cliente/lista-cliente.component';
import { NuevoClienteComponent } from './cliente/components/nuevo-cliente/nuevo-cliente.component';

const routes: Routes = [
  {path: '', component: ListaClienteComponent},
  {path: 'listar', component: ListaClienteComponent},
  {path: 'nuevo', component: NuevoClienteComponent},
  {path: 'editar/:id', component:EditarClienteComponent},
  {path: 'cliente/:id/detalle', component: DetalleClienteComponent},
  {path: 'cliente/:clienteId/cuenta/:id/movimiento', component: ListTransactionsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
