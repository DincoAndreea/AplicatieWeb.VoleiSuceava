import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { VoleiJuvenilComponent } from "src/app/pagina-volei-juvenil/volei-juvenil-admin/volei-juvenil/volei-juvenil.component";
import { JucatoriComponent } from "../jucatori/jucatori.component";

const routes: Routes =[
    {path: `jucatori`, component:JucatoriComponent}
];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule],
})
export class JucatoriRoutingModule {}