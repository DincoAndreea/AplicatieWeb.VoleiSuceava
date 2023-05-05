package ro.marcc.server.service;

import org.springframework.stereotype.Service;
import ro.marcc.server.dto.DetaliiClubDto;
import ro.marcc.server.repository.RepositoryDetaliiClub;


@Service
public class ServicesDetaliiClub {
    private final RepositoryDetaliiClub repositoryDetaliiClub;

    public ServicesDetaliiClub(RepositoryDetaliiClub repositoryDetaliiClub) {
        this.repositoryDetaliiClub = repositoryDetaliiClub;
    }

    public DetaliiClubDto getViziuneClub(){
        return repositoryDetaliiClub.getViziuneClub();
    }
    public DetaliiClubDto getIstoricClub(){
        return repositoryDetaliiClub.getIstoricClub();
    }
    public DetaliiClubDto getTrofeeClub(){
        return repositoryDetaliiClub.getTrofeeClub();
    }
}
