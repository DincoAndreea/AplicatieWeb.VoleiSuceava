import { IPremiiLot } from "./premii-lot";

export interface IUpdateCadeti{
    id: number;
    lot: string;
    informatii: string;
    imagineLot: string;
    premiiCadeti: IPremiiLot[];
}