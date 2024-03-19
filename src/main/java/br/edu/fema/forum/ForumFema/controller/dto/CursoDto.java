package br.edu.fema.forum.ForumFema.controller.dto;

import br.edu.fema.forum.ForumFema.domain.Curso;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CursoDto {

    private long id;
    private String nome;

    public CursoDto(Curso curso) {
        super();
        this.id = curso.getId();
        this.nome = curso.getNome();
    }

    public static List<CursoDto> converter(List<Curso> cursos){
        return cursos.stream().map(CursoDto::new).collect(Collectors.toList());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
        CursoDto cursoDto = (CursoDto) o;
        return id == cursoDto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
