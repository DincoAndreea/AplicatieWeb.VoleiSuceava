package ro.marcc.server.service;

import org.springframework.stereotype.Service;
import ro.marcc.server.dto.Meci.*;
import ro.marcc.server.dto.PaginareDto;
import ro.marcc.server.exception.MeciExpectationFailedException;
import ro.marcc.server.exception.MeciNotFoundException;
import ro.marcc.server.model.Meciuri.Echipa;
import ro.marcc.server.model.Meciuri.Meci;
import ro.marcc.server.repository.RepositoryMeciuri;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServicesMeciuri {
    private final RepositoryMeciuri repositoryMeciuri;

    public ServicesMeciuri(RepositoryMeciuri repositoryMeciuri) {
        this.repositoryMeciuri = repositoryMeciuri;
    }

    public RezultatFiltrareMeciuriDto getMeciuri(PaginareDto paginareDto){
        List<Meci> meciuri =  repositoryMeciuri.getMeciuri(true);
        if(paginareDto == null){
            throw new MeciExpectationFailedException("GET. Preluarea meciurilor necesita un obiect de tip Paginare care nu este null!");
        }

        int numarPagini =  meciuri.size()% paginareDto.getNumarElemente()==0?Math.max(0,meciuri.size()/ paginareDto.getNumarElemente()-1):Math.max(0,meciuri.size()/ paginareDto.getNumarElemente());

        List<Meci> rezultat = new ArrayList<>();

        int nrMeciuri = meciuri.size();
        int nrMeciuriAdaugate = 0;

        for(int indexMeci = paginareDto.getNumarPagina()* paginareDto.getNumarElemente();
            indexMeci <nrMeciuri && nrMeciuriAdaugate< paginareDto.getNumarElemente();
            indexMeci++,nrMeciuriAdaugate++){
            rezultat.add(meciuri.get(indexMeci));
        }

        return new RezultatFiltrareMeciuriDto(numarPagini,rezultat.size(),rezultat);
    }

    public List<Meci> getMeciuri(Echipa echipa){
        return repositoryMeciuri.getMeciuri(echipa);
    }
    public Meci getMeci(Integer idMeci){
        return repositoryMeciuri.getMeci(idMeci);
    }

    public boolean postMeci(Meci meci){
        return repositoryMeciuri.addMeci(meci);
    }

    public boolean updateMeci(Integer idMeci,Meci meciActualizat){
        return repositoryMeciuri.updateMeci(idMeci,meciActualizat);
    }

    public boolean deleteMeci(Integer idMeci){
        return repositoryMeciuri.deleteMeci(idMeci);
    }


    public RezultatFiltrareMeciuriDto getMeciuriDupaIdDivizie(Integer idDivizie, PaginareDto paginareDto) {
        List<Meci> meciuri = repositoryMeciuri.getMeciuriDupaDivizieSiDataMeci(idDivizie,null);
        if(paginareDto == null){
            throw new MeciExpectationFailedException("GET. Preluarea meciurilor necesita un obiect de tip Paginare care nu este null!");
        }

        int numarPagini =  meciuri.size()% paginareDto.getNumarElemente()==0?Math.max(0,meciuri.size()/ paginareDto.getNumarElemente()-1):Math.max(0,meciuri.size()/ paginareDto.getNumarElemente());

        List<Meci> rezultat = new ArrayList<>();

        int nrMeciuri = meciuri.size();
        int nrMeciuriAdaugate = 0;

        for(int indexMeci = paginareDto.getNumarPagina()* paginareDto.getNumarElemente();
            indexMeci <nrMeciuri && nrMeciuriAdaugate< paginareDto.getNumarElemente();
            indexMeci++,nrMeciuriAdaugate++){
            rezultat.add(meciuri.get(indexMeci));
        }

        return new RezultatFiltrareMeciuriDto(numarPagini, rezultat.size(), rezultat);
    }

    public RezultatPrevizualizareMeciuriDto getMeciuriFiltrate(FiltruCalendarMeci filtruCalendarMeci) {
        List<Meci> meciuri;
        if(filtruCalendarMeci.getIdDivizie()==null){
            meciuri = repositoryMeciuri.getMeciuri(true);
        }else{
            meciuri = repositoryMeciuri.getMeciuriDupaDivizieSiDataMeci(filtruCalendarMeci.getIdDivizie(),null);
        }

        if(filtruCalendarMeci.getTipMeci()!=null) {
            if (filtruCalendarMeci.getTipMeci().equals("A")) {
                meciuri = meciuri.stream()
                        .filter(m -> m.getDataMeci().compareTo(LocalDateTime.now()) < 0)
                        .filter(m->m.getScor()[0]!=-1 && m.getScor()[1]!=-1)
                        .collect(Collectors.toList());
            } else if (filtruCalendarMeci.getTipMeci().equals("V")) {
                meciuri = meciuri.stream()
                        .filter(m->m.getDataMeci().compareTo(LocalDateTime.now())>0)
                        .filter(m->m.getScor()[0]==-1 || m.getScor()[1]==-1)
                        .collect(Collectors.toList());
            } else if (filtruCalendarMeci.getTipMeci().equals("L")) {

                meciuri = meciuri.stream()
                        .filter(m -> m.getDataMeci().compareTo(LocalDateTime.now()) <= 0)
                        .filter(m -> m.getScor()[0] == -1 || m.getScor()[1] == -1)
                        .collect(Collectors.toList());
            }
        }
        if(filtruCalendarMeci.getDataIncepere()!=null && filtruCalendarMeci.getDataSfarsit()!=null) {
            meciuri = meciuri.stream()
                    .filter(m -> m.getDataMeci().toLocalDate().compareTo(filtruCalendarMeci.getDataIncepere()) >= 0)
                    .filter(m -> m.getDataMeci().toLocalDate().compareTo(filtruCalendarMeci.getDataSfarsit()) <= 0)
                    .collect(Collectors.toList());
        }

        if(filtruCalendarMeci.getPaginareDto() == null){
            throw new MeciExpectationFailedException("GET. Preluarea meciurilor necesita un obiect de tip Paginare in filtru care nu este null!");
        }

        PaginareDto paginareDto = filtruCalendarMeci.getPaginareDto();
        int numarPagini =  meciuri.size()% paginareDto.getNumarElemente()==0?Math.max(0,meciuri.size()/ paginareDto.getNumarElemente()-1):Math.max(0,meciuri.size()/ paginareDto.getNumarElemente());

        List<MeciPrevizualizareDto> rezultat = new ArrayList<>();

        int nrMeciuri = meciuri.size();
        int nrMeciuriAdaugate = 0;

        for(int indexMeci = paginareDto.getNumarPagina()* paginareDto.getNumarElemente();
            indexMeci <nrMeciuri && nrMeciuriAdaugate< paginareDto.getNumarElemente();
            indexMeci++,nrMeciuriAdaugate++){
            rezultat.add(new MeciPrevizualizareDto(meciuri.get(indexMeci)));
        }
        return new RezultatPrevizualizareMeciuriDto(numarPagini,rezultat.size(),rezultat);
    }
    public MeciAnteriorDto getMeciAnterior(){
        List<Meci> meciuri = repositoryMeciuri.getMeciuri(true);
        Optional<Meci> meciAnterior = meciuri.stream()
                .filter(m->m.getDataMeci().compareTo(LocalDateTime.now())<0)
                .filter(m->m.getScor()[0]!=-1 && m.getScor()[1]!=-1)
                .findFirst();
        if(meciAnterior.isPresent()){
            return new MeciAnteriorDto(meciAnterior.get());
        }
        throw new MeciNotFoundException("GET. Meciul anterior nu a putut fi gasit!");
    }

    public MeciViitorDto getMeciViitor(){
        List<Meci> meciuri = repositoryMeciuri.getMeciuri(false);
        Optional<Meci> meciViitor = meciuri.stream()
                .filter(m->m.getDataMeci().compareTo(LocalDateTime.now())>0)
                .filter(m->m.getScor()[0]==-1 || m.getScor()[1]==-1)
                .findFirst();
        if(meciViitor.isPresent()){
            return new MeciViitorDto(meciViitor.get());
        }
        throw new MeciNotFoundException("GET. Meciul viitor nu a putut fi gasit!");

    }



}
