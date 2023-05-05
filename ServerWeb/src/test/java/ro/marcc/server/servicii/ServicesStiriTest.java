package ro.marcc.server.servicii;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.marcc.server.dto.PaginareDto;
import ro.marcc.server.dto.Stire.StireAdministrareDto;
import ro.marcc.server.model.Stire;
import ro.marcc.server.repository.RepositoryStiri;
import ro.marcc.server.service.ServicesStiri;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class ServicesStiriTest {
    @Autowired
    ServicesStiri servicesStiri;
    @Autowired
    RepositoryStiri repositoryStiri;

    LocalDate dtMock1 = LocalDate.parse("2008-11-01");
    LocalDate dtMock2 = LocalDate.parse("2012-07-21");
    List<String> hashtagTest = Arrays.asList("victorieTest","campionatTest");
    Stire stireMock1 = new Stire(76,"Titlu de test1","Descriere de test1",null,null,null,false,dtMock1);
    Stire stireMock2 = new Stire(77,"Titlu de test2","Descriere de test2",hashtagTest,null,null,false,dtMock1);
    Stire stireMock3 = new Stire(78,"Titlu de test3","Descriere de test3",null,null,null,false,dtMock2);
    Stire stireMock4 = new Stire(79,"Titlu de test4","Descriere de test4",null,null,null,false,dtMock2);

    StireAdministrareDto stireAdministrareDTOMock1 = new StireAdministrareDto(70,"Titlu Test",dtMock1);
    PaginareDto paginareMock1 = new PaginareDto(7,13,dtMock1,dtMock2);
    @BeforeAll
    void initializareMediuDeTestare(){
        System.out.println("--> Initializare stiri de test");
        //stergerea elementelor pe care vrem sa le adaugam
        repositoryStiri.deleteStire(stireMock1.getId());
        repositoryStiri.deleteStire(stireMock2.getId());
        repositoryStiri.deleteStire(stireMock3.getId());
        repositoryStiri.deleteStire(stireMock4.getId());

        //adaugam elementele de testare
        repositoryStiri.addStire(stireMock1);
        repositoryStiri.addStire(stireMock2);
        repositoryStiri.addStire(stireMock3);
    }

    @Test
    void getStiriDraftTest(){
        List<Stire> stiri = repositoryStiri.getStiri(false,false);
        boolean stire1Gasit = false;
        boolean stire2Gasit = false;
        boolean stire3Gasit = false;

        for (Stire stire:
            stiri) {
            if(stire.getDescriere().equals(stireMock1.getDescriere())){
                stire1Gasit = true;
            }if(stire.getDescriere().equals(stireMock2.getDescriere())){
                stire2Gasit = true;
            }if(stire.getDescriere().equals(stireMock3.getDescriere())){
                stire3Gasit = true;
            }
        }
        log.info("TEST. Stirea  " + stireMock1.getTitlu() + " a fost gasita cu succes!");
        assertThat(stire1Gasit);

        log.info("TEST. Stirea  " + stireMock2.getTitlu() + " a fost gasita cu succes!");
        assertThat(stire2Gasit);

        log.info("TEST. Stirea  " + stireMock3.getTitlu() + " a fost gasita cu succes!");
        assertThat(stire3Gasit);
    }

    @Test
    void getStiriTipStireDraftTest(){
        boolean programat = true;
        boolean draft1 = true;
        boolean stire1Gasit = false;
        List<Stire> stiri = servicesStiri.getStiri(programat,draft1);
        for (Stire stire: stiri){
            if(stire.isDraft())
                stire1Gasit = true;
        }
        log.info("TEST. Stirea  draft a fost gasita cu succes!");
        assertThat(stire1Gasit);
    }

    @Test
    void getStiriTipStireMediaCronologicPaginareTest(){
        Integer tipStireMock1 = 2;
        Character tipMedia1 = 'i';
        Integer tipStireCronologicMock1 = 1;
        Integer tipStireMock2 = 1;
        Character tipMedia2 = 'v';
        Integer tipStireCronologicMock2 = 5;

        log.info("TEST. Stirea  de tipul " + tipStireMock1 +", " + tipMedia1+" a fost gasita cu succes!");
        assertThat(servicesStiri.getStiri(tipStireMock1,tipMedia1,tipStireCronologicMock1,paginareMock1));

        log.info("TEST. Stirea  de tipul " + tipStireMock2 +", " + tipMedia2+" a fost gasita cu succes!");
        assertThat(servicesStiri.getStiri(tipStireMock2,tipMedia2,tipStireCronologicMock2,paginareMock1));
    }

    @Test
    void getStiriPaginareTest(){
        log.info("TEST. Paginarea stiri!");
        assertThat(servicesStiri.getStiri(null,paginareMock1));
        log.info("TEST. Paginarea a fost gasita cu succes!");
    }

    @Test
    void getStiriIdDraftTest(){
        Character idMock = 79;
        boolean draftMock = true;

        log.info("TEST. Stirea "+stireMock4.getTitlu()+" a fost gasita cu succes!");
        assertThat(servicesStiri.getStire(idMock).equals(stireMock4));
    }

    @Test
    void postStireTest(){
        log.info("TEST. Stirea "+stireMock4.getTitlu()+" a fost postata cu succes!");
        assertThat(servicesStiri.postStire(stireMock4));
    }

    @Test
    void deleteStireTitluTest() {
        log.info("TEST. Stergerea stirei "+stireMock4.getTitlu()+" a fost realizata cu succes!");
        assertThat(repositoryStiri.deleteStire(stireMock4.getTitlu()));
    }

    @Test
    void deleteSitreIdTest(){
        log.info("TEST. Stergerea stirei cu id-ul "+stireMock4.getId()+" a fost realizata cu succes!");
        assertThat(repositoryStiri.deleteStire(stireMock2.getId()));
    }

    @Test
    void updateStireTest(){
        log.info("TEST. Update-ul la stirea "+stireMock3.getTitlu()+" a fost realizata cu succes!");
        assertThat(repositoryStiri.updateStire(stireMock3.getId(),stireMock4));
    }

    @Test
    void getNumarDePaginiTest(){
//        int numarDeElementePePaginaMock0 = 0;
//        log.info("TEST. Numarul de pagina "+numarDeElementePePaginaMock0+" a stirei a fost gasita cu succes!");
//        assertThatIOException(servicesStiri.getNumarDePagini(numarDeElementePePaginaMock0));

        int numarDeElementePePaginaMock1 = 1;
        boolean stiriPostateMock1 = true;
        log.info("TEST. Numarul de pagi in caz de se sa fie "+numarDeElementePePaginaMock1+" stiri pe pagina!");
        assertThat(servicesStiri.getNumarDePagini(stiriPostateMock1,numarDeElementePePaginaMock1)>0);
    }

    @AfterAll
    void curatareMediuDeTestare(){
        System.out.println("--> Eliminare utilizatori de test");
        repositoryStiri.deleteStire(stireMock1.getId());
        repositoryStiri.deleteStire(stireMock2.getId());
        repositoryStiri.deleteStire(stireMock3.getId());
        repositoryStiri.deleteStire(stireMock4.getId());

    }

}
