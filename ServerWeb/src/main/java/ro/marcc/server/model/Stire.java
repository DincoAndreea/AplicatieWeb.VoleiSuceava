package ro.marcc.server.model;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.context.annotation.Configuration;
import ro.marcc.server.convertoare.StringListConvertor;
import ro.marcc.server.validators.interfaces.ConstrangereLinkuriMedia;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Configuration
@ToString
@NoArgsConstructor
@Entity(name="stire")
public class Stire {
    @Id
    @Column(name="sti_id")
    private Integer id;
    @NotNull
    @Column(name="sti_titlu")
    private String titlu;
    @NotNull
    @Column(name="sti_descriere")
    private String descriere;
    @NotNull
    @Convert(converter = StringListConvertor.class)
    @Column(name="sti_hastaguri")
    private List<String> hashtaguri;
    @ConstrangereLinkuriMedia
    @Transient
    private List<String> imagini;
    @ConstrangereLinkuriMedia
    @Transient
    private List<String> videoclipuri;

    @Column(name="sti_draft")
    private boolean draft;

    @Column(name="sti_data_postare")
    private LocalDate dataPostare;

    public Stire(Integer id, String titlu, String descriere, List<String> hashtaguri, List<String> imagini, List<String> videoclipuri, boolean draft, LocalDate dataPostare) {
        this.id = id;
        this.titlu = titlu;
        this.descriere = descriere;
        this.hashtaguri = hashtaguri;
        this.imagini = imagini;
        this.videoclipuri = videoclipuri;
        this.draft = draft;
        this.dataPostare = dataPostare;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public List<String> getHashtaguri() {
        return hashtaguri;
    }

    public void setHashtaguri(List<String> hashtaguri) {
        this.hashtaguri = hashtaguri;
    }

    public List<String> getImagini() {
        return imagini;
    }

    public void setImagini(List<String> imagini) {
        this.imagini = imagini;
    }

    public List<String> getVideoclipuri() {
        return videoclipuri;
    }

    public void setVideoclipuri(List<String> videoclipuri) {
        this.videoclipuri = videoclipuri;
    }

    public boolean isDraft() {
        return draft;
    }

    public void setDraft(boolean draft) {
        this.draft = draft;
    }

    public LocalDate getDataPostare() {
        return dataPostare;
    }

    public void setDataPostare(LocalDate dataPostare) {
        this.dataPostare = dataPostare;
    }

    public Character tipMedia(){
        if(imagini!=null && !imagini.isEmpty()){
            return 'i';
        }if(videoclipuri!=null && !videoclipuri.isEmpty()){
            return 'v';
        }
        return 't';
    }
}
