package br.edu.fema.forum.ForumFema.controller.dto;

import br.edu.fema.forum.ForumFema.domain.Curso;
import br.edu.fema.forum.ForumFema.domain.Topico;
import br.edu.fema.forum.ForumFema.repository.CursoRepository;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

public class TopicosForm {

    @NotNull
    @NotEmpty
    @Length(min = 5)
    private String titulo;
    @NotNull
    @NotEmpty
    @Length(min = 10)
    private String mensagem;
    @NotNull
    @NotEmpty
    private String nomeCurso;

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopicosForm that = (TopicosForm) o;
        return titulo.equals(that.titulo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo);
    }

    public Topico converter(CursoRepository cursoRepository){
        Curso curso = cursoRepository.findByNome(this.nomeCurso);
        return new Topico(titulo, mensagem, curso);
    }


}
