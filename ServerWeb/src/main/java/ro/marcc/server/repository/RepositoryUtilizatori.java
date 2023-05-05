package ro.marcc.server.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import ro.marcc.server.exception.PersonalExpectationFailedException;
import ro.marcc.server.exception.StireExpectationFailedException;
import javax.persistence.*;
import javax.transaction.Transactional;

@Slf4j
@Repository
@Configuration
public class RepositoryUtilizatori {
    private final EntityManager entityManager;

    public RepositoryUtilizatori(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public boolean addCreatorDeContinut(String nume,String numeUtilizator,String parola) {
        try {
            entityManager.createNativeQuery("INSERT INTO utilizator(uti_nume,uti_parola,uti_nume_utilizator,uti_rol) VALUES (?,?,?,?)")
                    .setParameter(1, nume)
                    .setParameter(2, parola)
                    .setParameter(3, numeUtilizator)
                    .setParameter(4, 'c')
                    .executeUpdate();
            log.info("POST. Utilizatorul "+numeUtilizator+" a fost inregistrat ca si creator de continut!");

            return true;
        }catch(RollbackException e){
            log.warn("POST. Utilizatorul "+numeUtilizator+" nu a putut fi inregistrat ca si creator de continut!");

            return false;
        }catch(PersistenceException e){
            throw new StireExpectationFailedException("POST. Utilizatorul "+numeUtilizator+" a fost inregistrat ca si creator de continut!");
        }catch(DataIntegrityViolationException e){
            throw new PersonalExpectationFailedException("POST. Utilizatorul "+numeUtilizator+" a fost inregistrat ca si creator de continut avand informatii lipsa sau necorespunzatoare!");
        }
    }
}
