package ro.marcc.server.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ro.marcc.server.model.VoleiJuvenil.Juniori.Juniori;

import java.util.List;
import java.util.Optional;

public interface RepositoryJunioriJPA extends JpaRepository<Juniori, Integer> {

    @Query("select j from juniori j")
    List<Juniori> getJuniori();


    @Query("select j from juniori j where j.id = ?1")
    Juniori getJuniori(Integer integer);


    @Query("delete from juniori j where j.id = ?1")
    @Transactional
    @Modifying
    int deleteJuniori(Integer integer);
}