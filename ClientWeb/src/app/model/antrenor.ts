import { IEchipa } from "./echipa";
import { IPremiiP } from "./premii-persoane";

export interface IAntrenor{
    id: number;
    nume: string;
    prenume: string;
    dataNasterii: string;
    inaltime: number;
    post: string;
    descriere: string;
    nationalitate: string;
    linkPoza: string;
    premii: IPremiiP[];
    roluri: Map<Date, string>;
    lotSeniori: number;
    echipa: IEchipa;
}