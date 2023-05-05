import { ILocalitate } from "./localitate";

export interface ICampionat{
    id: number;
    denumire: string;
    dataIncepere: Date;
    dataSfarsit: Date;
    localitate: ILocalitate;
}