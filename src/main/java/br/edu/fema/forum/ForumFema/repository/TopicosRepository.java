package br.edu.fema.forum.ForumFema.repository;

import br.edu.fema.forum.ForumFema.domain.StatusTopico;
import br.edu.fema.forum.ForumFema.domain.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopicosRepository extends JpaRepository<Topico, Long> {

    List<Topico> findByCursoNome(String nomeCurso);

    @Query("Select t FROM Topico t where t.curso.nome = :nomeCurso")
    List<Topico> carregarPorNomeCurso(@Param("nomeCurso") String nomeCurso);

    List<Topico> findByStatus(StatusTopico statusCurso);

}
