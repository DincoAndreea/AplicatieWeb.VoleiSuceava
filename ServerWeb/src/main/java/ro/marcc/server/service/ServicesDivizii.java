package ro.marcc.server.service;

import org.springframework.stereotype.Service;
import ro.marcc.server.model.Meciuri.Divizie;
import ro.marcc.server.repository.RepositoryDivizii;

import java.util.List;

@Service
public class ServicesDivizii {
    private RepositoryDivizii repositoryDivizii;

    public ServicesDivizii(RepositoryDivizii repositoryDivizii) {
        this.repositoryDivizii = repositoryDivizii;
    }
    public List<Divizie> getDivizii() {
        return repositoryDivizii.getDivizii();
    }

    public Divizie getDivizie(int idDivizie){
        return repositoryDivizii.getDivizie(idDivizie);
    }

    public boolean postDivizie(Divizie divizie){
        return repositoryDivizii.addDivizie(divizie);
    }

    public boolean putDivizie(int idDivizie,Divizie divizie){
        return repositoryDivizii.updateDivizie(idDivizie,divizie);
    }

    public boolean deleteDivizie(int idDivizie){
        return repositoryDivizii.deleteDivizie(idDivizie);
    }
}
