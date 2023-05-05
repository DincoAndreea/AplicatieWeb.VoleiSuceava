package ro.marcc.server.dto.Meci;

import lombok.*;
import ro.marcc.server.dto.EchipaDto;
import ro.marcc.server.model.Meciuri.Meci;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MeciViitorDto {
    private Integer id;
    private EchipaDto echipa1;
    private EchipaDto echipa2;

    public MeciViitorDto(Meci meci) {
        this.id = meci.getId();
        this.echipa1 = new EchipaDto(meci.getEchipe()[0]);
        this.echipa2 = new EchipaDto(meci.getEchipe()[1]);
    }

}
