import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { VoleiJuvenilComponent } from "../../volei-juvenil-admin/volei-juvenil/volei-juvenil.component";
import { JunioriComponent } from "../juniori/juniori.component";

const routes: Routes =[
    {path: `juniori`, component:JunioriComponent},
    {path: `voleijuvenil`, component:VoleiJuvenilComponent}
];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule],
})
export class JunioriRoutingModule {}