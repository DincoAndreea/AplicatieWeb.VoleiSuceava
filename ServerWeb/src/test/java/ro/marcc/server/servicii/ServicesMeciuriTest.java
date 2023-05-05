package ro.marcc.server.servicii;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.marcc.server.dto.Meci.FiltruCalendarMeci;
import ro.marcc.server.dto.Meci.MeciPrevizualizareDto;
import ro.marcc.server.dto.PaginareDto;
import ro.marcc.server.model.Localitate.Localitate;
import ro.marcc.server.model.Meciuri.Campionat;
import ro.marcc.server.model.Meciuri.Divizie;
import ro.marcc.server.model.Meciuri.Echipa;
import ro.marcc.server.model.Meciuri.Meci;
import ro.marcc.server.repository.RepositoryMeciuri;
import ro.marcc.server.service.ServicesDivizii;
import ro.marcc.server.service.ServicesMeciuri;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class ServicesMeciuriTest {
    @Autowired
    RepositoryMeciuri repositoryMeciuri;
    @Autowired
    ServicesMeciuri servicesMeciuri;
    @Autowired
    ServicesDivizii servicesDivizii;

    Echipa echipaMock1 = new Echipa();
    Echipa echipaMock2 = new Echipa();
    Echipa[] echipeListMock1 = {echipaMock1, echipaMock2};
    int[] scorMock1 = new int[2];
    LocalDateTime localDateTime1 = LocalDateTime.of(2021, Month.APRIL, 24, 14, 33, 48, 123456789);
    Campionat campionatMock1 = new Campionat();
    Localitate localitateMock1 = new Localitate();
    Divizie divizieMock1 = new Divizie();
    Meci meciMock1 = new Meci(8,echipeListMock1,scorMock1,localDateTime1,campionatMock1,localitateMock1,"link test",divizieMock1);

    @BeforeAll
    void InitializareMediuDeTestare(){
        System.out.println("--> Initializare meciuri de test");
        /*stergere elemente care vor fi adaugate pentru test*/
        repositoryMeciuri.deleteMeci(meciMock1.getId());

        /*declarare alte variabile ajutatoare*/
        echipaMock1.setId(1);
        echipaMock2.setId(2);
        campionatMock1.setId(1);
        localitateMock1.setId(1);
        divizieMock1.setId(1);

        /*adaugare elemente de test*/
        repositoryMeciuri.addMeci(meciMock1);
    }
    @Test
    void getMeciuriTest(){
        List<Meci> listaMeciuri = repositoryMeciuri.getMeciuri(true);
        boolean gasitMeciMock1 = false;
        for(Meci meciuri:listaMeciuri){
            if(meciuri.equals(meciMock1)){
                gasitMeciMock1 = true;
            }
        }
        log.info("TEST. Verificare Meci " + meciMock1.getId() + " exista in lista, fiind adaugat cu succes!");
        assertThat(gasitMeciMock1);
    }

    @Test
    void getMeciuriEchipaTest(){
        List<Meci> listaMeciuri = servicesMeciuri.getMeciuri(meciMock1.getEchipe()[1]);
        boolean gasitMeciMock1 = false;
        for(Meci meciuri:listaMeciuri){
            if(meciuri.getEchipe()[1].equals(meciMock1.getEchipe()[1])){
                gasitMeciMock1 = true;
            }
        }
        log.info("TEST. Verificare Meci " + meciMock1.getEchipe()[1] + " exista in functie de echipa, fiind adaugat cu succes!");
        assertThat(gasitMeciMock1);
    }

    @Test
    void getMeciIdTest(){
        Meci meciGasit = repositoryMeciuri.getMeci(meciMock1.getId());
        System.out.println("--> Meci" + meciGasit.getId());
        log.info("TEST. ID-ul de la Meci " + meciMock1.getId() + " corespunde cu ID-ul elemntului adaugat");
        assertThat(meciGasit.getId().equals(meciMock1.getId()));

        log.info("TEST. Linkul de la Meci " + meciMock1.getLink() + " corespunde cu Linkul elemntului adaugat");
        assertThat(meciGasit.getLink().equals(meciMock1.getLink()));
    }

    @Test
    void postMeciTest(){
        repositoryMeciuri.deleteMeci(meciMock1.getId());
        log.info("TEST. Meciul "+ meciMock1.getId() +" a fost postat cu succes!");
        assertThat(servicesMeciuri.postMeci(meciMock1));

    }

    @Test
    void updateMeciTest(){
        Meci meciModificat = servicesMeciuri.getMeci(meciMock1.getId());
        LocalDateTime localDateTimeModificat = LocalDateTime.of(2022, Month.SEPTEMBER, 10, 10, 00, 01, 123456789);
        meciModificat.setDataMeci(localDateTimeModificat);
        meciModificat.setLink("link modificat test");

        assertThat(repositoryMeciuri.updateMeci(meciMock1.getId(),meciModificat));
        assertThat(meciModificat.getDataMeci().equals(meciMock1.getDataMeci()));
        assertThat(meciModificat.getLink().equals(meciMock1.getLink()));
    }

    @Test
    void deleteMeciTest(){
        log.info("TEST. Stergerea meciului cu id-ul "+ meciMock1.getId()+" a fost realizata cu succes!");
        assertThat(repositoryMeciuri.deleteMeci(meciMock1.getId()));
    }

    @Test
    void getDiviziiTest(){
        List<Divizie> listDivizieGasit = servicesDivizii.getDivizii();
        boolean divizieGasit = false;
        for(Divizie divizii:listDivizieGasit){
            if(divizii.getId().equals(meciMock1.getDivizie().getId())){
                divizieGasit = true;
            }
        }
        log.info("Divizia din meciul adaugat a fost gasita cu succes! ");
        assertThat(divizieGasit);
    }

    @Test
    void getMeciuriDupaIdDivizieTest(){
        List<Meci> listMeciuriDupaIdDivizieGasit = servicesMeciuri.getMeciuriDupaIdDivizie(meciMock1.getDivizie().getId(),new PaginareDto(1,200,null,null)).getMeciuri();
        boolean meciDupaIdDivizieGasit = false;
        for(Meci meciuri :listMeciuriDupaIdDivizieGasit){
            if(meciuri.getDivizie().getId().equals(meciMock1.getDivizie().getId())){
                meciDupaIdDivizieGasit = true;
            }
        }
        log.info("Meciul dupa id-ul "+meciMock1.getDivizie().getId()+" al diviziei a fost gasita cu succes! ");
        assertThat(meciDupaIdDivizieGasit);
    }

    @Test
    void getMeciuriFiltrate(){
        LocalDate dateInitiala = localDateTime1.toLocalDate();
        List<MeciPrevizualizareDto> listMeciuriFiltrareGasit = servicesMeciuri.getMeciuriFiltrate(new FiltruCalendarMeci(meciMock1.getDivizie().getId(),null,dateInitiala,dateInitiala,null)).getMeciuri();
        boolean meciuriFiltrareGasit = false;
        for(MeciPrevizualizareDto meciuri :listMeciuriFiltrareGasit){

            if(meciuri.getDataMeci().equals(meciMock1.getDataMeci().toLocalDate())) {
                meciuriFiltrareGasit = true;
            }

        }
        log.info("Meciurile filtrate au fost gasite cu succes! ");
        assertThat(meciuriFiltrareGasit);
    }

    @AfterAll
    void curatareMediuDeTestare(){
        System.out.println("--> Eliminare meciuri de test");
        repositoryMeciuri.deleteMeci(meciMock1.getId());
    }
}
