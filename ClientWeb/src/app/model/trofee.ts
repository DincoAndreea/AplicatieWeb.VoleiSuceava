import { ICampionat } from "./campionat";

export interface ITrofee{
    denumire: string;
    dataAcordare: Date;
    campionat: ICampionat;
}