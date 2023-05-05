package ro.marcc.server.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import ro.marcc.server.dto.SenioriDto;
import ro.marcc.server.exception.PersonalExpectationFailedException;
import ro.marcc.server.exception.PersonalNotFoundException;
import ro.marcc.server.exception.StireExpectationFailedException;
import ro.marcc.server.model.Localitate.Judet;
import ro.marcc.server.model.Localitate.Localitate;
import ro.marcc.server.model.Localitate.Tara;
import ro.marcc.server.model.Meciuri.Campionat;
import ro.marcc.server.model.Meciuri.Echipa;
import ro.marcc.server.model.Personal.Persoana;
import ro.marcc.server.model.Personal.Roluri.Antrenor;
import ro.marcc.server.model.Personal.Roluri.Jucator;
import ro.marcc.server.model.Realizari.Premiu;
import ro.marcc.server.model.Realizari.Trofeu;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Repository
public class RepositoryPersonal {
    private final EntityManager entityManager;

    private final RepositoryPremii repositoryPremii;

    public RepositoryPersonal(EntityManager entityManager, RepositoryPremii repositoryPremii) {
        this.entityManager = entityManager;
        this.repositoryPremii = repositoryPremii;
    }

    @Transactional
    public Persoana getPersoana(int idPersoana, boolean transferabil){
        try{
            List<Object[]> persoaneBD = entityManager.createNativeQuery("SELECT p.per_id,p.per_nume,p.per_prenume,p.per_link_imagine,p.per_nationalitate,p.per_post,p.per_data_nasterii,p.per_inaltime,p.per_descriere,p.per_id_echipa,p.per_seniori FROM Persoana p WHERE p.per_id = ?")
                        .setParameter(1,idPersoana)
                        .getResultList();

            if(!persoaneBD.isEmpty()) {
                Object[] persoanaBD = persoaneBD.get(0);

                int id = (int) persoanaBD[0];
                String nume = (String) persoanaBD[1];
                String prenume = (String) persoanaBD[2];
                String linkImagine = (String) persoanaBD[3];
                String nationalitate = (String) persoanaBD[4];
                String post = (String) persoanaBD[5];
                String dataNasterii = (String) persoanaBD[6];
                int inaltime = (int) persoanaBD[7];
                String descriere = (String) persoanaBD[8];
                Integer idEchipa = (Integer) persoanaBD[9];
                Integer lotSeniori = (Integer) persoanaBD[10];


                List<Premiu> listaPremii = repositoryPremii.getPremii(id);

                Echipa echipa = echipa = getEchipa(idEchipa, true);

                if (!transferabil) {
                    echipa = getEchipa(idEchipa, true);
                    echipa.setAntrenor(null);
                    for (Jucator jucator :
                            echipa.getJucatori()) {
                        jucator.setEchipa(null);
                    }
                }

                log.info("GET. S-au preluat datele persoanei cu id-ul " + idPersoana + "!");

                return new Persoana(id, nume, prenume, linkImagine, nationalitate, post, dataNasterii, inaltime, descriere, getRoluriPersona(id), listaPremii, lotSeniori);

            }else{
                log.info("GET. Nu s-au preluat datele persoanei cu id-ul "+idPersoana+"!");
            }
        }catch(PersistenceException e) {
            log.warn("GET. Nu s-a putut prelua datele persoanei cu id-ul "+idPersoana+"!");
        }
        return null;
    }
    @Transactional
    public Set<Jucator> getJucatori(String valoareDeFiltrare) {
        Set<Jucator> jucatoriFiltrati = new LinkedHashSet<>();
        List<Object[]> jucatoriBD = null;

        try {

            jucatoriBD = entityManager.createNativeQuery("SELECT p.per_id,p.per_nume,p.per_prenume,p.per_link_imagine,p.per_nationalitate,p.per_post,p.per_data_nasterii,p.per_inaltime,p.per_descriere,p.per_id_echipa,p.per_id_antrenor,p.per_seniori FROM Persoana p LEFT JOIN meci m ON p.per_id_echipa=m.mec_id_echipa1 OR p.per_id_echipa=m.mec_id_echipa2 LEFT JOIN campionat c ON m.mec_id_campionat=c.cam_id WHERE (upper(c.cam_denumire) LIKE ?1 OR upper(p.per_prenume) LIKE ?1 OR upper(p.per_nume) LIKE ?1) AND p.per_id_antrenor is not null")
                    .setParameter(1, valoareDeFiltrare.toUpperCase() + "%")
                    .getResultList();

            if (!jucatoriBD.isEmpty()) {
                for (Object[] obj :
                        jucatoriBD) {
                    int id = (int) obj[0];
                    String nume = (String) obj[1];
                    String prenume = (String) obj[2];
                    String linkImagine = (String) obj[3];
                    String nationalitate = (String) obj[4];
                    String post = (String) obj[5];
                    String dataNasterii = (String) obj[6];
                    int inaltime = (int) obj[7];
                    String descriere = (String) obj[8];
                    int idEchipa = (Integer) obj[9];
                    Integer lotSeniori = (Integer) obj[10];

                    List<Premiu> listaPremii = repositoryPremii.getPremii(id);

                    Echipa echipa = echipa = getEchipa(idEchipa, true);


                    Jucator jucator = new Jucator(id, nume, prenume, linkImagine, nationalitate, post, dataNasterii, inaltime, descriere, getRoluriPersona(id), listaPremii, echipa,lotSeniori);

                    jucatoriFiltrati.add(jucator);
                }
                log.info("GET. S-a preluat cu succes lista de jucatori filtrati!");

            }else{
                log.info("GET. Nu s-a gasit nici un jucator de preluat ce sa respecte filtrul!");
            }
        }catch(PersistenceException e){
            throw new PersonalExpectationFailedException("GET. Nu s-a putut prelua lista filtrata de jucatori!");
        }
        return jucatoriFiltrati;
    }
    @Transactional
    public Set<Antrenor> getAntrenori(String valoareDeFiltrare) {
        Set<Antrenor> antrenoriFiltrati = new LinkedHashSet<>();
        List<Object[]> antrenoriBD = null;

        try {

            antrenoriBD = entityManager.createNativeQuery("SELECT p.per_id,p.per_nume,p.per_prenume,p.per_link_imagine,p.per_nationalitate,p.per_post,p.per_data_nasterii,p.per_inaltime,p.per_descriere,p.per_id_echipa,p.per_id_antrenor,p.per_seniori FROM Persoana p LEFT JOIN meci m ON p.per_id_echipa=m.mec_id_echipa1 OR p.per_id_echipa=m.mec_id_echipa2 LEFT JOIN campionat c ON m.mec_id_campionat=c.cam_id WHERE (upper(c.cam_denumire) LIKE ?1 OR upper(p.per_prenume) LIKE ?1 OR upper(p.per_nume) LIKE ?1) AND p.per_id_antrenor is null")
                    .setParameter(1, valoareDeFiltrare.toUpperCase() + "%")
                    .getResultList();

            if (!antrenoriBD.isEmpty()) {
                for (Object[] obj :
                        antrenoriBD) {
                    int id = (int) obj[0];
                    String nume = (String) obj[1];
                    String prenume = (String) obj[2];
                    String linkImagine = (String) obj[3];
                    String nationalitate = (String) obj[4];
                    String post = (String) obj[5];
                    String dataNasterii = (String) obj[6];
                    int inaltime = (int) obj[7];
                    String descriere = (String) obj[8];
                    int idEchipa = (Integer) obj[9];
                    Integer lotSeniori = (Integer) obj[10];

                    List<Premiu> listaPremii = repositoryPremii.getPremii(id);

                    Echipa echipa = echipa = getEchipa(idEchipa, true);


                    Antrenor antrenor = new Antrenor(id, nume, prenume, linkImagine, nationalitate, post, dataNasterii, inaltime, descriere, getRoluriPersona(id), listaPremii, echipa,lotSeniori);

                    antrenoriFiltrati.add(antrenor);
                }
                log.info("GET. S-a preluat cu succes lista de antrenori filtrati!");

            }else{
                log.info("GET. Nu s-a gasit nici un antrenor de preluat ce sa respecte filtrul!");
            }
        }catch(PersistenceException e){
            throw new PersonalExpectationFailedException("GET. Nu s-a putut prelua lista filtrata de antrenori!");
        }
        return antrenoriFiltrati;
    }

