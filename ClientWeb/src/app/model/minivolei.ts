import { IPremiiLot } from "./premii-lot";

export interface IMinivolei{
    id: number,
    lot: string;
    informatii: string;
    imagineLot: string;
    premiiMinivolei: IPremiiLot[];
}