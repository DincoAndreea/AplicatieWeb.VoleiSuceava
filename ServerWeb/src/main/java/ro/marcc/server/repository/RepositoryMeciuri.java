package ro.marcc.server.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import ro.marcc.server.exception.MeciExpectationFailedException;
import ro.marcc.server.exception.MeciNotFoundException;
import ro.marcc.server.exception.PersonalExpectationFailedException;
import ro.marcc.server.exception.StireExpectationFailedException;
import ro.marcc.server.model.Localitate.Localitate;
import ro.marcc.server.model.Meciuri.Campionat;
import ro.marcc.server.model.Meciuri.Divizie;
import ro.marcc.server.model.Meciuri.Echipa;
import ro.marcc.server.model.Meciuri.Meci;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class RepositoryMeciuri {

    private final EntityManager entityManager;
    private final RepositoryLocatie repositoryLocatie;
    private final RepositoryPersonal repositoryPersonal;
    private final RepositoryCampionat repositoryCampionat;
    private final RepositoryDivizii repositoryDivizii;

    public RepositoryMeciuri(EntityManager entityManager, RepositoryLocatie repositoryLocatie, RepositoryPersonal repositoryPersonal, RepositoryCampionat repositoryCampionat, RepositoryDivizii repositoryDivizii) {
        this.entityManager = entityManager;
        this.repositoryLocatie = repositoryLocatie;
        this.repositoryPersonal = repositoryPersonal;
        this.repositoryCampionat = repositoryCampionat;
        this.repositoryDivizii = repositoryDivizii;
    }

    @Transactional
    public List<Meci> getMeciuri(boolean sortareDesc){
        List<Meci> meciuri = new ArrayList<>();
        List<Object[]> meciuriBD;
        try {
            meciuriBD = entityManager.createNativeQuery("SELECT m.mec_id, m.mec_scor, m.mec_data_meci,m.mec_link,m.mec_id_divizie,m.mec_id_locatie,m.mec_id_echipa1,m.mec_id_echipa2,m.mec_id_campionat,d.div_nume FROM Meci m LEFT JOIN divizie d ON d.div_id = m.mec_id_divizie ORDER BY m.mec_data_meci "+(sortareDesc?"DESC":"ASC")+",m.mec_id_castigator "+(sortareDesc?"DESC":"ASC")).getResultList();
        }catch(PersistenceException e){
            throw new MeciExpectationFailedException("GET. Nu s-a putut prelua lista de meciuri!");
        }
        for (Object[] obj:
                meciuriBD) {
            int id = (int)obj[0];
            String[] scor;
            int[] scorFormatat;
            String scorNeformatat = (String)obj[1];
            if(scorNeformatat.contains(";")) {
                scor = ((String) obj[1]).split(";");
            }else{
                scor = new String[]{"-1","-1"};
            }
            try{
                scorFormatat = new int[]{Integer.parseInt(scor[0]),Integer.parseInt(scor[1])};
            }catch(NumberFormatException e){
                scorFormatat = new int[]{-1,-1};
            }

            LocalDateTime dataMeci = ((Timestamp)obj[2]).toLocalDateTime();
            String link = (String)obj[3];
            Integer idDivizie = (Integer)obj[4];
            Integer idLocalitate = (Integer)obj[5];
            Integer idEchipa1 = (Integer)obj[6];
            Integer idEchipa2 = (Integer)obj[7];
            Integer idCampionat = (Integer)obj[8];
            String numeDivizie = (String)obj[9];

            Echipa echipa1 = repositoryPersonal.getEchipa(idEchipa1,true),
                   echipa2 = repositoryPersonal.getEchipa(idEchipa2,true);


            Localitate locatie = repositoryLocatie.getLocalitate(idLocalitate);

            Divizie divizie = new Divizie(idDivizie,numeDivizie);

            Campionat campionat = null;
            if(idCampionat!=null) {
                campionat = repositoryCampionat.getCampionat(idCampionat);
            }


            Meci meci = new Meci(id,new Echipa[]{echipa1,echipa2},scorFormatat, dataMeci,campionat,locatie,link,divizie);


            meciuri.add(meci);

        }
        if(meciuri.isEmpty()){
            log.info("GET. Nu s-a gasit nici un meci de preluat!");
        }else{
            log.info("GET. Lista de meciuri a fost preluata cu succes!");
        }

        return meciuri;
    }
    @Transactional
    public List<Meci> getMeciuriDupaDivizieSiDataMeci(Integer idDivizie,LocalDate dataMeciFiltrare) {
        List<Meci> meciuri = new ArrayList<>();
        List<Object[]> meciuriBD = null;
        if (idDivizie != null) {
            try {
                if (dataMeciFiltrare != null) {
                    meciuriBD = entityManager.createNativeQuery("SELECT m.mec_id, m.mec_scor, m.mec_data_meci,m.mec_link,m.mec_id_divizie,m.mec_id_locatie,m.mec_id_echipa1,m.mec_id_echipa2,m.mec_id_campionat,d.div_nume FROM Meci m LEFT JOIN divizie d ON d.div_id = m.mec_id_divizie WHERE d.div_id=?1 AND m.mec_data_meci>=?2 AND m.mec_data_meci<?3 ORDER BY m.mec_data_meci DESC,m.mec_id_castigator DESC")
                            .setParameter(1, idDivizie)
                            .setParameter(2, dataMeciFiltrare)
                            .setParameter(3, dataMeciFiltrare.plusDays(1))
                            .getResultList();
                } else {
                    meciuriBD = entityManager.createNativeQuery("SELECT m.mec_id, m.mec_scor, m.mec_data_meci,m.mec_link,m.mec_id_divizie,m.mec_id_locatie,m.mec_id_echipa1,m.mec_id_echipa2,m.mec_id_campionat,d.div_nume FROM Meci m LEFT JOIN divizie d ON d.div_id = m.mec_id_divizie WHERE d.div_id=? ORDER BY m.mec_data_meci DESC,m.mec_id_castigator DESC")
                            .setParameter(1, idDivizie)
                            .getResultList();
                }
            } catch (PersistenceException e) {
                throw new MeciExpectationFailedException("GET. Nu s-a putut prelua lista de meciuri!");
            }

        }else{
            try {
                if (dataMeciFiltrare != null) {
                    meciuriBD = entityManager.createNativeQuery("SELECT m.mec_id, m.mec_scor, m.mec_data_meci,m.mec_link,m.mec_id_divizie,m.mec_id_locatie,m.mec_id_echipa1,m.mec_id_echipa2,m.mec_id_campionat FROM Meci m  WHERE m.mec_data_meci>=?2 AND m.mec_data_meci<?3 ORDER BY m.mec_data_meci DESC,m.mec_id_castigator DESC")
                            .setParameter(1, dataMeciFiltrare)
                            .setParameter(2, dataMeciFiltrare.plusDays(1))
                            .getResultList();
                } else {
                    meciuriBD = entityManager.createNativeQuery("SELECT m.mec_id, m.mec_scor, m.mec_data_meci,m.mec_link,m.mec_id_divizie,m.mec_id_locatie,m.mec_id_echipa1,m.mec_id_echipa2,m.mec_id_campionat FROM Meci m  ORDER BY m.mec_data_meci DESC,m.mec_id_castigator DESC")
                            .getResultList();
                }
            } catch (PersistenceException e) {
                throw new MeciExpectationFailedException("GET. Nu s-a putut prelua lista de meciuri!");
            }
        }


        for (Object[] obj:
                meciuriBD) {
            int id = (int)obj[0];
            String[] scor;
            int[] scorFormatat;
            String scorNeformatat = (String)obj[1];
            if(scorNeformatat.contains(";")) {
                scor = ((String) obj[1]).split(";");
            }else{
                scor = new String[]{"-1","-1"};
            }
            try{
                scorFormatat = new int[]{Integer.parseInt(scor[0]),Integer.parseInt(scor[1])};
            }catch(NumberFormatException e){
                scorFormatat = new int[]{-1,-1};
            }

            LocalDateTime dataMeci = ((Timestamp)obj[2]).toLocalDateTime();
            String link = (String)obj[3];
            Integer idLocalitate = (Integer)obj[5];
            Integer idEchipa1 = (Integer)obj[6];
            Integer idEchipa2 = (Integer)obj[7];
            Integer idCampionat = (Integer)obj[8];

            Echipa echipa1 = repositoryPersonal.getEchipa(idEchipa1,true),
                   echipa2 = repositoryPersonal.getEchipa(idEchipa2,true);


            Localitate locatie = repositoryLocatie.getLocalitate(idLocalitate);


            Campionat campionat = null;
            if(idCampionat!=null) {
                campionat = repositoryCampionat.getCampionat(idCampionat);
            }

            Divizie divizie = null;

            if(idDivizie!=null){
                String numeDivizie = (String)obj[9];
                divizie = new Divizie(idDivizie,numeDivizie);
            }

            Meci meci = new Meci(id,new Echipa[]{echipa1,echipa2},scorFormatat, dataMeci,campionat,locatie,link,divizie);
            if(divizie==null || (divizie.getNume().equalsIgnoreCase("amicale")&&idCampionat==null) || !divizie.getNume().equalsIgnoreCase("amicale")) {
                meciuri.add(meci);
            }
        }

        if(meciuri.isEmpty()){
            if(idDivizie!=null) {
                log.info("GET. Nu s-a gasit nici un meci la divizia cu id-ul " + idDivizie + "!");
            }else{
                log.info("GET. Nu s-a gasit nici un meci!");
            }
        }else{
            if(idDivizie!=null) {
                log.info("GET. Lista de meciuri de la divizia cu id-ul " + idDivizie + " preluata cu succes!");
            }else{
                log.info("GET. Lista de meciuri preluata cu succes!");
            }
        }

        return meciuri;
    }


    @Transactional
    public List<Meci> getMeciuri(Echipa echipa){
        List<Meci> meciuri = new ArrayList<>();

        if(echipa!=null ) {
            if(echipa.getId()!=null) {
                List<Object[]> meciuriBD = null;

                try {
                    meciuriBD = entityManager.createNativeQuery("SELECT m.mec_id, m.mec_scor, m.mec_data_meci,m.mec_link,m.mec_id_divizie,m.mec_id_locatie,m.mec_id_echipa1,m.mec_id_echipa2,m.mec_id_campionat,d.div_nume FROM Meci m LEFT JOIN divizie d ON d.div_id = m.mec_id_divizie WHERE m.mec_id_echipa1=?1 OR m.mec_id_echipa2 =?1 ORDER BY m.mec_data_meci DESC,m.mec_id_castigator DESC")
                            .setParameter(1, echipa.getId())
                            .getResultList();
                } catch (PersistenceException e) {
                    throw new MeciExpectationFailedException("GET. Nu s-a putut prelua lista de meciuri!");
                }

                for (Object[] obj :
                        meciuriBD) {
                    int id = (int) obj[0];
                    String[] scor;
                    int[] scorFormatat;
                    String scorNeformatat = (String) obj[1];
                    if (scorNeformatat.contains(";")) {
                        scor = ((String) obj[1]).split(";");
                    } else {
                        scor = new String[]{"-1", "-1"};
                    }
                    try {
                        scorFormatat = new int[]{Integer.parseInt(scor[0]), Integer.parseInt(scor[1])};
                    } catch (NumberFormatException e) {
                        scorFormatat = new int[]{-1, -1};
                    }

                    LocalDateTime dataMeci = ((Timestamp) obj[2]).toLocalDateTime();
                    String link = (String) obj[3];
                    Integer idDivizie = (Integer) obj[4];
                    Integer idLocalitate = (Integer) obj[5];
                    Integer idEchipa1 = (Integer) obj[6];
                    Integer idEchipa2 = (Integer) obj[7];
                    Integer idCampionat = (Integer) obj[8];
                    String numeDivizie = (String) obj[9];

                    Echipa echipa1 = repositoryPersonal.getEchipa(idEchipa1, true),
                            echipa2 = repositoryPersonal.getEchipa(idEchipa2, true);


                    Localitate locatie = repositoryLocatie.getLocalitate(idLocalitate);

                    Divizie divizie = new Divizie(idDivizie, numeDivizie);
                    Campionat campionat = null;
                    if(idCampionat!=null) {
                        campionat = repositoryCampionat.getCampionat(idCampionat);
                    }

                    Meci meci = new Meci(id, new Echipa[]{echipa1, echipa2}, scorFormatat, dataMeci, campionat, locatie, link, divizie);

                    meciuri.add(meci);
                }
                if(meciuri.isEmpty()){
                    log.info("GET. Nu s-a gasit nici un meci al echipei id-ul "+echipa.getId()+"!");
                }else{
                    log.info("GET. Lista meciurilor echipei cu id-ul "+echipa.getId()+" preluata cu succes!");
                }
            }else{
                throw new MeciExpectationFailedException("GET. In cadrul obiectului de tip Echipa nu poate primi id-ul valori nule!");

            }
        }else{
            throw new MeciExpectationFailedException("GET. Parametrul echipa nu poate primi valori nule!");
        }

        return meciuri;
    }

    @Transactional
    public Meci getMeci(Integer idMeci){
        Meci meci = null;

        try {
            if (idMeci != null) {
                List<Object[]> meciuriBD = null;

                meciuriBD = entityManager.createNativeQuery("SELECT m.mec_id, m.mec_scor, m.mec_data_meci,m.mec_link,m.mec_id_divizie,m.mec_id_locatie,m.mec_id_echipa1,m.mec_id_echipa2,m.mec_id_campionat,d.div_nume FROM Meci m LEFT JOIN divizie d ON d.div_id = m.mec_id_divizie WHERE m.mec_id = ?1 ORDER BY m.mec_data_meci,m.mec_id_castigator DESC")
                        .setParameter(1, idMeci)
                        .getResultList();

                if (meciuriBD.size() > 0) {
                    int id = (int) meciuriBD.get(0)[0];
                    String[] scor;
                    int[] scorFormatat;
                    String scorNeformatat = (String) meciuriBD.get(0)[1];
                    if (scorNeformatat.contains(";")) {
                        scor = ((String) meciuriBD.get(0)[1]).split(";");
                    } else {
                        scor = new String[]{"-1", "-1"};
                    }
                    try {
                        scorFormatat = new int[]{Integer.parseInt(scor[0]), Integer.parseInt(scor[1])};
                    } catch (NumberFormatException e) {
                        scorFormatat = new int[]{-1, -1};
                    }

                    LocalDateTime dataMeci = ((Timestamp) meciuriBD.get(0)[2]).toLocalDateTime();
                    String link = (String) meciuriBD.get(0)[3];
                    Integer idDivizie = (Integer) meciuriBD.get(0)[4];
                    Integer idLocalitate = (Integer) meciuriBD.get(0)[5];
                    Integer idEchipa1 = (Integer) meciuriBD.get(0)[6];
                    Integer idEchipa2 = (Integer) meciuriBD.get(0)[7];
                    Integer idCampionat = (Integer) meciuriBD.get(0)[8];
                    String numeDivizie = (String) meciuriBD.get(0)[9];

                    Echipa echipa1 = repositoryPersonal.getEchipa(idEchipa1, true),
                            echipa2 = repositoryPersonal.getEchipa(idEchipa2, true);


                    Localitate locatie = repositoryLocatie.getLocalitate(idLocalitate);

                    Divizie divizie = new Divizie(idDivizie, numeDivizie);

                    Campionat campionat = null;
                    if(idCampionat!=null) {
                        campionat = repositoryCampionat.getCampionat(idCampionat);
                    }


                    meci = new Meci(id, new Echipa[]{echipa1, echipa2}, scorFormatat, dataMeci, campionat, locatie, link, divizie);

                    log.info("GET. Meciul cu id-ul "+idMeci+" preluat cu succes!");

                } else {
                    throw new MeciNotFoundException("GET. Nu a putut fi gasit meciul cu id-ul " + idMeci + "!");
                }
            } else {
                throw new MeciExpectationFailedException("GET. Parametrul echipa nu poate primi valori nule!");
            }
        }catch(PersistenceException e){
            throw new StireExpectationFailedException("GET. Nu a putut fi gasit meciul cu id-ul " + idMeci + "!");
        }

        return meci;
    }

    @Transactional
    public boolean addMeci(Meci meci){
        if(meci!=null){
            if(meci.getDivizie()!=null && meci.getDivizie().getId()!=null) {
                if (meci.getLocatie() != null) {
                    try {
                        if (meci.getLocatie().getId() != null) {
                            if (repositoryLocatie.getLocalitate(meci.getLocatie().getId()) == null) {
                                repositoryLocatie.addLccalitate(meci.getLocatie());
                                try {
                                    meci.getLocatie().setId((Integer) entityManager.createNativeQuery("SELECT l.loc_id FROM localitate l WHERE l.loc_localitate =?")
                                            .setParameter(1, meci.getLocatie().getLocalitate())
                                            .getSingleResult());
                                } catch (PersistenceException e) {
                                    throw new MeciExpectationFailedException("POST. Locatia meciului nu a putut fi adaugata!");
                                }
                            }
                        }

                        Divizie divizie = repositoryDivizii.getDivizie(meci.getDivizie().getId());
                        if(divizie==null){
                            throw new MeciNotFoundException("POST. Nu exista divizia cu id-ul "+meci.getDivizie().getId()+"!");
                        }

                        if(divizie.getNume().equalsIgnoreCase("amicale")){
                            meci.setCampionat(null);
                        }

                        int rezultat = 0;
                        if (meci.getId() == null) {
                            rezultat = entityManager.createNativeQuery("INSERT INTO meci(mec_scor,mec_data_meci,mec_link,mec_id_divizie,mec_id_locatie,mec_id_echipa1,mec_id_echipa2,mec_id_campionat,mec_id_castigator) VALUES (?,?,?,?,?,?,?,?,?)")
                                    .setParameter(1, ((meci.getScor() == null) || (meci.getScor().length != 2) || (meci.getScor()[0] < 0) || (meci.getScor()[1] < 0)) ? "" : (meci.getScor()[0] + ";" + meci.getScor()[1]))
                                    .setParameter(2, meci.getDataMeci())
                                    .setParameter(3, meci.getLink())
                                    .setParameter(4, meci.getDivizie().getId())
                                    .setParameter(5, meci.getLocatie().getId())
                                    .setParameter(6, meci.getEchipe()[0].getId())
                                    .setParameter(7, meci.getEchipe()[1].getId())
                                    .setParameter(8, meci.getCampionat() == null || meci.getCampionat().getId() == null ? null : meci.getCampionat().getId() < 0 ? null : meci.getCampionat().getId())
                                    .setParameter(9, ((meci.getScor() == null) || (meci.getScor().length != 2)) ? null : ((meci.getScor()[0] > meci.getScor()[1]) ? meci.getEchipe()[0] : ((meci.getScor()[0] < meci.getScor()[1]) ? meci.getEchipe()[1] : null)))
                                    .executeUpdate();
                        } else {
                            rezultat = entityManager.createNativeQuery("INSERT INTO meci(mec_id,mec_scor,mec_data_meci,mec_link,mec_id_divizie,mec_id_locatie,mec_id_echipa1,mec_id_echipa2,mec_id_campionat,mec_id_castigator) VALUES (?,?,?,?,?,?,?,?,?,?)")
                                    .setParameter(1, meci.getId())
                                    .setParameter(2, meci.getScor() == null || meci.getScor().length != 2 || meci.getScor()[0] < 0 || meci.getScor()[1] < 0 ? "" : meci.getScor()[0] + ";" + meci.getScor()[1])
                                    .setParameter(3, meci.getDataMeci())
                                    .setParameter(4, meci.getLink())
                                    .setParameter(5, meci.getDivizie().getId())
                                    .setParameter(6, meci.getLocatie().getId())
                                    .setParameter(7, meci.getEchipe()[0].getId())
                                    .setParameter(8, meci.getEchipe()[1].getId())
                                    .setParameter(9, meci.getCampionat() == null || meci.getCampionat().getId() == null ? null : meci.getCampionat().getId() < 0 ? null : meci.getCampionat().getId())
                                    .setParameter(10, meci.getScor() == null || meci.getScor().length != 2 ? null : (meci.getScor()[0] > meci.getScor()[1]) ? meci.getEchipe()[0] : ((meci.getScor()[0] < meci.getScor()[1]) ? meci.getEchipe()[1] : null))
                                    .executeUpdate();
                        }
                        if (rezultat > 0) {
                            log.info("POST. Meciul a fost inregistrata cu succes!");
                            return true;
                        } else {
                            log.warn("POST. Meciul nu a putut fi inregistrat!");
                            return false;
                        }
                    } catch (PersistenceException e) {
                        throw new MeciExpectationFailedException("POST. Meciul nu a putut fi inregistrat!");
                    } catch (DataIntegrityViolationException e) {
                        throw new PersonalExpectationFailedException("POST. Meciul nu a putut fi inregistrat avand informatii lipsa sau necorespunzatoare!");
                    }
                } else {
                    log.warn("POST. Locatia meciului nu poate fi nule!");
                }
            }else{
                log.warn("POST. Parametrul divizie si idDivize nu poate fi null!");
            }
        }else{
            log.warn("POST. Parametrul meci nu poate primi valori nule!");
        }

        return false;
    }

    @Transactional
    public boolean updateMeci(Integer idMeci,Meci meciActualizat){
        if(idMeci!=null) {
            if (meciActualizat != null) {
                if (meciActualizat.getDivizie() != null && meciActualizat.getDivizie().getId() != null) {
                    if (meciActualizat.getLocatie() != null) {
                        try {
                            Divizie divizie = repositoryDivizii.getDivizie(meciActualizat.getDivizie().getId());
                            if(divizie==null){
                                throw new MeciNotFoundException("PUT. Nu exista divizia cu id-ul "+meciActualizat.getDivizie().getId()+"!");
                            }

                            if(divizie.getNume().equalsIgnoreCase("amicale")){
                                meciActualizat.setCampionat(null);
                            }

                            int rezultat = entityManager.createNativeQuery("UPDATE meci m SET m.mec_scor=?1,m.mec_data_meci=?2,m.mec_link=?3,m.mec_id_divizie=?4,m.mec_id_locatie=?5,m.mec_id_echipa1=?6,m.mec_id_echipa2=?7,m.mec_id_campionat=?8,m.mec_id_castigator=?9 WHERE m.mec_id=?10")
                                    .setParameter(1, meciActualizat.getScor() == null || meciActualizat.getScor().length != 2 || meciActualizat.getScor()[0] < 0 || meciActualizat.getScor()[1] < 0 ? "" : meciActualizat.getScor()[0] + ";" + meciActualizat.getScor()[1])
                                    .setParameter(2, meciActualizat.getDataMeci())
                                    .setParameter(3, meciActualizat.getLink())
                                    .setParameter(4, meciActualizat.getDivizie().getId())
                                    .setParameter(5, meciActualizat.getLocatie().getId())
                                    .setParameter(6, meciActualizat.getEchipe()[0].getId())
                                    .setParameter(7, meciActualizat.getEchipe()[1].getId())
                                    .setParameter(8, meciActualizat.getCampionat() == null || meciActualizat.getCampionat().getId() == null ? null : meciActualizat.getCampionat().getId() < 0 ? null : meciActualizat.getCampionat().getId())
                                    .setParameter(9, ((meciActualizat.getScor() == null) || (meciActualizat.getScor().length != 2)) ? null : ((meciActualizat.getScor()[0] > meciActualizat.getScor()[1]) ? meciActualizat.getEchipe()[0] : ((meciActualizat.getScor()[0] < meciActualizat.getScor()[1]) ? meciActualizat.getEchipe()[1] : null)))
                                    .setParameter(10, idMeci)
                                    .executeUpdate();
                            if (rezultat > 0) {
                                log.info("PUT. Meciul cu id-ul " + idMeci + " a fost actualizat cu succes!");
                                return true;
                            } else {
                                log.warn("PUT. Meciul cu id-ul " + idMeci + " nu a putut fi actualizat!");
                                return false;
                            }
                        } catch (PersistenceException e) {
                            throw new MeciExpectationFailedException("POST. Meciul nu a putut fi inregistrat!");
                        } catch (DataIntegrityViolationException e) {
                            throw new MeciExpectationFailedException("POST. Meciul nu a putut fi inregistrat avand informatii lipsa sau necorespunzatoare!");
                        }
                    } else {
                        log.warn("PUT. Locatia meciului nu poate fi nule!");
                    }

                } else {
                    log.warn("PUT. Parametrul meci nu poate primi valori nule!");
                }
            }else{
                log.warn("INSERT. Parametrul divizie si idDivize nu poate fi null!");
            }
        }else{
            log.warn("INSERT. Parametrul idMeci nu poate primi valori nule!");
        }

        return false;
    }
    @Transactional
    public boolean deleteMeci(int idMeci){
        try {
            int rezultat = entityManager.createNativeQuery("DELETE FROM meci m WHERE m.mec_id=?")
                    .setParameter(1, idMeci)
                    .executeUpdate();
            if (rezultat > 0) {
                log.info("DELETE. Meciul cu id-ul " + idMeci + " a fost stears cu succes!");
                return true;
            } else {
                log.warn("DELETE. Meciul cu id-ul " + idMeci + " nu a putut fi stears!");
                return false;
            }
        }catch(PersistenceException e) {
            throw new MeciExpectationFailedException("DELETE. Meciul nu a putut fi stears!");
        }

    }






}
