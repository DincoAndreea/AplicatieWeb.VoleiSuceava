package ro.marcc.server.model.Localitate;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.context.annotation.Configuration;
import ro.marcc.server.validators.interfaces.ConstrangereDenumireOptionala;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Configuration
@ToString
@NoArgsConstructor
@Entity(name="tara")
public class Tara {
    @Id
    @Column(name = "tar_id")
    private Integer id;
    @ConstrangereDenumireOptionala
    @Column(name = "tar_tara")
    private String tara;

    public Tara(Integer id, String tara) {
        this.id = id;
        this.tara = tara;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTara() {
        return tara;
    }

    public void setTara(String tara) {
        this.tara = tara;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tara tara1 = (Tara) o;
        return Objects.equals(tara, tara1.tara);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tara);
    }
}
