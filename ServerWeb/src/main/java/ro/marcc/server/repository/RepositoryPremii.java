package ro.marcc.server.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import ro.marcc.server.exception.PersonalExpectationFailedException;
import ro.marcc.server.model.Personal.Persoana;
import ro.marcc.server.model.Realizari.Premiu;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Repository
public class RepositoryPremii {
    private final EntityManager entityManager;

    public RepositoryPremii(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public List<Premiu> getPremii(int idPersoana){
        List<Premiu> premii = new ArrayList<>();

        try {
            List<Object[]> premiiBD = entityManager.createNativeQuery("SELECT p.pre_id,p.pre_denumire,p.pre_data_acordare FROM premiu p WHERE p.pre_id_castigator = ?1")
                    .setParameter(1, idPersoana)
                    .getResultList();
            for (Object[] obj :
                    premiiBD) {
                Integer id = (Integer) obj[0];
                String denumire = (String) obj[1];
                Date dataAcordare = (Date) obj[2];

                premii.add(new Premiu(id, denumire, dataAcordare.toLocalDate()));
            }
            log.info("GET. Lista de premii al persoanei cu id-ul " + idPersoana + " au fost preluate cu succes!");

        }catch (PersistenceException e){
            log.warn("GET. Nu s-a putut prelua lista de premii ale persoanei cu id-ul "+idPersoana+"!");
        }

        return premii;
    }

    @Transactional
    public boolean updatePremii(Persoana persoana){
        if(persoana!=null) {
            if (persoana.getPremii() != null) {
                if (persoana.getId() == null) {
                    Object idPersoana = entityManager.createNativeQuery("SELECT p.per_id FROM persoana p WHERE p.per_nume=? AND p.per_prenume=? AND p.per_link_imagine=? AND p.per_nationalitate=? AND p.per_post=? AND p.per_data_nasterii=? AND p.per_inaltime=? AND p.per_descriere=?")
                            .setParameter(1, persoana.getNume())
                            .setParameter(2, persoana.getPrenume())
                            .setParameter(3, persoana.getLinkPoza())
                            .setParameter(4, persoana.getNationalitate())
                            .setParameter(5, persoana.getPost())
                            .setParameter(6, persoana.getDataNasterii())
                            .setParameter(7, persoana.getInaltime())
                            .setParameter(8, persoana.getDescriere())
                            .getSingleResult();
                    if (idPersoana !=null) {
                        log.warn("GET. Id-ul persoanei nu poate fi null!");
                        return false;
                    } else {
                        persoana.setId((Integer) idPersoana);
                    }
                }

                try {
                    entityManager.createNativeQuery("DELETE FROM premiu p WHERE p.pre_id_castigator=?")
                            .setParameter(1, persoana.getId())
                            .executeUpdate();

                    int rezultat = 0;
                    for (Premiu premiu : persoana.getPremii()) {
                        entityManager.createNativeQuery("INSERT INTO premiu(pre_denumire,pre_data_acordare,pre_id_castigator) VALUES(?,?,?)")
                                .setParameter(1, premiu.getDenumire())
                                .setParameter(2, premiu.getDataAcordare())
                                .setParameter(3, persoana.getId())
                                .executeUpdate();
                        rezultat++;
                    }
                    if (persoana.getPremii().isEmpty()||rezultat > 0) {
                        log.info("PUT. Premiile persoanei cu id-ul " + persoana.getId() + " actualizat cu succes!");
                        return true;
                    } else {
                        log.warn("PUT. Premiile persoanei cu id-ul " + persoana.getId() + " nu a putut fi actualizat!");
                        return false;
                    }
                } catch (PersistenceException e) {
                    throw new PersonalExpectationFailedException("PUT. Nu s-au putut actualizat premiile persoanei cu id-ul " + persoana.getId() + "!");
                } catch (DataIntegrityViolationException e) {
                    throw new PersonalExpectationFailedException("PUT. Nu s-a putut actualiza rolurile persoanei cu id-ul " + persoana.getId() + " avand informatii lipsa sau necorespunzatoare!");
                }

            } else {
                log.warn("PUT. In cazul obiectului de tip Persoana atributul premii nu poate primi valori nule!");
            }

        }else {
            log.warn("PUT. Parametrul persoana nu poate primi valori nule!");
        }

        return false;
    }

    @Transactional
    public boolean deletePremii(Integer idPersoana){
        if(idPersoana!=null){
            try {
                int rezultat = entityManager.createNativeQuery("DELETE FROM premiu p WHERE p.pre_id_castigator=?")
                        .setParameter(1, idPersoana)
                        .executeUpdate();
                if(rezultat>0) {
                    log.info("GET. Premiile persoanei cu id-ul " + idPersoana + " au fost sterse cu succes!");
                }else{
                    log.info("GET. Nu s-a gasit nici un premiu al persoanei cu id-ul " + idPersoana + " pentru a fi sterse!");
                }
                return true;
            }catch (PersistenceException e){
                log.warn("DELETE. Premiile persoanei cu id-ul "+idPersoana+" nu au putut fi sterse!");
                return false;
            }
        }else{
            log.warn("DELETE. Parametrul idPersoana nu poate primi valori nule!");

        }
        return false;
    }

}
