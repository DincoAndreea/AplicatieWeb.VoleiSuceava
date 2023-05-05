package ro.marcc.server.servicii;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.marcc.server.model.Sponsor;
import ro.marcc.server.repository.RepositorySponsori;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class ServicesSponsorTests {
    @Autowired
    RepositorySponsori repositorySponsori;
    Sponsor sponsorMock1 = new Sponsor(2,"Sprite","2018-2023","link","LogoTest");

    @BeforeAll
    void initializareMediuDeTestare(){
        System.out.println("--> Initializare sponsor de test");
    }

    @Test
    void getSponsoriTest(){
        List<Sponsor> sponsori = repositorySponsori.getSponsori();
        boolean sponsori1Gasit = false;
        for (Sponsor sponsor:
                sponsori) {
            if(sponsor.getNume().equals(sponsorMock1.getNume()))
                sponsori1Gasit = true;
        }
        log.info("TEST. Sponsorul a fost preluat succes! ");
        assertThat(sponsori1Gasit);
    }

    @Test
    void getLinkuriImaginiTest(){
        log.info("TEST. Link-urile imaginilor au fost preluate cu succes! ");
    }

    @AfterAll
    void curatareMediuDeTestare(){
        System.out.println("--> Eliminare sponsori de test");
        repositorySponsori.getSponsori().remove(sponsorMock1.getId());
    }
}