    @Transactional
    public List<Antrenor> getAntrenori(boolean transferabil){
        List<Antrenor> antrenori = new ArrayList<>();
        try {
            List<Object[]> antrenoriBD = entityManager.createNativeQuery("SELECT p.per_id,p.per_nume,p.per_prenume,p.per_link_imagine,p.per_nationalitate,p.per_post,p.per_data_nasterii,p.per_inaltime,p.per_descriere,p.per_id_echipa,p.per_seniori FROM Persoana p WHERE p.per_id_antrenor is null").getResultList();
            if(!antrenoriBD.isEmpty()){
                for (Object[] obj :
                    antrenoriBD) {
                    int id = (int) obj[0];
                    String nume = (String) obj[1];
                    String prenume = (String) obj[2];
                    String linkImagine = (String) obj[3];
                    String nationalitate = (String) obj[4];
                    String post = (String) obj[5];
                    String dataNasterii = (String) obj[6];
                    int inaltime = (int) obj[7];
                    String descriere = (String) obj[8];
                    Integer idEchipa = (Integer) obj[9];
                    Integer lotSeniori = (Integer) obj[10];

                    Echipa echipa = getEchipa(idEchipa, true);
                    echipa.setJucatori(null);
                    echipa.setAntrenor(null);

                    List<Premiu> listaPremii = repositoryPremii.getPremii(id);

                    Antrenor antrenor = new Antrenor(id, nume, prenume, linkImagine, nationalitate, post, dataNasterii, inaltime, descriere, getRoluriPersona(id), listaPremii, echipa, lotSeniori);


                    antrenori.add(antrenor);
                }
                log.info("GET. S-a preluat cu succes lista de antrenori!");
            }else{
                log.info("GET. Nu s-a agasit nici un antrenor de preluat!");
            }


        }catch(PersistenceException e){
            log.warn("GET. Nu s-a putut prelua lista de antrenori!");
        }

        return antrenori;
    }

    @Transactional
    public Antrenor getAntrenorEchipa(Integer idEchipa, boolean transferabil){
        Antrenor antrenor = new Antrenor();
        antrenor.setId(null);
        try {
            List<Object[]> antrenoriBD = entityManager.createNativeQuery("SELECT p.per_id,p.per_nume,p.per_prenume,p.per_link_imagine,p.per_nationalitate,p.per_post,p.per_data_nasterii,p.per_inaltime,p.per_descriere,p.per_seniori FROM Persoana p WHERE p.per_id_echipa = ?1 and p.per_id_antrenor is null")
                    .setParameter(1, idEchipa)
                    .getResultList();
            if (!antrenoriBD.isEmpty()) {
                Integer id = (Integer) antrenoriBD.get(0)[0];
                String nume = (String) antrenoriBD.get(0)[1];
                String prenume = (String) antrenoriBD.get(0)[2];
                String linkImagine = (String) antrenoriBD.get(0)[3];
                String nationalitate = (String) antrenoriBD.get(0)[4];
                String post = (String) antrenoriBD.get(0)[5];
                String dataNasterii = (String) antrenoriBD.get(0)[6];
                int inaltime = (int) antrenoriBD.get(0)[7];
                String descriere = (String) antrenoriBD.get(0)[8];
                Integer lotSeniori = (Integer) antrenoriBD.get(0)[9];

                List<Premiu> listaPremii = repositoryPremii.getPremii(id);
                Echipa echipa = null;

                if (!transferabil) {
                    echipa = getEchipa(idEchipa, true);
                    echipa.setAntrenor(null);
                    for (Jucator jucator :
                            echipa.getJucatori()) {
                        jucator.setEchipa(null);
                    }
                }

                antrenor = new Antrenor(id, nume, prenume, linkImagine, nationalitate, post, dataNasterii, inaltime, descriere, getRoluriPersona(id), listaPremii, echipa,lotSeniori);
                log.info("GET. S-a preluat cu succes antrenorul echipei cu id-ul "+idEchipa+"!");
            }
        }catch(PersistenceException e){
            log.warn("GET. Nu s-a putut prelua antrenorul echipei cu id-ul "+idEchipa+"!");
        }


        return antrenor;
    }

