package ro.marcc.server.model.VoleiJuvenil.Sperante;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.context.annotation.Configuration;
import javax.persistence.*;
import java.util.Set;

@Configuration
@ToString
@NoArgsConstructor
@Entity(name="sperante")
public class Sperante {
    @Id
    @Column(name="spe_id")
    private Integer id;
    @Column(name="spe_lot")
    private String lot;
    @Column(name="spe_imagine_lot")
    private String imagineLot;
    @Column(name="spe_informatii")
    private String informatii;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sperante")
    private Set<PremiiSperante> premiiSperante;

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

    public Set<PremiiSperante> getPremiiSperante() {
        return premiiSperante;
    }

    public void setPremiiSperante(Set<PremiiSperante> premiiSperante) {
        this.premiiSperante = premiiSperante;
    }

    public Sperante(Integer id, String lot, String imagineLot, String informatii, Set<PremiiSperante> premiiSperante) {
        this.id = id;
        this.lot = lot;
        this.imagineLot = imagineLot;
        this.informatii = informatii;
        this.premiiSperante = premiiSperante;
    }
}
