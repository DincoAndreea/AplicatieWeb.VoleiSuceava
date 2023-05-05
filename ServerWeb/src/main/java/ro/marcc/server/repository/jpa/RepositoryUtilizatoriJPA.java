package ro.marcc.server.repository.jpa;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ro.marcc.server.dto.Utilizator.UtilizatorPreviewDto;
import ro.marcc.server.model.Utilizator.Utilizator;

import java.util.List;

public interface RepositoryUtilizatoriJPA extends CrudRepository<Utilizator,Integer> {
    @Query("select u from utilizator u where u.rol = 'c'")
    List<Utilizator> getCreatoriDeContinut();


    @Query("select u from utilizator u where u.numeUtilizator = ?1")
    Utilizator getUtilizator(String numeUtilizator);

    @Query("select u from utilizator u where u.id = ?1")
    Utilizator getUtilizatorById(int id);

    @Query("select u from utilizator u where u.numeUtilizator = ?1 and u.parola = ?2")
    Utilizator getUtilizator(String numeUtilizator, String parola);

    @Transactional
    @Modifying
    @Query("delete from utilizator u where u.id = ?1")
    int deleteUtilizator(int id);

    long deleteByNumeUtilizator(String numeUtilizator);



}