    @Transactional
    private List<Jucator> getListaSeniori(int idSeniori){
        List<Jucator> jucatori = new ArrayList<>();
        try{
            List<Object[]> jucatoriBD;

            jucatoriBD = entityManager.createNativeQuery("SELECT p.per_id,p.per_nume,p.per_prenume,p.per_link_imagine,p.per_nationalitate,p.per_post,p.per_data_nasterii,p.per_inaltime,p.per_descriere,p.per_id_echipa,p.per_seniori FROM Persoana p WHERE p.per_seniori=?")
                    .setParameter(1,idSeniori)
                    .getResultList();

            if(!jucatoriBD.isEmpty()){
                for (Object[] obj:
                        jucatoriBD) {
                    int id = (int) obj[0];
                    String nume = (String) obj[1];
                    String prenume = (String) obj[2];
                    String linkImagine = (String) obj[3];
                    String nationalitate = (String) obj[4];
                    String post = (String) obj[5];
                    String dataNasterii = (String) obj[6];
                    int inaltime = (int) obj[7];
                    String descriere = (String) obj[8];
                    int idEchipa = (Integer) obj[9];
                    Integer lotSeniori = (Integer) obj[10];


                    List<Premiu> listaPremii = repositoryPremii.getPremii(id);

                    Echipa echipa = echipa = getEchipa(idEchipa, true);

                    Jucator jucator = new Jucator(id, nume, prenume, linkImagine, nationalitate, post, dataNasterii, inaltime, descriere, getRoluriPersona(id), listaPremii,echipa,lotSeniori);

                    jucatori.add(jucator);
                }
                log.info("GET. S-a preluat cu succes lista seniorilor!");
            }else{
                log.info("GET. Nu s-a gasit nici un senior!");
            }
        }catch(PersistenceException e) {
            log.warn("GET. Nu s-a putut prelua lista de seniori!");
        }

        return jucatori;
    }

    @Transactional
    public SenioriDto getSeniori(){
        try{
            List<Object[]> senioriBD = entityManager.createNativeQuery("SELECT * FROM seniori s").getResultList();

            if(senioriBD.isEmpty()){
                throw new PersonalNotFoundException("GET. Nu s-au gasit detaliile referitoare la seniori!");
            }
            Object[] seniorBD = senioriBD.get(0);

            int idSeniori = (Integer)seniorBD[0];
            String senioriLot = (String)seniorBD[1];
            String senioriImagine = (String)seniorBD[2];
            String senioriDetalii = (String)seniorBD[3];

            SenioriDto senioriDto = new SenioriDto(
                    senioriLot,
                    senioriImagine,
                    senioriDetalii,
                    getListaSeniori(idSeniori)
            );
            log.info("GET. Detalii referitoare la seniori preluate cu succes!");
            return senioriDto;
        }catch(PersistenceException e) {
            throw new PersonalExpectationFailedException("GET. Nu s-au putut prelua detaliile referitoare la seniori!");
        }
    }

    @Transactional
    public List<Jucator> getjucatoriEchipa(Integer idEchipa, boolean transferabil){
        List<Jucator> jucatori = new ArrayList<>();
        try{
            List<Object[]> jucatoriBD;
            if(idEchipa==null) {
                jucatoriBD = entityManager.createNativeQuery("SELECT p.per_id,p.per_nume,p.per_prenume,p.per_link_imagine,p.per_nationalitate,p.per_post,p.per_data_nasterii,p.per_inaltime,p.per_descriere,p.per_id_echipa,p.per_seniori FROM Persoana p WHERE p.per_id_antrenor is not null").getResultList();
            }else{
                jucatoriBD = entityManager.createNativeQuery("SELECT p.per_id,p.per_nume,p.per_prenume,p.per_link_imagine,p.per_nationalitate,p.per_post,p.per_data_nasterii,p.per_inaltime,p.per_descriere,p.per_id_echipa,p.per_seniori FROM Persoana p WHERE p.per_id_echipa = ?1 AND p.per_id_antrenor is not null")
                    .setParameter(1,idEchipa)
                    .getResultList();
            }
            if(!jucatoriBD.isEmpty()){
                for (Object[] obj:
                jucatoriBD) {
                    int id = (int) obj[0];
                    String nume = (String) obj[1];
                    String prenume = (String) obj[2];
                    String linkImagine = (String) obj[3];
                    String nationalitate = (String) obj[4];
                    String post = (String) obj[5];
                    String dataNasterii = (String) obj[6];
                    int inaltime = (int) obj[7];
                    String descriere = (String) obj[8];
                    idEchipa = (Integer) obj[9];
                    Integer lotSeniori = (Integer) obj[10];


                    List<Premiu> listaPremii = repositoryPremii.getPremii(id);

                    Echipa echipa = echipa = getEchipa(idEchipa, true);

                    if (!transferabil) {
                        echipa = getEchipa(idEchipa, true);
                        echipa.setAntrenor(null);
                        for (Jucator jucator :
                                echipa.getJucatori()) {
                            jucator.setEchipa(null);
                        }
                    }

                    Jucator jucator = new Jucator(id, nume, prenume, linkImagine, nationalitate, post, dataNasterii, inaltime, descriere, getRoluriPersona(id), listaPremii, echipa,lotSeniori);

                    jucatori.add(jucator);
                }
                log.info("GET. S-a preluat cu succes lista jucatorilor echipei cu id-ul "+idEchipa+"!");
            }else{
                log.info("GET. Nu s-au gasit jucatorii echipei cu id-ul "+idEchipa+"!");
            }
        }catch(PersistenceException e) {
            log.warn("GET. Nu s-a putut prelua lista de jucatori ai echipei cu id-ul "+idEchipa+"!");

        }

        return jucatori;
    }

