package ro.marcc.server.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ro.marcc.server.model.VoleiJuvenil.Cadeti.Cadeti;

import java.util.List;
import java.util.Optional;

public interface RepositoryCadetiJPA extends JpaRepository<Cadeti,Integer> {
    @Query("select c from cadeti c")
    List<Cadeti> getCadeti();


    @Query("select c from cadeti c where c.id = ?1")
    Cadeti getCadeti(Integer integer);


    @Query("delete from cadeti c where c.id = ?1")
    @Transactional
    @Modifying
    int deteleCadeti(Integer integer);




}
