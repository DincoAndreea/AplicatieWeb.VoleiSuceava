import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { AntrenoriComponent } from "../antrenori/antrenori.component";

const routes: Routes =[
    {path: `antrenori`, component:AntrenoriComponent}
];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule],
})
export class AntrenoriRoutingModule {}