import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EscuelasComponent } from './escuelas/escuelas.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { EscuelaDetailComponent } from './escuela-detail/escuela-detail.component';

const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'detail/:id', component: EscuelaDetailComponent },
  { path: 'escuelas', component: EscuelasComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
