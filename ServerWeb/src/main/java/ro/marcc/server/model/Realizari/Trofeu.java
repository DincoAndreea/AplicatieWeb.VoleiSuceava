package ro.marcc.server.model.Realizari;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.context.annotation.Configuration;
import ro.marcc.server.model.Meciuri.Campionat;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.util.Objects;

@Configuration
@ToString
@NoArgsConstructor
@Entity(name="trofeu")
public class Trofeu {
    @Id
    @Column(name="tro_id")
    private Integer id;

    @Column(name="tro_denumire")
    private String denumire;

    @Column(name="tro_data_acordare")
    private LocalDate dataAcordare;

    @Transient
    private Campionat campionat;

    public Trofeu(Integer id, String denumire, LocalDate dataAcordare, Campionat campionat) {
        this.id = id;
        this.denumire = denumire;
        this.dataAcordare = dataAcordare;
        this.campionat = campionat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public LocalDate getDataAcordare() {
        return dataAcordare;
    }

    public void setDataAcordare(LocalDate dataAcordare) {
        this.dataAcordare = dataAcordare;
    }

    public Campionat getCampionat() {
        return campionat;
    }

    public void setCampionat(Campionat campionat) {
        this.campionat = campionat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trofeu trofeu = (Trofeu) o;
        return Objects.equals(denumire, trofeu.denumire) && Objects.equals(dataAcordare, trofeu.dataAcordare) && Objects.equals(campionat, trofeu.campionat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(denumire, dataAcordare, campionat);
    }
}
