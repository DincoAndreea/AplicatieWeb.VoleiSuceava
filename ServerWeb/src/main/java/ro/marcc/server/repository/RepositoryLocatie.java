package ro.marcc.server.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import ro.marcc.server.exception.LocatieExpectationFailedException;
import ro.marcc.server.model.Localitate.Judet;
import ro.marcc.server.model.Localitate.Localitate;
import ro.marcc.server.model.Localitate.Tara;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class RepositoryLocatie {
    private final EntityManager entityManager;

    public RepositoryLocatie(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public List<Localitate> getLocalitati(){
        List<Localitate> localitati = new ArrayList<>();
        try {
            List<Object[]> locatiiBD = entityManager.createNativeQuery("SELECT l.loc_id,l.loc_localitate, j.jud_id,j.jud_judet,t.tar_id,t.tar_tara FROM localitate l JOIN judet j ON l.loc_id_judet = j.jud_id JOIN tara t ON j.jud_id_tara = t.tar_id")
                    .getResultList();

            if(locatiiBD.isEmpty()){
                log.info("GET. Nu s-a gasit nici o localitate de preluat!");
            }else{
                for(Object[] obj:locatiiBD){
                    Integer idLocalitate = (Integer) obj[0];
                    String localitate = (String) obj[1];
                    Integer idJudet = (Integer) obj[2];
                    String judet = (String) obj[3];
                    Integer idTara = (Integer) obj[4];
                    String tara = (String) obj[5];

                    Localitate locatie = new Localitate(idLocalitate,localitate,new Judet(idJudet,judet,new Tara(idTara,tara)));
                    localitati.add(locatie);
                }
                log.info("GET. Preluare lista de localitati realizata cu succes!");
            }

        }catch(PersistenceException e){
            log.warn("GET. Nu s-a putut prelua lista de localitati!");
        }


        return localitati;
    }
    @Transactional
    public Localitate getLocalitate(Integer idLocalitate){
        Localitate locatie = null;
        List<Object[]> locatieBD = entityManager.createNativeQuery("SELECT l.loc_localitate, j.jud_id,j.jud_judet,t.tar_id,t.tar_tara FROM localitate l JOIN judet j ON l.loc_id_judet = j.jud_id JOIN tara t ON j.jud_id_tara = t.tar_id WHERE l.loc_id=?")
                .setParameter(1, idLocalitate)
                .getResultList();

        if(locatieBD.size()>0) {
            String localitate = (String) locatieBD.get(0)[0];
            Integer idJudet = (Integer) locatieBD.get(0)[1];
            String judet = (String) locatieBD.get(0)[2];
            Integer idTara = (Integer) locatieBD.get(0)[3];
            String tara = (String) locatieBD.get(0)[4];

            locatie = new Localitate(idLocalitate,localitate,new Judet(idJudet,judet,new Tara(idTara,tara)));
            log.info("GET. Locatia cu id-ul "+ idLocalitate +" a fost gasita!");
        }else{
            log.warn("GET. Locatia cu id-ul "+ idLocalitate +" nu a fost gasita!");
        }


        return locatie;
    }

    @Transactional
    public boolean addLccalitate(Localitate localitate){
        if(localitate!=null && localitate.getLocalitate()!=null){
            if(localitate.getJudet()!=null && localitate.getJudet().getJudet()!=null){
                Judet judet = localitate.getJudet();
                if(judet.getTara()!=null && judet.getTara().getTara()!=null){
                    Tara tara = judet.getTara();

                    try {
                        tara.setId((Integer)entityManager.createNativeQuery("SELECT t.tar_id FROM tara t WHERE t.tar_tara =?")
                                .setParameter(1, tara.getTara())
                                .getSingleResult());
                    }catch (NoResultException noResultException) {
                        try{
                            entityManager.createNativeQuery("INSERT INTO tara(tar_tara) VALUES (?)")
                                    .setParameter(1,tara.getTara())
                                    .executeUpdate();
                            tara.setId((Integer)entityManager.createNativeQuery("SELECT t.tar_id FROM tara t WHERE t.tar_tara =?")
                                    .setParameter(1, tara.getTara())
                                    .getSingleResult());
                            log.info("POST. A fost adaugata tara cu numele de "+tara.getTara()+"!");

                        }catch(PersistenceException persistenceException){
                            log.warn("POST. Nu a putut fi adaugata tara cu numele de "+tara.getTara()+"!");
                            throw new LocatieExpectationFailedException("POST. Nu a putut fi adaugata tara cu numele de "+tara.getTara()+"!");
                        }
                    }

                    try {
                        judet.setId((Integer)entityManager.createNativeQuery("SELECT j.jud_id FROM judet j WHERE j.jud_judet =? AND j.jud_id_tara=?")
                                .setParameter(1, judet.getJudet())
                                .setParameter(2, tara.getId())
                                .getSingleResult());
                    }catch (NoResultException noResultException) {
                        try{
                            entityManager.createNativeQuery("INSERT INTO judet(jud_judet,jud_id_tara) VALUES (?1,?2)")
                                    .setParameter(1, judet.getJudet())
                                    .setParameter(2, tara.getId())
                                    .executeUpdate();
                            judet.setId((Integer)entityManager.createNativeQuery("SELECT j.jud_id FROM judet j WHERE j.jud_judet =? AND j.jud_id_tara=?")
                                    .setParameter(1, judet.getJudet())
                                    .setParameter(2, tara.getId())
                                    .getSingleResult());
                            log.info("POST. A fost adaugat judetul cu numele de "+judet.getJudet()+"!");

                        }catch(PersistenceException persistenceException){
                            log.warn("POST. Nu a putut fi adaugat judetul cu numele de "+judet.getJudet()+"!");
                            throw new LocatieExpectationFailedException("POST. Nu a putut fi adaugat judetul cu numele de "+judet.getJudet()+"!");
                        }
                    }

                    try {
                        localitate.setId((Integer)entityManager.createNativeQuery("SELECT l.loc_id FROM localitate l WHERE l.loc_localitate =? AND l.loc_id_judet =?")
                                .setParameter(1, localitate.getLocalitate())
                                .setParameter(2, judet.getId())
                                .getSingleResult());
                    }catch (NoResultException noResultException) {
                        try{
                            entityManager.createNativeQuery("INSERT INTO localitate(loc_localitate,loc_id_judet) VALUES (?,?)")
                                    .setParameter(1, localitate.getLocalitate())
                                    .setParameter(2, judet.getId())
                                    .executeUpdate();
                            localitate.setId((Integer)entityManager.createNativeQuery("SELECT l.loc_id FROM localitate l WHERE l.loc_localitate =? AND l.loc_id_judet =?")
                                    .setParameter(1, localitate.getLocalitate())
                                    .setParameter(2, judet.getId())
                                    .getSingleResult());
                            log.info("POST. A fost adaugata localitatea cu numele de "+localitate.getLocalitate()+"!");
                        }catch(PersistenceException persistenceException){
                            log.warn("POST. Nu a putut fi adaugata localitatea cu numele de "+localitate.getLocalitate()+"!");
                            throw new LocatieExpectationFailedException("POST. Nu a putut fi adaugata localitatea cu numele de "+localitate.getLocalitate()+"!");
                        }
                    }
                    return true;
                }else{
                    log.warn("POST. Localitatea nu poate fi specificata fara o tara!");
                }
            }else{
                log.warn("POST. Localitatea nu poate sa fie specificata fara un judet!");
            }
        }else{
            log.warn("POST. Parametrul localitate nu poate primi valori nule!");
        }
        return false;
    }

    @Transactional
    public boolean updateLocalitate(int idLocalitate,Localitate localitateActualziata){
        System.out.println(localitateActualziata);
        if(localitateActualziata!=null && localitateActualziata.getLocalitate()!=null){
            if(localitateActualziata.getJudet()!=null && localitateActualziata.getJudet().getJudet()!=null){
                Judet judet = localitateActualziata.getJudet();
                if(judet.getTara()!=null && judet.getTara()!=null){
                    Tara tara = judet.getTara();

                    try {
                        tara.setId((Integer)(entityManager.createNativeQuery("SELECT t.tar_id FROM tara t WHERE t.tar_tara =?")
                                .setParameter(1, tara.getTara())
                                .getSingleResult()));
                    }catch (NoResultException noResultException) {
                        try{
                            entityManager.createNativeQuery("INSERT INTO tara(tar_tara) VALUES (?)")
                                    .setParameter(1,tara.getTara())
                                    .executeUpdate();
                            tara.setId((Integer)entityManager.createNativeQuery("SELECT t.tar_id FROM tara t WHERE t.tar_tara =?")
                                    .setParameter(1, tara.getTara())
                                    .getSingleResult());
                            log.info("POST. A fost adaugata tara cu numele de "+tara.getTara()+"!");

                        }catch(PersistenceException persistenceException){
                            log.warn("POST. Nu a putut fi adaugata tara cu numele de "+tara.getTara()+"!");
                            throw new LocatieExpectationFailedException("PUT. Nu s-a putut actualiza datele localitatii cu id-ul " + idLocalitate + " avand informatii lipsa sau necorespunzatoare referitoare la tara!");
                        }
                    }

                    try {
                        judet.setId((Integer)entityManager.createNativeQuery("SELECT j.jud_id FROM judet j WHERE j.jud_judet =? AND j.jud_id_tara=?")
                                .setParameter(1, judet.getJudet())
                                .setParameter(2, tara.getId())
                                .getSingleResult());
                    }catch (NoResultException noResultException) {
                        try{

                            entityManager.createNativeQuery("INSERT INTO judet(jud_judet,jud_id_tara) VALUES (?,?)")
                                    .setParameter(1, judet.getJudet())
                                    .setParameter(2, tara.getId())
                                    .executeUpdate();
                            judet.setId((Integer)entityManager.createNativeQuery("SELECT j.jud_id FROM judet j WHERE j.jud_judet =? j.jud_id_tara=?")
                                    .setParameter(1, judet.getJudet())
                                    .setParameter(2, tara.getId())
                                    .getSingleResult());
                            log.info("POST. A fost adaugat judetul cu numele de "+judet.getJudet()+"!");

                        }catch(PersistenceException persistenceException){
                            log.warn("POST. Nu a putut fi adaugat judetul cu numele de "+judet.getJudet()+"!");
                            throw new LocatieExpectationFailedException("PUT. Nu s-a putut actualiza datele localitatii cu id-ul " + idLocalitate + " avand informatii lipsa sau necorespunzatoare referitoare la judet!");
                        }
                    }

                    try{
                        entityManager.createNativeQuery("UPDATE localitate l SET l.loc_localitate=?2,loc_id_judet=?3 WHERE l.loc_id=?1")
                                .setParameter(1,idLocalitate)
                                .setParameter(2,localitateActualziata.getLocalitate())
                                .setParameter(3,judet.getId())
                                .executeUpdate();
                        localitateActualziata.setId((Integer)entityManager.createNativeQuery("SELECT l.loc_id FROM localitate l WHERE l.loc_localitate =? AND l.loc_id_judet =?")
                                .setParameter(1, localitateActualziata.getLocalitate())
                                .setParameter(2, judet.getId())
                                .getSingleResult());
                        log.info("PUT. A fost actualizata localitatea cu id-ul"+idLocalitate+"!");

                    }catch (PersistenceException e) {
                        throw new LocatieExpectationFailedException("PUT. Nu s-a putut actualiza datele localitatii cu id-ul " + idLocalitate + "!");
                    } catch (DataIntegrityViolationException e) {
                        throw new LocatieExpectationFailedException("PUT. Nu s-a putut actualiza datele localitatii cu id-ul " + idLocalitate + " avand informatii lipsa sau necorespunzatoare referitoare la localitate!");
                    }


                    return true;
                }else{
                    log.warn("POST. Localitatea nu poate fi specificata fara o tara!");
                }
            }else{
                log.warn("POST. Localitatea nu poate sa fie specificata fara un judet!");
            }
        }else{
            log.warn("POST. Parametrul localitate nu poate primi valori nule!");
        }
        return false;
    }

    @Transactional
    public boolean deleteLocalitate(int idLocalitate){
        try{
            int rezultat =  entityManager.createNativeQuery("DELETE FROM localitate l WHERE l.loc_id=?")
                    .setParameter(1,idLocalitate)
                    .executeUpdate();

            if(rezultat>0){
                log.info("DELETE. Localitatea cu id-ul "+idLocalitate+" a fost stearsa cu succes!");
                return true;
            }else
            {
                log.warn("DELETE. Localitatea cu id-ul "+idLocalitate+" nu a fost stearsa!");
                return false;
            }
        }catch (PersistenceException e) {
            throw new LocatieExpectationFailedException("DELETE. Nu s-a putut sterge datele localitatii cu id-ul " + idLocalitate + "!");
        } catch (DataIntegrityViolationException e) {
            throw new LocatieExpectationFailedException("DELETE. Nu s-a putut sterge datele localitatii cu id-ul " + idLocalitate + " avand informatii lipsa sau necorespunzatoare!");
        }
    }

}
