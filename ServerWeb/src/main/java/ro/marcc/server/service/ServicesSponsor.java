package ro.marcc.server.service;

import org.springframework.stereotype.Service;
import ro.marcc.server.exception.SponsorNotFoundException;
import ro.marcc.server.model.Sponsor;
import ro.marcc.server.repository.RepositorySponsori;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicesSponsor {
    private final RepositorySponsori repositorySponsori;

    public ServicesSponsor(RepositorySponsori repositorySponsori) {
        this.repositorySponsori = repositorySponsori;
    }

    public List<Sponsor> getSponsori(){
        List<Sponsor> sponsori = repositorySponsori.getSponsori();
        if(sponsori.isEmpty()){
            throw new SponsorNotFoundException("GET. Nu s-a putut accesa lista de sponsori.");
        }
        return sponsori;
    }
    public List<String> getLinkuriImagini(){
        List<String> linkuriImagini = new ArrayList<>();

        getSponsori().stream().limit(5).forEach(u-> linkuriImagini.add(u.getLogo()));

        return linkuriImagini;
    }
}
