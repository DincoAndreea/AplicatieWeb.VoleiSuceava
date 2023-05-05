package ro.marcc.server.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import ro.marcc.server.exception.VoleiJuvenilExpectationFailedException;
import ro.marcc.server.exception.VoleiJuvenitNotFoundException;
import ro.marcc.server.model.VoleiJuvenil.Cadeti.Cadeti;
import ro.marcc.server.model.VoleiJuvenil.Cadeti.PremiiCadeti;
import ro.marcc.server.repository.jpa.RepositoryCadetiJPA;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Repository
public class RepositoryCadeti {
    private final EntityManager entityManager;
    private final RepositoryCadetiJPA repositoryCadetiJPA;

    public RepositoryCadeti(EntityManager entityManager, RepositoryCadetiJPA repositoryCadetiJPA) {
        this.entityManager = entityManager;
        this.repositoryCadetiJPA = repositoryCadetiJPA;
    }

    @Transactional
    public List<Cadeti> getCadeti(){
        try {
            List<Cadeti> cadeti = repositoryCadetiJPA.getCadeti();
            if(cadeti.isEmpty()){
                log.info("GET. Nu a putut fi gasita nici un cadet!");
            }else {
                log.info("GET. Lista de cadeti s-a preluat cu succes!");
            }
            return cadeti;
        }catch(PersistenceException e){
            throw new VoleiJuvenilExpectationFailedException("GET. Nu s-a putut prelua lista de cadeti!");
        }
    }

    @Transactional
    public Cadeti getCadeti(Integer idCadeti){
        try {
            Cadeti cadetiCautati =  repositoryCadetiJPA.getCadeti(idCadeti);

            if(cadetiCautati!=null){
                log.info("GET. Cadetii cu id-ul "+idCadeti+" preluata cu succes!");
                return cadetiCautati;
            }else{
                log.info("GET. Cadetii cu id-ul "+idCadeti+" nu a putut fi preluata!");
                throw new VoleiJuvenitNotFoundException("GET. Nu s-au gasit cadetii cu id-ul "+idCadeti+"!");
            }

        }catch(PersistenceException e){
            throw new VoleiJuvenilExpectationFailedException("GET. Nu s-au putut prelua cadetii cu id-ul "+idCadeti+"!");
        }
    }

    @Transactional
    public boolean addCadeti(Cadeti cadeti){
        if(cadeti!=null){
            int rezultat = 0;
            try{
                if(cadeti.getId()==null){
                    rezultat = entityManager.createNativeQuery("INSERT INTO cadeti(cad_lot,cad_imagine_lot,cad_informatii) VALUES (?,?,?)")
                            .setParameter(1,cadeti.getLot())
                            .setParameter(2,cadeti.getImagineLot())
                            .setParameter(3,cadeti.getInformatii())
                            .executeUpdate();

                }else{
                    rezultat = entityManager.createNativeQuery("INSERT INTO cadeti(cad_id,cad_lot,cad_imagine_lot,cad_informatii) VALUES (?,?,?,?)")
                            .setParameter(1,cadeti.getId())
                            .setParameter(2,cadeti.getLot())
                            .setParameter(3,cadeti.getImagineLot())
                            .setParameter(4,cadeti.getInformatii())
                            .executeUpdate();
                }

                if (updatePremii(cadeti) && rezultat > 0) {
                    log.info("POST. Cadetii au fost inregistrati cu succes!");
                    return true;
                } else {
                    log.warn("POST. Cadetii nu au putut fi inregistrati!");
                    return false;
                }
            }catch(PersistenceException e){
                throw new VoleiJuvenilExpectationFailedException("POST. Nu s-a putut inregistra cadetii!");
            }
            catch(DataIntegrityViolationException e){
                throw new VoleiJuvenilExpectationFailedException("POST. Nu s-a putut inregistra cadetii avand informatii lipsa sau necorespunzatoare!");
            }
        }else{
            throw new VoleiJuvenilExpectationFailedException("POST. Parametrul cadeti nu poate primi valori nule!");
        }
    }

