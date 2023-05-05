package ro.marcc.server.model.Meciuri;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.context.annotation.Configuration;
import ro.marcc.server.model.Personal.Roluri.Antrenor;
import ro.marcc.server.model.Personal.Roluri.Jucator;
import ro.marcc.server.model.Realizari.Trofeu;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.List;

@Configuration
@ToString
@NoArgsConstructor
@Entity(name="echipa")
public class Echipa {
    @Id
    @Column(name="ech_id")
    private Integer id;
    @Column(name="ech_nume")
    private String nume;
    @Transient
    @NotNull
    private List<Jucator> jucatori;

    @Transient
    private Antrenor antrenor;
    @Transient
    private List<Trofeu> trofee;
    @Column(name="ech_logo")
    private String logo;

    //Atribut de verificare al faptului ca echipa apartine clubului sportiv municipal Suceava
    @Column(name="ech_csm")
    private boolean csm;

    public Echipa(Integer id, String nume, List<Jucator> jucatori, Antrenor antrenor, List<Trofeu> trofee, String logo, boolean csm) {
        this.id = id;
        this.nume = nume;
        this.jucatori = jucatori;
        this.antrenor = antrenor;
        this.trofee = trofee;
        this.logo = logo;
        this.csm = csm;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Jucator> getJucatori() {
        return jucatori;
    }

    public void setJucatori(List<Jucator> jucatori) {
        this.jucatori = jucatori;
    }

    public Antrenor getAntrenor() {
        return antrenor;
    }

    public void setAntrenor(Antrenor antrenor) {
        this.antrenor = antrenor;
    }

    public List<Trofeu> getTrofee() {
        return trofee;
    }

    public void setTrofee(List<Trofeu> trofee) {
        this.trofee = trofee;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public boolean isCsm() {
        return csm;
    }

    public void setCsm(boolean csm) {
        this.csm = csm;
    }
}
