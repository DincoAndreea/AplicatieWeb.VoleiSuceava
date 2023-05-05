package ro.marcc.server.dto.Meci;

import lombok.*;
import ro.marcc.server.model.Meciuri.Meci;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RezultatFiltrareMeciuriDto {
    private int numarPagini;
    private int numarElemente;
    private List<Meci> meciuri;
}
