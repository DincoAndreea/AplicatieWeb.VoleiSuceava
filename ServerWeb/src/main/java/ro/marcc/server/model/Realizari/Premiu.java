package ro.marcc.server.model.Realizari;

import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;

@Configuration
@NoArgsConstructor
@Entity(name="premiu")
public class Premiu {
    @Id
    @Column(name="pre_id")
    private Integer id;

    @Column(name="pre_denumire")
    private String denumire;

    @Column(name="pre_data_acordare")
    private LocalDate dataAcordare;

    public Premiu(Integer id, String denumire, LocalDate dataAcordare) {
        this.id = id;
        this.denumire = denumire;
        this.dataAcordare = dataAcordare;
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



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Premiu premiu = (Premiu) o;
        return Objects.equals(denumire, premiu.denumire) && Objects.equals(dataAcordare, premiu.dataAcordare);
    }

    @Override
    public int hashCode() {
        return Objects.hash(denumire, dataAcordare);
    }

    @Override
    public String toString() {
        return "Premiu{" +
                "id=" + id +
                ", denumire='" + denumire + '\'' +
                ", dataAcordare=" + dataAcordare +
                '}';
    }
}
