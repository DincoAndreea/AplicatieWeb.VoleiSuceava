import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { VoleiJuvenilComponent } from "../../volei-juvenil-admin/volei-juvenil/volei-juvenil.component";
import { SperanteComponent } from "../sperante/sperante.component";

const routes: Routes =[
    {path: `sperante`, component:SperanteComponent},
    {path: `voleijuvenil`, component:VoleiJuvenilComponent}
];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule],
})
export class SperanteRoutingModule {}