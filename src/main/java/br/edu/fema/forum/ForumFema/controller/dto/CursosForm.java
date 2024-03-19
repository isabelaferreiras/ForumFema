package br.edu.fema.forum.ForumFema.controller.dto;

import br.edu.fema.forum.ForumFema.domain.Curso;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class CursosForm {

    @NotNull
    @NotEmpty
    @Length(min = 5)
    private String nome;

    @NotNull
    @NotEmpty
    @Length(min = 5)
    private String categoria;

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Curso converter(){
        return new Curso(this.nome, this.categoria);
    }

}
