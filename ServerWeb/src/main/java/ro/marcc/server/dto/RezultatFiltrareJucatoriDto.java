package ro.marcc.server.dto;

import lombok.*;
import ro.marcc.server.model.Personal.Roluri.Jucator;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RezultatFiltrareJucatoriDto {
    private int numarPagini;
    private int numarElemente;
    private Set<Jucator> jucatori;

}
