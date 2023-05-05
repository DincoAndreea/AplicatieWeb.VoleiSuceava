package ro.marcc.server.model.VoleiJuvenil.Juniori;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.context.annotation.Configuration;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Configuration
@ToString
@NoArgsConstructor
@Entity(name="juniori_premii")
public class PremiiJuniori {
    @Id
    @Column(name="jpr_id")
    private Integer id;
    @Column(name="jpr_denumire_premiu")
    private String denumirePremiu;
    @Column(name="jpr_loc")
    @NotNull
    @Min(1)
    private Integer loc;
    @Column(name="jpr_an")
    @Min(1)
    private Integer an;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jpr_lot")
    private Juniori juniori;

    public int getId() {
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

    public PremiiJuniori(Integer id, String denumirePremiu, Integer loc, Integer an, Juniori juniori) {
        this.id = id;
        this.denumirePremiu = denumirePremiu;
        this.loc = loc;
        this.an = an;
        this.juniori = juniori;
    }
}
