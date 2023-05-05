package ro.marcc.server.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ro.marcc.server.model.VoleiJuvenil.Sperante.Sperante;

import java.util.List;
import java.util.Optional;

public interface RepositorySperanjeJPA extends JpaRepository<Sperante, Integer> {
    @Query("select s from sperante s")
    List<Sperante> getSperante();


    @Query("select s from sperante s where s.id = ?1")
    Sperante getSperante(Integer integer);

    @Transactional
    @Modifying
    @Query("delete from sperante s where s.id = ?1")
    int deleteSperante(int id);


}