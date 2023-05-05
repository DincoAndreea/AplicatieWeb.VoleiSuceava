import { IPremiiLot } from "./premii-lot";

export interface IUpdateMinivolei{
    id: number;
    lot: string;
    informatii: string;
    imagineLot: string;
    premiiMinivolei: IPremiiLot[];
}