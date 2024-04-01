package br.edu.fema.forum.ForumFema.controller.dto;

import br.edu.fema.forum.ForumFema.domain.Curso;
import br.edu.fema.forum.ForumFema.repository.CursoRepository;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

public class AtualizacaoCursoForm {
    //essa classe serve para limitar ao cliente as informações que ele pode atualizar depois de já tê-las criado,
    // no caso aqui apenas nome do curso

    @NotNull
    @NotEmpty
    @Length(min = 5)
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AtualizacaoCursoForm that = (AtualizacaoCursoForm) o;
        return nome.equals(that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }

    public Curso atualizar(Long id, CursoRepository cursoRepository){
        Curso curso = cursoRepository.getReferenceById(id);
        curso.setNome(this.nome);
        return curso;
    }
}
