package ro.marcc.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ro.marcc.server.dto.Utilizator.CreatorDeContinutAddDto;
import ro.marcc.server.dto.Utilizator.UtilizatorLoginDto;
import ro.marcc.server.dto.Utilizator.UtilizatorPreviewDto;
import ro.marcc.server.model.Utilizator.Utilizator;
import ro.marcc.server.service.ServicesUtilizator;

import java.util.List;


@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/utilizatori")
public class ControllerUtilizatori {
    private final ServicesUtilizator servicesUtilizator;

    public ControllerUtilizatori(ServicesUtilizator servicesUtilizator) {
        this.servicesUtilizator = servicesUtilizator;
    }
    @GetMapping("")
    public List<UtilizatorPreviewDto> getUtilizatori(){
        return servicesUtilizator.getCreatoriDeContinut();
    }

    @PostMapping("/logare")
    public Character authUtilziator(@RequestBody UtilizatorLoginDto utilizator){
        return servicesUtilizator.autentificare(utilizator);
    }


    @PostMapping("/inregistrare")
    public boolean inregistrareCreatorDeContinut(@RequestBody CreatorDeContinutAddDto creatorDeContinutAddDto){
        return servicesUtilizator.inregistrareCreatorDeContinut(creatorDeContinutAddDto.getNume(), creatorDeContinutAddDto.getNumeUtilizator(), creatorDeContinutAddDto.getParola());
    }

    @DeleteMapping("/administrare/{id_utilizator}")
    public boolean deleteUtilizator(@PathVariable(name="id_utilizator")Integer idUtilizator){
        return servicesUtilizator.deleteCreatorDeContinut(idUtilizator);
    }


}