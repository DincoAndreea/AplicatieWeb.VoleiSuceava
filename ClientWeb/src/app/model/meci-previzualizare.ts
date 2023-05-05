import { Time } from "@angular/common";
import { IEchipa } from "./echipa";

export interface IMeciPrevizualizare{
    id: number;
    echipa1: IEchipa;
    echipa2: IEchipa;
    scor: string;
    dataMeci: Date;
    oraMeci: Date;
    castigator: IEchipa;
}