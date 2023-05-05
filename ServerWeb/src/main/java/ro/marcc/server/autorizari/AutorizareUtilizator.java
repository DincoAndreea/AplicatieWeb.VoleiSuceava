package ro.marcc.server.autorizari;

import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
@ToString
public class AutorizareUtilizator implements GrantedAuthority {
    private String autorizare;

    public AutorizareUtilizator(String autorizare) {
        this.autorizare = autorizare;
    }

    @Override
    public String getAuthority() {
        return autorizare;
    }
}
