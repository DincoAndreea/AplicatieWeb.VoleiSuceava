package ro.marcc.server.dto;

import lombok.*;
import ro.marcc.server.model.Personal.Roluri.Jucator;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SenioriDto {
    private String senioriLot;
    private String senioriImagine;
    private String senioriDetalii;
    private List<Jucator> senioriLista;
}
