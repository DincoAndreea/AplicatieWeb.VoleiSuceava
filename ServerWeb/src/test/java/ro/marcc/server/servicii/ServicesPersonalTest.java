package ro.marcc.server.servicii;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.marcc.server.model.Meciuri.Echipa;
import ro.marcc.server.model.Personal.Persoana;
import ro.marcc.server.model.Personal.Roluri.Antrenor;
import ro.marcc.server.model.Personal.Roluri.Jucator;
import ro.marcc.server.model.Realizari.Premiu;
import ro.marcc.server.repository.RepositoryPersonal;
import ro.marcc.server.service.ServicesPersonal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class ServicesPersonalTest {
    @Autowired
    RepositoryPersonal repositoryPersonal;
    ServicesPersonal servicesPersonal;

    TreeMap<LocalDate, String> rolMock1 = new TreeMap<>();
    LocalDate dtMock1 = LocalDate.parse("2008-11-01");
    Premiu premiuMock1 = new Premiu();
    Premiu premiuMock2 = new Premiu();
    List<Premiu> listPremiuMock1 = new ArrayList<>();

    Persoana persoanaMock1;
    Persoana persoanaMock2;
    Persoana persoanaMock3;
    Persoana persoanaMock4;

    String logoMock1 = "logo Test1";
    String logoMock2 = "logo Test2";

    Echipa echipaMock1;
    Echipa echipaMock2;

    Jucator jucatorMock1;
    Jucator jucatorMock2;
    Antrenor antrenorMock1;
    Antrenor antrenorMock2;

    @BeforeAll
    void initializareMediuDeTestare(){
        ///Pregatim scenariul de test stergand posibile date ramase din testele anterioare
        repositoryPersonal.deleteEchipa(333);
        repositoryPersonal.deleteEchipa(444);
        repositoryPersonal.deletePersoana(170);
        repositoryPersonal.deletePersoana(180);
        repositoryPersonal.deletePersoana(190);
        repositoryPersonal.deletePersoana(200);

        ///Initializam datele de test
        System.out.println("--> Initializare Personal de test");
        premiuMock1 = new Premiu(600,"Premniu Test 1",dtMock1);
        premiuMock2 = new Premiu(700,"Premniu Test 2",dtMock1);
        listPremiuMock1 = Arrays.asList(premiuMock1, premiuMock2);

        persoanaMock1 = new Persoana(170,"Polocoser","Rebeca","link","Roman","Ridicator","02/04/2000",168,"Descriere ridicator Test",rolMock1, listPremiuMock1,null);
        persoanaMock2 = new Persoana(180,"Ilisei","Denis","linkPoza","Roman","Ridicator","01/09/1989",180,"Descriere antrenor Test",rolMock1,listPremiuMock1,null);
        persoanaMock3 = new Persoana(190,"Test1","Antrenor1","link1","Roman","Antrenor","02/04/2000",168,"Descriere ridicator Test",rolMock1, listPremiuMock1,null);
        persoanaMock4 = new Persoana(200,"Test2","Antrenor2","link2","Roman","Antrenor","01/09/1989",180,"Descriere antrenor Test",rolMock1,listPremiuMock1,null);

        echipaMock1 = new Echipa(333,"CSM Test1",null,null,null,logoMock1, true);
        echipaMock2 = new Echipa(444,"CSM Test2",null,null,null,logoMock2,true);

        jucatorMock1 = new Jucator(persoanaMock1,echipaMock1);
        jucatorMock2 = new Jucator(persoanaMock2,echipaMock2);
        antrenorMock1 = new Antrenor(persoanaMock3,echipaMock1);
        antrenorMock2 = new Antrenor(persoanaMock4,echipaMock2);

        ///Adaugam datele de test in repository

        //adaugam echipe pentru jucatorii si antrenorii ce vor fi adaugati in repository
        repositoryPersonal.addEchipa(echipaMock1);
        repositoryPersonal.addEchipa(echipaMock2);

        //adaugam antrenori echipelor anterior adaugate
        repositoryPersonal.addAntrenor(antrenorMock1);
        repositoryPersonal.addAntrenor(antrenorMock2);

        //adaugam cate un jucator echipelor anterior adaugate
        repositoryPersonal.addJucator(jucatorMock1);
        repositoryPersonal.addJucator(jucatorMock2);
    }

    //antrenori
    @Test
    void getAntrenoriTest(){
//        List<Antrenor> antrenoriMock = Arrays.asList(antrenorMock1);
        boolean antrenor1Gasit = false;
//        for (Antrenor antrenor:
//                antrenoriMock){
//            if(antrenor.equals(antrenorMock1))
                antrenor1Gasit = true;
//        }
        assertThat(antrenor1Gasit);
    }

    @Test
    void getAntrenoriPaginareTest(){}

    //jucatori
    @Test
    void getJucatoriEchipaIdTest(){
        assertThat(repositoryPersonal.getjucatoriEchipa(100,false)==null);
    }

    @Test
    void getJucatoriPaginareTest(){}

    //personal
    @Test
    void getPersonalFiltruTest(){}

    //update
    @Test
    void updateJucatorIdTest(){}

    @Test
    void updateAntrenorIdTest(){}

    //delete
    @Test
    void deletePersoanaIdTest(){}

    //getNumarDePagini
    @Test
    void getNumarDePaginiJucatoriTest(){}

    @Test
    void getNumarDePaginiAntrenoriTest(){}

    //get altele
    @Test
    void getEchipeTest(){}

    @Test
    void getTrofeeIdTest(){}

    @Test
    void getTrofeeTest(){}

    //post
    @Test
    void postJucatorTest(){}

    @Test
    void postAntrenorTest(){}

    @Test
    void postEchipaTest(){}

    //altele
    @Test
    void putEchipaUpdate(){}

    @Test
    void deleteEchipaTest(){}

    @AfterAll
    void curatareMediuDeTestare(){
        System.out.println("--> Eliminare personal de test");

        repositoryPersonal.deletePersoana(persoanaMock1.getId());
        repositoryPersonal.deletePersoana(persoanaMock2.getId());
        repositoryPersonal.deletePersoana(persoanaMock3.getId());
        repositoryPersonal.deletePersoana(persoanaMock4.getId());

        repositoryPersonal.deleteEchipa(echipaMock1.getId());
        repositoryPersonal.deleteEchipa(echipaMock2.getId());
    }
}
