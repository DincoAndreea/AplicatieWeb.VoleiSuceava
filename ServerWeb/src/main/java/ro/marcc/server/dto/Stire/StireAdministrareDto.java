package ro.marcc.server.dto.Stire;

import lombok.*;
import ro.marcc.server.model.Stire;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StireAdministrareDto {
    private int id;
    private String titlu;
    private LocalDate dataPostare;

    public StireAdministrareDto(Stire stire){
        this.id = stire.getId();
        this.titlu = stire.getTitlu();
        this.dataPostare = stire.getDataPostare();
    }
}
