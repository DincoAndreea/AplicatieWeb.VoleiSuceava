package ro.marcc.server.model.Meciuri;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.context.annotation.Configuration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Configuration
@ToString
@NoArgsConstructor
@Entity(name="divizie")
public class Divizie {
    @Id
    @Column(name="div_id")
    private Integer id;

    @NotBlank
    @Column(name = "div_nume")
    private String nume;

    public Divizie(Integer id, String nume) {
        this.id = id;
        this.nume = nume;
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
}
