import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { SenioriComponent } from "../seniori/seniori.component";

const routes: Routes =[
    
];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class SenioriRoutingModule {}