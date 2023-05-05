package ro.marcc.server.dto.Utilizator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CreatorDeContinutAddDto {
    @NotNull
    @NotEmpty
    @Length(max=40)
    protected String nume;
    @NotNull
    @NotEmpty
    protected String parola;
    @NotNull
    @NotEmpty
    @Length(max=40)
    protected String numeUtilizator;
}