    @Transactional
    public boolean addJucator(Jucator jucator){
        if(jucator!=null){
            Echipa echipaJucator = jucator.getEchipa();
            if(echipaJucator==null || echipaJucator.getId()==null){
                throw new PersonalExpectationFailedException("POST. Jucatorul trebuie sa aiba mentionata si o echipa!");
            }

            Antrenor antrenorJucator = echipaJucator.getAntrenor();
            if(antrenorJucator==null || antrenorJucator.getId()==null){
                antrenorJucator = new Antrenor();
                antrenorJucator.setId(getAntrenorEchipa(echipaJucator.getId(),true).getId());
            }
            int result=0;
            try {
                if(jucator.getId()==null) {
                    result = entityManager.createNativeQuery("INSERT INTO Persoana(per_nume,per_prenume,per_link_imagine,per_nationalitate,per_post,per_data_nasterii,per_inaltime,per_descriere,per_id_echipa,per_id_antrenor,per_seniori) VALUES(?,?,?,?,?,?,?,?,?,?,?)")
                            .setParameter(1, jucator.getNume())
                            .setParameter(2, jucator.getPrenume())
                            .setParameter(3, jucator.getLinkPoza())
                            .setParameter(4, jucator.getNationalitate())
                            .setParameter(5, jucator.getPost())
                            .setParameter(6, jucator.getDataNasterii())
                            .setParameter(7, jucator.getInaltime())
                            .setParameter(8, jucator.getDescriere())
                            .setParameter(9, echipaJucator.getId())
                            .setParameter(10, antrenorJucator == null ? null : antrenorJucator.getId())
                            .setParameter(11, jucator.getLotSeniori()==null?null:jucator.getLotSeniori()<0?null:jucator.getLotSeniori())
                            .executeUpdate();
                }
                else{
                    result = entityManager.createNativeQuery("INSERT INTO Persoana(per_id,per_nume,per_prenume,per_link_imagine,per_nationalitate,per_post,per_data_nasterii,per_inaltime,per_descriere,per_id_echipa,per_id_antrenor,per_seniori) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)")
                            .setParameter(1, jucator.getId())
                            .setParameter(2, jucator.getNume())
                            .setParameter(3, jucator.getPrenume())
                            .setParameter(4, jucator.getLinkPoza())
                            .setParameter(5, jucator.getNationalitate())
                            .setParameter(6, jucator.getPost())
                            .setParameter(7, jucator.getDataNasterii())
                            .setParameter(8, jucator.getInaltime())
                            .setParameter(9, jucator.getDescriere())
                            .setParameter(10, echipaJucator.getId())
                            .setParameter(11, antrenorJucator == null ? null : antrenorJucator.getId())
                            .setParameter(12, jucator.getLotSeniori()==null?null:jucator.getLotSeniori()<0?null:jucator.getLotSeniori())
                            .executeUpdate();

                }
                if(updateRoluri(jucator.getId(),jucator.getRoluri())
                        && repositoryPremii.updatePremii(jucator)
                        && result>0) {
                    log.info("POST. Jucator inregistrat cu succes!");
                    return true;
                }else{
                    log.info("POST. Jucatorul nu a putut fi inregistrat!");
                    return false;
                }
            }catch(PersistenceException e){
                throw new PersonalExpectationFailedException("POST. Nu s-a putut inregistra noul jucator!");
            }catch(DataIntegrityViolationException e){
                throw new PersonalExpectationFailedException("POST. Nu s-a putut inregistra noul jucator avand informatii lipsa sau necorespunzatoare!");
            }

        }else{
            log.warn("POST. Parametrul jucator nu poate primi valori nule!");
        }
        return false;
    }
    @Transactional
    public boolean addAntrenor(Antrenor antrenor){
        if(antrenor!=null){
            Echipa echipaJucator = antrenor.getEchipa();
            int result=0;
            if(echipaJucator==null || echipaJucator.getId()==null){
                throw new PersonalExpectationFailedException("POST. Antrenorul trebuie sa aiba mentionata si o echipa!");
            }

            try {
                if(antrenor.getId()==null) {
                    result = entityManager.createNativeQuery("INSERT INTO Persoana(per_nume,per_prenume,per_nationalitate,per_post,per_data_nasterii,per_inaltime,per_descriere,per_id_echipa,per_link_imagine,per_seniori) VALUES(?,?,?,?,?,?,?,?,?,?)")
                            .setParameter(1, antrenor.getNume())
                            .setParameter(2, antrenor.getPrenume())
                            .setParameter(3, antrenor.getNationalitate())
                            .setParameter(4, antrenor.getPost())
                            .setParameter(5, antrenor.getDataNasterii())
                            .setParameter(6, antrenor.getInaltime())
                            .setParameter(7, antrenor.getDescriere())
                            .setParameter(8, echipaJucator.getId())
                            .setParameter(9, antrenor.getLinkPoza())
                            .setParameter(10, antrenor.getLotSeniori()==null?null:antrenor.getLotSeniori()<0?null:antrenor.getLotSeniori())

                            .executeUpdate();
                }else{
                    result = entityManager.createNativeQuery("INSERT INTO Persoana(per_id,per_nume,per_prenume,per_nationalitate,per_post,per_data_nasterii,per_inaltime,per_descriere,per_id_echipa,per_link_imagine,per_seniori) VALUES(?,?,?,?,?,?,?,?,?,?,?)")
                            .setParameter(1, antrenor.getId())
                            .setParameter(2, antrenor.getNume())
                            .setParameter(3, antrenor.getPrenume())
                            .setParameter(4, antrenor.getNationalitate())
                            .setParameter(5, antrenor.getPost())
                            .setParameter(6, antrenor.getDataNasterii())
                            .setParameter(7, antrenor.getInaltime())
                            .setParameter(8, antrenor.getDescriere())
                            .setParameter(9, echipaJucator.getId())
                            .setParameter(10, antrenor.getLinkPoza())
                            .setParameter(11, antrenor.getLotSeniori()==null?null:antrenor.getLotSeniori()<0?null:antrenor.getLotSeniori())

                            .executeUpdate();
                }
                ;
                if(updateRoluri(antrenor.getId(),antrenor.getRoluri())
                        && repositoryPremii.updatePremii(antrenor)
                        && result>0) {
                    log.info("POST. Antrenor inregistrat cu succes!");
                    return true;
                }else{
                    log.info("POST. Antrenorul nu a putut fi inregistrat!");
                }
            }catch(PersistenceException e){
                throw new PersonalExpectationFailedException("POST. Nu s-a putut inregistra noul antrenor!");
            }catch(DataIntegrityViolationException e){
                throw new PersonalExpectationFailedException("POST. Nu s-a putut inregistra noul antrenor avand informatii lipsa sau necorespunzatoare!");
            }

        }else{
            log.warn("POST. Parametrul antrenor nu poate primi valori nule!");
        }
        return false;
    }

