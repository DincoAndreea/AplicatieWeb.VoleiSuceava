import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { VoleiJuvenilComponent } from "../../volei-juvenil-admin/volei-juvenil/volei-juvenil.component";
import { MiniVoleiComponent } from "../mini-volei/mini-volei.component";

const routes: Routes =[
    {path: `minivolei`, component:MiniVoleiComponent},
    {path: `voleijuvenil`, component:VoleiJuvenilComponent}
];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule],
})
export class MiniVoleiRoutingModule {}