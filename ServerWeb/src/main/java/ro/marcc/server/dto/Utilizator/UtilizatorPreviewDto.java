package ro.marcc.server.dto.Utilizator;

import lombok.*;
import ro.marcc.server.model.Utilizator.Utilizator;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class UtilizatorPreviewDto {
    private int id;
    private String nume;
    private String numeUtilizator;


    public UtilizatorPreviewDto(int id, String nume, String numeUtilizator) {
        this.id = id;
        this.nume = nume;
        this.numeUtilizator = numeUtilizator;
    }
    public UtilizatorPreviewDto(Utilizator utilizator) {
        this.id = utilizator.getId();
        this.nume = utilizator.getNume();
        this.numeUtilizator = utilizator.getNumeUtilizator();
    }

}
