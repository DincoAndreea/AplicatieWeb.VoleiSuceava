package ro.marcc.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.marcc.server.dto.PaginareDto;
import ro.marcc.server.dto.RezultatFiltrareAntrenoriDto;
import ro.marcc.server.dto.RezultatFiltrareJucatoriDto;
import ro.marcc.server.dto.SenioriDto;
import ro.marcc.server.exception.PersonalExpectationFailedException;
import ro.marcc.server.exception.PersonalNotFoundException;
import ro.marcc.server.model.Meciuri.Echipa;
import ro.marcc.server.model.Personal.Roluri.Antrenor;
import ro.marcc.server.model.Personal.Roluri.Jucator;
import ro.marcc.server.model.Realizari.Trofeu;
import ro.marcc.server.repository.RepositoryPersonal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
@Slf4j
@Service
public class ServicesPersonal {
    private final RepositoryPersonal repositoryPersonal;

    public ServicesPersonal(RepositoryPersonal repositoryPersonal) {
        this.repositoryPersonal = repositoryPersonal;
    }

    public List<Antrenor> getAntrenori(){
        List<Antrenor> antrenori = repositoryPersonal.getAntrenori(true);
        if(antrenori.size()==0){
            throw new PersonalNotFoundException("GET. Nu s-a putut prelua lista de antrenori!");
        }

        return antrenori;
    }
    public List<Antrenor> getAntrenori(PaginareDto paginareDto){
        if(paginareDto.getNumarElemente()<1){
            throw new PersonalExpectationFailedException("GET. Numarul de elemente pe pagina trebuie sa fie cel putin 1!");
        }
        List<Antrenor> antrenori = repositoryPersonal.getAntrenori(true);
        if(antrenori.size()==0){
            throw new PersonalNotFoundException("GET. Nu s-a putut prelua lista de antrenori!");
        }
        List<Antrenor> rezultat = new ArrayList<>();

        int nrAntrenori = antrenori.size();
        int nrAntrenoriAdaugati = 0;

        for(int indexAntrenor = paginareDto.getNumarPagina()* paginareDto.getNumarElemente();
            indexAntrenor < nrAntrenori && nrAntrenoriAdaugati < paginareDto.getNumarElemente();
            indexAntrenor++,nrAntrenoriAdaugati++){
            rezultat.add(antrenori.get(indexAntrenor));
        }

        return rezultat;
    }

    public List<Jucator> getJucatori(Integer idEchipa){
        List<Jucator> jucatori = repositoryPersonal.getjucatoriEchipa(idEchipa,true);
        if(jucatori.isEmpty()){
            throw new PersonalNotFoundException("GET. Nu s-a putut prelua lista de jucatori"+idEchipa==null?"":(" ai echipei cu id-ul "+idEchipa)+"!");
        }
        return  jucatori;
    }



    public SenioriDto getSeniori(){
        return repositoryPersonal.getSeniori();
    }
    public List<Jucator> getJucatori(PaginareDto paginareDto){
        if(paginareDto.getNumarElemente()<1){
            throw new PersonalExpectationFailedException("GET. Numarul de elemente pe pagina trebuie sa fie cel putin 1!");
        }
        List<Jucator> jucatori = repositoryPersonal.getjucatoriEchipa(null,true);
        if(jucatori.isEmpty()){
            throw new PersonalNotFoundException("GET. Nu s-a putut prelua lista de jucatori!");
        }

        List<Jucator> rezultat = new ArrayList<>();


        int nrJucatori = jucatori.size();
        int nrJucatoriAdaugati = 0;

        for(int indexJucator = paginareDto.getNumarPagina()* paginareDto.getNumarElemente();
            indexJucator < nrJucatori && nrJucatoriAdaugati < paginareDto.getNumarElemente();
            indexJucator++,nrJucatoriAdaugati++){
            rezultat.add(jucatori.get(indexJucator));
        }

        return rezultat;
    }

