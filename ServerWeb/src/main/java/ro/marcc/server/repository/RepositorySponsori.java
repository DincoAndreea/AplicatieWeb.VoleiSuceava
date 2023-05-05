package ro.marcc.server.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ro.marcc.server.model.Sponsor;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Repository
public class RepositorySponsori {
    private final EntityManager entityManager;

    public RepositorySponsori(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
        public List<Sponsor> getSponsori(){
            List<Sponsor> sponsori = new ArrayList<>();
            try {
                List<Object[]> sponsoriBD = entityManager.createNativeQuery("SELECT s.spo_id, s.spo_nume,s.spo_editie,s.spo_link,s.spo_link_logo FROM Sponsori s").getResultList();
                if (sponsoriBD.isEmpty()) {
                    log.warn("GET. Nu s-a putut gasi nici un sponsor!");
                }else{
                    log.info("GET. S-a preluat lista de sponsori cu succes!");
                }

                for (Object[] sponsor : sponsoriBD) {
                    sponsori.add(new Sponsor((Integer) sponsor[0], (String) sponsor[1], (String) sponsor[2], (String) sponsor[3], (String) sponsor[4]));
                }

            }catch(PersistenceException e){
                log.warn("GET. Nu s-a putut prelua lista de sponsori!");
            }

            return sponsori;
        }
}
