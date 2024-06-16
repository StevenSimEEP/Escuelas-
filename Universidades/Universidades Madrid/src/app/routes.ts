import { Routes } from "@angular/router";
import { HomeComponent } from "./home/home.component";
import { DeatilsComponent } from "./deatils/deatils.component";
import { AgregarUniversidadComponent } from "./agregar-universidad/agregar-universidad.component";

const routeConfig: Routes = [
  {
    path: '',
    component: HomeComponent,
    title: 'Página de inicio'
  },
  {
    path: 'details/:id',
    component: DeatilsComponent,
    title: 'Detalle de la universidad'
  },
  {
    path: 'agregar-universidad',
    component: AgregarUniversidadComponent,
    title: 'Agregar universidad'
  },
];

export default routeConfig;
