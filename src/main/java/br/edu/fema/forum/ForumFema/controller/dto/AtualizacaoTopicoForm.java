package br.edu.fema.forum.ForumFema.controller.dto;

import br.edu.fema.forum.ForumFema.domain.Topico;
import br.edu.fema.forum.ForumFema.repository.TopicosRepository;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

public class AtualizacaoTopicoForm {
    //essa classe serve para limitar ao cliente as informações que ele pode atualizar depois de já tê-las criado,
    // no caso aqui apenas título e mensagem do tópico

    @NotNull
    @NotEmpty
    @Length(min = 5)
    private String titulo;
    @NotNull
    @NotEmpty
    @Length(min = 10)
    private String mensagem;

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AtualizacaoTopicoForm that = (AtualizacaoTopicoForm) o;
        return titulo.equals(that.titulo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo);
    }

    public Topico atualizar(Long id, TopicosRepository topicosRepository){
        Topico topico = topicosRepository.getReferenceById(id);
        topico.setTitulo(this.titulo);
        topico.setMensagem(this.mensagem);
        return topico;
    }
}
