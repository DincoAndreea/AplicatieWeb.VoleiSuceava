package ro.marcc.server.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import ro.marcc.server.convertoare.StringListConvertor;
import ro.marcc.server.exception.StireExpectationFailedException;
import ro.marcc.server.exception.StireNotFoundException;
import ro.marcc.server.model.Stire;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class RepositoryStiri {
    private final EntityManager entityManager;

    public RepositoryStiri(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public List<Stire> getStiri(Boolean postate,boolean draft){
        // postate: null  - nu se i-a in considerare faptul ca au fost postate sau nu
        //          true  - se preiau doar stirile postate
        //          false - se preiau doar stirile programate
        // draft:   true  - se preiau stirile de tip draft
        //          false - se preiau stirile ce nu sunt de tip draft
        List<Stire> stiri = new ArrayList<>();

        try {
            List<Object[]> stiriBD = new ArrayList<>();
            if(postate==null) {
                stiriBD = entityManager.createNativeQuery("SELECT s.sti_id, s.sti_titlu,s.sti_descriere,s.sti_hastaguri,m.med_tip_media,m.med_link,s.sti_draft,s.sti_data_postare FROM Stire s LEFT JOIN Medias m ON s.sti_id_media = m.med_id WHERE s.sti_draft=? order by s.sti_data_postare desc")
                        .setParameter(1, draft)
                        .getResultList();
            }else{
                stiriBD = entityManager.createNativeQuery("SELECT s.sti_id, s.sti_titlu,s.sti_descriere,s.sti_hastaguri,m.med_tip_media,m.med_link,s.sti_draft,s.sti_data_postare FROM Stire s LEFT JOIN Medias m ON s.sti_id_media = m.med_id WHERE s.sti_draft=?1 AND s.sti_data_postare "+(postate?"<= ?2":"> ?2")+" order by s.sti_data_postare desc")
                        .setParameter(1, draft)
                        .setParameter(2, LocalDate.now())
                        .getResultList();
            }


            if (stiriBD.isEmpty()) {
                throw new StireNotFoundException("GET. Nu s-a putut accesa lista de stiri" + (draft ? " draft" : "") + "!");
            }

            for (Object[] stire : stiriBD) {
                Character tipMedia = (Character) stire[4];
                if (tipMedia != null) {
                    if (tipMedia == 'i') {
                        stiri.add(new Stire((Integer) stire[0],
                                (String) stire[1],
                                (String) stire[2],
                                new StringListConvertor().convertToEntityAttribute((String) stire[3]),
                                new StringListConvertor().convertToEntityAttribute((String) stire[5]),
                                null,
                                (Boolean) stire[6],
                                ((Date) stire[7]).toLocalDate())
                        );
                    } else if (tipMedia == 'v') {
                        stiri.add(new Stire((Integer) stire[0],
                                (String) stire[1], (String) stire[2],
                                new StringListConvertor().convertToEntityAttribute((String) stire[3]),
                                null,
                                new StringListConvertor().convertToEntityAttribute((String) stire[5]),
                                (Boolean) stire[6],
                                ((Date) stire[7]).toLocalDate())
                        );
                    }
                } else {
                    stiri.add(new Stire((Integer) stire[0],
                            (String) stire[1],
                            (String) stire[2],
                            new StringListConvertor().convertToEntityAttribute((String) stire[3]),
                            null,
                            null,
                            (Boolean) stire[6],
                            ((Date) stire[7]).toLocalDate())
                    );
                }
            }
            log.info("GET. Preluare cu succes a listei de stiri" + (draft ? " draft" : "") + "!");
        }catch(PersistenceException e){
            log.warn("GET. Nu s-a putut prelua lista de stiri"+ (draft ? " draft" : "") + "!");
        }
        return stiri;
    }
    @Transactional
    public Stire getStire(int id){
        Stire stire = null;

        try {
            List<Object[]> stiriBD = entityManager.createNativeQuery("SELECT s.sti_id, s.sti_titlu,s.sti_descriere,s.sti_hastaguri,m.med_tip_media,m.med_link,s.sti_draft,s.sti_data_postare FROM Stire s LEFT JOIN Medias m ON s.sti_id_media = m.med_id WHERE s.sti_id = ?1")
                    .setParameter(1, id)
                    .getResultList();
            if (stiriBD.isEmpty()) {
                throw new StireNotFoundException("GET. Nu s-a gasit stirea cu id-ul " + id + "!");
            }

            Character tipMedia = (Character) stiriBD.get(0)[4];
            if (tipMedia != null) {
                if (tipMedia == 'i') {
                    stire = new Stire((Integer) stiriBD.get(0)[0],
                            (String) stiriBD.get(0)[1], (String) stiriBD.get(0)[2],
                            new StringListConvertor().convertToEntityAttribute((String) stiriBD.get(0)[3]),
                            new StringListConvertor().convertToEntityAttribute((String) stiriBD.get(0)[5]),
                            null,
                            (Boolean) stiriBD.get(0)[6],
                            ((Date) stiriBD.get(0)[7]).toLocalDate())
                    ;
                } else if (tipMedia == 'v') {
                    stire = new Stire((Integer) stiriBD.get(0)[0],
                            (String) stiriBD.get(0)[1], (String) stiriBD.get(0)[2],
                            new StringListConvertor().convertToEntityAttribute((String) stiriBD.get(0)[3]),
                            null,
                            new StringListConvertor().convertToEntityAttribute((String) stiriBD.get(0)[5]),
                            (Boolean) stiriBD.get(0)[6],
                            ((Date) stiriBD.get(0)[7]).toLocalDate()
                    );
                }
            } else {
                stire = new Stire((Integer) stiriBD.get(0)[0],
                        (String) stiriBD.get(0)[1],
                        (String) stiriBD.get(0)[2],
                        new StringListConvertor().convertToEntityAttribute((String) stiriBD.get(0)[3]),
                        null,
                        null,
                        (Boolean) stiriBD.get(0)[6],
                        ((Date) stiriBD.get(0)[7]).toLocalDate()
                );

            }
            log.info("GET. Preluare cu succes a stirei cu id-ul " + id + "!");
        }catch(PersistenceException e){
            log.warn("GET. Nu s-a putut prelua stirea cu id-ul " + id + "!");
        }

        return stire;
    }
    @Transactional
    public boolean addStire(Stire stire){
        if(stire!=null) {
            try {
                int numarVideoclipuri = 0;
                if (stire.getVideoclipuri() != null) {
                    numarVideoclipuri = stire.getVideoclipuri().size();
                }
                int numarImagini = 0;
                if (stire.getImagini() != null) {
                    numarImagini = stire.getImagini().size();
                }

                Integer idMedia = -1;
                char tipMedia = numarImagini > 0 ? 'i' : numarVideoclipuri > 0 ? 'v' : ' ';
                String link = "";

                if (tipMedia != ' ') {
                    if (numarImagini > 0) {
                        link = new StringListConvertor().convertToDatabaseColumn(stire.getImagini());

                    } else if (numarVideoclipuri > 0) {
                        link = new StringListConvertor().convertToDatabaseColumn(stire.getVideoclipuri());
                    }
                    try {
                        List<Object> result = entityManager.createNativeQuery("SELECT m.med_id FROM medias m WHERE m.med_tip_media=?1 AND m.med_link=?2")
                                .setParameter(1, tipMedia)
                                .setParameter(2, link)
                                .getResultList();


                        if (result.isEmpty()) {
                            entityManager.createNativeQuery("INSERT INTO medias(med_tip_media,med_link) VALUES (?,?)")
                                    .setParameter(1, tipMedia)
                                    .setParameter(2, link)
                                    .executeUpdate();
                            log.info("POST. Incarcare " + (tipMedia == 'i' ? "imagini" : tipMedia == 'v' ? "videoclipuri" : "media") + " realizata cu succes!");
                            result = entityManager.createNativeQuery("SELECT m.med_id FROM medias m WHERE m.med_tip_media=?1 AND m.med_link=?2")
                                    .setParameter(1, tipMedia)
                                    .setParameter(2, link)
                                    .getResultList();

                        } else {
                            log.warn("POST. Fisierele media sunt deja incarcate!");
                        }
                        idMedia = (Integer) result.get(0);
                    }catch(PersistenceException e){
                        throw new StireExpectationFailedException("POST. Nu s-au putut insera fisierele!");
                    }catch(DataIntegrityViolationException e){
                        throw new StireExpectationFailedException("POST. Nu s-au putut insera fisierele avand informatii lipsa sau necorespunzatoare!");
                    }
                }
                try {
                    if (idMedia != -1) {
                        if (stire.getId() == null) {
                            entityManager.createNativeQuery("INSERT INTO stire(sti_titlu,sti_descriere,sti_hastaguri,sti_id_media,sti_draft,sti_data_postare) VALUES(?,?,?,?,?,?)")
                                    .setParameter(1, stire.getTitlu())
                                    .setParameter(2, stire.getDescriere())
                                    .setParameter(3, new StringListConvertor().convertToDatabaseColumn(stire.getHashtaguri()))
                                    .setParameter(4, idMedia)
                                    .setParameter(5, stire.isDraft())
                                    .setParameter(6, stire.getDataPostare())
                                    .executeUpdate();
                        } else {
                            entityManager.createNativeQuery("INSERT INTO stire(sti_id,sti_titlu,sti_descriere,sti_hastaguri,sti_id_media,sti_draft,sti_data_postare) VALUES(?,?,?,?,?,?,?)")
                                    .setParameter(1, stire.getId())
                                    .setParameter(2, stire.getTitlu())
                                    .setParameter(3, stire.getDescriere())
                                    .setParameter(4, new StringListConvertor().convertToDatabaseColumn(stire.getHashtaguri()))
                                    .setParameter(5, idMedia)
                                    .setParameter(6, stire.isDraft())
                                    .setParameter(7, stire.getDataPostare())
                                    .executeUpdate();
                        }
                    } else {
                        if (stire.getId() == null) {
                            entityManager.createNativeQuery("INSERT INTO stire(sti_titlu,sti_descriere,sti_hastaguri,sti_draft,sti_data_postare) VALUES (?,?,?,?,?)")
                                    .setParameter(1, stire.getTitlu())
                                    .setParameter(2, stire.getDescriere())
                                    .setParameter(3, new StringListConvertor().convertToDatabaseColumn(stire.getHashtaguri()))
                                    .setParameter(4, stire.isDraft())
                                    .setParameter(5, stire.getDataPostare())
                                    .executeUpdate();
                        } else {
                            entityManager.createNativeQuery("INSERT INTO stire(sti_id,sti_titlu,sti_descriere,sti_hastaguri,sti_draft,sti_data_postare) VALUES (?,?,?,?,?,?)")
                                    .setParameter(1, stire.getId())
                                    .setParameter(2, stire.getTitlu())
                                    .setParameter(3, stire.getDescriere())
                                    .setParameter(4, new StringListConvertor().convertToDatabaseColumn(stire.getHashtaguri()))
                                    .setParameter(5, stire.isDraft())
                                    .setParameter(6, stire.getDataPostare())
                                    .executeUpdate();
                        }
                    }
                }catch(PersistenceException e){
                    throw new StireExpectationFailedException("POST. Nu s-a putut inregistra stirea!");
                }catch(DataIntegrityViolationException e){
                    throw new StireExpectationFailedException("POST. Nu s-a putut inregistra stirea avand informatii lipsa sau necorespunzatoare!");
                }


                return true;
            } catch (PersistenceException e) {
                log.info("POST. Nu s-a putut inregistra noua stire!");

                return false;
            }
        }else{
            log.warn("POST. Parametrul stire nu poate primi valori nule!");
        }
        return false;
    }
    @Transactional
    public boolean deleteStire(int id){
        try {
            int rezultat = entityManager.createNativeQuery("DELETE FROM stire WHERE sti_id = ?1")
                    .setParameter(1, id)
                    .executeUpdate();
            if(rezultat>0){
                log.info("DELETE. Stirea cu id-ul "+ id +" a fost stearsa cu succes!");
                return true;
            }else{
                log.info("DELETE. Stirea cu id-ul "+ id +" nu a putut fi stearsa!");
                return false;
            }
        }catch(PersistenceException e){
            throw new StireExpectationFailedException("DELETE. Stirea cu id-ul "+ id +" nu a putut fi stearsa!");
        }

    }
    @Transactional
    public boolean deleteStire(String titlu){
        try {
            int rezultat = entityManager.createNativeQuery("DELETE FROM stire s WHERE s.sti_titlu = ?1")
                    .setParameter(1, titlu)
                    .executeUpdate();
            if(rezultat>0){
                log.info("DELETE. Stirea cu titlul \""+titlu+"\" a fost stearsa cu succes!");
                return true;
            }else{
                log.info("DELETE. Stirea cu titlul \""+titlu+"\" nu a putut fi stearsa!");
                return false;
            }
        }catch(PersistenceException e){
            throw new StireExpectationFailedException("DELETE. Stirea cu titlul \""+titlu+"\" nu a putut fi stearsa!");
        }
    }
    @Transactional
    public boolean updateStire(int idStire,Stire stireActualizata){
        if(stireActualizata!=null) {
            try {
                int numarVideoclipuri = 0;
                if (stireActualizata.getVideoclipuri() != null) {
                    numarVideoclipuri = stireActualizata.getVideoclipuri().size();
                }
                int numarImagini = 0;
                if (stireActualizata.getImagini() != null) {
                    numarImagini = stireActualizata.getImagini().size();
                }

                Integer idMedia = null;
                char tipMedia = numarImagini > 0 ? 'i' : numarVideoclipuri > 0 ? 'v' : ' ';
                String linkuriMedia = tipMedia == 'i' ?
                        new StringListConvertor().convertToDatabaseColumn(stireActualizata.getImagini()) : tipMedia == 'v' ?
                        new StringListConvertor().convertToDatabaseColumn(stireActualizata.getVideoclipuri()) : "";

                if (tipMedia != ' ') {
                    List<Object> result = entityManager.createNativeQuery("SELECT m.med_id FROM medias m WHERE m.med_tip_media=?1 AND m.med_link=?2")
                            .setParameter(1, tipMedia)
                            .setParameter(2, linkuriMedia)
                            .getResultList();

                    if (result.size() == 0) {
                        entityManager.createNativeQuery("INSERT INTO medias(med_tip_media,med_link) VALUES (?,?)")
                                .setParameter(1, tipMedia)
                                .setParameter(2, linkuriMedia)
                                .executeUpdate();
                        log.info("PUT. Incarcare " + (tipMedia == 'i' ? "imagini" : tipMedia == 'v' ? "videoclipuri" : "media") + " realizata cu succes!");

                        result = entityManager.createNativeQuery("SELECT m.med_id FROM medias m WHERE m.med_tip_media=?1 AND m.med_link=?2")
                                .setParameter(1, tipMedia)
                                .setParameter(2, linkuriMedia)
                                .getResultList();
                    } else {
                        log.info("PUT. Fisierele media sunt deja incarcate.");
                    }
                    idMedia = (Integer) result.get(0);
                }


                int rezultatUpdateStire = entityManager.createNativeQuery("UPDATE stire s SET s.sti_titlu=?2,s.sti_descriere=?3,s.sti_hastaguri=?4,s.sti_id_media=?5,s.sti_draft=?6,s.sti_data_postare=?7 WHERE s.sti_id = ?1")
                        .setParameter(1, idStire)
                        .setParameter(2, stireActualizata.getTitlu())
                        .setParameter(3, stireActualizata.getDescriere())
                        .setParameter(4, new StringListConvertor().convertToDatabaseColumn(stireActualizata.getHashtaguri()))
                        .setParameter(5, idMedia)
                        .setParameter(6, stireActualizata.isDraft())
                        .setParameter(7, stireActualizata.getDataPostare())
                        .executeUpdate();

                if (rezultatUpdateStire > 0) {
                    log.info("PUT. Stirea cu id-ul \"" + idStire + "\" a fost actualizata cu succes!");
                    return true;
                } else {
                    log.info("PUT. Stirea cu id-ul \"" + idStire + "\" nu a putut fi actualizata sau nu exista!");
                    throw new StireNotFoundException("PUT. Stirea cu id-ul \"" + idStire + "\" nu a putut fi actualizata sau nu exista!");
                }
            } catch (PersistenceException e) {
                throw new StireExpectationFailedException("PUT. Stirea cu id-ul \"" + idStire + "\" nu a putut fi actualizata sau nu exista!");
            }catch(DataIntegrityViolationException e){
                throw new StireExpectationFailedException("PUT. Stirea cu id-ul \"" + idStire + "\" nu a putut fi actualizata sau nu exista avand informatii lipsa sau necorespunzatoare!");
            }
        }else{
            log.warn("UPDATE. Parametrul stireActualizata nu poate primi valori nule!");
        }
        return false;
    }
}
