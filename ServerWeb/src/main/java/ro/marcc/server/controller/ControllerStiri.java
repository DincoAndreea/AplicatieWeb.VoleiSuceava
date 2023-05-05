package ro.marcc.server.controller;

import org.springframework.web.bind.annotation.*;
import ro.marcc.server.dto.PaginareDto;
import ro.marcc.server.dto.Stire.RezultatFiltrareStiri;
import ro.marcc.server.dto.Stire.StireDto;
import ro.marcc.server.model.Stire;
import ro.marcc.server.service.ServicesStiri;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/stiri")
public class ControllerStiri {
    private final ServicesStiri servicesStiri;

    public ControllerStiri(ServicesStiri servicesStiri) {
        this.servicesStiri = servicesStiri;
    }

    @GetMapping("/postate")
    public List<Stire> getStiriPostate(){
        return servicesStiri.getStiri(true,false);
    }

    @GetMapping("/programate")
    public List<Stire> getStiriProgramate(){
        return servicesStiri.getStiri(false,false);
    }
    @GetMapping("/postate/media/{tip_media}")
    public List<Stire> getStiriPostate(@PathVariable(name="tip_media") Character tipMedia){
        return servicesStiri.getStiri(true,tipMedia,false);
    }
    @GetMapping("/programate/media/{tip_media}")
    public List<Stire> getStiriProgramate(@PathVariable(name="tip_media") Character tipMedia){
        return servicesStiri.getStiri(true,tipMedia,false);
    }

    @GetMapping("/draft")
    public List<Stire> getStiriDraft(){
        return servicesStiri.getStiri(null,true);
    }
    @GetMapping("/draft/media/{tip_media}")
    public List<Stire> getStiriDraft(@PathVariable(name="tip_media") Character tipMedia){
        return servicesStiri.getStiri(null,tipMedia,true);
    }
    @PostMapping("/filtru/{tip_stire}/{tip_media}/{tip_stire_cronologic}")
    public RezultatFiltrareStiri getStiri(@PathVariable(name="tip_stire") Integer tipStire, @PathVariable(name="tip_media") Character tipMedia, @PathVariable(name="tip_stire_cronologic") Integer tipStireCronologic, @RequestBody PaginareDto paginareDto){
        /**
         * tip_stire: 0- Publicat
         *            1- Programat
         *            2- Draft
         * tip_media: i- doar imagini
         *            v- doar videocliopuri
         *            t- doar text
         * tip_stire_cronologic: 0- Ieri
         *                       1- Ultimele 7 zile
         *                       2- Ultimele 28 de zile
         *                       3- Ultimele 30 de zile
         *                       4- Luna curenta
         *                       5- Anul curent
         *                       6- Altele
         *-------------------------------------------------
         */

        return servicesStiri.getStiri(tipStire,tipMedia,tipStireCronologic, paginareDto);
    }



    @GetMapping("/{id}")
    public Stire getStire(@PathVariable int id){
        return servicesStiri.getStire(id);
    }



    @PostMapping("/postate/paginare")
    public List<StireDto> getStiriPostate(@RequestBody PaginareDto paginareDto){
        return servicesStiri.getStiri(true,paginareDto);
    }

    @PostMapping("/programate/paginare")
    public List<StireDto> getStiriProgramate(@RequestBody PaginareDto paginareDto){
        return servicesStiri.getStiri(false,paginareDto);
    }


    @GetMapping("/postate/paginare/{numar_elemente}")
    public int getNumarDePaginiStiriPostate(@PathVariable(name="numar_elemente") int numarElemente){
        return servicesStiri.getNumarDePagini(true,numarElemente);
    }

    @GetMapping("/programate/paginare/{numar_elemente}")
    public int getNumarDePaginiStiriProgramate(@PathVariable(name="numar_elemente") int numarElemente){
        return servicesStiri.getNumarDePagini(false,numarElemente);
    }

    @PostMapping("/administrare")
    public boolean postStire(@RequestBody Stire stire){

        stire.setId(null);
        return servicesStiri.postStire(stire);
    }

    @DeleteMapping("/administrare/{id}")
    public boolean deleteStire(@PathVariable int id){
        return servicesStiri.deleteStire(id);
    }

    @DeleteMapping("/administrare")
    public boolean deleteStire(@RequestBody String titlu){return servicesStiri.deleteStire(titlu);}


    @PutMapping("/administrare/{id}")
    public boolean putStire(@PathVariable int id, @RequestBody Stire stire){
        return servicesStiri.updateStire(id,stire);
    }

}
