import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { NoutateCreareEditareAdminComponent } from "../noutate-creare-editare-admin/noutate-creare-editare-admin.component";
import { NoutateDetaliatComponent } from "../noutate-detaliat/noutate-detaliat.component";
import { NoutatiAdminComponent } from "../noutati-admin/noutati-admin.component";

const routes: Routes =[
    {path: `noutateDetailed`, component:NoutateDetaliatComponent},
    {path:`noutateCreareEditare`, component:NoutateCreareEditareAdminComponent},
    {path:`noutatiAdmin`, component:NoutatiAdminComponent}
];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class NoutatiRoutingModule {}