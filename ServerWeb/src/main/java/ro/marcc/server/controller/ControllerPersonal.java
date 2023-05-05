package ro.marcc.server.controller;

import org.springframework.web.bind.annotation.*;
import ro.marcc.server.dto.PaginareDto;
import ro.marcc.server.dto.RezultatFiltrareAntrenoriDto;
import ro.marcc.server.dto.RezultatFiltrareJucatoriDto;
import ro.marcc.server.dto.SenioriDto;
import ro.marcc.server.model.Meciuri.Echipa;
import ro.marcc.server.model.Personal.Roluri.Antrenor;
import ro.marcc.server.model.Personal.Roluri.Jucator;
import ro.marcc.server.model.Realizari.Trofeu;
import ro.marcc.server.service.ServicesPersonal;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/personal")
public class ControllerPersonal {
    private final ServicesPersonal servicesPersonal;

    public ControllerPersonal(ServicesPersonal servicesPersonal) {
        this.servicesPersonal = servicesPersonal;
    }

    @GetMapping("/antrenori")
    public List<Antrenor> getAntrenori(HttpServletRequest request){
        return servicesPersonal.getAntrenori();
    }
    @GetMapping("/jucatori")
    public List<Jucator> getJucatori(HttpServletRequest request){
        return servicesPersonal.getJucatori((Integer) null);
    }
    @GetMapping("/seniori")
    public SenioriDto getSeniori(HttpServletRequest request){
        return servicesPersonal.getSeniori();
    }
    @GetMapping("/jucatori/{id_echipa}")
    public List<Jucator> getJucatori(@PathVariable(name="id_echipa") int idEchipa, HttpServletRequest request){
        return servicesPersonal.getJucatori(idEchipa);
    }

    @GetMapping("/echipe/jucatori")
    public List<Echipa> getEchipe(){
        return servicesPersonal.getEchipeAntrenori();
    }

    @GetMapping("/echipe/antrenori")
    public List<Echipa> getEchipeJucatori(){
        return servicesPersonal.getEchipeJucatori();
    }



    @GetMapping("/echipe/trofee/{id_echipa}")
    public List<Trofeu> getTrofee(@PathVariable(name="id_echipa") int idEchipa, HttpServletRequest request){
        return servicesPersonal.getTrofee(idEchipa);
    }

    @GetMapping("/echipe/trofee")
    public List<Trofeu> getTrofee(){
        return servicesPersonal.getTrofee();
    }

    @PostMapping("/echipe/administrare")
    public boolean postEchipa(@RequestBody Echipa echipa, HttpServletRequest request){
        echipa.setId(null);
        return servicesPersonal.postEchipa(echipa);
    }
    @PutMapping("/echipe/administrare/{id_echipa}")
    public boolean postEchipa(@PathVariable(name="id_echipa") int idEchipa, @RequestBody Echipa echipaActualizata, HttpServletRequest request){
        return servicesPersonal.putEchipa(idEchipa,echipaActualizata);
    }
    @PostMapping("/jucatori/filtrare/{valoare_de_filtrare}")
    public RezultatFiltrareJucatoriDto getJucatori(@PathVariable(name = "valoare_de_filtrare") String valoareDeFiltrare, @RequestBody PaginareDto paginareDto, HttpServletRequest request){
        return servicesPersonal.getJucatori(valoareDeFiltrare, paginareDto);
    }@PostMapping("/antrenori/filtrare/{valoare_de_filtrare}")
    public RezultatFiltrareAntrenoriDto getAntrenori(@PathVariable(name = "valoare_de_filtrare") String valoareDeFiltrare, @RequestBody PaginareDto paginareDto, HttpServletRequest request){
        return servicesPersonal.getAntrenori(valoareDeFiltrare, paginareDto);
    }

    @DeleteMapping("/echipe/administrare/{id_echipa}")
    public boolean deleteEchipa(@PathVariable(name="id_echipa") int idEchipa, HttpServletRequest request){
        return servicesPersonal.deleteEchipa(idEchipa);
    }
    @PostMapping("/antrenori/administrare")
    public boolean postAntrenor(@RequestBody Antrenor antrenor){

        antrenor.setId(null);
        return servicesPersonal.postAntrenor(antrenor);
    }

    @PostMapping("/jucatori/administrare")
    public boolean postJucatori(@RequestBody Jucator jucator){
        jucator.setId(null);
        return servicesPersonal.postJucator(jucator);
    }
    @PostMapping("/jucatori/paginare")
    public List<Jucator> getJucatori(@RequestBody PaginareDto paginareDto){
        return servicesPersonal.getJucatori(paginareDto);
    }


    @GetMapping("/jucatori/paginare/{numar_elemente}")
    public int getNumarDePaginiJucatori(@PathVariable(name="numar_elemente") int numarElemente){
        return servicesPersonal.getNumarDePaginiJucatori(numarElemente);
    }
    @PostMapping("/antrenori/paginare")
    public List<Antrenor> getAntrenori(@RequestBody PaginareDto paginareDto){
        return servicesPersonal.getAntrenori(paginareDto);
    }

    @GetMapping("/antrenori/paginare/{numar_elemente}")
    public int getNumarDePaginiAntrenorii(@PathVariable(name="numar_elemente") int numarElemente){
        return servicesPersonal.getNumarDePaginiAntrenori(numarElemente);
    }

    @PutMapping("/jucatori/administrare/{id}")
    public boolean putJucatori(@PathVariable int id,@RequestBody Jucator jucator){
        return servicesPersonal.updateJucator(id,jucator);
    }
    @PutMapping("/antrenori/administrare/{id}")
    public boolean putAntrenori(@PathVariable int id,@RequestBody Antrenor antrenor){
        return servicesPersonal.updateAntrenor(id,antrenor);
    }

    @DeleteMapping("/jucatori/administrare/{id}")
    public boolean deleteJucatori(@PathVariable int id){
        return servicesPersonal.deletePersoana(id);
    }
    @DeleteMapping("/antrenori/administrare/{id}")
    public boolean deleteAntrenori(@PathVariable int id){
        return servicesPersonal.deletePersoana(id);
    }

}
