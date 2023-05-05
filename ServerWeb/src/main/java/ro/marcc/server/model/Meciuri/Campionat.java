package ro.marcc.server.model.Meciuri;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.context.annotation.Configuration;
import ro.marcc.server.dto.CampionatDto;
import ro.marcc.server.model.Localitate.Judet;
import ro.marcc.server.model.Localitate.Localitate;
import ro.marcc.server.model.Localitate.Tara;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Configuration
@ToString
@NoArgsConstructor
@Entity(name="campionat")
public class Campionat {
    @Id
    @Column(name="cam_id")
    private Integer id;

    @Column(name="cam_denumire")
    private String denumire;

    @Column(name="cam_data_incepere")
    private LocalDate dataIncepere;

    @Column(name="cam_data_sfarsit")
    private LocalDate dataSfarsit;
    @Transient
    private Localitate localitate;
    @Transient
    private List<Meci> meciuri;

    public Campionat(Integer id, String denumire, LocalDate dataIncepere, LocalDate dataSfarsit, Localitate localitate, List<Meci> meciuri) {
        this.id = id;
        this.denumire = denumire;
        this.dataIncepere = dataIncepere;
        this.dataSfarsit = dataSfarsit;
        this.localitate = localitate;
        this.meciuri = meciuri;
    }
    public Campionat(CampionatDto campionatDto) {

        this.denumire = campionatDto.getDenumire();
        this.dataIncepere = campionatDto.getDataIncepere();
        this.dataSfarsit = campionatDto.getDataSfarsit();
        this.localitate = new Localitate(null,campionatDto.getLocalitate(),
                new Judet(null,campionatDto.getJudet(),
                        new Tara(null,campionatDto.getTara())));
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

    public LocalDate getDataIncepere() {
        return dataIncepere;
    }

    public void setDataIncepere(LocalDate dataIncepere) {
        this.dataIncepere = dataIncepere;
    }

    public LocalDate getDataSfarsit() {
        return dataSfarsit;
    }

    public void setDataSfarsit(LocalDate dataSfarsit) {
        this.dataSfarsit = dataSfarsit;
    }

    public Localitate getLocalitate() {
        return localitate;
    }

    public void setLocalitate(Localitate localitate) {
        this.localitate = localitate;
    }

    public List<Meci> getMeciuri() {
        return meciuri;
    }

    public void setMeciuri(List<Meci> meciuri) {
        this.meciuri = meciuri;
    }
}
