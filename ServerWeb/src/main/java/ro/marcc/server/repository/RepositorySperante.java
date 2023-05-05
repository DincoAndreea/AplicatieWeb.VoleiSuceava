package ro.marcc.server.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import ro.marcc.server.exception.VoleiJuvenilExpectationFailedException;
import ro.marcc.server.exception.VoleiJuvenitNotFoundException;
import ro.marcc.server.model.VoleiJuvenil.Sperante.PremiiSperante;
import ro.marcc.server.model.VoleiJuvenil.Sperante.Sperante;
import ro.marcc.server.repository.jpa.RepositorySperanjeJPA;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Repository
public class RepositorySperante {
    private final EntityManager entityManager;
    private final RepositorySperanjeJPA repositorySperante;

    public RepositorySperante(EntityManager entityManager, RepositorySperanjeJPA repositorySperante) {
        this.entityManager = entityManager;
        this.repositorySperante = repositorySperante;
    }

    @Transactional
    public List<Sperante> getSperante(){
        try {
            List<Sperante> sperantes = repositorySperante.getSperante();
            if(sperantes.isEmpty()){
                log.info("GET. Nu a putut fi gasita nici o speranta!");
            }else {
                log.info("GET. Lista de sperante s-a preluat cu succes!");
            }
            return sperantes;
        }catch(PersistenceException e){
            throw new VoleiJuvenilExpectationFailedException("GET. Nu s-a putut prelua lista de sperante!");
        }
    }

    @Transactional
    public Sperante getSperante(Integer idSperante){
        try {
            Sperante speranteCautate =  repositorySperante.getSperante(idSperante);

            if(speranteCautate!=null){
                log.info("GET. Speranta cu id-ul "+idSperante+" preluata cu succes!");
                return speranteCautate;
            }else{
                log.info("GET. Speranta cu id-ul "+idSperante+" nu a putut fi preluata!");
                throw new VoleiJuvenitNotFoundException("GET. Nu s-au gasit sperantele cu id-ul "+idSperante+"!");
            }

        }catch(PersistenceException e){
            throw new VoleiJuvenilExpectationFailedException("GET. Nu s-au putut prelua sperantele cu id-ul "+idSperante+"!");
        }
    }

    @Transactional
    public boolean addSperante(Sperante sperante){
        if(sperante!=null){
            int rezultat = 0;
            try{
                if(sperante.getId()==null){
                    rezultat = entityManager.createNativeQuery("INSERT INTO sperante(spe_lot,spe_imagine_lot,spe_informatii) VALUES (?,?,?)")
                            .setParameter(1,sperante.getLot())
                            .setParameter(2,sperante.getImagineLot())
                            .setParameter(3,sperante.getInformatii())
                            .executeUpdate();

                }else{
                    rezultat = entityManager.createNativeQuery("INSERT INTO sperante(spe_id,spe_lot,spe_imagine_lot,spe_informatii) VALUES (?,?,?,?)")
                            .setParameter(1,sperante.getId())
                            .setParameter(2,sperante.getLot())
                            .setParameter(3,sperante.getImagineLot())
                            .setParameter(4,sperante.getInformatii())
                            .executeUpdate();
                }

                if (updatePremii(sperante) && rezultat > 0) {
                    log.info("POST. Sperantele au fost inregistrati cu succes!");
                    return true;
                } else {
                    log.warn("POST. Sperantele nu au putut fi inregistrati!");
                    return false;
                }
            }catch(PersistenceException e){
                throw new VoleiJuvenilExpectationFailedException("POST. Nu s-a putut inregistra sperantele!");
            }
            catch(DataIntegrityViolationException e){
                throw new VoleiJuvenilExpectationFailedException("POST. Nu s-a putut inregistra sperantele avand informatii lipsa sau necorespunzatoare!");
            }
        }else{
            throw new VoleiJuvenilExpectationFailedException("POST. Parametrul sperante nu poate primi valori nule!");
        }
    }

    @Transactional
    public boolean updateSperante(Integer idSperante, Sperante speranteActualizate){
        if(speranteActualizate!=null){
            if(idSperante!=null) {
                speranteActualizate.setId(idSperante);
                int rezultat;
                try {
                    rezultat = entityManager.createNativeQuery("UPDATE sperante s SET s.spe_lot=?2,s.spe_imagine_lot=?3,s.spe_informatii=?4 WHERE s.spe_id=?1 ")
                            .setParameter(1,idSperante)
                            .setParameter(2,speranteActualizate.getLot())
                            .setParameter(3,speranteActualizate.getImagineLot())
                            .setParameter(4,speranteActualizate.getInformatii())
                            .executeUpdate();
                    updatePremii(speranteActualizate);
                    if (rezultat > 0) {
                        log.info("PUT. Actualizare cu succes a informatiilor sperantelor cu id-ul" + idSperante + "!");
                        return true;
                    } else {
                        log.warn("PUT. Actualizare esuata a informatiilor sperantelor cu id-ul" + idSperante + "!");
                        return true;
                    }

                } catch (PersistenceException e) {
                    throw new VoleiJuvenilExpectationFailedException("PUT. Nu s-a putut actualiza datele sperantelor cu id-ul " + idSperante + "!");
                } catch (DataIntegrityViolationException e) {
                    throw new VoleiJuvenilExpectationFailedException("PUT. Nu s-a putut actualiza datele sperantelor cu id-ul " + idSperante + " avand informatii lipsa sau necorespunzatoare!");
                }
            }else{
                log.warn("PUT. Parametrul idSperante nu poate primi valori nule!");
            }
        }else{
            log.warn("PUT. Parametrul speranteActualizate nu poate primi valori nule!");
        }

        return false;
    }


