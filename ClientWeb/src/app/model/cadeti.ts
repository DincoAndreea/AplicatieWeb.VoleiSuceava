import { IPremiiLot } from "./premii-lot";

export interface ICadeti{
    id: number;
    lot: string;
    informatii: string;
    imagineLot: string;
    premiiCadeti: IPremiiLot[];
}