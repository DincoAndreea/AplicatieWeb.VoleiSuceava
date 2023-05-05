import { LOCALE_ID, NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import localeRo from '@angular/common/locales/ro';
import { registerLocaleData } from '@angular/common';
registerLocaleData(localeRo);

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ContainerComponent } from './pagina-autentificare/container/container.component';
import { PaginaAutentificareComponent } from './pagina-autentificare/pagina-autentificare/pagina-autentificare.component';
import { SponsorComponent } from './pagina-sponsori/sponsor/sponsor.component';
import { SponsoriComponent } from './pagina-sponsori/sponsori/sponsori.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { SubsolComponent } from './subsol/subsol.component';
import { NoutatePreviewComponent } from './pagina-noutati/noutate-preview/noutate-preview.component';
import { NoutatiPreviewComponent } from './pagina-noutati/noutati-preview/noutati-preview.component';
import { NoutateDetaliatComponent } from './pagina-noutati/noutate-detaliat/noutate-detaliat.component';
import { NoutatiAdminComponent } from './pagina-noutati/noutati-admin/noutati-admin.component';
import { NoutateCreareEditareAdminComponent } from './pagina-noutati/noutate-creare-editare-admin/noutate-creare-editare-admin.component';
import { NoutatiServices } from './pagina-noutati/service/NoutatiService';
import { HttpClientModule } from '@angular/common/http';
import { SponsoriService } from './pagina-sponsori/service/SponsoriService';
import { ViziuneComponent } from './pagina-detalii-club/pagina-viziune/viziune/viziune.component';
import { IstoricComponent } from './pagina-detalii-club/pagina-istoric/istoric/istoric.component';
import { TrofeeComponent } from './pagina-detalii-club/pagina-trofee/trofee/trofee.component';
import { AcasaComponent } from './pagina-acasa/acasa/acasa.component';
import { CalendarMeciuriComponent } from './pagina-calendar-meciuri/calendar-meciuri/calendar-meciuri.component';
import { PersonalComponent } from './pagina-personal/pagina-personal/personal/personal.component';
import { JunioriComponent } from './pagina-volei-juvenil/pagina-juniori/juniori/juniori.component';
import { CadetiComponent } from './pagina-volei-juvenil/pagina-cadeti/cadeti/cadeti.component';
import { SperanteComponent } from './pagina-volei-juvenil/pagina-sperante/sperante/sperante.component';
import { MiniVoleiComponent } from './pagina-volei-juvenil/pagina-mini-volei/mini-volei/mini-volei.component';
import { JucatoriComponent } from './pagina-personal/pagina-jucatori/jucatori/jucatori.component';
import { AntrenoriComponent } from './pagina-personal/pagina-antrenori/antrenori/antrenori.component';
import { PrototipNavBarComponent } from './prototip-nav-bar/prototip/prototip-nav-bar.component';
import { SiglaNavBarComponent } from './prototip-nav-bar/sigla-nav-bar/sigla-nav-bar/sigla-nav-bar.component';
import { PaginiNavBarComponent } from './prototip-nav-bar/pagini-nav-bar/pagini-nav-bar/pagini-nav-bar.component';
import { JucatorComponent } from './pagina-personal/pagina-jucatori/jucator/jucator.component';
import { AntrenorComponent } from './pagina-personal/pagina-antrenori/antrenor/antrenor.component';
import { VoleiJuvenilComponent } from './pagina-volei-juvenil/volei-juvenil-admin/volei-juvenil/volei-juvenil.component';
import { CadetiCompComponent } from './pagina-volei-juvenil/pagina-cadeti/cadeti-comp/cadeti-comp.component';
import { JunioriCompComponent } from './pagina-volei-juvenil/pagina-juniori/juniori-comp/juniori-comp.component';
import { MiniVoleiCompComponent } from './pagina-volei-juvenil/pagina-mini-volei/mini-volei-comp/mini-volei-comp.component';
import { SperanteCompComponent } from './pagina-volei-juvenil/pagina-sperante/sperante-comp/sperante-comp.component';
import { CadetiModifComponent } from './pagina-volei-juvenil/pagina-cadeti/cadeti-modif/cadeti-modif.component';
import { JunioriModifComponent } from './pagina-volei-juvenil/pagina-juniori/juniori-modif/juniori-modif.component';
import { MiniVoleiModifComponent } from './pagina-volei-juvenil/pagina-mini-volei/mini-volei-modif/mini-volei-modif.component';
import { SperanteModifComponent } from './pagina-volei-juvenil/pagina-sperante/sperante-modif/sperante-modif.component';
import { SenioriComponent } from './pagina-acasa/seniori/seniori.component';
import { PersonalAdministrareComponent } from './pagina-personal/pagina-personal/personal-administrare/personal-administrare.component';
import { EchipeComponent } from './pagina-personal/echipe/echipe.component';
import { SenioriModifComponent } from './pagina-personal/seniori-modif/seniori-modif.component';
import { AntrenoriAdaugareComponent } from './pagina-personal/pagina-antrenori/antrenori-adaugare/antrenori-adaugare.component';
import { JucatoriAdaugareComponent } from './pagina-personal/pagina-jucatori/jucatori-adaugare/jucatori-adaugare.component';
import { EchipeAdaugareComponent } from './pagina-personal/echipe-adaugare/echipe-adaugare.component';
import { CalendarMeciuriPreviewComponent } from './pagina-calendar-meciuri/calendar-meciuri-preview/calendar-meciuri-preview.component';
import { AdminCalendarMeciuriComponent } from './pagina-calendar-meciuri/pagina-administrare-calendar/admin-calendar-meciuri/admin-calendar-meciuri.component';
import { CampionateModifComponent } from './pagina-calendar-meciuri/pagina-administrare-calendar/campionate-admin/campionate-modif/campionate-modif.component';
import { CampionateAdaugareComponent } from './pagina-calendar-meciuri/pagina-administrare-calendar/campionate-admin/campionate-adaugare/campionate-adaugare.component';
import { DiviziiModifComponent } from './pagina-calendar-meciuri/pagina-administrare-calendar/divizii-admin/divizii-modif/divizii-modif.component';
import { DiviziiAdaugareComponent } from './pagina-calendar-meciuri/pagina-administrare-calendar/divizii-admin/divizii-adaugare/divizii-adaugare.component';
import { LocatiiModifComponent } from './pagina-calendar-meciuri/pagina-administrare-calendar/locatii-admin/locatii-modif/locatii-modif.component';
import { LocatiiAdaugareComponent } from './pagina-calendar-meciuri/pagina-administrare-calendar/locatii-admin/locatii-adaugare/locatii-adaugare.component';
import { MeciuriModifComponent } from './pagina-calendar-meciuri/pagina-administrare-calendar/meciuri-admin/meciuri-modif/meciuri-modif.component';
import { MeciuriAdaugareComponent } from './pagina-calendar-meciuri/pagina-administrare-calendar/meciuri-admin/meciuri-adaugare/meciuri-adaugare.component';
import { CalendarMeciuriDetaliatComponent } from './pagina-calendar-meciuri/calendar-meciuri-detaliat/calendar-meciuri-detaliat.component';
import { PaginaAdministrareConturiComponent } from './pagina-administrare-conturi/administrare/pagina-administrare-conturi.component';


