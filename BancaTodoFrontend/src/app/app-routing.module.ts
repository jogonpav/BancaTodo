import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateAccountsComponent } from './accounts/components/create-accounts/create-accounts.component';
import { ConsignarComponentsComponent } from './accounts/transactions/components/consignar-components/consignar-components.component';
import { ListTransactionsComponent } from './accounts/transactions/components/list-transactions/list-transactions.component';
import { RetirarComponentsComponent } from './accounts/transactions/components/retirar-components/retirar-components.component';
import { TransferirComponentsComponent } from './accounts/transactions/components/transferir-components/transferir-components.component';
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
  {path: 'cliente/:clienteId/cuenta/crear', component: CreateAccountsComponent},
  {path: 'cliente/:clienteId/cuenta/:cuentaId/movimiento', component: ListTransactionsComponent},
  {path: 'cliente/:clienteId/cuenta/:cuentaId/consignar', component: ConsignarComponentsComponent},
  {path: 'cliente/:clienteId/cuenta/:cuentaId/retirar', component: RetirarComponentsComponent},
  {path: 'cliente/:clienteId/cuenta/:cuentaId/transferencia', component: TransferirComponentsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
