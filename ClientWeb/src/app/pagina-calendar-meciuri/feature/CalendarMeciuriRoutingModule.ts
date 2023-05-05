import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { CalendarMeciuriComponent } from "../calendar-meciuri/calendar-meciuri.component";
import { CalendarMeciuriPreviewComponent } from "../calendar-meciuri-preview/calendar-meciuri-preview.component";
import { AdminCalendarMeciuriComponent } from "../pagina-administrare-calendar/admin-calendar-meciuri/admin-calendar-meciuri.component";
import { CalendarMeciuriDetaliatComponent } from "../calendar-meciuri-detaliat/calendar-meciuri-detaliat.component";

const routes: Routes = [
    { path: `calendar`, component: CalendarMeciuriComponent },
    { path: `meciuriPreview`, component: CalendarMeciuriPreviewComponent },
    { path: `meciDetaliat`, component: CalendarMeciuriDetaliatComponent },
    { path: `adminCalendar`, component:AdminCalendarMeciuriComponent}
];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class CalendarMeciuriRoutingModule { }