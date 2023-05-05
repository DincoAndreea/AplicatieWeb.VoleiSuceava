package ro.marcc.server.dto;

import lombok.*;
import ro.marcc.server.validators.interfaces.ConstrangereDenumireOptionala;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LocalitateDto {
    @ConstrangereDenumireOptionala
    private String localitate;
    @ConstrangereDenumireOptionala
    private String judet;
    @ConstrangereDenumireOptionala
    private String tara;
}
