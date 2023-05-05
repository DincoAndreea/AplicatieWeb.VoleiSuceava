package ro.marcc.server.dto.Stire;

import lombok.*;
import ro.marcc.server.dto.Stire.StireAdministrareDto;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RezultatFiltrareStiri {
    private int numarPagini;
    private List<StireAdministrareDto> stiri;
}
