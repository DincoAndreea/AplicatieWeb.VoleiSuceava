package ro.marcc.server.model.Localitate;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.context.annotation.Configuration;
import ro.marcc.server.validators.interfaces.ConstrangereDenumireOptionala;

import javax.persistence.*;
import java.util.Objects;

@Configuration
@ToString
@NoArgsConstructor
@Entity(name="judet")
public class Judet {
    @Id
    @Column(name ="jud_id")
    private Integer id;

    @ConstrangereDenumireOptionala
    @Column(name= "jud_judet")
    private String judet;

    @Transient
    private Tara tara;



    public Judet(Integer id, String judet, Tara tara) {
        this.id = id;
        this.judet = judet;
        this.tara = tara;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJudet() {
        return judet;
    }

    public void setJudet(String judet) {
        this.judet = judet;
    }

    public Tara getTara() {
        return tara;
    }

    public void setTara(Tara tara) {
        this.tara = tara;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Judet judet1 = (Judet) o;
        return Objects.equals(judet, judet1.judet) && Objects.equals(tara, judet1.tara);
    }

    @Override
    public int hashCode() {
        return Objects.hash(judet, tara);
    }
}
