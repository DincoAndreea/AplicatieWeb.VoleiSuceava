package ro.marcc.server.dto;

import lombok.*;
import ro.marcc.server.model.Meciuri.Echipa;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EchipaDto {
    private Integer id;
    private String nume;
    private String logo;
    private boolean csm;

    public EchipaDto(Echipa echipa){
        this.id = echipa.getId();
        this.nume = echipa.getNume();
        this.logo = echipa.getLogo();
        this.csm = echipa.isCsm();
    }
}
