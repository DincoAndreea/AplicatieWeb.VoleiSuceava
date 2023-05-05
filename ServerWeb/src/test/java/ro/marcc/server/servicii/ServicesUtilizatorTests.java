package ro.marcc.server.servicii;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.marcc.server.dto.Utilizator.UtilizatorLoginDto;
import ro.marcc.server.model.Utilizator.Utilizator;
import ro.marcc.server.repository.RepositoryUtilizatori;
import ro.marcc.server.repository.jpa.RepositoryUtilizatoriJPA;
import ro.marcc.server.service.ServicesUtilizator;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class ServicesUtilizatorTests {
    @Autowired
    ServicesUtilizator servicesUtilizator;
    @Autowired
    RepositoryUtilizatoriJPA repositoryUtilizatoriJPA;
    @Autowired
    RepositoryUtilizatori repositoryUtilizatori;

    Utilizator utilizatorMock1 = new Utilizator(0,"MikeSeravis","Mike2000","cherry23",'v');
    Utilizator utilizatorMock2 = new Utilizator(1,"SarahMillo","PurpleHouse","mydogmychair",'c');
    Utilizator utilizatorMock3 = new Utilizator(2,"MagiunMissasel","missThisCar","potatoes",'a');
    Utilizator creatorDeContinutMock = new Utilizator(2,"ErrisTsan","tsan_ming","yourDog",'c');


    @BeforeAll
    void initializareMediuDeTestare(){
        System.out.println("--> Initializare utilizatori de test");
        repositoryUtilizatori.addCreatorDeContinut(utilizatorMock1.getNume(),utilizatorMock1.getNumeUtilizator(), utilizatorMock1.getParola());
        repositoryUtilizatori.addCreatorDeContinut(utilizatorMock2.getNume(),utilizatorMock2.getNumeUtilizator(), utilizatorMock2.getParola());
        repositoryUtilizatori.addCreatorDeContinut(utilizatorMock3.getNume(),utilizatorMock3.getNumeUtilizator(), utilizatorMock3.getParola());
    }

    @Test
    void autentificareCuOriceFelDeUtilizator(){
        log.info("TEST. Autentificare utilizator 1 cu succes! ");
        assertThat(servicesUtilizator.autentificare(new UtilizatorLoginDto(utilizatorMock1)));

        log.info("TEST. Autentificare utilizator 2 cu succes! ");
        assertThat(servicesUtilizator.autentificare(new UtilizatorLoginDto(utilizatorMock2)));

        log.info("TEST. Autentificare utilizator 3 cu succes! ");
        assertThat(servicesUtilizator.autentificare(new UtilizatorLoginDto(utilizatorMock3)));
    }

    @Test
    void inregistrareCreatoriDeContinut(){
        UtilizatorLoginDto creatorDeContinut;
        log.info("TEST. Adaugarea unui nou creator de continut a fost realizata cu succes! ");
        assertThat(repositoryUtilizatori.addCreatorDeContinut(creatorDeContinutMock.getNume(),creatorDeContinutMock.getNumeUtilizator(),creatorDeContinutMock.getParola()));

        log.info("TEST. Verficarea creatorului de continut ca nefiind null realizata cu succes! ");
        assertThat(( creatorDeContinut = new UtilizatorLoginDto(creatorDeContinutMock))!=null);
        Utilizator creatorDeContinutInregistrat;

        log.info("TEST. Inregistrarea unui nou creator de continut a fost realizata cu succes");
        assertThat( (creatorDeContinutInregistrat =  repositoryUtilizatoriJPA.getUtilizator(creatorDeContinut.getNumeUtilizator()))!=null);

        log.info("TEST. Numele de utilizator"+ creatorDeContinut.getNumeUtilizator() +" corespunde cu numele elementului inregistrat");
        assertThat(creatorDeContinutInregistrat.getNumeUtilizator().equals(creatorDeContinut.getNumeUtilizator()));

        log.info("TEST. Parola "+ creatorDeContinut.getParola() + " corespunde cu parola inregistrat");
        assertThat(creatorDeContinutInregistrat.getParola().equals(creatorDeContinut.getParola()));

        log.info("TEST. Rolul creatorului de continut "+ creatorDeContinutInregistrat.getRol() +" corespunde cu rolul creatorului de continut inregistrat");
        assertThat(creatorDeContinutInregistrat.getRol()=='c');

        log.info("TEST. Stergerea creatorului de continut adaugat a fost realizata cu succes!");
        assertThat(repositoryUtilizatoriJPA.deleteByNumeUtilizator(creatorDeContinutInregistrat.getNumeUtilizator()));
    }

    @AfterAll
    void curatareMediuDeTestare(){
        System.out.println("--> Eliminare utilizatori de test");
        repositoryUtilizatoriJPA.deleteByNumeUtilizator(utilizatorMock1.getNumeUtilizator());
        repositoryUtilizatoriJPA.deleteByNumeUtilizator(utilizatorMock2.getNumeUtilizator());
        repositoryUtilizatoriJPA.deleteByNumeUtilizator(utilizatorMock3.getNumeUtilizator());
    }
}