    @Transactional
    public boolean updateCadeti(Integer idCadeti, Cadeti cadetiActualizati){
        if(cadetiActualizati!=null){
            if(idCadeti!=null) {
                cadetiActualizati.setId(idCadeti);
                int rezultat;
                try {
                    rezultat = entityManager.createNativeQuery("UPDATE cadeti c SET c.cad_lot=?2,c.cad_imagine_lot=?3,c.cad_informatii=?4 WHERE c.cad_id=?1 ")
                            .setParameter(1,idCadeti)
                            .setParameter(2,cadetiActualizati.getLot())
                            .setParameter(3,cadetiActualizati.getImagineLot())
                            .setParameter(4,cadetiActualizati.getInformatii())
                            .executeUpdate();
                    updatePremii(cadetiActualizati);
                    if (rezultat > 0) {
                        log.info("PUT. Actualizare cu succes a informatiilor cadetilor cu id-ul" + idCadeti + "!");
                        return true;
                    } else {
                        log.warn("PUT. Actualizare esuata a informatiilor cadetilor cu id-ul" + idCadeti + "!");
                        return false;
                    }

                } catch (PersistenceException e) {
                    throw new VoleiJuvenilExpectationFailedException("PUT. Nu s-a putut actualiza datele cadetilor cu id-ul " + idCadeti + "!");
                } catch (DataIntegrityViolationException e) {
                    throw new VoleiJuvenilExpectationFailedException("PUT. Nu s-a putut actualiza datele cadetilor cu id-ul " + idCadeti + " avand informatii lipsa sau necorespunzatoare!");
                }
            }else{
                log.warn("PUT. Parametrul idCadeti nu poate primi valori nule!");
            }
        }else{
            log.warn("PUT. Parametrul cadetiActualizati nu poate primi valori nule!");
        }

        return false;
    }


    @Transactional
    public boolean deleteCadeti(Integer idCadeti){
        try {
            deletePremii(idCadeti);
            boolean sterse = repositoryCadetiJPA.deteleCadeti(idCadeti) > 0;
            if(sterse){
                log.info("DELETE. Cadetii cu id-ul "+idCadeti+" stearsi cu succes!");
                return true;
            }else{
                log.warn("DELETE. Cadetii cu id-ul "+idCadeti+" nu au putut fi stersi!");
                return false;
            }
        }catch(PersistenceException e){
            throw new VoleiJuvenilExpectationFailedException("DELETE. Nu s-au putut sterge cadetii cu id-ul "+idCadeti+"!");
        }
    }

    @Transactional
    private boolean updatePremii(Cadeti cadeti){
        if(cadeti!=null) {
            if (cadeti.getPremiiCadeti() != null) {
                if (cadeti.getId()==null) {
                    Object idCadeti = entityManager.createNativeQuery("SELECT c.cad_id FROM cadeti c WHERE c.cad_lot=? AND c.cad_imagine_lot=? AND c.cad_informatii=?")
                            .setParameter(1, cadeti.getLot())
                            .setParameter(2, cadeti.getImagineLot())
                            .setParameter(3, cadeti.getInformatii())
                            .getSingleResult();
                    if(idCadeti ==null){
                        return false;
                    }else{
                        cadeti.setId((Integer) idCadeti);
                    }
                }
                try {
                    entityManager.createNativeQuery("DELETE FROM cadeti_premii p WHERE p.cpr_lot=?")
                            .setParameter(1, cadeti.getId())
                            .executeUpdate();

                    int rezultat= 0;
                    for (PremiiCadeti premiu : cadeti.getPremiiCadeti()) {
                        entityManager.createNativeQuery("INSERT INTO cadeti_premii(cpr_denumire_premiu,cpr_loc,cpr_an,cpr_lot) VALUES(?,?,?,?)")
                                .setParameter(1, premiu.getDenumirePremiu())
                                .setParameter(2, premiu.getLoc())
                                .setParameter(3, premiu.getAn())
                                .setParameter(4, cadeti.getId())
                                .executeUpdate();
                        rezultat++;
                    }
                    if (cadeti.getPremiiCadeti().isEmpty() || rezultat > 0) {
                        log.info("PUT. Premiile cadetilor cu id-ul " + cadeti.getId() + " actualizat cu succes!");
                        return true;
                    } else {
                        log.warn("PUT. Premiile cadetilor cu id-ul " + cadeti.getId() + " nu a putut fi actualizat!");
                        return false;
                    }
                } catch (PersistenceException e) {

                        throw new VoleiJuvenilExpectationFailedException("PUT. Nu s-au putut actualizat premiile cadetilor cu id-ul " + cadeti.getId() + "!");
                } catch (DataIntegrityViolationException e) {
                        throw new VoleiJuvenilExpectationFailedException("PUT. Nu s-a putut actualiza rolurile cadetilor cu id-ul " + cadeti.getId() + " avand informatii lipsa sau necorespunzatoare!");
                }
            } else {
                log.warn("PUT. In cazul obiectului de tip Cadeti atributul premiiCadeti nu poate primi valori nule!");
            }
        }else{
            log.warn("PUT. Parametrul cadeti nu poate primi valori nule!");
        }

        return false;
    }

    @Transactional
    private boolean deletePremii(Integer idCadeti){
        if(idCadeti!=null){
            try {
                entityManager.createNativeQuery("DELETE FROM cadeti_premii p WHERE p.cpr_lot=?")
                        .setParameter(1, idCadeti)
                        .executeUpdate();
                return true;
            }catch (PersistenceException e){
                log.warn("DELETE. Premiile cadetiilor cu id-ul "+idCadeti+" nu au putut fi sterse!");
                return false;
            }
        }else{
            log.warn("DELETE. Parametrul idCadeti nu poate primi valori nule!");

        }
        return false;
    }
}
