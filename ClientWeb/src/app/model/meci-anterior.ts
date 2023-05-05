import { IEchipa } from "./echipa";

export interface IAnterior{
    id: number;
    echipa1: IEchipa;
    echipa2: IEchipa;
    scor: string;
}