package ro.marcc.server.service;

import org.springframework.stereotype.Service;
import ro.marcc.server.model.Meciuri.Campionat;
import ro.marcc.server.repository.RepositoryCampionat;

import java.util.List;

@Service
public class ServicesCampionat {
    private final RepositoryCampionat repositoryCampionat;

    public ServicesCampionat(RepositoryCampionat repositoryCampionat) {
        this.repositoryCampionat = repositoryCampionat;
    }
    public List<Campionat> getCampionate(){
        return repositoryCampionat.getCampionate();
    }
    public Campionat getCampionat(int idCampionat){
        return repositoryCampionat.getCampionat(idCampionat);
    }
    public boolean postCampionat(Campionat campionat){
        return repositoryCampionat.addCampionat(campionat);
    }
    public boolean putCampionat(int idCampionat,Campionat campionat){
        return repositoryCampionat.updateCampionat(idCampionat,campionat);
    }
    public boolean deleteCampionat(int idCampionat){
        return repositoryCampionat.deleteCampionat(idCampionat);
    }
}
