package ro.marcc.server.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ro.marcc.server.dto.DetaliiClubDto;
import ro.marcc.server.exception.DetaliiClubExpectationFailedException;
import ro.marcc.server.exception.DetaliiClubNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Repository
public class RepositoryDetaliiClub {
    private final EntityManager entityManager;

    public RepositoryDetaliiClub(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public DetaliiClubDto getViziuneClub(){
        try{
            List<Object[]> detaliiClubBD = entityManager.createNativeQuery("SELECT d.viz_descriere,d.viz_imagine FROM viziune d")
                    .getResultList();
            if(detaliiClubBD.isEmpty()){
                throw new DetaliiClubNotFoundException("GET. Nu s-au gasit detaliile referitoare la viziune club!");
            }
            Object[] detaliiClub = detaliiClubBD.get(0);
            DetaliiClubDto viziuneClub = new DetaliiClubDto(
                    (String)detaliiClub[0],
                    (String)detaliiClub[1]
            );
            log.info("GET. Detalii club referitoare la viziune preluate cu succes!");
            return viziuneClub;
        }catch(PersistenceException e){
            throw new DetaliiClubExpectationFailedException("GET. Nu s-au putut prelua detaliile referitoare la viziune club!");
        }
    }

    @Transactional
    public DetaliiClubDto getIstoricClub(){
        try{
            List<Object[]> detaliiClubBD = entityManager.createNativeQuery("SELECT d.ist_descriere,d.ist_imagine FROM istoric d")
                    .getResultList();
            if(detaliiClubBD.isEmpty()){
                throw new DetaliiClubNotFoundException("GET. Nu s-au gasit detaliile referitoare la istoric club!");
            }
            Object[] detaliiClub = detaliiClubBD.get(0);
            DetaliiClubDto viziuneClub = new DetaliiClubDto(
                    (String)detaliiClub[0],
                    (String)detaliiClub[1]
            );
            log.info("GET. Detalii club referitoare la istoric preluate cu succes!");
            return viziuneClub;
        }catch(PersistenceException e){
            throw new DetaliiClubExpectationFailedException("GET. Nu s-au putut prelua detaliile referitoare la istoric club!");
        }
    }

    @Transactional
    public DetaliiClubDto getTrofeeClub(){
        try{
            List<Object[]> detaliiClubBD = entityManager.createNativeQuery("SELECT d.tdc_descriere,d.tdc_imagine FROM trofee_detalii_club d")
                    .getResultList();
            if(detaliiClubBD.isEmpty()){
                throw new DetaliiClubNotFoundException("GET. Nu s-au gasit detaliile referitoare la trofee club!");
            }
            Object[] detaliiClub = detaliiClubBD.get(0);
            DetaliiClubDto viziuneClub = new DetaliiClubDto(
                    (String)detaliiClub[0],
                    (String)detaliiClub[1]
            );
            log.info("GET. Detalii club referitoare la trofee preluate cu succes!");
            return viziuneClub;
        }catch(PersistenceException e){
            throw new DetaliiClubExpectationFailedException("GET. Nu s-au putut prelua detaliile referitoare la trofee club!");
        }
    }


}
