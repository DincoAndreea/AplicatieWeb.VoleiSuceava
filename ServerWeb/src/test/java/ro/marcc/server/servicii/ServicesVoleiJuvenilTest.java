package ro.marcc.server.servicii;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.marcc.server.model.VoleiJuvenil.Cadeti.Cadeti;
import ro.marcc.server.model.VoleiJuvenil.Cadeti.PremiiCadeti;
import ro.marcc.server.model.VoleiJuvenil.Juniori.Juniori;
import ro.marcc.server.model.VoleiJuvenil.Juniori.PremiiJuniori;
import ro.marcc.server.model.VoleiJuvenil.Minivolei.Minivolei;
import ro.marcc.server.model.VoleiJuvenil.Minivolei.PremiiMinivolei;
import ro.marcc.server.model.VoleiJuvenil.Sperante.PremiiSperante;
import ro.marcc.server.model.VoleiJuvenil.Sperante.Sperante;
import ro.marcc.server.repository.RepositoryCadeti;
import ro.marcc.server.repository.RepositoryJuniori;
import ro.marcc.server.repository.RepositoryMinivolei;
import ro.marcc.server.repository.RepositorySperante;
import ro.marcc.server.service.ServicesVoleiJuvenil;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class ServicesVoleiJuvenilTest {
    @Autowired
    ServicesVoleiJuvenil servicesVoleiJuvenil;
    @Autowired
    RepositoryCadeti repositoryCadeti;
    @Autowired
    RepositoryJuniori repositoryJuniori;
    @Autowired
    RepositoryMinivolei repositoryMinivolei;
    @Autowired
    RepositorySperante repositorySperante;

    Set<PremiiCadeti> premiiCadeti = Set.of();
    Set<PremiiJuniori> premiiJuniori = Set.of();
    Set<PremiiMinivolei> premiiMinivoiei = Set.of();
    Set<PremiiSperante> premiiSperante = Set.of();

    Cadeti cadetiMock1 = new Cadeti(2,"lot Test1","imagine Lot Test1","Informatii cadeti Test 1",premiiCadeti);
    Cadeti cadetiMock2 = new Cadeti(3,"lot Test2","imagine Lot Test2","Informatii cadeti Test 2",premiiCadeti);
    Juniori junioriMock1 = new Juniori(4,"lot test1","imagine juniori Test1","Infomratii Juniori test1", premiiJuniori);
    Juniori junioriMock2 = new Juniori(5,"lot test2","imagine juniori test2","Informatii Juniori test2", premiiJuniori);
    Minivolei minivoleiMock1 = new Minivolei(2,"lot test1","imagine test1","Informatii minivolei test1", premiiMinivoiei);
    Minivolei minivoleiMock2 = new Minivolei(3,"lot test2","imagine test2","Informatii minivolei test2", premiiMinivoiei);
    Sperante speranteMock1 = new Sperante(2,"lot test1","imagine test1","Informatii sperante test1", premiiSperante);
    Sperante speranteMock2 = new Sperante(3,"lot test2","imagine test2","Informatii sperante test2", premiiSperante);

    @BeforeAll
    void InitializareMediuDeTestare() {
        System.out.println("--> Initializare volei juvenil de test");
        //stergem cadetii, juniorii, minivolei, sperante de la ID-urile pe care vrem sa le introducem pentru test
        repositoryCadeti.deleteCadeti(cadetiMock1.getId());
        repositoryCadeti.deleteCadeti(cadetiMock2.getId());
        repositoryJuniori.deleteJuniori(junioriMock1.getId());
        repositoryJuniori.deleteJuniori(junioriMock2.getId());
        repositoryMinivolei.deleteMinivolei(minivoleiMock1.getId());
        repositoryMinivolei.deleteMinivolei(minivoleiMock2.getId());
        repositorySperante.deleteSperante(speranteMock1.getId());
        repositorySperante.deleteSperante(speranteMock2.getId());

        //adaugare valorile de test
        repositoryCadeti.addCadeti(cadetiMock1);
        repositoryJuniori.addJuniori(junioriMock1);
        repositoryMinivolei.addMinivolei(minivoleiMock1);
        repositorySperante.addSperante(speranteMock1);
    }

    @Test
    void addCadetiTest(){
        log.info("TEST. Cadetii cu ID-ul " + cadetiMock2.getId() + " au fost adaugati cu succes!");
        assertThat(repositoryCadeti.addCadeti(cadetiMock2));
    }

    @Test
    void addJunioriTest(){
        log.info("TEST. Juniorii cu ID-ul " + junioriMock2.getId() +" au fost adaugati cu succes!");
        assertThat(repositoryJuniori.addJuniori(junioriMock2));
    }

    @Test
    void addMinivoleiTest(){
        log.info("TEST. Minivolei cu ID-ul " + minivoleiMock2.getId() +" a fost adaugat cu succes!");
        assertThat(repositoryMinivolei.addMinivolei(minivoleiMock2));
    }

    @Test
    void addSperanteTest(){
        log.info("TEST. Sperante cu ID-ul " + speranteMock2.getId() +" a fost adaugat cu succes!" );
        assertThat(repositorySperante.addSperante(speranteMock2));
    }

    @Test
    void getCadetiTest(){
        List<Cadeti> listaCadetiMock1 = repositoryCadeti.getCadeti();
        boolean gasitCadetiMock1 = false;
        boolean gasitCadetiMock2 = false;
        for(Cadeti cadeti:listaCadetiMock1){
            if(cadeti.equals(cadetiMock1)){
                gasitCadetiMock1 = true;
            }
            if(cadeti.equals(cadetiMock2)){
                gasitCadetiMock2 = true;
            }
        }
        log.info("TEST. Verificare Cadeti " + cadetiMock1.getId() + " exista in lista");
        assertThat(gasitCadetiMock1);

        log.info("TEST. Verificare Cadeti " + cadetiMock2.getId() + " exista in lista");
        assertThat(gasitCadetiMock2);
    }

    @Test
    void getJunioriTest(){
        List<Juniori> listaJunioriMock1 = repositoryJuniori.getJuniori();
        boolean gasitJunioriMock1 = false;
        boolean gasitJunioriMock2 = false;
        for(Juniori juniori:listaJunioriMock1){
            if(juniori.equals(junioriMock1)){
                gasitJunioriMock1 = true;
            }
            if(juniori.equals(junioriMock2)){
                gasitJunioriMock2 = true;
            }
        }
        log.info("TEST. Verificare Juniori " + junioriMock1.getId() + " exista in lista");
        assertThat(gasitJunioriMock1);

        log.info("TEST. Verificare Juniori " + junioriMock2.getId() + " exista in lista");
        assertThat(gasitJunioriMock2);
    }

    @Test
    void getMinivoleiTest(){
        List<Minivolei> listaMinivoleiMock1 = repositoryMinivolei.getMinivolei();
        boolean gasitMinivoleiMock1 = false;
        boolean gasitMinivoleiMock2 = false;
        for(Minivolei minivolei:listaMinivoleiMock1){
            if(minivolei.equals(minivoleiMock1)){
                gasitMinivoleiMock1 = true;
            }
            if(minivolei.equals(minivoleiMock2)){
                gasitMinivoleiMock2 = true;
            }
        }
        log.info("TEST. Verificare Minivolei " + minivoleiMock1.getId() + " exista in lista");
        assertThat(gasitMinivoleiMock1);

        log.info("TEST. Verificare Minivolei " + minivoleiMock2.getId() + " exista in lista");
        assertThat(gasitMinivoleiMock2);
    }

    @Test
    void getSperanteTest(){
        List<Sperante> listaSperanteMock1 = repositorySperante.getSperante();
        boolean gasitSperanteMock1 = false;
        boolean gasitSperanteMock2 = false;
        for(Sperante sperante:listaSperanteMock1){
            if(sperante.equals(speranteMock1)){
                gasitSperanteMock1 = true;
            }
            if(sperante.equals(speranteMock2)){
                gasitSperanteMock2 = true;
            }
        }
        log.info("TEST. Verificare Sperante " + speranteMock1.getId()+ " exista in lista");
        assertThat(gasitSperanteMock1);

        log.info("TEST. Verificare Sperante " + speranteMock2.getId() + " exista in lista");
        assertThat(gasitSperanteMock2);
    }



    @Test
    void getCadetiIdTest(){
        Cadeti cadetiGasit = servicesVoleiJuvenil.getCadeti(cadetiMock1.getId());
        log.info("TEST. ID-ul de la Cadetul " + cadetiMock1.getId() + " corespunde cu ID-ul elemntului adaugat");
        assertThat(cadetiGasit.getId().equals(cadetiMock1.getId()));

        log.info("TEST. Lotul de la Cadetul " + cadetiMock1.getId() + " corespunde cu Lotul elemntului adaugat");
        assertThat(cadetiGasit.getLot().equals(cadetiMock1.getLot()));

        log.info("TEST. Informatiile de la Cadetul " + cadetiMock1.getId() + " corespund cu Informatiile elemntului adaugat");
        assertThat(cadetiGasit.getInformatii().equals(cadetiMock1.getInformatii()));

        log.info("TEST. Imaginea Lotului de la Cadetul " + cadetiMock1.getId() + " corespunde cu Imaginea Lotului elemntului adaugat");
        assertThat(cadetiGasit.getImagineLot().equals(cadetiMock1.getImagineLot()));
    }

    @Test
    void getJunioriIdTest(){
        Juniori junioriGasit = servicesVoleiJuvenil.getJuniori(junioriMock1.getId());
        log.info("TEST. ID-ul de la Juniori " + cadetiMock1.getId() + " corespunde cu ID-ul elemntului adaugat");
        assertThat(junioriGasit.getId().equals(junioriMock1.getId()));

        log.info("TEST. Lotul de la Juniori " + cadetiMock1.getId() + " corespunde cu Lotul elemntului adaugat");
        assertThat(junioriGasit.getLot().equals(junioriMock1.getLot()));

        log.info("TEST. Informatiile de la Juniori " + cadetiMock1.getId() + " corespund cu Informatiile elemntului adaugat");
        assertThat(junioriGasit.getInformatii().equals(junioriMock1.getInformatii()));

        log.info("TEST. Imaginea Lotului de la Juniori " + cadetiMock1.getId() + " corespunde cu Imaginea Lotului elemntului adaugat");
        assertThat(junioriGasit.getImagineLot().equals(junioriMock1.getImagineLot()));
    }

    @Test
    void getMinivoleiIdTest(){
        Minivolei minivoleiGasit = servicesVoleiJuvenil.getMinivolei(minivoleiMock1.getId());
        log.info("TEST. ID-ul de la Minivolei " + cadetiMock1.getId() + " corespunde cu ID-ul elemntului adaugat");
        assertThat(minivoleiGasit.getId().equals(minivoleiMock1.getId()));

        log.info("TEST. Lotul de la Minivolei " + cadetiMock1.getId() + " corespunde cu Lotul elemntului adaugat");
        assertThat(minivoleiGasit.getLot().equals(minivoleiMock1.getLot()));

        log.info("TEST. Informatiile de la Minivolei " + cadetiMock1.getId() + " corespund cu Informatiile elemntului adaugat");
        assertThat(minivoleiGasit.getInformatii().equals(minivoleiMock1.getInformatii()));

        log.info("TEST. Imaginea Lotului de la Minivolei " + cadetiMock1.getId() + " corespunde cu Imaginea Lotului elemntului adaugat");
        assertThat(minivoleiGasit.getImagineLot().equals(minivoleiMock1.getImagineLot()));
    }

    @Test
    void getSperanteIdTest(){
        Sperante speranteGasit = servicesVoleiJuvenil.getSperante(minivoleiMock1.getId());
        log.info("TEST. ID-ul de la Sperante " + cadetiMock1.getId() + " corespunde cu ID-ul elemntului adaugat");
        assertThat(speranteGasit.getId().equals(speranteMock2.getId()));

        log.info("TEST. Lotul de la Sperante" + cadetiMock1.getId() + " corespunde cu Lotul elemntului adaugat");
        assertThat(speranteGasit.getLot().equals(speranteMock1.getLot()));

        log.info("TEST. Informatiile de la Sperante " + cadetiMock1.getId() + " corespund cu Informatiile elemntului adaugat");
        assertThat(speranteGasit.getInformatii().equals(speranteMock1.getInformatii()));

        log.info("TEST. Imaginea Lotului de la Sperante " + cadetiMock1.getId() + " corespunde cu Imaginea Lotului elemntului adaugat");
        assertThat(speranteGasit.getImagineLot().equals(speranteMock1.getImagineLot()));
    }

    @Test
    void deleteCadetiIdTest(){
        log.info("TEST. Stergere cu succes - Cadeti cu ID "+ cadetiMock2.getId());
        assertThat(repositoryCadeti.deleteCadeti(cadetiMock2.getId()));
    }

    @Test
    void deleteJunioriIdTest(){
        log.info("TEST. Stergere cu succes - Juniori cu ID "+ junioriMock2.getId());
        assertThat(repositoryJuniori.deleteJuniori(junioriMock2.getId()));
    }

    @Test
    void deleteMinivoleiIdTest(){
        log.info("TEST. Stergere cu succes - Minivolei cu ID "+ minivoleiMock2.getId());
        assertThat(repositoryMinivolei.deleteMinivolei(minivoleiMock2.getId()));
    }

    @Test
    void deleteSperanteIDTest(){
        log.info("TEST. Stergere cu succes - Sperante cu ID "+ speranteMock2.getId());
        assertThat(repositorySperante.deleteSperante(speranteMock2.getId()));
    }

    @AfterAll
    void curatareMediuDeTestare() {
        System.out.println("--> Eliminare volei juvenil de test");
        repositoryCadeti.deleteCadeti(cadetiMock1.getId());
        repositoryJuniori.deleteJuniori(junioriMock1.getId());
        repositoryMinivolei.deleteMinivolei(minivoleiMock1.getId());
        repositorySperante.deleteSperante(speranteMock1.getId());
    }
}
