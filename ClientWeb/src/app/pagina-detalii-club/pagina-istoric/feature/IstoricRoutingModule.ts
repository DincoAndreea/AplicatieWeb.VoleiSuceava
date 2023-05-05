import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { IstoricComponent } from "../istoric/istoric.component";

const routes: Routes =[
    {path: `istoric`, component:IstoricComponent}
];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule],
})
export class IstoricRoutingModule {}