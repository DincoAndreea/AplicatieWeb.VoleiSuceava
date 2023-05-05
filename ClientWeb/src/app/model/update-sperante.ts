import { IPremiiLot } from "./premii-lot";

export interface IUpdateSperante{
    id: number;
    lot: string;
    informatii: string;
    imagineLot: string;
    premiiSperante: IPremiiLot[];
}