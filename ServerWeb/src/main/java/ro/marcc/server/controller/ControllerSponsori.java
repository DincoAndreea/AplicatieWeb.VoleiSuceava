package ro.marcc.server.controller;

import org.springframework.web.bind.annotation.*;
import ro.marcc.server.model.Sponsor;
import ro.marcc.server.service.ServicesSponsor;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/sponsori")
public class ControllerSponsori {
    private final ServicesSponsor servicesSponsor;

    public ControllerSponsori(ServicesSponsor servicesSponsor) {
        this.servicesSponsor = servicesSponsor;
    }

    @GetMapping("")
    public List<Sponsor> getSponsori(HttpServletRequest request){
        return servicesSponsor.getSponsori();
    }

    @GetMapping("/imagini")
    public List<String> getLinkuriImagini(){
        return servicesSponsor.getLinkuriImagini();
    }
}
