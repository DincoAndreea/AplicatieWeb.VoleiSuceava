import { Component, OnDestroy, OnInit } from '@angular/core';
import { of, Subject } from 'rxjs';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { INoutateDetaliat } from 'src/app/model/noutate-detaliat';
import { NoutatiServices } from '../service/NoutatiService';
import { INoutatePostPut } from 'src/app/model/noutate-post-put';
import { Data, Router } from '@angular/router';

@Component({
  selector: 'app-noutate-creare-editare-admin',
  templateUrl: './noutate-creare-editare-admin.component.html',
  styleUrls: ['./noutate-creare-editare-admin.component.css']
})

export class NoutateCreareEditareAdminComponent implements OnInit, OnDestroy {

  ultimaLocatie: string = "";
  unsubscribe$: Subject<void>;
  noutate: INoutatePostPut = {
    'id': -1,
    'titlu': "",
    'descriere': "",
    'hashtaguri': [],
    'imagini': [],
    'videoclipuri': [],
    'dataPostare': new Date(),
    'draft': false
  };

  media: Number = 0;//0 pentru text, 1 pentru imagini, 2 pentru video
  stilPublicare: number = 0; //0 pt draft, 1 pt publicat, 2 pt viitor
  dataDeAzi: Date = new Date();
  butonBlocatPeData: Boolean = true; //e logica inversa
  butonBlocatPeHashtag: Boolean = true; //e logica inversa
  butonBlocatPeImagine: Boolean = true; //e logica inversa
  butonBlocatPeVideoclip: Boolean = true; //e logica inversa


  noutateForm = new FormGroup({
    titlu: new FormControl('', [
      Validators.required,
      Validators.minLength(6),
      Validators.maxLength(1000),
    ]),
    descriere: new FormControl('', [
      Validators.required,
      Validators.minLength(6)
    ]),
    dataPostare: new FormControl('', [
      Validators.required,
    ]),
  });

  constructor(private readonly noutatiServices: NoutatiServices, private router: Router) {
    this.unsubscribe$ = new Subject<void>;
  }


  ngOnInit(): void {
    this.ultimaLocatie = localStorage.getItem("pagina-actuala") || "";
    localStorage.setItem("pagina-actuala", "noutateCreareEditare");
    //console.log("care e ultima locatie?: " + this.ultimaLocatie);

    let idStire: string = localStorage.getItem("noutateaDetaliata") || "-1";//aici verificam tipul, daca e put sau push
    if (idStire != "-1") {
      this.noutatiServices.getNoutateDetaliat(Number.parseInt(idStire)).subscribe((noutate) => {
        this.noutate = JSON.parse(JSON.stringify(noutate));

        //console.dir(this.noutate);

        //console.log("Asta e titlul: " + JSON.stringify(this.noutate.titlu).substring(1, (JSON.stringify(this.noutate.titlu).length - 1)));
        //console.log(JSON.stringify(this.noutate.dataPostare).substring(1, (JSON.stringify(this.noutate.dataPostare).length - 1)));
        //console.log("data postare dupa initializare: "+this.noutateForm.get('dataPostare')?.value);
        
        this.noutateForm.setValue({
          titlu: JSON.stringify(this.noutate.titlu).substring(1, (JSON.stringify(this.noutate.titlu).length - 1)),
          descriere: JSON.stringify(this.noutate.descriere).substring(1, (JSON.stringify(this.noutate.descriere).length - 1)),
          dataPostare: JSON.stringify(this.noutate.dataPostare).substring(1, (JSON.stringify(this.noutate.dataPostare).length - 1))
        });
        //console.log("dupa initializare: "+this.noutateForm.get('dataPostare')?.value);
        //console.log("draft: "+this.noutate.draft+" stil publicare: "+this.stilPublicare);
        //console.log("tipul original al datei postate: "+typeof this.noutate.dataPostare);
        
        //populare si interfata
        if(this.noutate.imagini == null && this.noutate.videoclipuri == null)
        {
          //console.log("doar text");
          this.butonBlocatPeImagine = false;
          this.butonBlocatPeVideoclip = false;
        }

        if (this.noutate.imagini != null) {
          this.media = 1;
          this.noutate.imagini.length == 0 ? this.butonBlocatPeImagine = true : this.butonBlocatPeImagine = false;
          this.butonBlocatPeVideoclip = false;
        }
        else {
          this.noutate.imagini = [];//trebuie initializat ca dupa sa pot face push
        }
        if (this.noutate.videoclipuri != null) {
          this.media = 2;
          this.butonBlocatPeImagine = false;
          this.noutate.videoclipuri.length == 0 ? this.butonBlocatPeVideoclip = true : this.butonBlocatPeVideoclip = false;
        }
        else {
          this.noutate.videoclipuri = [];//trebuie initializat ca dupa sa pot face push
        }

        this.butonBlocatPeData = false;
        if (this.noutate.hashtaguri.length != 0)
          this.butonBlocatPeHashtag = false;

        if (this.noutate.draft == true)
          this.updateDraftValue(0);
        else {
          //console.log(typeof this.noutate.dataPostare);//datapostare e string
          //console.log(new Date(this.noutate.dataPostare));
          
          if (this.comparaDouaDate(this.dataDeAzi, new Date(this.noutate.dataPostare)))//daca data de azi<dataPostare => false
          {
            this.noutate.dataPostare = this.dataDeAzi;//adica, daca facem o modificare, incepem de azi
            this.updateDraftValue(1);
          }
          else {
            this.updateDraftValue(2);
          }
        }
        //console.dir(this.noutate);
      });
    }
    else {//adica post
      this.noutate.dataPostare = this.dataDeAzi;
      this.updateDraftValue(1);
      this.butonBlocatPeData=false;
      this.butonBlocatPeImagine = false;
      this.butonBlocatPeVideoclip = false;
      this.noutateForm.setValue({
        titlu: "Insert title here",
        descriere: "Insert description here",
        dataPostare: this.dataDeAzi.toDateString()});
    }

  }

