package ro.marcc.server.model.Localitate;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.context.annotation.Configuration;
import ro.marcc.server.dto.LocalitateDto;
import ro.marcc.server.validators.interfaces.ConstrangereDenumireOptionala;

import javax.persistence.*;
import java.util.Objects;

@Configuration
@ToString
@NoArgsConstructor
@Entity(name="localitate")
public class Localitate {
    @Id
    @Column(name = "loc_id")
    private Integer id;
    @Column(name="loc_localitate")
    @ConstrangereDenumireOptionala
    private String localitate;

    @Transient
    private Judet judet;

    public Judet getJudet() {
        return judet;
    }

    public void setJudet(Judet judet) {
        this.judet = judet;
    }

    public Localitate(Integer id, String localitate, Judet judet) {
        this.id = id;
        this.localitate = localitate;
        this.judet = judet;
    }

    public Localitate(LocalitateDto localitateDto){
        this.localitate = localitateDto.getLocalitate();
        this.judet = new Judet(null,localitateDto.getJudet(), new Tara(null,localitateDto.getTara()));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocalitate() {
        return localitate;
    }

    public void setLocalitate(String localitate) {
        this.localitate = localitate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Localitate that = (Localitate) o;
        return Objects.equals(localitate, that.localitate) && Objects.equals(judet, that.judet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(localitate, judet);
    }
}
