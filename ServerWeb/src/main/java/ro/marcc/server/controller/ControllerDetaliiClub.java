package ro.marcc.server.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.marcc.server.dto.DetaliiClubDto;
import ro.marcc.server.service.ServicesDetaliiClub;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/club")
public class ControllerDetaliiClub {
    private final ServicesDetaliiClub servicesDetaliiClub;

    public ControllerDetaliiClub(ServicesDetaliiClub servicesDetaliiClub) {
        this.servicesDetaliiClub = servicesDetaliiClub;
    }

    @GetMapping("/viziune")
    public DetaliiClubDto getViziuneClub(){
        return servicesDetaliiClub.getViziuneClub();
    }
    @GetMapping("/istoric")
    public DetaliiClubDto getIstoricClub(){
        return servicesDetaliiClub.getIstoricClub();
    }
    @GetMapping("/trofee")
    public DetaliiClubDto getTrofeeClub(){
        return servicesDetaliiClub.getTrofeeClub();
    }
}
