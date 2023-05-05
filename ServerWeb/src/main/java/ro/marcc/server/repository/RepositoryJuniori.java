package ro.marcc.server.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import ro.marcc.server.exception.VoleiJuvenilExpectationFailedException;
import ro.marcc.server.exception.VoleiJuvenitNotFoundException;
import ro.marcc.server.model.VoleiJuvenil.Juniori.Juniori;
import ro.marcc.server.model.VoleiJuvenil.Juniori.PremiiJuniori;
import ro.marcc.server.repository.jpa.RepositoryJunioriJPA;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Repository
public class RepositoryJuniori {
    private final EntityManager entityManager;
    private final RepositoryJunioriJPA repositoryJuniori;

    public RepositoryJuniori(EntityManager entityManager, RepositoryJunioriJPA repositoryJuniori) {
        this.entityManager = entityManager;
        this.repositoryJuniori = repositoryJuniori;
    }

    @Transactional
    public List<Juniori> getJuniori(){
        try {
            List<Juniori> juniori = repositoryJuniori.getJuniori();

            if(juniori.isEmpty()){
                log.info("GET. Nu a putut fi gasita nici un junior!");
            }else {
                log.info("GET. Lista de juniori s-a preluat cu succes!");
            }

            return juniori;
        }catch(PersistenceException e){
            throw new VoleiJuvenilExpectationFailedException("GET. Nu s-a putut prelua lista de juniori!");
        }
    }

    @Transactional
    public Juniori getJuniori(Integer idJuniori){
        try {
            Juniori juniori =  repositoryJuniori.getJuniori(idJuniori);

            if(juniori !=null){
                log.info("GET. Juniorii cu id-ul "+idJuniori+" preluati cu succes!");
                return juniori;
            }else{
                log.info("GET. Juniorii cu id-ul "+idJuniori+" nu au putut fi preluati!");
                throw new VoleiJuvenitNotFoundException("GET. Nu s-au gasit juniorii cu id-ul "+idJuniori+"!");
            }

        }catch(PersistenceException e){
            throw new VoleiJuvenilExpectationFailedException("GET. Nu s-au putut prelua juniorii cu id-ul "+idJuniori+"!");
        }
    }

    @Transactional
    public boolean addJuniori(Juniori juniori){
        if(juniori!=null){
            int rezultat = 0;
            try{
                if(juniori.getId()==null){
                    rezultat = entityManager.createNativeQuery("INSERT INTO juniori(jun_lot,jun_imagine_lot,jun_informatii) VALUES (?,?,?)")
                            .setParameter(1,juniori.getLot())
                            .setParameter(2,juniori.getImagineLot())
                            .setParameter(3,juniori.getInformatii())
                            .executeUpdate();

                }else{
                    rezultat = entityManager.createNativeQuery("INSERT INTO juniori(jun_id,jun_lot,jun_imagine_lot,jun_informatii) VALUES (?,?,?,?)")
                            .setParameter(1,juniori.getId())
                            .setParameter(2,juniori.getLot())
                            .setParameter(3,juniori.getImagineLot())
                            .setParameter(4,juniori.getInformatii())
                            .executeUpdate();
                }

                if (updatePremii(juniori) && rezultat > 0) {
                    log.info("POST. Juniorii a fost inregistrat cu succes!");
                    return true;
                } else {
                    log.warn("POST. Juniorii nu au putut fi inregistrat!");
                    return false;
                }
            }catch(PersistenceException e){
                throw new VoleiJuvenilExpectationFailedException("POST. Nu s-a putut inregistra juniorii!");
            }
            catch(DataIntegrityViolationException e){
                throw new VoleiJuvenilExpectationFailedException("POST. Nu s-a putut inregistra juniorii avand informatii lipsa sau necorespunzatoare!");
            }
        }else{
            throw new VoleiJuvenilExpectationFailedException("POST. Parametrul juniori nu poate primi valori nule!");
        }
    }

