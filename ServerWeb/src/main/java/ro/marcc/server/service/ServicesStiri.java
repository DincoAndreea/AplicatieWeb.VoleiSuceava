package ro.marcc.server.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import ro.marcc.server.dto.PaginareDto;
import ro.marcc.server.dto.Stire.RezultatFiltrareStiri;
import ro.marcc.server.dto.Stire.StireAdministrareDto;
import ro.marcc.server.dto.Stire.StireDto;
import ro.marcc.server.exception.StireExpectationFailedException;
import ro.marcc.server.model.Stire;
import ro.marcc.server.repository.RepositoryStiri;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicesStiri {

    private final RepositoryStiri repositoryStiri;

    public ServicesStiri(RepositoryStiri repositoryStiri) {
        this.repositoryStiri = repositoryStiri;
    }

    public List<Stire> getStiri(Boolean postate, boolean draft){
        return repositoryStiri.getStiri(postate,draft);
    }
    public List<Stire> getStiri(Boolean postate,Character tipStire, boolean draft){
        return repositoryStiri.getStiri(postate,draft).stream().filter(s->s.tipMedia().equals(tipStire)).collect(Collectors.toList());
    }

    public RezultatFiltrareStiri getStiri(Integer tipStire, Character tipMedia, Integer tipStireCronologic, PaginareDto paginareDto){
        /**
         * tipStire: 0- Publicat
         *           1- Programat
         *           2- Draft
         * tipMedia: i- doar imagini
         *           v- doar videocliopuri
         *           t- doar text
         * tipStireCronologic: 0- Ieri
         *                     1- Ultimele 7 zile
         *                     2- Ultimele 28 de zile
         *                     3- Ultimele 30 de zile
         *                     4- Luna curenta
         *                     5- Anul curent
         *                     6- Altele
         *-------------------------------------------------
         */

        if(paginareDto.getNumarElemente()<1){
            throw new StireExpectationFailedException("GET. Numarul de elemente pe pagina trebuie sa fie cel putin 1!");
        }
        LocalDate dataCurenta = LocalDate.now();

        List<StireAdministrareDto> stiriFiltrate = repositoryStiri.getStiri(null,tipStire==2).stream()
                .filter(s->s.tipMedia().equals(tipMedia)
                        && (tipStire==0?dataCurenta.compareTo(s.getDataPostare())>=0:tipStire==1?dataCurenta.compareTo(s.getDataPostare())<0:true))
                .map(StireAdministrareDto::new)
                .collect(Collectors.toList());

        if(tipStireCronologic==0){
            stiriFiltrate = stiriFiltrate.stream()
                    .filter(s->
                        dataCurenta.minusDays(1).equals(s.getDataPostare())
                    )
                    .collect(Collectors.toList());
        }else if(tipStireCronologic==1){
            stiriFiltrate = stiriFiltrate.stream()
                    .filter(s->
                            dataCurenta.minusDays(7).compareTo(s.getDataPostare())<0
                    )
                    .collect(Collectors.toList());
        }else if(tipStireCronologic==2){
            stiriFiltrate = stiriFiltrate.stream()
                    .filter(s->
                            dataCurenta.minusDays(28).compareTo(s.getDataPostare())<0
                    )
                    .collect(Collectors.toList());
        }else if(tipStireCronologic==3){
            stiriFiltrate = stiriFiltrate.stream()
                    .filter(s->
                            dataCurenta.minusDays(30).compareTo(s.getDataPostare())<0
                    )
                    .collect(Collectors.toList());
        }else if(tipStireCronologic==4){
            stiriFiltrate = stiriFiltrate.stream()
                    .filter(s->
                            dataCurenta.getYear()==s.getDataPostare().getYear()
                                    && dataCurenta.getMonth()==s.getDataPostare().getMonth()
                    )
                    .collect(Collectors.toList());
        }else if(tipStireCronologic==5){
            stiriFiltrate = stiriFiltrate.stream()
                    .filter(s->
                            dataCurenta.getYear()==s.getDataPostare().getYear()
                    )
                    .collect(Collectors.toList());
        }else if(tipStireCronologic==6){

            if(paginareDto == null || paginareDto.getDataStart()==null || paginareDto.getDataSfarsit()==null){
                throw new StireExpectationFailedException("POST. Preluarea stirilor filtrate necesita un obiect de tip Paginare care nu este null!");
            }
            stiriFiltrate = stiriFiltrate.stream()
                    .filter(s->
                            paginareDto.getDataStart().compareTo(s.getDataPostare())<=0
                                    && s.getDataPostare().compareTo(paginareDto.getDataSfarsit())<=0
                    )
                    .collect(Collectors.toList());
        }


        int numarPagini =  stiriFiltrate.size()% paginareDto.getNumarElemente()==0?Math.max(0,stiriFiltrate.size()/ paginareDto.getNumarElemente()-1):Math.max(0,stiriFiltrate.size()/ paginareDto.getNumarElemente());

        List<StireAdministrareDto> rezultat = new ArrayList<>();

        int nrStiri = stiriFiltrate.size();
        int nrStiriAdaugate = 0;

        for(int indexStire = paginareDto.getNumarPagina()* paginareDto.getNumarElemente();
            indexStire<nrStiri && nrStiriAdaugate< paginareDto.getNumarElemente();
            indexStire++,nrStiriAdaugate++){
            rezultat.add(stiriFiltrate.get(indexStire));
        }


        return new RezultatFiltrareStiri(numarPagini,rezultat);

    }
    public List<StireDto> getStiri(Boolean postate,PaginareDto paginareDto){
        List<Stire> stiri = repositoryStiri.getStiri(postate,false);
        List<Stire> rezultat = new ArrayList<>();

        int nrStiri = stiri.size();
        int nrStiriAdaugate = 0;

        for(int indexStire = paginareDto.getNumarPagina()*paginareDto.getNumarElemente();
            indexStire<nrStiri && nrStiriAdaugate< paginareDto.getNumarElemente();
            indexStire++,nrStiriAdaugate++){
            rezultat.add(stiri.get(indexStire));
        }

        return rezultat.stream().map(u->new StireDto(u)).collect(Collectors.toList());
    }
    public Stire getStire(int id){
        return repositoryStiri.getStire(id);
    }

    public boolean postStire(Stire stire){
        try {
            return repositoryStiri.addStire(stire);
        }catch(UnexpectedRollbackException e){
            throw new StireExpectationFailedException("POST. Stirea nu a putut fi inregistrata!");
        }
    }

    public boolean deleteStire(int id){
        return repositoryStiri.deleteStire(id);
    }

    public boolean deleteStire(String titlu){
        return repositoryStiri.deleteStire(titlu);
    }
    public boolean updateStire(int id,Stire stire){
        try {
            return repositoryStiri.updateStire(id, stire);
        }
        catch(UnexpectedRollbackException e){
                throw new StireExpectationFailedException("PUT. Stirea nu a putut fi actualizata!");
            }
    }

    public int getNumarDePagini(Boolean postate,int numarDeElementePePagina){
        if(numarDeElementePePagina<1){
            throw new StireExpectationFailedException("GET. Numarul de elemente pe pagina trebuie sa fie cel putin 1!");
        }
        int numarStiri = repositoryStiri.getStiri(postate,false).size();

        return numarStiri%numarDeElementePePagina==0?Math.max(0,numarStiri/numarDeElementePePagina-1):Math.max(0,numarStiri/numarDeElementePePagina);
    }

}
