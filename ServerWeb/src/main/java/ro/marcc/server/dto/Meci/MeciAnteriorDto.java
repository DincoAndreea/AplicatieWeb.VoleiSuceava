package ro.marcc.server.dto.Meci;

import lombok.*;
import ro.marcc.server.dto.EchipaDto;
import ro.marcc.server.model.Meciuri.Meci;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MeciAnteriorDto {
    private Integer id;
    private EchipaDto echipa1;
    private EchipaDto echipa2;
    private String scor;

    public MeciAnteriorDto(Meci meci) {
        this.id = meci.getId();
        this.echipa1 = new EchipaDto(meci.getEchipe()[0]);
        this.echipa2 = new EchipaDto(meci.getEchipe()[1]);
        this.scor = meci.getScor()[0] +"-"+meci.getScor()[1];
    }
}
