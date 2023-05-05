import { IEchipa } from "./echipa";
import { ILocalitate } from "./localitate";

export interface IMeciA{
    id: number;
    scor: number[];
    link: string;
    idDivizie: number;
    locatie: ILocalitate;
    echipe: IEchipa[];
    idCampionat: number;
    dataMeci: Date;
}