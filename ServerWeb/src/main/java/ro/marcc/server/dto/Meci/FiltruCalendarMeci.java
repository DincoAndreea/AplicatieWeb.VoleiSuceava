package ro.marcc.server.dto.Meci;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import ro.marcc.server.dto.PaginareDto;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FiltruCalendarMeci {
    private Integer idDivizie;

    /*
    * tip meci:
    *           - A: meciuri anterioare
    *           - V: meciuri viitoare
    *           - L: meciuri in derulare (live)
    * */
    @Length(max = 1,message="tipMeci poate primi ca valoarea doar: A, V sau L")
    private String tipMeci;
    private LocalDate dataIncepere;
    private LocalDate dataSfarsit;

    private PaginareDto paginareDto;
    public String getTipMeci() {
        return tipMeci!=null?tipMeci.toUpperCase():null;
    }


}
