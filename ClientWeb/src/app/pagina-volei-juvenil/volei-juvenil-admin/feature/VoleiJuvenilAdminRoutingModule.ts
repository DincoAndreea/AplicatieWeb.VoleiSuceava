import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { VoleiJuvenilComponent } from "../volei-juvenil/volei-juvenil.component";

const routes: Routes =[
    {path: `voleijuvenil`, component:VoleiJuvenilComponent}
];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule],
})
export class VoleiJuvenilAdminRoutingModule {}