@NgModule({
  declarations: [
  AppComponent,
  AcasaComponent,
  PaginaAutentificareComponent,
  ContainerComponent,
  SponsorComponent,
  SponsoriComponent,
  NavBarComponent, 
  SubsolComponent,
  NoutatePreviewComponent,
  NoutatiPreviewComponent,
  NoutateDetaliatComponent, 
  NoutatiAdminComponent,
  NoutateCreareEditareAdminComponent,
  ViziuneComponent, 
  IstoricComponent,
  TrofeeComponent,
  CalendarMeciuriComponent,
  PersonalComponent,
  JunioriComponent,
  CadetiComponent,
  SperanteComponent,
  MiniVoleiComponent,
  JucatoriComponent,
  AntrenoriComponent,
  PrototipNavBarComponent,
  SiglaNavBarComponent,
  PaginiNavBarComponent,
  JucatorComponent,
  AntrenorComponent,
  VoleiJuvenilComponent,
  CadetiCompComponent,
  JunioriCompComponent,
  MiniVoleiCompComponent,
  SperanteCompComponent,
  CadetiModifComponent,
  JunioriModifComponent,
  MiniVoleiModifComponent,
  SperanteModifComponent,
  SenioriComponent,
  PersonalAdministrareComponent,
  EchipeComponent,
  SenioriModifComponent,
  AntrenoriAdaugareComponent,
  JucatoriAdaugareComponent,
  EchipeAdaugareComponent,
  CalendarMeciuriPreviewComponent,
  AdminCalendarMeciuriComponent,
  CampionateModifComponent,
  CampionateAdaugareComponent,
  DiviziiModifComponent,
  DiviziiAdaugareComponent,
  LocatiiModifComponent,
  LocatiiAdaugareComponent,
  MeciuriModifComponent,
  MeciuriAdaugareComponent,
  CalendarMeciuriDetaliatComponent,
  PaginaAdministrareConturiComponent,
  ],

  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    ReactiveFormsModule,
    RouterModule.forRoot([
      { path: `autentificare`, component: PaginaAutentificareComponent },
      {
        path: `autentificare`,
        loadChildren: () =>
          import(
            `src/app/pagina-autentificare/feature/AutentificareModule`
          ).then((u) => u.AutentificareModule),
      },
      { path: `sponsori`, component: SponsoriComponent },
      {
        path: `sponsori`,
        loadChildren: () =>
          import(`src/app/pagina-sponsori/feature/SponsoriModule`).then(
            (u) => u.SponsoriModule
          ),
      },
      { path: `noutati`, component: NoutatiPreviewComponent },
      {
        path: `noutati`,
        loadChildren: () =>
          import(`src/app/pagina-noutati/feature/NoutatiModule`).then(
            (u) => u.NoutatiModule
          ),
      },
      { path: `istoric`, component: IstoricComponent },
      {
        path: `istoric`,
        loadChildren: () =>
          import(
            `src/app/pagina-detalii-club/pagina-istoric/feature/IstoricModule`
          ).then((u) => u.IstoricModule),
      },
      { path: `viziune`, component: ViziuneComponent },
      {
        path: `viziune`,
        loadChildren: () =>
          import(
            `src/app/pagina-detalii-club/pagina-viziune/feature/ViziuneModule`
          ).then((u) => u.ViziuneModule),
      },
      { path: `trofee`, component: TrofeeComponent },
      {
        path: `trofee`,
        loadChildren: () =>
          import(
            `src/app/pagina-detalii-club/pagina-trofee/feature/TrofeeModule`
          ).then((u) => u.TrofeeModule),
      },
      { path: `antrenori`, component: AntrenoriComponent },
      {
        path: `antrenori`,
        loadChildren: () =>
          import(
            `src/app/pagina-personal/pagina-antrenori/feature/AntrenoriModule`
          ).then((u) => u.AntrenoriModule),
      },
      { path: `cadeti`, component: CadetiComponent },
      {
        path: `cadeti`,
        loadChildren: () =>
          import(
            `src/app/pagina-volei-juvenil/pagina-cadeti/feature/CadetiModule`
          ).then((u) => u.CadetiModule),
      },
      { path: `calendar`, component: CalendarMeciuriComponent },
      {
        path: `calendar`,
        loadChildren: () =>
          import(
            `src/app/pagina-calendar-meciuri/feature/CalendarMeciuriModule`
          ).then((u) => u.CalendarMeciuriModule),
      },
      { path: `jucatori`, component: JucatoriComponent },
      {
        path: `jucatori`,
        loadChildren: () =>
          import(
            `src/app/pagina-personal/pagina-jucatori/feature/JucatoriModule`
          ).then((u) => u.JucatoriModule),
      },
      { path: `juniori`, component: JunioriComponent },
      {
        path: `juniori`,
        loadChildren: () =>
          import(
            `src/app/pagina-volei-juvenil/pagina-juniori/feature/JunioriModule`
          ).then((u) => u.JunioriModule),
      },
      { path: `minivolei`, component: MiniVoleiComponent },
      {
        path: `minivolei`,
        loadChildren: () =>
          import(
            `src/app/pagina-volei-juvenil/pagina-mini-volei/feature/MiniVoleiModule`
          ).then((u) => u.MiniVoleiModule),
      },
      { path: `personal`, component: PersonalComponent },
      {
        path: `personal`,
        loadChildren: () =>
          import(
            `src/app/pagina-personal/pagina-personal/feature/PersonalModule`
          ).then((u) => u.PersonalModule),
      },
      { path: `sperante`, component: SperanteComponent },
      {
        path: `sperante`,
        loadChildren: () =>
          import(
            `src/app/pagina-volei-juvenil/pagina-sperante/feature/SperanteModule`
          ).then((u) => u.SperanteModule),
      },
      { path: `acasa`, component: AcasaComponent },
      {
        path: `acasa`,
        loadChildren: () =>
          import(
            `src/app/pagina-acasa/feature/AcasaModule`
          ).then((u) => u.AcasaModule),
      },
      { path: `**`, component: AcasaComponent },
    ]),
  ],
  providers: [NoutatiServices, SponsoriService, { provide: LOCALE_ID, useValue: 'ro' }], // ca sa am in intreaga aplicatie
  bootstrap: [AppComponent],
})
export class AppModule {}
