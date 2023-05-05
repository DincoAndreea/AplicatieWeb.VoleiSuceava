package ro.marcc.server.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.marcc.server.dto.Utilizator.UtilizatorLoginDto;
import ro.marcc.server.dto.Utilizator.UtilizatorPreviewDto;
import ro.marcc.server.exception.UtilizatorExpectationFailedException;
import ro.marcc.server.model.Utilizator.Utilizator;
import ro.marcc.server.repository.RepositoryUtilizatori;
import ro.marcc.server.repository.jpa.RepositoryUtilizatoriJPA;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class ServicesUtilizator {
    private final RepositoryUtilizatoriJPA repositoryUtilizatorJPA;
    private final RepositoryUtilizatori repositoryUtilizatori;



    public ServicesUtilizator(RepositoryUtilizatoriJPA repositoryUtilizatorJPA, RepositoryUtilizatori repositoryUtilizatori) {
        this.repositoryUtilizatorJPA = repositoryUtilizatorJPA;
        this.repositoryUtilizatori = repositoryUtilizatori;
    }

    public Character autentificare(UtilizatorLoginDto utilizatorLoginDTO){
        Utilizator utilizatorCautat = repositoryUtilizatorJPA.getUtilizator(utilizatorLoginDTO.getNumeUtilizator(), utilizatorLoginDTO.getParola());
        if(utilizatorCautat!=null){
            log.info("GET. Autentificare utilizator "+utilizatorCautat.getNumeUtilizator()+" realizata cu succes!");
            return utilizatorCautat.getRol();
        }else{
            log.info("GET. Autentificare utilizator "+ utilizatorLoginDTO.getNumeUtilizator()+" a esuat!");
            return null;
        }
    }
    public List<UtilizatorPreviewDto> getCreatoriDeContinut(){
        return repositoryUtilizatorJPA.getCreatoriDeContinut().stream().map(UtilizatorPreviewDto::new).toList();
    }


    public boolean inregistrareCreatorDeContinut(String nume,String numeUtilizator,String parola) {
        Utilizator utilizator = repositoryUtilizatorJPA.getUtilizator(numeUtilizator);
        if(utilizator!=null){
            throw new UtilizatorExpectationFailedException("POST. Inregistrare utilizator esuata. Utilizatorul "+numeUtilizator+" deja exista.");
        }

        return repositoryUtilizatori.addCreatorDeContinut(nume,numeUtilizator,parola);
    }

    public boolean deleteCreatorDeContinut(Integer idUtilizator){
        Optional<Utilizator> utilizatorCautat = repositoryUtilizatorJPA.findById(idUtilizator);
        if(utilizatorCautat.isPresent() && utilizatorCautat.get().getRol()=='c') {
            return repositoryUtilizatorJPA.deleteUtilizator(idUtilizator)>0;

        }
        return false;
    }

}
