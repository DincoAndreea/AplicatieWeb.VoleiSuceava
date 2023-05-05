package ro.marcc.server.configuration;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ro.marcc.server.model.Utilizator.Utilizator;

import java.util.Collection;

public class DetaliiUtilizator implements UserDetails {
    private Utilizator utilizator;

    public DetaliiUtilizator(Utilizator utilizator) {
        this.utilizator = utilizator;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return utilizator.getAutorizari();
    }

    @Override
    public String getPassword() {
        return utilizator.getParola();
    }

    @Override
    public String getUsername() {
        return utilizator.getNumeUtilizator();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