    @Transactional
    public boolean updateJucator(Integer idPersoana,Jucator jucator){
        if(jucator!=null){
            if(idPersoana!=null) {
                jucator.setId(idPersoana);
                Echipa echipaJucator = jucator.getEchipa();
                if(echipaJucator==null || echipaJucator.getId()==null){
                    throw new PersonalExpectationFailedException("POST. Jucatorul trebuie sa aiba mentionata si o echipa!");
                }
                Antrenor antrenorJucator = echipaJucator.getAntrenor();
                if(antrenorJucator==null || antrenorJucator.getId()==null){
                    antrenorJucator = new Antrenor();
                    antrenorJucator.setId(getAntrenorEchipa(echipaJucator.getId(),true).getId());
                }
                int rezultat;
                try {

                    rezultat = entityManager.createNativeQuery("UPDATE persoana p SET p.per_nume=?1,p.per_prenume=?2,p.per_nationalitate=?3,p.per_post=?4,p.per_data_nasterii=?5,p.per_inaltime=?6,p.per_descriere=?7,p.per_id_echipa=?8,p.per_id_antrenor=?9,p.per_link_imagine=?10,p.per_seniori=?11 WHERE p.per_id=?12")
                            .setParameter(12, idPersoana)
                            .setParameter(1, jucator.getNume())
                            .setParameter(2, jucator.getPrenume())
                            .setParameter(3, jucator.getNationalitate())
                            .setParameter(4, jucator.getPost())
                            .setParameter(5, jucator.getDataNasterii())
                            .setParameter(6, jucator.getInaltime())
                            .setParameter(7, jucator.getDescriere())
                            .setParameter(8, echipaJucator.getId())
                            .setParameter(9, antrenorJucator.getId())
                            .setParameter(10, jucator.getLinkPoza())
                            .setParameter(11, jucator.getLotSeniori()==null?null:jucator.getLotSeniori()<0?null:jucator.getLotSeniori())
                            .executeUpdate();
                    updateRoluri(idPersoana, jucator.getRoluri());
                    repositoryPremii.updatePremii(jucator);
                    if (rezultat > 0) {
                        log.info("PUT. Actualizare cu succes a informatiilor jucatorului cu id-ul" + idPersoana + "!");
                        return true;
                    } else {
                        log.warn("PUT. Actualizare esuata a informatiilor jucatorului cu id-ul" + idPersoana + "!");
                        return false;
                    }
                } catch (PersistenceException e) {
                    throw new PersonalExpectationFailedException("PUT. Nu s-a putut actualiza datele persoanei cu id-ul " + idPersoana + "!");
                } catch (DataIntegrityViolationException e) {
                    throw new PersonalExpectationFailedException("PUT. Nu s-a putut actualiza datele persoanei cu id-ul " + idPersoana + " avand informatii lipsa sau necorespunzatoare!");
                }
            }else{
                log.warn("PUT. Parametrul idPersoana nu poate primi valori nule!");
            }
        }else{
            log.warn("PUT. Parametrul jucator nu poate primi valori nule!");
        }
        return false;
    }

