package ro.marcc.server.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import ro.marcc.server.exception.MeciExpectationFailedException;
import ro.marcc.server.exception.PersonalExpectationFailedException;
import ro.marcc.server.model.Meciuri.Divizie;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class RepositoryDivizii {
    private final EntityManager entityManager;

    public RepositoryDivizii(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public List<Divizie> getDivizii(){
        List<Divizie> divizii = new ArrayList<>();
        try {
            List<Object[]> diviziiBD = entityManager.createNativeQuery("SELECT * FROM divizie")
                    .getResultList();

            if(diviziiBD.isEmpty()){
                log.info("GET. Nu s-a gasit nici o divizie de preluat!");
            }else{
                for (Object[] object:diviziiBD) {
                    Integer idDivizie = (Integer) object[0];
                    String numeDivizie = (String) object[1];

                    divizii.add(new Divizie(idDivizie,numeDivizie));
                }
                log.info("GET. Preluare lista de divizii realizata cu succes!");
            }


        }catch(PersistenceException e){
            log.warn("GET. Nu s-a putut prelua lista de divizii!");
        }


        return divizii;
    }

    @Transactional
    public Divizie getDivizie(int idDivizie){
        Divizie divizie = null;
        try {
            Object divizieBD = entityManager.createNativeQuery("SELECT d.div_nume FROM divizie d WHERE d.div_id=?")
                    .setParameter(1, idDivizie)
                    .getSingleResult();

            if (divizieBD!=null) {
                String denumire = (String) divizieBD;

                divizie = new Divizie(idDivizie,denumire);

                log.info("GET. Preluarea divizie cu id-ul "+idDivizie+" realizata cu succes!");
            }
        }catch(PersistenceException e){
            log.warn("GET. Nu s-a putut prelua divizia cu id-ul "+idDivizie+"!");
        }
        return divizie;
    }

    @Transactional
    public boolean addDivizie(Divizie divizie){
        if(divizie!=null){
                try {
                    int rezultat = 0;
                    if(divizie.getId()==null) {
                        rezultat = entityManager.createNativeQuery("INSERT INTO divizie(div_nume) VALUES (?)")
                                .setParameter(1, divizie.getNume())
                                .executeUpdate();
                    }else {
                        rezultat = entityManager.createNativeQuery("INSERT INTO divizie(div_id,div_nume) VALUES (?,?)")
                                .setParameter(1, divizie.getId())
                                .setParameter(2, divizie.getNume())
                                .executeUpdate();
                    }

                    if (rezultat > 0) {
                        log.info("POST. Divizia a fost inregistrata cu succes!");
                        return true;
                    } else {
                        log.warn("POST. Divizia nu a putut fi inregistrata!");
                        return false;
                    }
                } catch (PersistenceException e) {
                    throw new MeciExpectationFailedException("POST. Divizia nu a putut fi inregistrata!");
                }catch(DataIntegrityViolationException e){
                    throw new PersonalExpectationFailedException("POST. Divizia nu a putut fi inregistrata avand informatii lipsa sau necorespunzatoare!");
                }
        }else{
            log.warn("INSERT. Parametrul divizie nu poate primi valori nule!");
        }

        return false;
    }

    @Transactional
    public boolean updateDivizie(int idDivizie, Divizie divizieActualizata) {
        if (divizieActualizata != null) {

                try {
                    int rezultat = entityManager.createNativeQuery("UPDATE divizie d SET d.div_nume=?2 WHERE d.div_id=?1")
                            .setParameter(1, idDivizie)
                            .setParameter(2, divizieActualizata.getNume())
                            .executeUpdate();
                    if (rezultat > 0) {
                        log.info("PUT. Divizia cu id-ul " + idDivizie + " a fost actualizata cu succes!");
                        return true;
                    } else {
                        log.warn("PUT. Divizia cu id-ul " + idDivizie + " nu a putut fi actualizata!");
                        return false;
                    }
                } catch (PersistenceException e) {
                    throw new MeciExpectationFailedException("POST. Divizia nu a putut fi inregistrata!");
                } catch (DataIntegrityViolationException e) {
                    throw new MeciExpectationFailedException("POST. Divizia nu a putut fi inregistrata avand informatii lipsa sau necorespunzatoare!");
                }

        } else {
            log.warn("INSERT. Parametrul divizie nu poate primi valori nule!");
        }


        return false;
    }

    @Transactional
    public boolean deleteDivizie(int idDivizie){
        try {
            int rezultat = entityManager.createNativeQuery("DELETE FROM divizie d WHERE d.div_id=?")
                    .setParameter(1, idDivizie)
                    .executeUpdate();
            if (rezultat > 0) {
                log.info("DELETE. Divizia cu id-ul " + idDivizie + " a fost stearsa cu succes!");
                return true;
            } else {
                log.warn("DELETE. Divizia cu id-ul " + idDivizie + " nu a putut fi stearsa!");
                return false;
            }
        }catch(PersistenceException e) {
            throw new MeciExpectationFailedException("DELETE. Divizia nu a putut fi stearsa!");
        }
    }
}
