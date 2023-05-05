import { IPaginare } from "./Paginare";

export interface IMeciFiltruCuDataPaginat{
    idDivizie:number;
    tipMeci:string;
    dataIncepere:Date;
    dataSfarsit:Date;
    paginareDto:IPaginare;
}