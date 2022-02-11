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
import { HomeComponent } from './core/header/home.component';
import { LoginComponent } from './core/login/login.component';
import { CreateUserComponent } from './user/components/create-user/create-user.component';
import { EditUserComponent } from './user/components/edit-user/edit-user.component';
import { ResetPasswordComponent } from './user/components/reset-password/reset-password.component';

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'listar', component: ListaClienteComponent},
  {path: 'nuevo', component: NuevoClienteComponent},
  {path: 'editar/:id', component:EditarClienteComponent},
  {path: 'cliente/:id/detalle', component: DetalleClienteComponent},
  {path: 'cliente/:clienteId/cuenta/crear', component: CreateAccountsComponent},
  {path: 'cliente/:clienteId/cuenta/:cuentaId/movimiento', component: ListTransactionsComponent},
  {path: 'cliente/:clienteId/cuenta/:cuentaId/consignar', component: ConsignarComponentsComponent},
  {path: 'cliente/:clienteId/cuenta/:cuentaId/retirar', component: RetirarComponentsComponent},
  {path: 'cliente/:clienteId/cuenta/:cuentaId/transferencia', component: TransferirComponentsComponent},
  {path: 'user/create', component: CreateUserComponent},
  {path: 'user/edit', component: EditUserComponent},
  {path: 'user/reset-password', component: ResetPasswordComponent},
  {path: 'login', component: LoginComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
