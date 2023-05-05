package ro.marcc.server.model.VoleiJuvenil.Minivolei;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.context.annotation.Configuration;
import javax.persistence.*;
import java.util.Set;

@Configuration
@ToString
@NoArgsConstructor
@Entity(name="minivolei")
public class Minivolei {
    @Id
    @Column(name="min_id")
    private Integer id;
    @Column(name="min_lot")
    private String lot;
    @Column(name="min_imagine_lot")
    private String imagineLot;
    @Column(name="min_informatii")
    private String informatii;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "minivolei")
    private Set<PremiiMinivolei> premiiMinivolei;

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

    public Set<PremiiMinivolei> getPremiiMinivolei() {
        return premiiMinivolei;
    }

    public void setPremiiMinivolei(Set<PremiiMinivolei> premiiMinivolei) {
        this.premiiMinivolei = premiiMinivolei;
    }

    public Minivolei(Integer id, String lot, String imagineLot, String informatii, Set<PremiiMinivolei> premiiMinivolei) {
        this.id = id;
        this.lot = lot;
        this.imagineLot = imagineLot;
        this.informatii = informatii;
        this.premiiMinivolei = premiiMinivolei;
    }
}
