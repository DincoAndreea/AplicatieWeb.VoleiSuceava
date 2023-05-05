package ro.marcc.server.model.VoleiJuvenil.Sperante;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.context.annotation.Configuration;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Configuration
@ToString
@NoArgsConstructor
@Entity(name="sperante_premii")
public class PremiiSperante {
    @Id
    @Column(name="spr_id")
    private Integer id;
    @Column(name="spr_denumire_premiu")
    private String denumirePremiu;
    @Column(name="spr_loc")
    @NotNull
    @Min(1)
    private Integer loc;
    @Column(name="spr_an")
    @Min(1)
    private Integer an;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spr_lot")
    private Sperante sperante;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public PremiiSperante(Integer id, String denumirePremiu, Integer loc, Integer an, Sperante sperante) {
        this.id = id;
        this.denumirePremiu = denumirePremiu;
        this.loc = loc;
        this.an = an;
        this.sperante = sperante;
    }
}