    @Transactional
    public boolean updateAntrenor(Integer idPersoana,Antrenor antrenor){
        if(antrenor!=null){
            if(idPersoana!=null) {
                antrenor.setId(idPersoana);
                Echipa echipaAntrenor = antrenor.getEchipa();
                if(echipaAntrenor==null || echipaAntrenor.getId()==null){
                    throw new PersonalExpectationFailedException("POST. Jucatorul trebuie sa aiba mentionata si o echipa!");
                }
                int result = 0;
                try {
                    result = entityManager.createNativeQuery("UPDATE persoana p SET p.per_nume=?1,p.per_prenume=?2,p.per_nationalitate=?3,p.per_post=?4,p.per_data_nasterii=?5,p.per_inaltime=?6,p.per_descriere=?7,p.per_id_echipa=?8,p.per_id_antrenor=?9,p.per_link_imagine=?10,p.per_seniori=?11 WHERE p.per_id=?12")
                            .setParameter(12, idPersoana)
                            .setParameter(1, antrenor.getNume())
                            .setParameter(2, antrenor.getPrenume())
                            .setParameter(3, antrenor.getNationalitate())
                            .setParameter(4, antrenor.getPost())
                            .setParameter(5, antrenor.getDataNasterii())
                            .setParameter(6, antrenor.getInaltime())
                            .setParameter(7, antrenor.getDescriere())
                            .setParameter(8, echipaAntrenor.getId())
                            .setParameter(9, null)
                            .setParameter(10, antrenor.getLinkPoza())
                            .setParameter(11, antrenor.getLotSeniori()==null?null:antrenor.getLotSeniori()<0?null:antrenor.getLotSeniori())
                            .executeUpdate();
                    updateRoluri(idPersoana, antrenor.getRoluri());
                    repositoryPremii.updatePremii(antrenor);
                    if (result > 0) {
                        log.info("PUT. Actualizare cu succes a informatiilor antrenorului cu id-ul" + idPersoana + "!");
                        return true;
                    } else {
                        log.warn("PUT. Actualizare esuata a informatiilor antrenorului cu id-ul" + idPersoana + "!");
                        return false;
                    }
                } catch (PersistenceException e) {
                    throw new PersonalExpectationFailedException("PUT. Nu s-a putut actualiza datele persoanei cu id-ul " + idPersoana + "!");
                } catch (DataIntegrityViolationException e) {
                    throw new PersonalExpectationFailedException("PUT. Nu s-a putut actualiza datele persoanei cu id-ul " + idPersoana + " avand informatii lipsa sau necorespunzatoare!");
                }
            }else{
                log.warn("PUT. Parametrul idPersoana nu poate primi valori nule!");
            }
        }else{
            log.warn("PUT. Parametrul antrenor nu poate primi valori nule!");
        }
        return false;
    }
    @Transactional
    public boolean deletePersoana(Integer idPersoana){
        if(idPersoana!=null){
            int result=0;
            try {
                updateRoluri(idPersoana,null);
                repositoryPremii.deletePremii(idPersoana);
                result = entityManager.createNativeQuery("DELETE FROM persoana p WHERE p.per_id = ?")
                        .setParameter(1,idPersoana)
                        .executeUpdate();
                if(result>0) {
                    log.info("DELETE. Persoana cu id-ul "+idPersoana+" stearsa cu succes!");
                    return true;
                }else{
                    log.warn("DELETE. Persoana cu id-ul "+idPersoana+" nu a putut fi stearsa!");
                    return false;
                }
            }catch(PersistenceException e){
                throw new PersonalExpectationFailedException("DELETE. Nu s-a putut sterge persoana cu id-ul "+idPersoana+"!");
            }

        }else{
            log.warn("DELETE. Parametrul id nu poate primi valori nule!");
        }
        return false;
    }

    @Transactional
    public TreeMap<LocalDate,String> getRoluriPersona(int idPersoana){
        TreeMap<LocalDate,String> roluri = new TreeMap<>();
        try {
            List<Object[]> roluriBD = entityManager.createNativeQuery("SELECT r.rol_data,r.rol_descriere FROM roluri r WHERE r.rol_id_persoana = ?1")
                    .setParameter(1, idPersoana)
                    .getResultList();
            for (Object[] obj :
                    roluriBD) {
                Date data = (Date) obj[0];
                String descriere = (String) obj[1];

                roluri.put(data.toLocalDate(), descriere);
            }

            if(roluriBD.isEmpty()){
                log.info("GET. Nu s-a gasit nici un rol al persoanei cu id-ul "+idPersoana+"!");
            }else{
                log.info("GET. Lista de roluri ale persoanei cu id-ul "+idPersoana+" preluata cu succes!");
            }
        }catch(PersistenceException e){
            log.warn("GET. Nu s-a putut prelua lista de roluri!");
        }


        return roluri;
    }

    @Transactional
    private boolean updateRoluri(Integer idPersoana,TreeMap<LocalDate,String> roluri){
        if (idPersoana != null) {
            try {
                entityManager.createNativeQuery("DELETE FROM roluri r WHERE r.rol_id_persoana=?")
                        .setParameter(1, idPersoana)
                        .executeUpdate();

            }catch(PersistenceException e){
                throw new StireExpectationFailedException("DELETE. Nu s-au putut sterge rolurile persoanei cu id-ul "+idPersoana+"!");
            }
            if (roluri != null) {
                try{
                    for(LocalDate dataPrimireRol: roluri.keySet()) {
                        entityManager.createNativeQuery("INSERT INTO roluri(rol_data,rol_descriere,rol_id_persoana) VALUES(?,?,?)")
                                .setParameter(1,dataPrimireRol)
                                .setParameter(2,roluri.get(dataPrimireRol))
                                .setParameter(3,idPersoana)
                                .executeUpdate();
                    }
                    return true;
                }catch(PersistenceException e){
                    throw new StireExpectationFailedException("POST. Nu s-au putut actualiza rolurile persoanei cu id-ul "+idPersoana+"!");
                }catch(DataIntegrityViolationException e){
                    throw new PersonalExpectationFailedException("PUT. Nu s-a putut actualiza rolurile persoanei cu id-ul "+idPersoana+" avand informatii lipsa sau necorespunzatoare!");
                }

            }else {
                log.warn("PUT. Parametrul roluri nu poate primi valori nule!");
            }

        }else {
            log.warn("PUT. Parametrul idPersoana nu poate primi valori nule!");
        }

        return false;
    }

