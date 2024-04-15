package br.edu.fema.forum.ForumFema.repository;

import br.edu.fema.forum.ForumFema.domain.StatusTopico;
import br.edu.fema.forum.ForumFema.domain.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopicosRepository extends JpaRepository<Topico, Long> {

    Page<Topico> findByCursoNome(String nomeCurso, Pageable paginacao);

    @Query("Select t FROM Topico t where t.curso.nome = :nomeCurso")
    Page<Topico> carregarPorNomeCurso(@Param("nomeCurso") String nomeCurso, Pageable paginacao);

    Page<Topico> findByStatus(StatusTopico statusCurso, Pageable paginacao);

}
