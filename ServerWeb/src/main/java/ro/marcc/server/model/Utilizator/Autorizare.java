package ro.marcc.server.model.Utilizator;

import lombok.NoArgsConstructor;
import lombok.ToString;
import ro.marcc.server.convertoare.StringListConvertor;
import javax.persistence.*;
import java.util.List;

@ToString
@NoArgsConstructor
@Entity(name="autorizari")
public class Autorizare {
    @Id
    @Column(name = "aut_rol")
    private Character rol;
    @Column(name="aut_roluri")
    @Convert(converter = StringListConvertor.class)
    private List<String> autorizari;

    public Character getRol() {
        return rol;
    }

    public void setRol(Character rol) {
        this.rol = rol;
    }

    public List<String> getAutorizari() {
        return autorizari;
    }

    public void setAutorizari(List<String> autorizari) {
        this.autorizari = autorizari;
    }
}