    @Transactional
    public List<Echipa> getEchipe(boolean cuAntrenor, boolean transferabil){
        List<Echipa> echipe = new ArrayList<>();
        try {
            List<Object[]> echipeBD = entityManager.createNativeQuery("SELECT e.ech_id,e.ech_nume,e.ech_logo,e.ech_csm FROM Echipa e").getResultList();
            for (Object[] obj :
                    echipeBD) {
                int id = (int) obj[0];
                String nume = (String) obj[1];
                String logo = (String) obj[2];
                boolean echipaCsm = (boolean) obj[3];
                List<Jucator> membriiEchipa = null;
                if (transferabil) {
                    membriiEchipa = getjucatoriEchipa(id, true);
                }
                Antrenor antrenor = getAntrenorEchipa(id, true);

                List<Trofeu> listaTrofee = getTrofee(id, true);

                Echipa echipa = new Echipa(id, nume, membriiEchipa, antrenor, listaTrofee, logo,echipaCsm);

                if(!cuAntrenor || antrenor != null) {
                    echipe.add(echipa);
                }
            }

            if(echipe.isEmpty()){
                log.info("GET. Nu s-a gasit nici o echipa de preluat!");
            }else{
                log.info("GET. Lista de echipe preluata cu succes!");

            }
        }catch(PersistenceException e){
            log.warn("GET. Nu s-a putut perlua lista de echipe!");
        }


        return echipe;
    }

    @Transactional
    public Echipa getEchipa(int idEchipa,boolean transferabil){
        try {
            Echipa echipa = null;

            List<Object[]> echipeBD = entityManager.createNativeQuery("SELECT e.ech_nume,e.ech_logo,e.ech_csm FROM Echipa e WHERE e.ech_id=?1")
                    .setParameter(1, idEchipa)
                    .getResultList();

            if (!echipeBD.isEmpty()) {
                String nume = (String) echipeBD.get(0)[0];
                String logo = (String) echipeBD.get(0)[1];
                boolean echipaCsm = (boolean) echipeBD.get(0)[2];
                List<Jucator> membriiEchipa = null;

                if (!transferabil) {
                    membriiEchipa = getjucatoriEchipa(idEchipa, true);
                    for (Jucator jucator :
                            membriiEchipa) {
                        jucator.setEchipa(null);
                    }
                }

                Antrenor antrenor = getAntrenorEchipa(idEchipa, true);

                List<Trofeu> listaTrofee = getTrofee(idEchipa, true);
                echipa = new Echipa(idEchipa, nume, membriiEchipa, antrenor, listaTrofee, logo, echipaCsm);
                log.info("GET. Echipa cu id-ul " + idEchipa + " a fost gasita!");
            } else {
                log.warn("GET. Echipa cu id-ul " + idEchipa + " nu a fost gasita!");
            }

            return echipa;
        }catch(PersistenceException e){
            throw new PersonalExpectationFailedException("GET. Echipa cu id-ul " + idEchipa + " nu a putut fi preluata!");
        }
    }

    @Transactional
    public boolean addEchipa(Echipa echipa){
        if(echipa!=null){
            int rezultat = 0;
            try {
                if (echipa.getId() == null) {
                    rezultat = entityManager.createNativeQuery("INSERT INTO echipa(ech_nume,ech_logo,ech_csm) VALUES (?,?,?)")
                            .setParameter(1, echipa.getNume())
                            .setParameter(2, echipa.getLogo())
                            .setParameter(3, echipa.isCsm())
                            .executeUpdate();
                } else{
                    rezultat = entityManager.createNativeQuery("INSERT INTO echipa(ech_id,ech_nume,ech_logo,ech_csm) VALUES (?,?,?,?)")
                            .setParameter(1, echipa.getId())
                            .setParameter(2, echipa.getNume())
                            .setParameter(3, echipa.getLogo())
                            .setParameter(4, echipa.isCsm())
                            .executeUpdate();
                }

            }catch(PersistenceException e){
                throw new PersonalExpectationFailedException("POST. Nu s-a putut inregistra echipa!");
            }
            catch(DataIntegrityViolationException e){
                throw new PersonalExpectationFailedException("POST. Nu s-a putut inregistra echipa avand informatii lipsa sau necorespunzatoare!");
            }
            if (rezultat > 0) {
                log.info("POST. Echipa a fost inregistrata cu succes!");
                return true;
            } else {
                log.warn("POST. Echipa nu a putut fi inregistrata!");
                return false;
            }
        }else{
            throw new PersonalExpectationFailedException("POST. Parametrul echipa nu poate primi valori nule!");
        }
    }

    @Transactional
    public boolean updateEchipa(Integer idEchipa,Echipa echipaActualizata){
        if(idEchipa!=null) {
            if (echipaActualizata != null) {
                int rezultat = 0;
                try {
                    rezultat = entityManager.createNativeQuery("UPDATE echipa e SET e.ech_nume=?2,e.ech_logo=?3,e.ech_csm=?4 WHERE e.ech_id =?1")
                            .setParameter(1, idEchipa)
                            .setParameter(2, echipaActualizata.getNume())
                            .setParameter(3, echipaActualizata.getLogo())
                            .setParameter(4, echipaActualizata.isCsm())
                            .executeUpdate();
                }catch(PersistenceException e){
                    throw new PersonalExpectationFailedException("PUT. Nu s-a putut actualiza echipa!");
                }
                catch(DataIntegrityViolationException e){
                    throw new PersonalExpectationFailedException("PUT. Nu s-a putut actualiza echipa avand informatii lipsa sau necorespunzatoare!");
                }
                if (rezultat > 0) {
                    log.info("PUT. Echipa a fost actualizata cu succes!");
                    return true;
                } else {
                    log.warn("PUT. Echipa nu a putut fi actualizata!");
                    return false;
                }
            } else {
                throw new PersonalExpectationFailedException("PUT. Parametrul echipa nu poate primi valori nule!");
            }
        }else{
            throw new PersonalExpectationFailedException("PUT. Parametrul idEchipa nu poate primi valori nule!");
        }
    }
    @Transactional
    public boolean deleteEchipa(Integer idEchipa){
        if(idEchipa!=null) {

                int rezultat = 0;

                try {
                    rezultat = entityManager.createNativeQuery("DELETE FROM echipa e WHERE e.ech_id=?")
                            .setParameter(1, idEchipa)
                            .executeUpdate();
                }catch(PersistenceException e){
                    throw new PersonalExpectationFailedException("DELETE. Nu s-a putut sterge echipa!");
                }
                if (rezultat > 0) {
                    log.info("DELETE. Echipa a fost stearsa cu succes!");
                    return true;
                } else {
                    log.warn("DELETE. Echipa nu a putut fi stearsa!");
                    return false;
                }
        }else{
            throw new PersonalExpectationFailedException("DELETE. Parametrul idEchipa nu poate primi valori nule!");
        }
    }

