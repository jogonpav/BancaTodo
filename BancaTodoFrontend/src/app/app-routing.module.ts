import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DetalleClienteComponent } from './cliente/detalle-cliente/detalle-cliente.component';
import { EditarClienteComponent } from './cliente/editar-cliente/editar-cliente.component';
import { ListaClienteComponent } from './cliente/lista-cliente/lista-cliente.component';
import { NuevoClienteComponent } from './cliente/nuevo-cliente/nuevo-cliente.component';

const routes: Routes = [
  {path: '', component: ListaClienteComponent},
  //{path: 'listar', component: ListaClienteComponent},
  {path: 'nuevo', component: NuevoClienteComponent},
  {path: 'editar/:id', component:EditarClienteComponent},
  {path: 'detalle/:id', component: DetalleClienteComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
