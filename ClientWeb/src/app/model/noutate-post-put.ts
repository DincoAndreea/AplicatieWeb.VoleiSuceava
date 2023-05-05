export interface INoutatePostPut{
    id:number;
    imagini: Array<string>;
    titlu: string;
    descriere: string;
    hashtaguri: Array<string>;
    videoclipuri: Array<string>;
    dataPostare: Date;
    draft: boolean;
}