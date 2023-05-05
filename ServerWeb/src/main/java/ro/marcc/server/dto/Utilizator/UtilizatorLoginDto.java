package ro.marcc.server.dto.Utilizator;

import lombok.*;
import ro.marcc.server.model.Utilizator.Utilizator;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UtilizatorLoginDto {
    private String numeUtilizator;
    private String parola;

    public UtilizatorLoginDto(Utilizator utilizator){
        this.numeUtilizator = utilizator.getNumeUtilizator();
        this.parola = utilizator.getParola();
    }
}
