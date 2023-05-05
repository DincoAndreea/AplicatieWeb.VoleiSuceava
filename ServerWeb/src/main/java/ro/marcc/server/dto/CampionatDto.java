package ro.marcc.server.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import ro.marcc.server.validators.interfaces.ConstrangereDenumireOptionala;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CampionatDto {
    @NotBlank
    @Length(min=2)
    private String denumire;
    @NotNull
    private LocalDate dataIncepere;
    @NotNull
    private LocalDate dataSfarsit;
    @ConstrangereDenumireOptionala
    private String localitate;
    @ConstrangereDenumireOptionala
    private String judet;
    @ConstrangereDenumireOptionala
    private String tara;
}
