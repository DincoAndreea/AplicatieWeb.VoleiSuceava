import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { CadetiComponent } from "../cadeti/cadeti.component";
import { VoleiJuvenilComponent } from "../../volei-juvenil-admin/volei-juvenil/volei-juvenil.component";

const routes: Routes =[
    {path: `cadeti`, component:CadetiComponent},
    {path: `voleijuvenil`, component:VoleiJuvenilComponent}
];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule],
})
export class CadetiRoutingModule {}