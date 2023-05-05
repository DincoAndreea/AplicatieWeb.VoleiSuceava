package ro.marcc.server.model.VoleiJuvenil.Cadeti;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.context.annotation.Configuration;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Configuration
@ToString
@NoArgsConstructor
@Entity(name="cadeti_premii")
public class PremiiCadeti {
    @Id
    @Column(name="cpr_id")
    private Integer id;
    @Column(name="cpr_denumire_premiu")
    private String denumirePremiu;
    @Column(name="cpr_loc")
    @NotNull
    @Min(1)
    private Integer loc;
    @Column(name="cpr_an")
    @Min(1)
    private Integer an;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cpr_lot")
    private Cadeti cadeti;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDenumirePremiu() {
        return denumirePremiu;
    }

    public void setDenumirePremiu(String denumirePremiu) {
        this.denumirePremiu = denumirePremiu;
    }

    public Integer getLoc() {
        return loc;
    }

    public void setLoc(Integer loc) {
        this.loc = loc;
    }

    public Integer getAn() {
        return an;
    }

    public void setAn(Integer an) {
        this.an = an;
    }

    public PremiiCadeti(Integer id, String denumirePremiu, Integer loc, Integer an, Cadeti cadeti) {
        this.id = id;
        this.denumirePremiu = denumirePremiu;
        this.loc = loc;
        this.an = an;
        this.cadeti = cadeti;
    }
}
