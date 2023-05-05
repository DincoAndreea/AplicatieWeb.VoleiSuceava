import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { ViziuneComponent } from "../viziune/viziune.component";

const routes: Routes =[
    {path: `viziune`, component:ViziuneComponent}
];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule],
})
export class ViziuneRoutingModule {}