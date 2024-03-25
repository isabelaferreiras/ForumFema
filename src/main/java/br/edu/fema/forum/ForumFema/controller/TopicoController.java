package br.edu.fema.forum.ForumFema.controller;

import br.edu.fema.forum.ForumFema.controller.dto.DetalhesDoTopicosDTO;
import br.edu.fema.forum.ForumFema.controller.dto.TopicosDto;
import br.edu.fema.forum.ForumFema.controller.dto.TopicosForm;
import br.edu.fema.forum.ForumFema.domain.StatusTopico;
import br.edu.fema.forum.ForumFema.domain.Topico;
import br.edu.fema.forum.ForumFema.repository.CursoRepository;
import br.edu.fema.forum.ForumFema.repository.TopicosRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicosRepository topicosRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public List<TopicosDto> listaNome(String cursoNome, StatusTopico statusCurso) {

        List<Topico> topicos = null;
        if (cursoNome != null) {
            topicos = topicosRepository.findByCursoNome(cursoNome);
        } else if (statusCurso != null) {
            topicos = topicosRepository.findByStatus(statusCurso);
        } else {
            topicos = topicosRepository.findAll();
        }
            return TopicosDto.converter(topicos);
    }

    @GetMapping("/status")
    public List<TopicosDto> listaS(StatusTopico status){
        if (status == null){
            List<Topico> topicos = topicosRepository.findAll();
            return TopicosDto.converter(topicos);
        } else {
            List<Topico> topicos = topicosRepository.findByStatus(status);
            return  TopicosDto.converter(topicos);
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity<TopicosDto> cadastrar(@RequestBody @Valid TopicosForm form, UriComponentsBuilder uriBuilder){
        Topico topico = form.converter(cursoRepository);
        topicosRepository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicosDto(topico));
    }

    @GetMapping("/{id}")
    public DetalhesDoTopicosDTO detalhar(@PathVariable Long id){
        Topico topico = topicosRepository.getReferenceById(id);
        return new DetalhesDoTopicosDTO(topico);
    }
}
