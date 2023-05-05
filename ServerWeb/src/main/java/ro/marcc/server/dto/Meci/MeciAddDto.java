package ro.marcc.server.dto.Meci;

import lombok.*;
import ro.marcc.server.model.Localitate.Localitate;
import ro.marcc.server.model.Meciuri.Echipa;
import ro.marcc.server.validators.interfaces.ConstrangereDenumireOptionala;
import javax.validation.Valid;
import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MeciAddDto {
    private Echipa[] echipe;
    private int[] scor;
    private LocalDateTime dataMeci;
    private Integer idCampionat;
    @Valid
    private Localitate locatie;
    private String link;
    private Integer idDivizie;
}
