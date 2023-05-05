package ro.marcc.server.model.VoleiJuvenil.Juniori;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.context.annotation.Configuration;
import javax.persistence.*;
import java.util.Set;

@Configuration
@ToString
@NoArgsConstructor
@Entity(name="juniori")
public class Juniori {
    @Id
    @Column(name="jun_id")
    private Integer id;
    @Column(name="jun_lot")
    private String lot;
    @Column(name="jun_imagine_lot")
    private String imagineLot;
    @Column(name="jun_informatii")
    private String informatii;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "juniori")
    private Set<PremiiJuniori> premiiJuniori;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getImagineLot() {
        return imagineLot;
    }

    public void setImagineLot(String imagineLot) {
        this.imagineLot = imagineLot;
    }

    public String getInformatii() {
        return informatii;
    }

    public void setInformatii(String informatii) {
        this.informatii = informatii;
    }

    public Set<PremiiJuniori> getPremiiJuniori() {
        return premiiJuniori;
    }

    public void setPremiiJuniori(Set<PremiiJuniori> premiiJuniori) {
        this.premiiJuniori = premiiJuniori;
    }

    public Juniori(Integer id, String lot, String imagineLot, String informatii, Set<PremiiJuniori> premiiJuniori) {
        this.id = id;
        this.lot = lot;
        this.imagineLot = imagineLot;
        this.informatii = informatii;
        this.premiiJuniori = premiiJuniori;
    }
}
