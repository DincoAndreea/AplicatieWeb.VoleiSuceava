package ro.marcc.server.model.Meciuri;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.context.annotation.Configuration;
import ro.marcc.server.dto.Meci.MeciAddDto;
import ro.marcc.server.model.Localitate.Localitate;
import ro.marcc.server.validators.interfaces.ConstrangereDenumireOptionala;
import ro.marcc.server.validators.interfaces.ConstrangereEchipeMeci;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Configuration
@ToString
@NoArgsConstructor
@Entity(name="meci")
public class Meci {
    @Id
    @Column(name = "mec_id")
    private Integer id;
    @Transient
    @ConstrangereEchipeMeci
    private Echipa[] echipe;
    @Transient
    private int[] scor;
    @Column(name ="mec_data_meci")
    private LocalDateTime dataMeci;
    @Transient
    private Campionat campionat;
    @Transient
    @Valid
    private Localitate locatie;
    @Column(name="mec_link")
    @NotNull
    private String link;
    @Transient
    private Divizie divizie;

    public Meci(Integer id, Echipa[] echipe, int[] scor, LocalDateTime dataMeci, Campionat campionat, Localitate locatie, String link, Divizie divizie) {
        this.id = id;
        this.echipe = echipe;
        this.scor = scor;
        this.dataMeci = dataMeci;
        this.campionat = campionat;
        this.locatie = locatie;
        this.link = link;
        this.divizie = divizie;
    }
    public Meci(MeciAddDto meci) {
        this.echipe = meci.getEchipe();
        this.scor = meci.getScor();
        this.dataMeci = meci.getDataMeci();
        this.campionat = new Campionat();
        this.campionat.setId(meci.getIdCampionat());
        this.locatie = meci.getLocatie();
        this.link = meci.getLink();
        this.divizie = new Divizie();
        this.divizie.setId(meci.getIdDivizie());
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Echipa[] getEchipe() {
        return echipe;
    }

    public void setEchipe(Echipa[] echipe) {
        this.echipe = echipe;
    }

    public int[] getScor() {
        return scor;
    }

    public void setScor(int[] scor) {
        this.scor = scor;
    }

    public LocalDateTime getDataMeci() {
        return dataMeci;
    }

    public void setDataMeci(LocalDateTime dataMeci) {
        this.dataMeci = dataMeci;
    }

    public Campionat getCampionat() {
        return campionat;
    }

    public void setCampionat(Campionat campionat) {
        this.campionat = campionat;
    }

    public Localitate getLocatie() {
        return locatie;
    }

    public void setLocatie(Localitate locatie) {
        this.locatie = locatie;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Divizie getDivizie() {
        return divizie;
    }

    public void setDivizie(Divizie divizie) {
        this.divizie = divizie;
    }
}
