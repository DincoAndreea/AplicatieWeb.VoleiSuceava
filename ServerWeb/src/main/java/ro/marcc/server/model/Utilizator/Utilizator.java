package ro.marcc.server.model.Utilizator;

import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import ro.marcc.server.autorizari.AutorizareUtilizator;
import ro.marcc.server.convertoare.StringListConvertor;
import ro.marcc.server.validators.interfaces.ConstrangereRolUtilizator;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration
@ToString
@Entity(name="utilizator")
public class Utilizator {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="uti_id", nullable = false, unique = true)
    protected Integer id;
    @NotNull
    @NotEmpty
    @Length(max=40)
    @Column(name="uti_nume")
    protected String nume;
    @NotNull
    @NotEmpty
    @Column(name="uti_parola")
    protected String parola;
    @NotNull
    @NotEmpty
    @Column(name="uti_nume_utilizator")
    protected String numeUtilizator;
    @NotEmpty
    @NotNull
    @ConstrangereRolUtilizator
    @Column(name="uti_rol")
    protected Character rol;

    @Transient
    @Convert(converter = StringListConvertor.class)
    @Column(name="aut_roluri")
    protected List<String> autorizari;

    public Utilizator() {
        this(0,"sistem","vizitator","parola",'v');
    }
    public Utilizator(Integer id, String nume,  String numeUtilizator, String parola,Character rol) {

        this.id = id;
        this.nume = nume;
        this.parola = parola;
        this.numeUtilizator = numeUtilizator;
        this.rol = rol;
        this.autorizari = new ArrayList<>();
    }

    public Utilizator(Integer id, String nume, String parola, String numeUtilizator, Character rol, List<String> autorizari) {
        this.id = id;
        this.nume = nume;
        this.parola = parola;
        this.numeUtilizator = numeUtilizator;
        this.rol = rol;
        this.autorizari = autorizari;
    }

    public Collection<? extends GrantedAuthority> getAutorizari(){
        Collection<GrantedAuthority> autorizari = new ArrayList<>();

        for (String autorizare:
                this.autorizari) {
            autorizari.add(new AutorizareUtilizator(autorizare));
        }

        return autorizari;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getNumeUtilizator() {
        return numeUtilizator;
    }

    public void setNumeUtilizator(String numeUtilizator) {
        this.numeUtilizator = numeUtilizator;
    }

    public Character getRol() {
        return rol;
    }

    public void setRol(Character rol) {
        this.rol = rol;
    }

    public void setAutorizari(List<String> autorizari) {
        this.autorizari = autorizari;
    }
}
