package br.edu.fema.forum.ForumFema.controller.dto;

import br.edu.fema.forum.ForumFema.domain.StatusTopico;
import br.edu.fema.forum.ForumFema.domain.Topico;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DetalhesDoTopicosDto {

    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private StatusTopico status;
    private String nomeAutor;
    private List<RespostaDto> respostas;

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public StatusTopico getStatus() {
        return status;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public List<RespostaDto> getRespostas() {
        return respostas;
    }

    public DetalhesDoTopicosDto(Topico topico){
        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.mensagem = topico.getMensagem();
        this.dataCriacao = topico.getDataCriacao();
        this.status = topico.getStatus();
        this.nomeAutor = topico.getAutor().getNome();
        this.respostas = new ArrayList<>();
        this.respostas.addAll(topico.getRespostas().stream().map(RespostaDto::new).collect(Collectors.toList()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetalhesDoTopicosDto that = (DetalhesDoTopicosDto) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
