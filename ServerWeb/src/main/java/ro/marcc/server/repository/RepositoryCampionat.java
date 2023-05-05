package ro.marcc.server.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import ro.marcc.server.exception.MeciExpectationFailedException;
import ro.marcc.server.exception.PersonalExpectationFailedException;
import ro.marcc.server.model.Localitate.Localitate;
import ro.marcc.server.model.Meciuri.Campionat;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class RepositoryCampionat {
    private final EntityManager entityManager;
    private final RepositoryLocatie repositoryLocatie;

    public RepositoryCampionat(EntityManager entityManager, RepositoryLocatie repositoryLocatie) {
        this.entityManager = entityManager;
        this.repositoryLocatie = repositoryLocatie;
    }

    @Transactional
    public List<Campionat> getCampionate(){
        List<Campionat> campionate = new ArrayList<>();
        try {
            List<Object[]> campionateBD = entityManager.createNativeQuery("SELECT * FROM campionat")
                    .getResultList();

            if(campionateBD.isEmpty()){
                log.info("GET. Nu s-a gasit nici un campionat de preluat!");
            }else{
                for (Object[] object: campionateBD) {
                    Integer idCampionat = (Integer)object[0];
                    String denumire = (String) object[1];
                    LocalDate dataIncepere = ((Date) object[2]).toLocalDate();
                    LocalDate dataSfarsit = ((Date) object[3]).toLocalDate();
                    Integer idLocalitate = (Integer)object[4];
                    Localitate localitate = repositoryLocatie.getLocalitate(idLocalitate);

                    campionate.add(new Campionat(idCampionat,denumire,dataIncepere,dataSfarsit,localitate,null));
                }
                log.info("GET. Preluare lista de campionate realizata cu succes!");
            }
        }catch(PersistenceException e){
            log.warn("GET. Nu s-a putut prelua lista de campionate!");
        }


        return campionate;
    }
    @Transactional
    public Campionat getCampionat(int idCampionat){
        Campionat campionat = null;
        try {
            List<Object[]> campionatBD = entityManager.createNativeQuery("SELECT c.cam_denumire,c.cam_data_incepere,c.cam_data_sfarsit,c.cam_id_localitate FROM campionat c WHERE c.cam_id=?")
                    .setParameter(1, idCampionat)
                    .getResultList();


            if (campionatBD.size() > 0) {
                String denumire = (String) campionatBD.get(0)[0];
                LocalDate dataIncepere = ((Date) campionatBD.get(0)[1]).toLocalDate();
                LocalDate dataSfarsit = ((Date) campionatBD.get(0)[2]).toLocalDate();
                Integer idLocalitate = (Integer)campionatBD.get(0)[3];
                Localitate localitate = repositoryLocatie.getLocalitate(idLocalitate);

                campionat = new Campionat(idCampionat, denumire, dataIncepere, dataSfarsit, localitate, null);
                log.info("GET. Preluarea campionatului cu id-ul "+idCampionat+" realizata cu succes!");
            }
        }catch(PersistenceException e){
            log.warn("GET. Nu s-a putut prelua campionatul cu id-ul "+idCampionat+"!");
        }
        return campionat;
    }

    @Transactional
    public boolean addCampionat(Campionat campionat){
        if(campionat!=null){
            if(campionat.getLocalitate()!=null) {
                try {
                    if(campionat.getLocalitate()!=null && campionat.getLocalitate().getLocalitate()!=null
                            && campionat.getLocalitate().getJudet()!=null && campionat.getLocalitate().getJudet().getJudet()!=null
                            && campionat.getLocalitate().getJudet().getTara()!=null && campionat.getLocalitate().getJudet().getTara().getTara()!=null) {
                        repositoryLocatie.addLccalitate(campionat.getLocalitate());
                    }else{
                        log.info("POST. Locatia campionatului "+campionat.getDenumire()+" nu a fost introdusa inca...");
                    }
                    int rezultat = 0;
                    if(campionat.getId()==null) {
                        rezultat = entityManager.createNativeQuery("INSERT INTO campionat(cam_denumire,cam_data_incepere,cam_data_sfarsit,cam_id_localitate) VALUES (?,?,?,?)")
                                .setParameter(1, campionat.getDenumire())
                                .setParameter(2, campionat.getDataIncepere())
                                .setParameter(3, campionat.getDataSfarsit())
                                .setParameter(4, campionat.getLocalitate().getId())
                                .executeUpdate();
                    }else {
                        rezultat = entityManager.createNativeQuery("INSERT INTO campionat(cam_id,cam_denumire,cam_data_incepere,cam_data_sfarsit,cam_id_localitate) VALUES (?,?,?,?)")
                                .setParameter(1, campionat.getId())
                                .setParameter(2, campionat.getDenumire())
                                .setParameter(3, campionat.getDataIncepere())
                                .setParameter(4, campionat.getDataSfarsit())
                                .setParameter(5, campionat.getLocalitate().getId())
                                .executeUpdate();
                    }
                    System.out.println(campionat);


                    if (rezultat > 0) {
                        log.info("POST. Campionatul a fost inregistrat cu succes!");
                        return true;
                    } else {
                        log.warn("POST. Campionatul nu a putut fi inregistrat!");
                        return false;
                    }
                } catch (PersistenceException e) {
                    throw new MeciExpectationFailedException("POST. Campionatul nu a putut fi inregistrat!");
                }catch(DataIntegrityViolationException e){
                    throw new PersonalExpectationFailedException("POST. Campionatul nu a putut fi inregistrat avand informatii lipsa sau necorespunzatoare!");
                }
            }else{
                log.warn("INSERT. Locatia campionatului nu poate fi nule!");
            }
        }else{
            log.warn("INSERT. Parametrul campionat nu poate primi valori nule!");
        }

        return false;
    }

    @Transactional
    public boolean updateCampionat(int idCampionat, Campionat campionatActualizat) {
        if (campionatActualizat != null) {
            if (campionatActualizat.getLocalitate() != null) {
                try {
                    if(campionatActualizat.getLocalitate()!=null && campionatActualizat.getLocalitate().getLocalitate()!=null
                            && campionatActualizat.getLocalitate().getJudet()!=null && campionatActualizat.getLocalitate().getJudet().getJudet()!=null
                            && campionatActualizat.getLocalitate().getJudet().getTara()!=null && campionatActualizat.getLocalitate().getJudet().getTara().getTara()!=null) {
                        repositoryLocatie.addLccalitate(campionatActualizat.getLocalitate());
                    }
                    int rezultat = entityManager.createNativeQuery("UPDATE campionat c SET c.cam_denumire=?2,c.cam_data_incepere=?3,c.cam_data_sfarsit=?4,c.cam_id_localitate=?5 WHERE c.cam_id=?1")
                            .setParameter(1, idCampionat)
                            .setParameter(2, campionatActualizat.getDenumire())
                            .setParameter(3, campionatActualizat.getDataIncepere())
                            .setParameter(4, campionatActualizat.getDataSfarsit())
                            .setParameter(5, campionatActualizat.getLocalitate().getId())
                            .executeUpdate();
                    if (rezultat > 0) {
                        log.info("PUT. Campionatul cu id-ul " + idCampionat + " a fost actualizat cu succes!");
                        return true;
                    } else {
                        log.warn("PUT. Campionatul cu id-ul " + idCampionat + " nu a putut fi actualizat!");
                        return false;
                    }
                } catch (PersistenceException e) {
                    throw new MeciExpectationFailedException("POST. Campionatul nu a putut fi inregistrat!");
                } catch (DataIntegrityViolationException e) {
                    throw new MeciExpectationFailedException("POST. Campionatul nu a putut fi inregistrat avand informatii lipsa sau necorespunzatoare!");
                }
            } else {
                log.warn("INSERT. Locatia Campionatul nu poate fi nule!");
            }

        } else {
            log.warn("INSERT. Parametrul campionat nu poate primi valori nule!");
        }


        return false;
    }

    @Transactional
    public boolean deleteCampionat(int idCampionat){
        try {
            int rezultat = entityManager.createNativeQuery("DELETE FROM campionat c WHERE c.cam_id=?")
                    .setParameter(1, idCampionat)
                    .executeUpdate();
            if (rezultat > 0) {
                log.info("DELETE. Campionatul cu id-ul " + idCampionat + " a fost stears cu succes!");
                return true;
            } else {
                log.warn("DELETE. Campionatul cu id-ul " + idCampionat + " nu a putut fi stears!");
                return false;
            }
        }catch(PersistenceException e) {
            throw new MeciExpectationFailedException("DELETE. Campionatul nu a putut fi stears!");
        }
    }
}