  ngOnDestroy(): void {
    localStorage.setItem("pagina-actuala", this.ultimaLocatie);//restaurez ultima locatie
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  updateMediaValue(numar: Number) {
    this.media = numar;
    if (this.media == 0) {
      this.butonBlocatPeImagine = false;
      this.butonBlocatPeVideoclip = false;
    }
    if (this.media == 1) {
      this.noutate.imagini.length == 0 ? this.butonBlocatPeImagine = true : this.butonBlocatPeImagine = false;
      this.butonBlocatPeVideoclip = false;
    }
    if (this.media == 2) {
      this.butonBlocatPeImagine = false;
      this.noutate.videoclipuri.length == 0 ? this.butonBlocatPeVideoclip = true : this.butonBlocatPeVideoclip = false;
    }
  }
  updateDraftValue(numar: number) {
    //console.log("Stil publicare: " + numar);
    this.stilPublicare = numar;
    if (numar == 0) {//draft
      this.noutate.draft = true;
      let aux =document.getElementById("draftTrue");
      //console.dir(aux);
      
    }
    else if (numar == 1) {//imediat
      this.noutate.draft = false;
      let aux =document.getElementById("draftFalse");
      //console.dir(aux);
    }
    else if (numar == 2) {//programat
      this.noutate.draft = false;
      let aux =document.getElementById("programat");
    }
  }

  submit() {

    this.noutate.titlu = this.noutateForm.get(['titlu'])?.value;
    this.noutate.descriere = this.noutateForm.get(['descriere'])?.value;
    //indiferent de post sau put, noua actualizare va impune o publicare din momentul actual inainte.
    //de e nevoie de alta specificatie, atuncio sa se faca aici modificari
    this.stilPublicare != 1 ? this.noutate.dataPostare = this.noutateForm.get(['dataPostare'])?.value : this.noutate.dataPostare = this.dataDeAzi;

    //draft, hashtags si medias e facut

    if (this.noutate.id == -1) {//populam doar postul ca sa il trimitem
      this.actualizeazaImaginiVideo();

      //("Creare noutate");
      //console.dir(this.noutate);
      this.noutatiServices.postNoutateNoua(this.noutate).subscribe();
      this.router.navigate([`/noutati`]);
    }
    else {
      this.actualizeazaImaginiVideo();

      //console.log("Actualizare");
      //console.dir(this.noutate);
      this.noutatiServices.putNoutateActualizata(this.noutate).subscribe();
      localStorage.setItem("noutateaDetaliata", this.noutate.id.toString());
      this.router.navigate([`/noutati/noutateDetailed`]);
    }
  }
  delete() {
    if (this.noutate.id != -1) {//adica avem un obiect
      //console.log("Stergem obiectul cu id: " + this.noutate.id);
      this.noutatiServices.deleteNoutate(this.noutate.id).subscribe();
      this.router.navigate([`/noutati`]);
    }
  }

  stergeHashtagDupaPozitie(poz: number) {
    //console.log(poz);
    let aux: string[] = [];
    while ((this.noutate.hashtaguri.length - 1) > poz) {
      aux.push(this.noutate.hashtaguri[this.noutate.hashtaguri.length - 1]);
      this.noutate.hashtaguri.pop();
    }
    this.noutate.hashtaguri.pop();//stergem elementul dorit
    while (aux.length != 0) {
      this.noutate.hashtaguri.push(aux[aux.length - 1]);
      aux.pop();
    }
    //console.log(this.noutate.hashtaguri);
    if (this.noutate.hashtaguri.length == 0)
      this.butonBlocatPeHashtag = true;
  }
  adaugaHashtag(newHashtag: string) {
    //console.log(newHashtag);
    this.noutate.hashtaguri.push(newHashtag);
    this.butonBlocatPeHashtag = false;
  }
  stergeImagineDupaPozitie(poz: number) {
    //console.log(poz);
    let aux: string[] = [];
    while ((this.noutate.imagini.length - 1) > poz) {
      aux.push(this.noutate.imagini[this.noutate.imagini.length - 1]);
      this.noutate.imagini.pop();
    }
    this.noutate.imagini.pop();//stergem elementul dorit
    while (aux.length != 0) {
      this.noutate.imagini.push(aux[aux.length - 1]);
      aux.pop();
    }
    this.noutate.imagini.length == 0 ? this.butonBlocatPeImagine = true : this.butonBlocatPeImagine = false;
  }
  adaugaImagine(newImagine: string) {
    //console.log(newImagine);
    this.noutate.imagini.push(newImagine);
    this.butonBlocatPeImagine = false;
  }
  stergeVideoDupaPozitie(poz: number) {
    //console.log(poz);
    let aux: string[] = [];
    while ((this.noutate.videoclipuri.length - 1) > poz) {
      aux.push(this.noutate.videoclipuri[this.noutate.videoclipuri.length - 1]);
      this.noutate.videoclipuri.pop();
    }
    this.noutate.videoclipuri.pop();//stergem elementul dorit
    while (aux.length != 0) {
      this.noutate.videoclipuri.push(aux[aux.length - 1]);
      aux.pop();
    }
    this.noutate.videoclipuri.length == 0 ? this.butonBlocatPeVideoclip = true : this.butonBlocatPeVideoclip = false;
  }
  adaugaVideo(newVideo: string) {
    //console.log(newVideo);
    this.noutate.videoclipuri.push(newVideo);
    this.butonBlocatPeVideoclip = false;
  }

  actualizeazaImaginiVideo() {
    switch (this.media) {
      case 0:
        {
          while (this.noutate.videoclipuri.length != 0)
            this.noutate.videoclipuri.pop();
          while (this.noutate.imagini.length != 0)
            this.noutate.imagini.pop();
          break;
        }
      case 1:
        {
          while (this.noutate.videoclipuri.length != 0)
            this.noutate.videoclipuri.pop();
          break;
        }
      case 2:
        {
          while (this.noutate.imagini.length != 0)
            this.noutate.imagini.pop();
          break;
        }
    }
  }
  actualizareData() {//asta e doar asa... :/
    this.noutate.dataPostare = this.noutateForm.get(['dataPostare'])?.value;
    // console.log("Preluarea initiala: " + this.noutate.dataPostare);
    let an = Number(this.noutate.dataPostare.toString().split('-')[0]);
    let luna = Number(this.noutate.dataPostare.toString().split('-')[1]);
    let zi = Number(this.noutate.dataPostare.toString().split('-')[2]);
    // console.log("Preluarea de pe pagina: " + zi + " " + luna + " " + an);
    let anCurent = this.dataDeAzi.getFullYear();
    let lunaCurenta = this.dataDeAzi.getMonth() + 1;
    let ziCurenta = this.dataDeAzi.getDate();
    // console.log("Preluarea de pe pagina: " + ziCurenta + " " + lunaCurenta + " " + anCurent);
    this.butonBlocatPeData = true;
    if (an > anCurent) {
      this.butonBlocatPeData = false;
    }
    else if (luna > lunaCurenta && an == anCurent) {
      this.butonBlocatPeData = false;
    }
    else if (zi >= ziCurenta && luna == lunaCurenta && an == anCurent) {
      this.butonBlocatPeData = false;
    }
    // console.log("Preluarea datei de azi: " + this.dataDeAzi.getDate() + " " + (Number(this.dataDeAzi.getMonth()) + 1) + " " + this.dataDeAzi.getFullYear());
    // console.log(this.butonBlocat);
  }

  comparaDouaDate(data1: Date, data2: Date): Boolean//daca data1<data2, return false. Else true
  {
    if (data1.getFullYear() < data2.getFullYear())
      return false;
    if (data1.getMonth() < data2.getMonth() && data1.getFullYear() == data2.getFullYear())
      return false;
    if (data1.getDate() < data2.getDate() && data1.getMonth() == data2.getMonth() && data1.getFullYear() == data2.getFullYear())
      return false;
    return true;

  }

}

