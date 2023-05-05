import { IPremiiLot } from "./premii-lot";

export interface IUpdateJuniori{
    id: number;
    lot: string;
    informatii: string;
    imagineLot: string;
    premiiJuniori: IPremiiLot[];
}