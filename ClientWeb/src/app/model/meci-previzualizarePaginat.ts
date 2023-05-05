import { Time } from "@angular/common";
import { IEchipa } from "./echipa";
import { IMeciPrevizualizare } from "./meci-previzualizare";

export interface IMeciPrevizualizarePaginat{
    meciuri:IMeciPrevizualizare[],
    numarPagini:number
}