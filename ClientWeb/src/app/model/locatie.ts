import { IJudet } from "./judet";

export interface ILocatie{
    id:number;
    localitate: string;
    judet: IJudet;
}