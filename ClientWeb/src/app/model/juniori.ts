import { IPremiiLot } from "./premii-lot";

export interface IJuniori{
    id: number,
    lot: string;
    informatii: string;
    imagineLot: string;
    premiiJuniori: IPremiiLot[];
}