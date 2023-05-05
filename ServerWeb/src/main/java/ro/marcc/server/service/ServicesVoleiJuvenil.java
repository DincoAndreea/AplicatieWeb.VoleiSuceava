package ro.marcc.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.marcc.server.model.VoleiJuvenil.Cadeti.Cadeti;
import ro.marcc.server.model.VoleiJuvenil.Juniori.Juniori;
import ro.marcc.server.model.VoleiJuvenil.Minivolei.Minivolei;
import ro.marcc.server.model.VoleiJuvenil.Sperante.Sperante;
import ro.marcc.server.repository.RepositoryCadeti;
import ro.marcc.server.repository.RepositoryJuniori;
import ro.marcc.server.repository.RepositoryMinivolei;
import ro.marcc.server.repository.RepositorySperante;

import java.util.List;

@Slf4j
@Service
public class ServicesVoleiJuvenil {

    private final RepositoryCadeti repositoryCadeti;
    private final RepositoryJuniori repositoryJuniori;
    private final RepositoryMinivolei repositoryMinivolei;
    private final RepositorySperante repositorySperanje;


    public ServicesVoleiJuvenil(RepositoryCadeti repositoryCadeti, RepositoryJuniori repositoryJuniori, RepositoryMinivolei repositoryMinivolei, RepositorySperante repositorySperanje) {
        this.repositoryCadeti = repositoryCadeti;
        this.repositoryJuniori = repositoryJuniori;
        this.repositoryMinivolei = repositoryMinivolei;
        this.repositorySperanje = repositorySperanje;
    }

    public List<Cadeti> getCadeti(){
        return repositoryCadeti.getCadeti();
    }

    public List<Juniori> getJuniori(){
        return repositoryJuniori.getJuniori();
    }

    public List<Minivolei> getMinivolei(){
        return repositoryMinivolei.getMinivolei();
    }

    public List<Sperante> getSperante(){
        return repositorySperanje.getSperante();
    }

    public Cadeti getCadeti(Integer id){
        return repositoryCadeti.getCadeti(id);
    }
    public Juniori getJuniori(Integer id){
        return repositoryJuniori.getJuniori(id);
    }
    public Minivolei getMinivolei(Integer id){
        return repositoryMinivolei.getMinivolei(id);
    }
    public Sperante getSperante(Integer id){
        return repositorySperanje.getSperante(id);
    }

    public boolean addCadeti(Cadeti cadeti){
        return repositoryCadeti.addCadeti(cadeti);
    }

    public boolean addJuniori(Juniori juniori){
        return repositoryJuniori.addJuniori(juniori);
    }
    public boolean addMinivolei(Minivolei minivolei){
        return repositoryMinivolei.addMinivolei(minivolei);
    }

    public boolean addSperante(Sperante sperante){
        return repositorySperanje.addSperante(sperante);
    }
    public boolean updateCadeti(Integer id,Cadeti cadetiActualizati){
        return repositoryCadeti.updateCadeti(id,cadetiActualizati);
    }
    public boolean updateJuniori(Integer id,Juniori junioriActualizati){
        return repositoryJuniori.updateJuniori(id,junioriActualizati);
    }
    public boolean updateMinivolei(Integer id,Minivolei minivoleiActualizati){
        return repositoryMinivolei.updateMinivolei(id,minivoleiActualizati);
    }
    public boolean updateSperante(Integer id,Sperante speranteActualizati){
        return repositorySperanje.updateSperante(id,speranteActualizati);
    }
    public boolean deleteCadeti(Integer id){
        return repositoryCadeti.deleteCadeti(id);
    }
    public boolean deleteJuniori(Integer id){
        return repositoryJuniori.deleteJuniori(id);
    }
    public boolean deleteMinivolei(Integer id){
        return repositoryMinivolei.deleteMinivolei(id);
    }
    public boolean deleteSperante(Integer id){
        return repositorySperanje.deleteSperante(id);
    }

}
