package ro.marcc.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.marcc.server.model.VoleiJuvenil.Cadeti.Cadeti;
import ro.marcc.server.model.VoleiJuvenil.Juniori.Juniori;
import ro.marcc.server.model.VoleiJuvenil.Minivolei.Minivolei;
import ro.marcc.server.model.VoleiJuvenil.Sperante.Sperante;
import ro.marcc.server.service.ServicesVoleiJuvenil;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/voleijuvenil")
public class ControllerVoleiJuvenil {
    private final ServicesVoleiJuvenil servicesVoleiJuvenil;

    public ControllerVoleiJuvenil(ServicesVoleiJuvenil servicesVoleiJuvenil) {
        this.servicesVoleiJuvenil = servicesVoleiJuvenil;
    }

    @GetMapping("/cadeti")
    public List<Cadeti> getCadeti(){
        return servicesVoleiJuvenil.getCadeti();
    }

    @GetMapping("/cadeti/{id}")
    public Cadeti getCadeti(@PathVariable Integer id){
        return servicesVoleiJuvenil.getCadeti(id);
    }

    @PostMapping("/cadeti/administrare")
    public boolean postCadeti(@RequestBody Cadeti cadeti){
        cadeti.setId(null);
        return servicesVoleiJuvenil.addCadeti(cadeti);
    }
    @PutMapping("/cadeti/administrare/{id}")
    public boolean postCadeti(@PathVariable int id,@RequestBody Cadeti cadetiActualizati){
        return servicesVoleiJuvenil.updateCadeti(id,cadetiActualizati);
    }
    @DeleteMapping("/cadeti/administrare/{id}")
    public boolean deleteCadeti(@PathVariable Integer id){
        return servicesVoleiJuvenil.deleteCadeti(id);
    }

    @GetMapping("/juniori")
    public List<Juniori> getJuniori(){
        return servicesVoleiJuvenil.getJuniori();
    }

    @GetMapping("/juniori/{id}")
    public Juniori getJuniori(@PathVariable Integer id){
        return servicesVoleiJuvenil.getJuniori(id);
    }

    @PostMapping("/juniori/administrare")
    public boolean postJuniori(@RequestBody Juniori juniori){

        juniori.setId(null);
        return servicesVoleiJuvenil.addJuniori(juniori);
    }
    @PutMapping("/juniori/administrare/{id}")
    public boolean putJuniori(@PathVariable int id, @RequestBody Juniori junioriActualizati){
        return servicesVoleiJuvenil.updateJuniori(id,junioriActualizati);
    }

    @DeleteMapping("/juniori/administrare/{id}")
    public boolean deletetJuniori(@PathVariable Integer id){
        return servicesVoleiJuvenil.deleteJuniori(id);
    }

    @GetMapping("/minivolei")
    public List<Minivolei> getMinivolei(){
        return servicesVoleiJuvenil.getMinivolei();
    }

    @GetMapping("/minivolei/{id}")
    public Minivolei getMinivolei(@PathVariable Integer id){
        return servicesVoleiJuvenil.getMinivolei(id);
    }

    @PostMapping("/minivolei/administrare")
    public boolean postMinivolei(@RequestBody Minivolei minivolei){
        minivolei.setId(null);
        return servicesVoleiJuvenil.addMinivolei(minivolei);
    }

    @PutMapping("/minivolei/administrare/{id}")
    public boolean putMinivolei(@PathVariable int id,@RequestBody Minivolei minivoleiActualizat){
        return servicesVoleiJuvenil.updateMinivolei(id,minivoleiActualizat);
    }

    @DeleteMapping("/minivolei/administrare/{id}")
    public boolean deleteMinivolei(@PathVariable Integer id){
        return servicesVoleiJuvenil.deleteMinivolei(id);
    }

    @GetMapping("/sperante")
    public List<Sperante> getSperante(){
        return servicesVoleiJuvenil.getSperante();
    }

    @GetMapping("/sperante/{id}")
    public Sperante getSperante(@PathVariable Integer id){
        return servicesVoleiJuvenil.getSperante(id);
    }

    @PostMapping("/sperante/administrare")
    public boolean postSperante(@RequestBody Sperante sperante){
        sperante.setId(null);
        return servicesVoleiJuvenil.addSperante(sperante);
    }

    @PutMapping("/sperante/administrare/{id}")
    public boolean putSperante(@PathVariable int id,@RequestBody Sperante sperante){
        return servicesVoleiJuvenil.updateSperante(id,sperante);
    }

    @DeleteMapping("/sperante/administrare/{id}")
    public boolean deleteSperante(@PathVariable Integer id){
        return servicesVoleiJuvenil.deleteSperante(id);
    }

}
