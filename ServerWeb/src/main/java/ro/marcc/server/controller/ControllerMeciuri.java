package ro.marcc.server.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ro.marcc.server.dto.CampionatDto;
import ro.marcc.server.dto.Meci.*;
import ro.marcc.server.dto.LocalitateDto;
import ro.marcc.server.dto.PaginareDto;
import ro.marcc.server.model.Localitate.Localitate;
import ro.marcc.server.model.Meciuri.Campionat;
import ro.marcc.server.model.Meciuri.Divizie;
import ro.marcc.server.model.Meciuri.Meci;
import ro.marcc.server.service.ServicesCampionat;
import ro.marcc.server.service.ServicesDivizii;
import ro.marcc.server.service.ServicesLocatie;
import ro.marcc.server.service.ServicesMeciuri;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Validated
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/meciuri")
public class ControllerMeciuri {
    private final ServicesMeciuri servicesMeciuri;
    private final ServicesDivizii servicesDivizii;
    private final ServicesLocatie servicesLocatie;
    private final ServicesCampionat servicesCampionat;

    public ControllerMeciuri(ServicesMeciuri servicesMeciuri, ServicesDivizii servicesDivizii, ServicesLocatie servicesLocatie, ServicesCampionat servicesCampionat) {
        this.servicesMeciuri = servicesMeciuri;
        this.servicesDivizii = servicesDivizii;
        this.servicesLocatie = servicesLocatie;
        this.servicesCampionat = servicesCampionat;
    }

    @PostMapping("")
    public RezultatFiltrareMeciuriDto getMeciuri(@RequestBody PaginareDto paginareDto, HttpServletRequest request){
        return servicesMeciuri.getMeciuri(paginareDto);
    }

    @GetMapping("/anterior")
    public MeciAnteriorDto getMeciAnterior(HttpServletRequest request){
        return servicesMeciuri.getMeciAnterior();
    }
    @GetMapping("/viitor")
    public MeciViitorDto getMeciViitor(HttpServletRequest request){
        return servicesMeciuri.getMeciViitor();
    }

    @GetMapping("/{id_meci}")
    public Meci getMeci(@PathVariable Integer id_meci,HttpServletRequest request){
        return servicesMeciuri.getMeci(id_meci);
    }


    @PostMapping("/administrare")
    public boolean postMeciuri(@RequestBody MeciAddDto meci, HttpServletRequest request){

        return servicesMeciuri.postMeci(new Meci(meci));
    }
    @PutMapping("/administrare/{id_meci}")
    public boolean putMeci(@PathVariable(name="id_meci") Integer idMeci,@Valid @RequestBody MeciAddDto meciActualizat, HttpServletRequest request){

        return servicesMeciuri.updateMeci(idMeci,new Meci(meciActualizat));
    }

    @DeleteMapping("/administrare/{id_meci}")
    public boolean deleteMeci(@PathVariable(name="id_meci") Integer idMeci, HttpServletRequest request){
        return servicesMeciuri.deleteMeci(idMeci);
    }
    @GetMapping("/divizii")
    public List<Divizie> getDivizii(HttpServletRequest request){
        return servicesDivizii.getDivizii();
    }

    @GetMapping("/divizii/{id_divizie}")
    public Divizie getDivizie(@PathVariable(name="id_divizie") Integer idDivizie){
        return servicesDivizii.getDivizie(idDivizie);
    }
    @PostMapping("/divizii/administrare")
    public boolean postDivizie(@NotBlank @RequestBody String numeDivizie){
        return servicesDivizii.postDivizie(new Divizie(null,numeDivizie));
    }

    @PutMapping("/divizii/administrare/{id_divizie}")
    public boolean putDivizie(@PathVariable(name="id_divizie") Integer idDivizie,@RequestBody String numeDivizie){
        return servicesDivizii.putDivizie(idDivizie,new Divizie(null,numeDivizie));
    }
    @DeleteMapping("/divizii/administrare/{id_divizie}")
    public boolean deleteDivizie(@PathVariable(name="id_divizie") Integer idDivizie){
        return servicesDivizii.deleteDivizie(idDivizie);
    }

    @GetMapping("/campionate")
    public List<Campionat> getCampionate(){
        return servicesCampionat.getCampionate();
    }
    @GetMapping("/campionate/{id_campionat}")
    public Campionat getCampionat(@PathVariable(name="id_campionat") Integer idCampionat){
        return servicesCampionat.getCampionat(idCampionat);
    }
    @PostMapping("/campionate/administrare")
    public boolean postCampionat(@Valid @RequestBody CampionatDto campionatDto){
        return servicesCampionat.postCampionat(new Campionat(campionatDto));
    }

    @PutMapping("/campionate/administrare/{id_campionat}")
    public boolean putCampionat(@PathVariable(name="id_campionat") Integer idCampionat,@Valid @RequestBody CampionatDto campionatDto){
        return servicesCampionat.putCampionat(idCampionat,new Campionat(campionatDto));
    }
    @DeleteMapping("/campionate/administrare/{id_campionat}")
    public boolean deleteCampionat(@PathVariable(name="id_campionat") Integer idCampionat){
        return servicesCampionat.deleteCampionat(idCampionat);
    }
    @GetMapping("/localitati")
    public List<Localitate> getLocalitati(){
        return servicesLocatie.getLocalitati();
    }
    @GetMapping("/localitati/{id_localitate}")
    public Localitate getLocalitate(@PathVariable(name="id_localitate") Integer idLocalitate){
        return servicesLocatie.getLocalitate(idLocalitate);
    }
    @PostMapping("/localitati/administrare")
    public boolean postLocalitate(@Valid @RequestBody LocalitateDto localitateDto){
        return servicesLocatie.postLocalitate(new Localitate(localitateDto));
    }

    @PutMapping("/localitati/administrare/{id_localitate}")
    public boolean putLocalitate(@PathVariable(name="id_localitate") Integer idLocalitate,@RequestBody LocalitateDto localitateDto){
        return servicesLocatie.putLocalitate(idLocalitate,new Localitate(localitateDto));
    }
    @DeleteMapping("/localitati/administrare/{id_localitate}")
    public boolean deleteLocalitate(@PathVariable(name="id_localitate") Integer idLocalitate){
        return servicesLocatie.deleteLocalitate(idLocalitate);
    }

    @PostMapping("/divizie/filtrare/{id_divizie}")
    public RezultatFiltrareMeciuriDto getMeciuri(@PathVariable(name="id_divizie") Integer idDivizie, @RequestBody PaginareDto paginareDto, HttpServletRequest request){
        return servicesMeciuri.getMeciuriDupaIdDivizie(idDivizie,paginareDto);
    }

    @PostMapping("/filtrare")
    public RezultatPrevizualizareMeciuriDto getMeciuri(@Valid @RequestBody FiltruCalendarMeci filtruCalendarMeci){
        return servicesMeciuri.getMeciuriFiltrate(filtruCalendarMeci);
    }


}
