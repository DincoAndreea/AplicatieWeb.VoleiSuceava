package ro.marcc.server.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import ro.marcc.server.exception.VoleiJuvenilExpectationFailedException;
import ro.marcc.server.exception.VoleiJuvenitNotFoundException;
import ro.marcc.server.model.VoleiJuvenil.Minivolei.Minivolei;
import ro.marcc.server.model.VoleiJuvenil.Minivolei.PremiiMinivolei;
import ro.marcc.server.repository.jpa.RepositoryMinivoleiJPA;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Repository
public class RepositoryMinivolei {
    private final EntityManager entityManager;
    private final RepositoryMinivoleiJPA repositoryMinivolei;

    public RepositoryMinivolei(EntityManager entityManager, RepositoryMinivoleiJPA repositoryMinivolei) {
        this.entityManager = entityManager;
        this.repositoryMinivolei = repositoryMinivolei;
    }

    @Transactional
    public List<Minivolei> getMinivolei(){
        try {
            List<Minivolei> minivolei = repositoryMinivolei.getMinivolei();
            if(minivolei.isEmpty()){
                log.info("GET. Nu a putut fi gasita nici un minivolei!");
            }else {
                log.info("GET. Lista de minivolei s-a preluat cu succes!");
            }
            return minivolei;
        }catch(PersistenceException e){
            throw new VoleiJuvenilExpectationFailedException("GET. Nu s-a putut prelua lista de minivolei!");
        }
    }

    @Transactional
    public Minivolei getMinivolei(Integer idMinivolei){
        try {
            Minivolei minivolei =  repositoryMinivolei.getMinivolei(idMinivolei);

            if(minivolei !=null){
                log.info("GET. Minivolei cu id-ul "+idMinivolei+" preluat cu succes!");
                return minivolei;
            }else{
                log.info("GET. Minivolei cu id-ul "+idMinivolei+" nu a putut fi preluat!");
                throw new VoleiJuvenitNotFoundException("GET. Nu s-au gasit minivolei-ul cu id-ul "+idMinivolei+"!");
            }

        }catch(PersistenceException e){
            throw new VoleiJuvenilExpectationFailedException("GET. Nu s-au putut prelua minivolei cu id-ul "+idMinivolei+"!");
        }
    }

    @Transactional
    public boolean addMinivolei(Minivolei minivolei){
        if(minivolei!=null){
            int rezultat = 0;
            try{
                if(minivolei.getId()==null){
                    rezultat = entityManager.createNativeQuery("INSERT INTO minivolei(min_lot,min_imagine_lot,min_informatii) VALUES (?,?,?)")
                            .setParameter(1,minivolei.getLot())
                            .setParameter(2,minivolei.getImagineLot())
                            .setParameter(3,minivolei.getInformatii())
                            .executeUpdate();

                }else{
                    rezultat = entityManager.createNativeQuery("INSERT INTO minivolei(min_id,min_lot,min_imagine_lot,min_informatii) VALUES (?,?,?,?)")
                            .setParameter(1,minivolei.getId())
                            .setParameter(2,minivolei.getLot())
                            .setParameter(3,minivolei.getImagineLot())
                            .setParameter(4,minivolei.getInformatii())
                            .executeUpdate();
                }

                if (updatePremii(minivolei) && rezultat > 0) {
                    log.info("POST. Minivolei a fost inregistrat cu succes!");
                    return true;
                } else {
                    log.warn("POST. Minivolei nu au putut fi inregistrat!");
                    return false;
                }
            }catch(PersistenceException e){
                throw new VoleiJuvenilExpectationFailedException("POST. Nu s-a putut inregistra minivolei!");
            }
            catch(DataIntegrityViolationException e){
                throw new VoleiJuvenilExpectationFailedException("POST. Nu s-a putut inregistra minivolei avand informatii lipsa sau necorespunzatoare!");
            }
        }else{
            throw new VoleiJuvenilExpectationFailedException("POST. Parametrul minivolei nu poate primi valori nule!");
        }
    }

    @Transactional
    public boolean updateMinivolei(Integer idMinivolei, Minivolei minivoleiActualizat){
        if(minivoleiActualizat !=null){
            if(idMinivolei!=null) {
                minivoleiActualizat.setId(idMinivolei);
                int rezultat;
                try {
                    rezultat = entityManager.createNativeQuery("UPDATE minivolei m SET m.min_lot=?2,m.min_imagine_lot=?3,m.min_informatii=?4 WHERE m.min_id=?1 ")
                            .setParameter(1,idMinivolei)
                            .setParameter(2, minivoleiActualizat.getLot())
                            .setParameter(3, minivoleiActualizat.getImagineLot())
                            .setParameter(4, minivoleiActualizat.getInformatii())
                            .executeUpdate();
                    updatePremii(minivoleiActualizat);
                    if (rezultat > 0) {
                        log.info("PUT. Actualizare cu succes a informatiilor la minivolei cu id-ul" + idMinivolei + "!");
                        return true;
                    } else {
                        log.warn("PUT. Actualizare esuata a informatiilor la minivolei cu id-ul" + idMinivolei + "!");
                        return false;
                    }

                } catch (PersistenceException e) {
                    throw new VoleiJuvenilExpectationFailedException("PUT. Nu s-a putut actualiza datele la minivolei cu id-ul " + idMinivolei + "!");
                } catch (DataIntegrityViolationException e) {
                    throw new VoleiJuvenilExpectationFailedException("PUT. Nu s-a putut actualiza datele la minivolei cu id-ul " + idMinivolei + " avand informatii lipsa sau necorespunzatoare!");
                }
            }else{
                log.warn("PUT. Parametrul idMinivolei nu poate primi valori nule!");
            }
        }else{
            log.warn("PUT. Parametrul minivoleiActualizat nu poate primi valori nule!");
        }

        return false;
    }


