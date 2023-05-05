package ro.marcc.server.model.Personal.Roluri;
import ro.marcc.server.model.Meciuri.Echipa;
import ro.marcc.server.model.Personal.Persoana;
import ro.marcc.server.model.Realizari.Premiu;
import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;

public class Antrenor extends Persoana {
    private Echipa echipa;

    public Antrenor() {
    }

    public Antrenor(Integer id, String nume, String prenume, String linkImagine, String nationalitate, String post, String data_nasterii, int inaltime, String descriere, TreeMap<LocalDate, String> roluri, List<Premiu> premii, Echipa echipa,Integer lotSeniori) {
        super(id, nume, prenume, linkImagine, nationalitate, post, data_nasterii, inaltime, descriere, roluri, premii,lotSeniori);
        this.echipa = echipa;
    }

    public Antrenor(Persoana persoana,Echipa echipa){
        super(persoana.getId(),
                persoana.getNume(),
                persoana.getPrenume(),
                persoana.getLinkPoza(),
                persoana.getNationalitate(),
                persoana.getPost(),
                persoana.getDataNasterii(),
                persoana.getInaltime(),
                persoana.getDescriere(),
                persoana.getRoluri(),
                persoana.getPremii(),
                persoana.getLotSeniori());
        this.echipa = echipa;
    }

    public Echipa getEchipa() {
        return echipa;
    }

    public void setEchipa(Echipa echipa) {
        this.echipa = echipa;
    }
}
