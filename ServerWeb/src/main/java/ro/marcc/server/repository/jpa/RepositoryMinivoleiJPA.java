package ro.marcc.server.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ro.marcc.server.model.VoleiJuvenil.Minivolei.Minivolei;

import java.util.List;
import java.util.Optional;

public interface RepositoryMinivoleiJPA extends JpaRepository<Minivolei, Integer> {

    @Query("select m from minivolei m")
    List<Minivolei> getMinivolei();


    @Query("select m from minivolei m where m.id = ?1")
    Minivolei getMinivolei(Integer integer);

    @Transactional
    @Modifying
    @Query("delete from minivolei m where m.id = ?1")
    int deleteMinivolei(int id);


}