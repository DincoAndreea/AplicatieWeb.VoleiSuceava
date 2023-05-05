package ro.marcc.server.model.Personal;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.context.annotation.Configuration;
import ro.marcc.server.model.Realizari.Premiu;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
@Configuration
@ToString
@NoArgsConstructor
@Entity(name="persoana")
public class Persoana {
    @Id
    @Column(name="per_id")
    protected Integer id;

    @NotNull
    @NotEmpty
    @Column(name="per_nume")
    protected String nume;
    @NotNull
    @NotEmpty
    @Column(name="per_prenume")
    protected String prenume;

    @Column(name="per_link_poza")
    protected String linkPoza;
    @NotNull
    @NotEmpty
    @Column(name="per_nationalitate")
    protected String nationalitate;
    @NotNull
    @NotEmpty
    @Column(name="per_post")
    protected String post;
    @NotNull
    @NotEmpty
    @Column(name="per_data_nasterii")
    protected String dataNasterii;

    @Column(name="per_inaltime")
    protected int inaltime;
    @NotNull
    @NotEmpty
    @Column(name="per_descriere")
    protected String descriere;
    @Transient
    protected TreeMap<LocalDate,String> roluri;
    @Transient
    protected List<Premiu> premii;

    @Column(name="per_seniori")
    protected Integer lotSeniori;

    public Persoana(Integer id, String nume, String prenume, String linkPoza, String nationalitate, String post, String dataNasterii, int inaltime, String descriere, TreeMap<LocalDate, String> roluri, List<Premiu> premii, Integer lotSeniori) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.linkPoza = linkPoza;
        this.nationalitate = nationalitate;
        this.post = post;
        this.dataNasterii = dataNasterii;
        this.inaltime = inaltime;
        this.descriere = descriere;
        this.roluri = roluri;
        this.premii = premii;
        this.lotSeniori = lotSeniori;
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

    public String getPrenume() {
        return prenume;
    }

    public String getLinkPoza() {
        return linkPoza;
    }

    public void setLinkPoza(String linkPoza) {
        this.linkPoza = linkPoza;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getNationalitate() {
        return nationalitate;
    }

    public void setNationalitate(String nationalitate) {
        this.nationalitate = nationalitate;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getDataNasterii() {
        return dataNasterii;
    }

    public void setDataNasterii(String dataNasterii) {
        this.dataNasterii = dataNasterii;
    }

    public int getInaltime() {
        return inaltime;
    }

    public void setInaltime(int inaltime) {
        this.inaltime = inaltime;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public TreeMap<LocalDate, String> getRoluri() {
        return roluri;
    }

    public void setRoluri(TreeMap<LocalDate, String> roluri) {
        this.roluri = roluri;
    }

    public List<Premiu> getPremii() {
        return premii;
    }

    public void setPremii(List<Premiu> premii) {
        this.premii = premii;
    }

    public Integer getLotSeniori() {
        return lotSeniori;
    }

    public void setLotSeniori(Integer lotSeniori) {
        this.lotSeniori = lotSeniori;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persoana persoana = (Persoana) o;
        return Objects.equals(nume, persoana.nume) && Objects.equals(prenume, persoana.prenume)  && Objects.equals(linkPoza, persoana.linkPoza) && Objects.equals(nationalitate, persoana.nationalitate) && Objects.equals(post, persoana.post) && Objects.equals(dataNasterii, persoana.dataNasterii) && Objects.equals(descriere, persoana.descriere);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nume, prenume, linkPoza, nationalitate, post, dataNasterii, descriere);
    }
}