    @Transactional
    public boolean deleteSperante(Integer idSperante){
        try {
            deletePremii(idSperante);
            boolean sterse = repositorySperante.deleteSperante(idSperante) > 0;
            if(sterse){
                log.info("DELETE. Sperantele cu id-ul "+idSperante+" stearse cu succes!");
                return true;
            }else{
                log.warn("DELETE. Sperantele cu id-ul "+idSperante+" nu au putut fi sterse!");
                return false;
            }
        }catch(PersistenceException e){
            throw new VoleiJuvenilExpectationFailedException("DELETE. Nu s-au putut sterge sperantele cu id-ul "+idSperante+"!");
        }
    }

    @Transactional
    private boolean updatePremii(Sperante sperante){
        if(sperante!=null) {
            if (sperante.getPremiiSperante() != null) {
                if (sperante.getId()==null) {
                    Object idSperante = entityManager.createNativeQuery("SELECT s.spe_id FROM sperante s WHERE s.spe_lot=? AND s.spe_imagine_lot=? AND s.spe_informatii=?")
                            .setParameter(1, sperante.getLot())
                            .setParameter(2, sperante.getImagineLot())
                            .setParameter(3, sperante.getInformatii())
                            .getSingleResult();
                    if(idSperante ==null){
                        log.warn("GET. Id-ul sperantelor nu poate fi null!");
                        return false;
                    }else{
                        sperante.setId((Integer) idSperante);
                    }
                }
                try {
                    entityManager.createNativeQuery("DELETE FROM sperante_premii p WHERE p.spr_lot=?")
                            .setParameter(1, sperante.getId())
                            .executeUpdate();

                    int rezultat= 0;
                    for (PremiiSperante premiu : sperante.getPremiiSperante()) {
                        entityManager.createNativeQuery("INSERT INTO sperante_premii(spr_denumire_premiu,spr_loc,spr_an,spr_lot) VALUES(?,?,?,?)")
                                .setParameter(1, premiu.getDenumirePremiu())
                                .setParameter(2, premiu.getLoc())
                                .setParameter(3, premiu.getAn())
                                .setParameter(4, sperante.getId())
                                .executeUpdate();
                        rezultat++;
                    }
                    if (sperante.getPremiiSperante().isEmpty() || rezultat > 0) {
                        log.info("PUT. Premiile sperantelor cu id-ul " + sperante.getId() + " actualizat cu succes!");
                        return true;
                    } else {
                        log.warn("PUT. Premiile sperantelor cu id-ul " + sperante .getId() + " nu a putut fi actualizat!");
                        return false;
                    }
                } catch (PersistenceException e) {
                    throw new VoleiJuvenilExpectationFailedException("PUT. Nu s-au putut actualizat premiile sperantelor cu id-ul " + sperante.getId() + "!");
                } catch (DataIntegrityViolationException e) {
                    throw new VoleiJuvenilExpectationFailedException("PUT. Nu s-a putut actualiza rolurile sperantelor cu id-ul " + sperante.getId() + " avand informatii lipsa sau necorespunzatoare!");
                }
            } else {
                log.warn("PUT. In cazul obiectului de tip Sperante atributul premiiSperante nu poate primi valori nule!");
            }
        }else{
            log.warn("PUT. Parametrul sperante nu poate primi valori nule!");
        }

        return false;
    }

    @Transactional
    private boolean deletePremii(Integer idSperante){
        if(idSperante!=null){
            try {
                int rezultat = entityManager.createNativeQuery("DELETE FROM sperante_premii p WHERE p.spr_lot=?")
                        .setParameter(1, idSperante)
                        .executeUpdate();
                if(rezultat>0) {
                    log.info("GET. Premiile sperantelor cu id-ul " + idSperante + " au fost sterse cu succes!");
                }else{
                    log.info("GET. Nu s-a gasit nici un premiu al sperantelor cu id-ul " + idSperante + " pentru a fi sterse!");
                }

                return true;
            }catch (PersistenceException e){
                log.warn("DELETE. Premiile sperantelor cu id-ul "+idSperante+" nu au putut fi sterse!");
                return false;
            }
        }else{
            log.warn("DELETE. Parametrul idSperante nu poate primi valori nule!");

        }
        return false;
    }
}
