package ro.marcc.server.dto;

import lombok.*;
import ro.marcc.server.model.Personal.Roluri.Antrenor;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RezultatFiltrareAntrenoriDto {
    private int numarPagini;
    private int numarElemente;

    private Set<Antrenor> antrenori;
}
