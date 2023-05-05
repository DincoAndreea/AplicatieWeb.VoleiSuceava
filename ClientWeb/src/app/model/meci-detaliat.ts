import { ICampionat } from "./campionat";
import { IDivizii } from "./divizii";
import { IEchipa } from "./echipa";
import { ILocatie } from "./locatie";

export interface IMeciDetaliat{
    id:number;
    campionat:ICampionat;//nu e complet
    dataMeci: Date;
    divizie: IDivizii;
    echipe:IEchipa[];//aici nu e complet
    link:string;
    locatie: ILocatie;
    scor:number[];
}