    @Transactional
    public boolean deleteMinivolei(Integer idMinivolei){
        try {
            deletePremii(idMinivolei);
            boolean sterse = repositoryMinivolei.deleteMinivolei(idMinivolei) > 0;

            if(sterse){
                log.info("DELETE. Minivolei cu id-ul "+idMinivolei+" stears cu succes!");
                return true;
            }else{
                log.warn("DELETE. Minivolei cu id-ul "+idMinivolei+" nu au putut fi sters!");
                return false;
            }
        }catch(PersistenceException e){
            throw new VoleiJuvenilExpectationFailedException("DELETE. Nu s-au putut sterge minivolei cu id-ul "+idMinivolei+"!");
        }
    }

    @Transactional
    private boolean updatePremii(Minivolei minivolei){
        if(minivolei!=null) {
            if (minivolei.getPremiiMinivolei() != null) {
                if (minivolei.getId()==null) {
                    Object idMinivolei = entityManager.createNativeQuery("SELECT m.min_id FROM minivolei m WHERE m.min_lot=? AND m.min_imagine_lot=? AND m.min_informatii=?")
                            .setParameter(1, minivolei.getLot())
                            .setParameter(2, minivolei.getImagineLot())
                            .setParameter(3, minivolei.getInformatii())
                            .getSingleResult();
                    if(idMinivolei ==null){
                        return false;
                    }else{
                        minivolei.setId((Integer) idMinivolei);
                    }
                }
                try {
                    entityManager.createNativeQuery("DELETE FROM minivolei_premii p WHERE p.mpr_lot=?")
                            .setParameter(1, minivolei.getId())
                            .executeUpdate();

                    int rezultat= 0;
                    for (PremiiMinivolei premiu : minivolei.getPremiiMinivolei()) {
                        entityManager.createNativeQuery("INSERT INTO minivolei_premii(mpr_denumire_premiu,mpr_loc,mpr_an,mpr_lot) VALUES(?,?,?,?)")
                                .setParameter(1, premiu.getDenumirePremiu())
                                .setParameter(2, premiu.getLoc())
                                .setParameter(3, premiu.getAn())
                                .setParameter(4, minivolei.getId())
                                .executeUpdate();
                        rezultat++;
                    }
                    if (minivolei.getPremiiMinivolei().isEmpty() || rezultat > 0) {
                        log.info("PUT. Premiile minivolei cu id-ul " + minivolei.getId() + " actualizat cu succes!");
                        return true;
                    } else {
                        log.warn("PUT. Premiile minivolei cu id-ul " + minivolei .getId() + " nu a putut fi actualizat!");
                        return false;
                    }
                } catch (PersistenceException e) {
                    throw new VoleiJuvenilExpectationFailedException("PUT. Nu s-au putut actualizat premiile la minivolei cu id-ul " + minivolei.getId() + "!");
                } catch (DataIntegrityViolationException e) {
                    throw new VoleiJuvenilExpectationFailedException("PUT. Nu s-a putut actualiza rolurile la minivolei cu id-ul " + minivolei.getId() + " avand informatii lipsa sau necorespunzatoare!");
                }
            } else {
                log.warn("PUT. In cazul obiectului de tip Minivolei atributul premiiMinivolei nu poate primi valori nule!");
            }
        }else{
            log.warn("PUT. Parametrul minivolei nu poate primi valori nule!");
        }

        return false;
    }

    @Transactional
    private boolean deletePremii(Integer idMinivolei){
        if(idMinivolei!=null){
            try {
                entityManager.createNativeQuery("DELETE FROM minivolei_premii p WHERE p.mpr_lot=?")
                        .setParameter(1, idMinivolei)
                        .executeUpdate();
                return true;
            }catch (PersistenceException e){
                log.warn("DELETE. Premiile la minivolei cu id-ul "+idMinivolei+" nu au putut fi sterse!");
                return false;
            }
        }else{
            log.warn("DELETE. Parametrul idMinivolei nu poate primi valori nule!");

        }
        return false;
    }
}
