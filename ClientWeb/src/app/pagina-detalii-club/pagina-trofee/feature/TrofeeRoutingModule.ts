import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { TrofeeComponent } from "../trofee/trofee.component";

const routes: Routes =[
    {path: `trofee`, component:TrofeeComponent}
];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule],
})
export class TrofeeRoutingModule {}