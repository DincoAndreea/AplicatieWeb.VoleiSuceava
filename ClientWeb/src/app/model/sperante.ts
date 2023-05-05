import { IPremiiLot } from "./premii-lot";

export interface ISperante{
    id: number,
    lot: string;
    informatii: string;
    imagineLot: string;
    premiiSperante: IPremiiLot[];
}