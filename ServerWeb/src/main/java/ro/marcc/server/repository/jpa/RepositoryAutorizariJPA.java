package ro.marcc.server.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.marcc.server.model.Utilizator.Autorizare;

public interface RepositoryAutorizariJPA extends JpaRepository<Autorizare,Character> {
    @Query("select a from autorizari a where a.rol = ?1")
    Autorizare getAutorizare(Character rol);

}
