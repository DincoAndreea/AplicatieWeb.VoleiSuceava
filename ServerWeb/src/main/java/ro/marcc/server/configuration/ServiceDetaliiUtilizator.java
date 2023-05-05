package ro.marcc.server.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ro.marcc.server.exception.UtilizatorExpectationFailedException;
import ro.marcc.server.model.Utilizator.Autorizare;
import ro.marcc.server.model.Utilizator.Utilizator;
import ro.marcc.server.repository.jpa.RepositoryAutorizariJPA;
import ro.marcc.server.repository.jpa.RepositoryUtilizatoriJPA;
@Slf4j
public class ServiceDetaliiUtilizator implements UserDetailsService {
    @Autowired
    private RepositoryUtilizatoriJPA repositoryUtilizatori;
    @Autowired
    private RepositoryAutorizariJPA repositoryAutorizari;
    @Override
    public UserDetails loadUserByUsername(String numeUtilizator) throws UsernameNotFoundException {
        Utilizator utilizator = repositoryUtilizatori.getUtilizator(numeUtilizator);
        if(utilizator == null){
            throw new UsernameNotFoundException("Nu s-a putut gasi utilizatorul "+numeUtilizator);
        }
        Autorizare autorizare = repositoryAutorizari.getAutorizare(utilizator.getRol());
        if(autorizare ==null){
            throw new UtilizatorExpectationFailedException("Nu s-au putut prelua autorizarile utilizatorului "+numeUtilizator);
        }
        utilizator.setAutorizari(autorizare.getAutorizari());

        return new DetaliiUtilizator(utilizator);
    }
}
