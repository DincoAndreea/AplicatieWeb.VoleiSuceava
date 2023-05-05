package ro.marcc.server.dto.Meci;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RezultatPrevizualizareMeciuriDto {
    private int numarPagini;
    private int numarElemente;
    private List<MeciPrevizualizareDto> meciuri;
}
