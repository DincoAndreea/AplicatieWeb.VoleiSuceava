package ro.marcc.server.dto.Stire;

import lombok.*;
import ro.marcc.server.model.Stire;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StireDto {
    private int id;
    private String titlu;
    private String descriere;
    private String imagine;

    private LocalDate dataPostare;

    public StireDto(Stire stire){
        this.id = stire.getId();
        this.titlu = stire.getTitlu();
        this.descriere = stire.getDescriere().length()>50?stire.getDescriere().substring(0,50):stire.getDescriere();

        if(stire.getImagini()!=null && stire.getImagini().size()>0){
            this.imagine = stire.getImagini().get(0);
        }else{
            this.imagine = "";
        }
        this.dataPostare = stire.getDataPostare();
    }
}
