import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { SenioriComponent } from "src/app/pagina-acasa/seniori/seniori.component";
import { PersonalAdministrareComponent } from "../personal-administrare/personal-administrare.component";
import { PersonalComponent } from "../personal/personal.component";

const routes: Routes =[
    {path: `personal`, component:PersonalComponent},
    {path: `personaladmin`, component:PersonalAdministrareComponent}
];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule],
})
export class PersonalRoutingModule {}