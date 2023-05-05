import { IJudet } from "./judet";
import { ITara } from "./tara";

export interface ILocalitate{
    id: number;
    localitate: string;
    judet: IJudet;
}