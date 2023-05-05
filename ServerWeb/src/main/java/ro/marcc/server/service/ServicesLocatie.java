package ro.marcc.server.service;

import org.springframework.stereotype.Service;
import ro.marcc.server.model.Localitate.Localitate;
import ro.marcc.server.repository.RepositoryLocatie;

import java.util.List;

@Service
public class ServicesLocatie {
    private RepositoryLocatie repositoryLocatie;

    public ServicesLocatie(RepositoryLocatie repositoryLocatie) {
        this.repositoryLocatie = repositoryLocatie;
    }

    public List<Localitate> getLocalitati(){
        return repositoryLocatie.getLocalitati();
    }
    public Localitate getLocalitate(int idLocalitate){
        return repositoryLocatie.getLocalitate(idLocalitate);
    }

    public boolean postLocalitate(Localitate localitate){
        return repositoryLocatie.addLccalitate(localitate);
    }
    public boolean putLocalitate(int idLocalitate,Localitate localitate){
        return repositoryLocatie.updateLocalitate(idLocalitate,localitate);
    }

    public boolean deleteLocalitate(int idLocalitate){
        return repositoryLocatie.deleteLocalitate(idLocalitate);
    }
}
