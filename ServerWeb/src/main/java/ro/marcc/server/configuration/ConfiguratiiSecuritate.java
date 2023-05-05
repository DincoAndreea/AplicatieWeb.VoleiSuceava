package ro.marcc.server.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Slf4j
public class ConfiguratiiSecuritate {
    @Bean
    ServiceDetaliiUtilizator serviceDetaliiUtilizator(){
        return new ServiceDetaliiUtilizator();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(serviceDetaliiUtilizator());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .mvcMatchers("/api/utilizatori/logare").hasAuthority("autentificare")
                .mvcMatchers("/api/utilizatori/logare").hasAnyRole("a","c","v")

                .mvcMatchers("/api/utilizatori").hasAuthority("administrareUtilizatori")
                .mvcMatchers("/api/utilizatori").hasAnyRole("a")

                .mvcMatchers("/api/utilizatori/administrare/{id_utilizator}").hasAuthority("administrareUtilizatori")
                .mvcMatchers("/api/utilizatori/administrare/{id_utilizator}").hasAnyRole("a")

                .mvcMatchers("/api/utilizatori/inregistrare").hasAuthority("administrareUtilizatori")
                .mvcMatchers("/api/utilizatori/inregistrare").hasRole("a")

                .mvcMatchers("/api/sponsori").hasAuthority("getSponsori")
                .mvcMatchers("/api/sponsori").hasAnyRole("a","c","v")

                .mvcMatchers("/api/club/viziune").hasAuthority("autentificare")
                .mvcMatchers("/api/club/viziune").hasAnyRole("a","c","v")

                .mvcMatchers("/api/club/istoric").hasAuthority("autentificare")
                .mvcMatchers("/api/club/istoric").hasAnyRole("a","c","v")

                .mvcMatchers("/api/club/trofee").hasAuthority("autentificare")
                .mvcMatchers("/api/club/trofee").hasAnyRole("a","c","v")

                .mvcMatchers("/api/sponsori/imagini").hasAuthority("getSponsori")
                .mvcMatchers("/api/sponsori/imagini").hasAnyRole("a","c","v")

                .mvcMatchers("/api/stiri/postate").hasAuthority("getStiri")
                .mvcMatchers("/api/stiri/postate").hasAnyRole("a","c","v")

                .mvcMatchers("/api/stiri/programate").hasAuthority("getStiri")
                .mvcMatchers("/api/stiri/programate").hasAnyRole("a","c","v")

                .mvcMatchers("/api/stiri/draft").hasAuthority("getStiri")
                .mvcMatchers("/api/stiri/draft").hasAnyRole("a","c")

                .mvcMatchers("/api/stiri/postate/paginare").hasAuthority("getStiri")
                .mvcMatchers("/api/stiri/postate/paginare").hasAnyRole("a","c","v")

                .mvcMatchers("/api/stiri/programate/paginare").hasAuthority("getStiri")
                .mvcMatchers("/api/stiri/programate/paginare").hasAnyRole("a","c","v")

                .mvcMatchers("/api/stiri/postate/paginare/{numar_elemente}").hasAuthority("getStiri")
                .mvcMatchers("/api/stiri/postate/paginare/{numar_elemente}").hasAnyRole("a","c","v")

                .mvcMatchers("/api/stiri/programate/paginare/{numar_elemente}").hasAuthority("getStiri")
                .mvcMatchers("/api/stiri/programate/paginare/{numar_elemente}").hasAnyRole("a","c","v")

                .mvcMatchers("/api/stiri/{id}").hasAuthority("getStiri")
                .mvcMatchers("/api/stiri/{id}").hasAnyRole("a","c","v")

                .mvcMatchers("/api/stiri/postate/media/{tip_media}").hasAuthority("getStiri")
                .mvcMatchers("/api/stiri/postate/media/{tip_media}").hasAnyRole("a","c","v")

                .mvcMatchers("/api/stiri/programate/media/{tip_media}").hasAuthority("getStiri")
                .mvcMatchers("/api/stiri/programate/media/{tip_media}").hasAnyRole("a","c","v")

                .mvcMatchers("/api/stiri/filtru/{tip_stire}/{tip_media}/{tip_stire_cronologic}").hasAuthority("administrareStiri")
                .mvcMatchers("/api/stiri/filtru/{tip_stire}/{tip_media}/{tip_stire_cronologic}").hasAnyRole("a","c")

                .mvcMatchers("/api/stiri/draft/media/{tip_media}").hasAuthority("getStiri")
                .mvcMatchers("/api/stiri/draft/media/{tip_media}").hasAnyRole("a","c")

                .mvcMatchers("/api/stiri/administrare").hasAuthority("administrareStiri")
                .mvcMatchers("/api/stiri/administrare").hasAnyRole("a","c")

                .mvcMatchers("/api/stiri/administrare/{id}").hasAuthority("administrareStiri")
                .mvcMatchers("/api/stiri/administrare/{id}").hasAnyRole("a","c")


                .mvcMatchers("/api/personal/jucatori").hasAuthority("getPersonal")
                .mvcMatchers("/api/personal/jucatori").hasAnyRole("a","c","v")

                .mvcMatchers("/api/personal/jucatori/{id_echipa}").hasAuthority("getPersonal")
                .mvcMatchers("/api/personal/jucatori/{id_echipa}").hasAnyRole("a","c","v")

                .mvcMatchers("/api/personal/jucatori/paginare/{numar_elemente}").hasAuthority("getPersonal")
                .mvcMatchers("/api/personal/jucatori/paginare/{numar_elemente}").hasAnyRole("a","c","v")

                .mvcMatchers("/api/personal/jucatori/paginare").hasAuthority("getPersonal")
                .mvcMatchers("/api/personal/jucatori/paginare").hasAnyRole("a","c","v")

                .mvcMatchers("/api/personal/antrenori/paginare/{numar_elemente}").hasAuthority("getPersonal")
                .mvcMatchers("/api/personal/antrenori/paginare/{numar_elemente}").hasAnyRole("a","c","v")

                .mvcMatchers("/api/personal/antrenori/paginare").hasAuthority("getPersonal")
                .mvcMatchers("/api/personal/antrenori/paginare").hasAnyRole("a","c","v")

                .mvcMatchers("/api/personal/antrenori").hasAuthority("getPersonal")
                .mvcMatchers("/api/personal/antrenori").hasAnyRole("a","c","v")

                .mvcMatchers("/api/personal/seniori").hasAuthority("getPersonal")
                .mvcMatchers("/api/personal/seniori").hasAnyRole("a","c","v")

                .mvcMatchers("/api/personal/antrenori/administrare").hasAuthority("administrarePersonal")
                .mvcMatchers("/api/personal/antrenori/administrare").hasAnyRole("a","c")

                .mvcMatchers("/api/personal/jucatori/administrare").hasAuthority("administrarePersonal")
                .mvcMatchers("/api/personal/jucatori/administrare").hasAnyRole("a","c")

                .mvcMatchers("/api/personal/antrenori/administrare/{id}").hasAuthority("administrarePersonal")
                .mvcMatchers("/api/personal/antrenori/administrare/{id}").hasAnyRole("a","c")

                .mvcMatchers("/api/personal/jucatori/administrare/{id}").hasAuthority("administrarePersonal")
                .mvcMatchers("/api/personal/jucatori/administrare/{id}").hasAnyRole("a","c")

                .mvcMatchers("/api/personal/jucatori/filtrare/{valoare_de_filtrare}").hasAuthority("getPersonal")
                .mvcMatchers("/api/personal/jucatori/filtrare/{valoare_de_filtrare}").hasAnyRole("a","c","v")

                .mvcMatchers("/api/personal/antrenori/filtrare/{valoare_de_filtrare}").hasAuthority("getPersonal")
                .mvcMatchers("/api/personal/antrenori/filtrare/{valoare_de_filtrare}").hasAnyRole("a","c","v")

                .mvcMatchers("/api/personal/echipe").hasAuthority("getPersonal")
                .mvcMatchers("/api/personal/echipe").hasAnyRole("a","c","v")

                .mvcMatchers("/api/personal/echipe/administrare").hasAuthority("administrarePersonal")
                .mvcMatchers("/api/personal/echipe/administrare").hasAnyRole("a","c")

                .mvcMatchers("/api/personal/echipe/administrare/{id_echipa}").hasAuthority("administrarePersonal")
                .mvcMatchers("/api/personal/echipe/administrare/{id_echipa}").hasAnyRole("a","c")

                .mvcMatchers("/api/personal/echipe/trofee").hasAuthority("getPersonal")
                .mvcMatchers("/api/personal/echipe/trofee").hasAnyRole("a","c","v")

                .mvcMatchers("/api/personal/echipe/trofee/{id_echipa}").hasAuthority("getPersonal")
                .mvcMatchers("/api/personal/echipe/trofee/{id_echipa}").hasAnyRole("a","c","v")

                .mvcMatchers("/api/personal/antrenori/administrare/{id}").hasAuthority("administrarePersonal")
                .mvcMatchers("/api/personal/antrenori/administrare/{id}").hasAnyRole("a","c")

                .mvcMatchers("/api/personal/jucatori/administrare/{id}").hasAuthority("administrarePersonal")
                .mvcMatchers("/api/personal/jucatori/administrare/{id}").hasAnyRole("a","c")

                .mvcMatchers("/api/voleijuvenil/cadeti").hasAuthority("getPersonal")
                .mvcMatchers("/api/voleijuvenil/cadeti").hasAnyRole("a","c","v")

                .mvcMatchers("/api/voleijuvenil/cadeti/{id}").hasAuthority("getPersonal")
                .mvcMatchers("/api/voleijuvenil/cadeti/{id}").hasAnyRole("a","c","v")

                .mvcMatchers("/api/voleijuvenil/cadeti/administrare/{id}").hasAuthority("administrarePersonal")
                .mvcMatchers("/api/voleijuvenil/cadeti/administrare/{id}").hasRole("a")

                .mvcMatchers("/api/voleijuvenil/juniori").hasAuthority("getPersonal")
                .mvcMatchers("/api/voleijuvenil/juniori").hasAnyRole("a","c","v")

                .mvcMatchers("/api/voleijuvenil/juniori/{id}").hasAuthority("getPersonal")
                .mvcMatchers("/api/voleijuvenil/juniori/{id}").hasAnyRole("a","c","v")

                .mvcMatchers("/api/voleijuvenil/juniori/administrare/{id}").hasAuthority("administrarePersonal")
                .mvcMatchers("/api/voleijuvenil/juniori/administrare/{id}").hasRole("a")

                .mvcMatchers("/api/voleijuvenil/minivolei").hasAuthority("getPersonal")
                .mvcMatchers("/api/voleijuvenil/minivolei").hasAnyRole("a","c","v")

                .mvcMatchers("/api/voleijuvenil/minivolei/{id}").hasAuthority("getPersonal")
                .mvcMatchers("/api/voleijuvenil/minivolei/{id}").hasAnyRole("a","c","v")

                .mvcMatchers("/api/voleijuvenil/minivolei/administrare/{id}").hasAuthority("administrarePersonal")
                .mvcMatchers("/api/voleijuvenil/minivolei/administrare/{id}").hasRole("a")

                .mvcMatchers("/api/voleijuvenil/sperante").hasAuthority("getPersonal")
                .mvcMatchers("/api/voleijuvenil/sperante").hasAnyRole("a","c","v")

                .mvcMatchers("/api/voleijuvenil/sperante/{id}").hasAuthority("getPersonal")
                .mvcMatchers("/api/voleijuvenil/sperante/{id}").hasAnyRole("a","c","v")

                .mvcMatchers("/api/voleijuvenil/sperante/administrare/{id}").hasAuthority("administrarePersonal")
                .mvcMatchers("/api/voleijuvenil/sperante/administrare/{id}").hasRole("a")

                .mvcMatchers("/api/meciuri").hasAuthority("getPersonal")
                .mvcMatchers("/api/meciuri").hasAnyRole("a","c","v")

                .mvcMatchers("/api/meciuri/anterior").hasAuthority("getPersonal")
                .mvcMatchers("/api/meciuri/anterior").hasAnyRole("a","c","v")

                .mvcMatchers("/api/meciuri/viitor").hasAuthority("getPersonal")
                .mvcMatchers("/api/meciuri/viitor").hasAnyRole("a","c","v")

                .mvcMatchers("/api/meciuri/filtrare").hasAuthority("getPersonal")
                .mvcMatchers("/api/meciuri/filtrare").hasAnyRole("a","c","v")

                .mvcMatchers("/api/meciuri/administrare").hasAuthority("administrarePersonal")
                .mvcMatchers("/api/meciuri/administrare").hasAnyRole("a","c")

                .mvcMatchers("/api/meciuri/administrare/{id_meci}").hasAuthority("administrarePersonal")
                .mvcMatchers("/api/meciuri/administrare/{id_meci}").hasAnyRole("a","c")

                .mvcMatchers("/api/meciuri/{id_meci}").hasAuthority("getPersonal")
                .mvcMatchers("/api/meciuri/{id_meci}").hasAnyRole("a","c","v")

                .mvcMatchers("/api/meciuri/divizii").hasAuthority("getPersonal")
                .mvcMatchers("/api/meciuri/divizii").hasAnyRole("a","c","v")

                .mvcMatchers("/api/meciuri/divizii/administrare").hasAuthority("administrarePersonal")
                .mvcMatchers("/api/meciuri/divizii/administrare").hasAnyRole("a","c")

                .mvcMatchers("/api/meciuri/divizii/administrare/{id_divizie}").hasAuthority("administrarePersonal")
                .mvcMatchers("/api/meciuri/divizii/administrare/{id_divizie}").hasAnyRole("a","c")

                .mvcMatchers("/api/meciuri/localitati").hasAuthority("getPersonal")
                .mvcMatchers("/api/meciuri/localitati").hasAnyRole("a","c","v")

                .mvcMatchers("/api/meciuri/localitati/administrare").hasAuthority("administrarePersonal")
                .mvcMatchers("/api/meciuri/localitati/administrare").hasAnyRole("a","c")

                .mvcMatchers("/api/meciuri/localitati/administrare/{id_localitate}").hasAuthority("administrarePersonal")
                .mvcMatchers("/api/meciuri/localitati/administrare/{id_localitate}").hasAnyRole("a","c")

                .mvcMatchers("/api/meciuri/campionate").hasAuthority("getPersonal")
                .mvcMatchers("/api/meciuri/campionate").hasAnyRole("a","c","v")

                .mvcMatchers("/api/meciuri/campionate/administrare").hasAuthority("administrarePersonal")
                .mvcMatchers("/api/meciuri/campionate/administrare").hasAnyRole("a","c")

                .mvcMatchers("/api/meciuri/campionate/administrare/{id_campionat}").hasAuthority("administrarePersonal")
                .mvcMatchers("/api/meciuri/campionate/administrare/{id_campionat}").hasAnyRole("a","c")

                .mvcMatchers("/api/meciuri/divizie/filtrare/{id_divizie}").hasAuthority("getPersonal")
                .mvcMatchers("/api/meciuri/divizie/filtrare/{id_divizie}").hasAnyRole("a","c","v")

                .mvcMatchers("/api/meciuri/divizie/filtrare/{id_divizie}/{an}/{luna}/{zi}").hasAuthority("getPersonal")
                .mvcMatchers("/api/meciuri/divizie/filtrare/{id_divizie}/{an}/{luna}/{zi}").hasAnyRole("a","c","v")

                .mvcMatchers("/api/meciuri/echipa").hasAuthority("getPersonal")
                .mvcMatchers("/api/meciuri/echipa").hasAnyRole("a","c","v")



                .anyRequest().authenticated()
                .and().logout((logout)->logout.logoutUrl("/api/utilizatori/autentificare").permitAll())
                .httpBasic();
        return httpSecurity.build();
    }


    @Bean
    PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