    @Transactional
    public boolean updateJuniori(Integer idJuniori, Juniori junioriActualizati){
        if(junioriActualizati !=null){
            if(idJuniori!=null) {
                junioriActualizati.setId(idJuniori);
                int rezultat;
                try {
                    rezultat = entityManager.createNativeQuery("UPDATE juniori j SET j.jun_lot=?2,j.jun_imagine_lot=?3,j.jun_informatii=?4 WHERE j.jun_id=?1 ")
                            .setParameter(1,idJuniori)
                            .setParameter(2, junioriActualizati.getLot())
                            .setParameter(3, junioriActualizati.getImagineLot())
                            .setParameter(4, junioriActualizati.getInformatii())
                            .executeUpdate();
                    updatePremii(junioriActualizati);
                    if (rezultat > 0) {
                        log.info("PUT. Actualizare cu succes a informatiilor la juniorii cu id-ul" + idJuniori + "!");
                        return true;
                    } else {
                        log.warn("PUT. Actualizare esuata a informatiilor la juniorii cu id-ul" + idJuniori + "!");
                        return false;
                    }

                } catch (PersistenceException e) {
                    throw new VoleiJuvenilExpectationFailedException("PUT. Nu s-a putut actualiza datele la juniorii cu id-ul " + idJuniori + "!");
                } catch (DataIntegrityViolationException e) {
                    throw new VoleiJuvenilExpectationFailedException("PUT. Nu s-a putut actualiza datele la juniorii cu id-ul " + idJuniori + " avand informatii lipsa sau necorespunzatoare!");
                }
            }else{
                log.warn("PUT. Parametrul idJuniori nu poate primi valori nule!");
            }
        }else{
            log.warn("PUT. Parametrul junioriActualizati nu poate primi valori nule!");
        }

        return false;
    }


    @Transactional
    public boolean deleteJuniori(Integer idJuniori){
        try {
            deletePremii(idJuniori);
            boolean sterse = repositoryJuniori.deleteJuniori(idJuniori) > 0;

            if(sterse){
                log.info("DELETE. Juniorii cu id-ul "+idJuniori+" stearsi cu succes!");
                return true;
            }else{
                log.warn("DELETE. Juniorii cu id-ul "+idJuniori+" nu au putut fi stersi!");
                return false;
            }
        }catch(PersistenceException e){
            throw new VoleiJuvenilExpectationFailedException("DELETE. Nu s-au putut sterge juniorii cu id-ul "+idJuniori+"!");
        }
    }

    @Transactional
    private boolean updatePremii(Juniori juniori){
        if(juniori!=null) {
            if (juniori.getPremiiJuniori() != null) {
                if (juniori.getId()==null) {
                    Object idJuniori = entityManager.createNativeQuery("SELECT j.jun_id FROM juniori j WHERE j.jun_lot=? AND j.jun_imagine_lot=? AND j.jun_informatii=?")
                            .setParameter(1, juniori.getLot())
                            .setParameter(2, juniori.getImagineLot())
                            .setParameter(3, juniori.getInformatii())
                            .getSingleResult();
                    if(idJuniori ==null){
                        return false;
                    }else{
                        juniori.setId((Integer) idJuniori);
                    }
                }
                try {
                    entityManager.createNativeQuery("DELETE FROM juniori_premii p WHERE p.jpr_lot=?")
                            .setParameter(1, juniori.getId())
                            .executeUpdate();

                    int rezultat= 0;
                    for (PremiiJuniori premiu : juniori.getPremiiJuniori()) {
                        entityManager.createNativeQuery("INSERT INTO juniori_premii(jpr_denumire_premiu,jpr_loc,jpr_an,jpr_lot) VALUES(?,?,?,?)")
                                .setParameter(1, premiu.getDenumirePremiu())
                                .setParameter(2, premiu.getLoc())
                                .setParameter(3, premiu.getAn())
                                .setParameter(4, juniori.getId())
                                .executeUpdate();
                        rezultat++;
                    }
                    if (juniori.getPremiiJuniori().isEmpty() || rezultat > 0) {
                        log.info("PUT. Premiile juniorior cu id-ul " + juniori.getId() + " actualizat cu succes!");
                        return true;
                    } else {
                        log.warn("PUT. Premiile juniorior cu id-ul " + juniori .getId() + " nu a putut fi actualizat!");
                        return false;
                    }
                } catch (PersistenceException e) {
                    throw new VoleiJuvenilExpectationFailedException("PUT. Nu s-au putut actualizat premiile la juniorii cu id-ul " + juniori.getId() + "!");
                } catch (DataIntegrityViolationException e) {
                    throw new VoleiJuvenilExpectationFailedException("PUT. Nu s-a putut actualiza rolurile la juniorii cu id-ul " + juniori.getId() + " avand informatii lipsa sau necorespunzatoare!");
                }
            } else {
                log.warn("PUT. In cazul obiectului de tip Juniori atributul premiiJuniori nu poate primi valori nule!");
            }
        }else{
            log.warn("PUT. Parametrul juniori nu poate primi valori nule!");
        }

        return false;
    }

    @Transactional
    private boolean deletePremii(Integer idJuniori){
        if(idJuniori!=null){
            try {
                entityManager.createNativeQuery("DELETE FROM juniori_premii p WHERE p.jpr_lot=?")
                        .setParameter(1, idJuniori)
                        .executeUpdate();
                return true;
            }catch (PersistenceException e){
                log.warn("DELETE. Premiile la juniorii cu id-ul "+idJuniori+" nu au putut fi sterse!");
                return false;
            }
        }else{
            log.warn("DELETE. Parametrul idJuniori nu poate primi valori nule!");

        }
        return false;
    }
}
