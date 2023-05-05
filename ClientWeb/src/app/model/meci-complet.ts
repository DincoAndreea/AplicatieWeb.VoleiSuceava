import { ICampionat } from "./campionat";
import { IDivizii } from "./divizii";
import { IEchipa } from "./echipa";
import { ILocalitate } from "./localitate";

export interface IMeciC{
    id: number;
    scor: number[];
    link: string;
    divizie: IDivizii;
    locatie: ILocalitate;
    echipe: IEchipa[];
    campionat: ICampionat;
    dataMeci: Date;
}