    public RezultatFiltrareJucatoriDto getJucatori(String valoareDeFiltrare, PaginareDto paginareDto){
        if(paginareDto.getNumarElemente()<1){
            throw new PersonalExpectationFailedException("GET. Numarul de elemente pe pagina trebuie sa fie cel putin 1!");
        }

        List<Jucator> jucatoriFiltrati =  repositoryPersonal.getJucatori(valoareDeFiltrare).stream().toList();

        int numarJucatoriFiltrati = jucatoriFiltrati.size();
        int numarPagini = numarJucatoriFiltrati % paginareDto.getNumarElemente() == 0 ? Math.max(0, numarJucatoriFiltrati / paginareDto.getNumarElemente() - 1) : Math.max(0, numarJucatoriFiltrati / paginareDto.getNumarElemente());



        Set<Jucator> rezultat = new LinkedHashSet<>();

        int nrJucatori = jucatoriFiltrati.size();
        int nrJucatoriAdaugati = 0;

        for(int indexJucator = paginareDto.getNumarPagina()* paginareDto.getNumarElemente();
            indexJucator<nrJucatori && nrJucatoriAdaugati< paginareDto.getNumarElemente();
            indexJucator++,nrJucatoriAdaugati++){
            rezultat.add(jucatoriFiltrati.get(indexJucator));
        }

        return new RezultatFiltrareJucatoriDto(numarPagini,rezultat.size(),rezultat);

    }
    public RezultatFiltrareAntrenoriDto getAntrenori(String valoareDeFiltrare, PaginareDto paginareDto){
        List<Antrenor> antrenoriFiltrati =  repositoryPersonal.getAntrenori(valoareDeFiltrare).stream().toList();

        int numarAntrenoriFiltrati = antrenoriFiltrati.size();
        int numarPagini = numarAntrenoriFiltrati % paginareDto.getNumarElemente() == 0 ? Math.max(0, numarAntrenoriFiltrati / paginareDto.getNumarElemente() - 1) : Math.max(0, numarAntrenoriFiltrati / paginareDto.getNumarElemente());


        Set<Antrenor> rezultat = new LinkedHashSet<>();

        int nrAntrenori = antrenoriFiltrati.size();
        int nrAntrenoriAdaugati = 0;

        for(int indexAntrenor = paginareDto.getNumarPagina()* paginareDto.getNumarElemente();
            indexAntrenor<nrAntrenori && nrAntrenoriAdaugati< paginareDto.getNumarElemente();
            indexAntrenor++,nrAntrenoriAdaugati++){
            rezultat.add(antrenoriFiltrati.get(indexAntrenor));
        }

        return new RezultatFiltrareAntrenoriDto(numarPagini,rezultat.size(),rezultat);
    }

    public boolean updateJucator(Integer idJucator,Jucator jucatorActualizat){
        return repositoryPersonal.updateJucator(idJucator,jucatorActualizat);
    }
    public boolean updateAntrenor(Integer idAntrenor,Antrenor antrenorActualizat){
        return repositoryPersonal.updateAntrenor(idAntrenor,antrenorActualizat);
    }

    public boolean deletePersoana(Integer idPersoana){
        return repositoryPersonal.deletePersoana(idPersoana);
    }

    public int getNumarDePaginiJucatori(int numarDeElementePePagina){
        if(numarDeElementePePagina<1){
            throw new PersonalExpectationFailedException("GET. Numarul de elemente pe pagina trebuie sa fie cel putin 1!");
        }
        int numarJucatori = repositoryPersonal.getjucatoriEchipa(null,true).size();

        return numarJucatori % numarDeElementePePagina == 0 ? Math.max(0, numarJucatori / numarDeElementePePagina - 1) : Math.max(0, numarJucatori / numarDeElementePePagina);
    }
    public int getNumarDePaginiAntrenori(int numarDeElementePePagina) {
        if (numarDeElementePePagina < 1) {
            throw new PersonalExpectationFailedException("GET. Numarul de elemente pe pagina trebuie sa fie cel putin 1!");
        }
        int numarAntrenori = repositoryPersonal.getAntrenori(true).size();
        return numarAntrenori % numarDeElementePePagina == 0 ? Math.max(0, numarAntrenori / numarDeElementePePagina - 1) : Math.max(0, numarAntrenori / numarDeElementePePagina);
    }
    public List<Echipa> getEchipeJucatori(){
        List<Echipa> echipe = repositoryPersonal.getEchipe(true,true);
        if(echipe.isEmpty()){
            throw new PersonalNotFoundException("GET. Nu s-a putut prelua lista de echipe!");
        }
        return echipe;
    }
    public List<Echipa> getEchipeAntrenori(){
        List<Echipa> echipe = repositoryPersonal.getEchipe(false,true);
        if(echipe.isEmpty()){
            throw new PersonalNotFoundException("GET. Nu s-a putut prelua lista de echipe!");
        }
        return echipe;
    }

    public List<Trofeu> getTrofee(int idEchipa){
        List<Trofeu> trofee = repositoryPersonal.getTrofee(idEchipa,true);
        if(trofee.isEmpty()){
            throw new PersonalNotFoundException("GET. Nu s-a putut prelua lista de trofee pentru echipa cu id-ul "+idEchipa+"!");
        }
        return trofee;
    }

    public List<Trofeu> getTrofee(){
        List<Trofeu> trofee = repositoryPersonal.getTrofee(null,true);
        if(trofee.isEmpty()){
            throw new PersonalNotFoundException("GET. Nu s-a putut prelua lista de trofee!");
        }
        return trofee;
    }

    public boolean postJucator(Jucator jucator){

        return repositoryPersonal.addJucator(jucator);
    }
    public boolean postAntrenor(Antrenor antrenor){
        return repositoryPersonal.addAntrenor(antrenor);
    }
    public boolean postEchipa(Echipa echipa){
        return repositoryPersonal.addEchipa(echipa);
    }
    public boolean putEchipa(Integer idEchipa,Echipa echipa){
        return repositoryPersonal.updateEchipa(idEchipa,echipa);
    }
    public boolean deleteEchipa(Integer idEchipa){
        return repositoryPersonal.deleteEchipa(idEchipa);
    }

}