    @Transactional
    public List<Trofeu> getTrofee(Integer idEchipa,boolean transferabil){
        List<Trofeu> trofee = new ArrayList<>();

        List<Object[]> premiiBD ;
        if(idEchipa!=null) {
            premiiBD = entityManager.createNativeQuery(" SELECT t.tro_id,t.tro_denumire,t.tro_data_acordare,c.cam_id,c.cam_denumire,c.cam_data_incepere,c.cam_data_sfarsit,l.loc_id,l.loc_localitate,j.jud_id,j.jud_judet,ta.tar_id,ta.tar_tara \n" +
                            " FROM trofeu t \n" +
                            " JOIN campionat c ON t.tro_id_campionat = c.cam_id \n" +
                            " JOIN localitate l ON l.loc_id = c.cam_id_localitate \n" +
                            " JOIN judet j ON l.loc_id_judet=j.jud_id \n" +
                            " JOIN tara ta ON j.jud_id_tara = ta.tar_id \n" +
                            " JOIN meci m ON m.mec_id_campionat = c.cam_id\n" +
                            " JOIN echipa e ON e.ech_id=m.mec_id_castigator\n" +
                            " WHERE m.mec_id_castigator IS NOT NULL \n" +
                            " AND e.ech_id=?\n" +
                            " AND m.mec_id_campionat IS NOT NULL \n" +
                            " AND m.mec_data_meci IN\n" +
                            "(SELECT MAX(m2.mec_data_meci) FROM meci m2 WHERE m2.mec_id_castigator IS NOT NULL AND m2.mec_id_campionat=m.mec_id_campionat GROUP BY m2.mec_id_campionat);")
                    .setParameter(1, idEchipa)
                    .getResultList();
        }else{
            premiiBD = entityManager.createNativeQuery(" SELECT t.tro_id,t.tro_denumire,t.tro_data_acordare,c.cam_id,c.cam_denumire,c.cam_data_incepere,c.cam_data_sfarsit,l.loc_id,l.loc_localitate,j.jud_id,j.jud_judet,ta.tar_id,ta.tar_tara \n" +
                            " FROM trofeu t \n" +
                            " JOIN campionat c ON t.tro_id_campionat = c.cam_id \n" +
                            " JOIN localitate l ON l.loc_id = c.cam_id_localitate \n" +
                            " JOIN judet j ON l.loc_id_judet=j.jud_id \n" +
                            " JOIN tara ta ON j.jud_id_tara = ta.tar_id \n" +
                            " JOIN meci m ON m.mec_id_campionat = c.cam_id\n" +
                            " JOIN echipa e ON e.ech_id=m.mec_id_castigator\n" +
                            " WHERE m.mec_id_castigator IS NOT NULL \n" +
                            " AND e.ech_csm=1\n" +
                            " AND m.mec_id_campionat IS NOT NULL \n" +
                            " AND m.mec_data_meci IN\n" +
                            "(SELECT MAX(m2.mec_data_meci) FROM meci m2 WHERE m2.mec_id_castigator IS NOT NULL AND m2.mec_id_campionat=m.mec_id_campionat GROUP BY m2.mec_id_campionat);")
                    .getResultList();
        }
        for (Object[] obj:
                premiiBD) {
            Integer idTrofeu = (Integer)obj[0];
            String denumireTrofeu = (String)obj[1];
            Date dataAcordareTrofeu = (Date)obj[2];

            Integer idCampionat = (Integer)obj[3];
            String denumireCampionat = (String)obj[4];
            Date dataIncepereCampionat = (Date)obj[5];
            Date dataSfarsitCampionat = (Date)obj[6];

            Integer idLocalitate = (Integer)obj[7];
            String localitate = (String)obj[8];
            Integer idJudet = (Integer)obj[9];
            String judet = (String)obj[10];
            Integer idTara = (Integer)obj[11];
            String tara = (String)obj[12];


            Campionat campionat = new Campionat(idCampionat,denumireCampionat,dataIncepereCampionat.toLocalDate(),dataSfarsitCampionat.toLocalDate(),new Localitate(idLocalitate,localitate,new Judet(idJudet,judet,new Tara(idTara,tara))),null);

            Trofeu trofeu = new Trofeu(idTrofeu,denumireTrofeu,dataAcordareTrofeu.toLocalDate(),campionat);
            trofee.add(trofeu);
        }

        if(trofee.isEmpty()){
            if(idEchipa!=null) {
                log.info("GET. Nu s-a gasit nici un trofeu al echipei cu id-ul " + idEchipa + "!");
            }else{
                log.info("GET. Nu s-a gasit nici un trofeu al echipelor CSM!");
            }
        }else{
            if(idEchipa!=null) {
                log.info("GET. Lista de trofee ale echipei cu id-ul " + idEchipa + " preluata cu succes!");
            }else{
                log.info("GET. Lista de trofee ale echipelor CSM preluate cu succes!");

            }
        }

        return trofee;
    }











}


