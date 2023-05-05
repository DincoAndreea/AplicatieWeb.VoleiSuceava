package ro.marcc.server.model;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.context.annotation.Configuration;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Configuration
@ToString
@NoArgsConstructor
@Entity(name="sponsori")
public class Sponsor {
    @Id
    @Column(name="spo_id")
    private Integer id;
    @NotNull
    @NotEmpty
    @Column(name="spo_nume")
    private String nume;

    @NotNull
    @NotEmpty
    @Column(name="spo_editie")
    private String editie;
    @NotNull
    @Column(name="spo_link")
    private String urlLink;
    @NotNull
    @Transient
    @Column(name="spo_id_logo")
    private String logo;

    public Sponsor(int id, String nume, String editie, String urlLink, String logo) {
        this.id = id;
        this.nume = nume;
        this.editie = editie;
        this.urlLink = urlLink;
        this.logo = logo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getEditie() {
        return editie;
    }

    public void setEditie(String editie) {
        this.editie = editie;
    }

    public String getUrlLink() {
        return urlLink;
    }

    public void setUrlLink(String urlLink) {
        this.urlLink = urlLink;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }


}
