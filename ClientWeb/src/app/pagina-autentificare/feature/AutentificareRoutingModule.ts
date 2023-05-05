import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PaginaAdministrareConturiComponent } from 'src/app/pagina-administrare-conturi/administrare/pagina-administrare-conturi.component';
import { ContainerComponent } from '../container/container.component';
import { PaginaAutentificareComponent } from '../pagina-autentificare/pagina-autentificare.component';

const routes: Routes = [
  { path: `autentificare`, component: PaginaAutentificareComponent },
  { path: `delogare`, component: PaginaAutentificareComponent },
  { path: `administrare`, component: PaginaAdministrareConturiComponent },

];
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AutentificareRoutingModule {}
