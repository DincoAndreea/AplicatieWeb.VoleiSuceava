import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { AcasaComponent } from "../acasa/acasa.component";
import { SenioriComponent } from "../seniori/seniori.component";

const routes: Routes =[  
    {path: `seniori`, component:SenioriComponent},
];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class AcasaRoutingModule {}