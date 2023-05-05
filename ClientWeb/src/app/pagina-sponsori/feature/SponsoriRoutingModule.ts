import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { SponsoriComponent } from "../sponsori/sponsori.component";

const routes: Routes =[
    {path: `sponsori`, component:SponsoriComponent}
];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class SponsoriRoutingModule {}