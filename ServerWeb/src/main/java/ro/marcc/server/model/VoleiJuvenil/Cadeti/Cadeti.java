package ro.marcc.server.model.VoleiJuvenil.Cadeti;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.context.annotation.Configuration;
import javax.persistence.*;
import java.util.Set;

@Configuration
@ToString
@NoArgsConstructor
@Entity(name="cadeti")
public class Cadeti {
    @Id
    @Column(name="cad_id")
    private Integer id;
    @Column(name="cad_lot")
    private String lot;
    @Column(name="cad_imagine_lot")
    private String imagineLot;
    @Column(name="cad_informatii")
    private String informatii;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cadeti")
    private Set<PremiiCadeti> premiiCadeti;

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

    public Set<PremiiCadeti> getPremiiCadeti() {
        return premiiCadeti;
    }

    public void setPremiiCadeti(Set<PremiiCadeti> premiiCadeti) {
        this.premiiCadeti = premiiCadeti;
    }

    public Cadeti(Integer id, String lot, String imagineLot, String informatii, Set<PremiiCadeti> premiiCadeti) {
        this.id = id;
        this.lot = lot;
        this.imagineLot = imagineLot;
        this.informatii = informatii;
        this.premiiCadeti = premiiCadeti;
    }
}
