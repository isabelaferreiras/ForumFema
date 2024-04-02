package br.edu.fema.forum.ForumFema.controller;

import br.edu.fema.forum.ForumFema.controller.dto.AtualizacaoTopicoForm;
import br.edu.fema.forum.ForumFema.controller.dto.DetalhesDoTopicosDto;
import br.edu.fema.forum.ForumFema.controller.dto.TopicoDto;
import br.edu.fema.forum.ForumFema.controller.dto.TopicoForm;
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
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicosRepository topicosRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public List<TopicoDto> listaNome(String cursoNome, StatusTopico statusCurso) {

        List<Topico> topicos = null;
        if (cursoNome != null) {
            topicos = topicosRepository.findByCursoNome(cursoNome);
        } else if (statusCurso != null) {
            topicos = topicosRepository.findByStatus(statusCurso);
        } else {
            topicos = topicosRepository.findAll();
        }
            return TopicoDto.converter(topicos);
    }

    @GetMapping("/status")
    public List<TopicoDto> listaS(StatusTopico status){
        if (status == null){
            List<Topico> topicos = topicosRepository.findAll();
            return TopicoDto.converter(topicos);
        } else {
            List<Topico> topicos = topicosRepository.findByStatus(status);
            return  TopicoDto.converter(topicos);
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder){
        Topico topico = form.converter(cursoRepository);
        topicosRepository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesDoTopicosDto> detalhar(@PathVariable Long id){
        Optional<Topico> topico = topicosRepository.findById(id);
        if (topico.isPresent()){
            return ResponseEntity.ok(new DetalhesDoTopicosDto(topico.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form){
        Optional<Topico> optional = topicosRepository.findById(id);
        if (optional.isPresent()){
            Topico topico = form.atualizar(id, topicosRepository);
            return ResponseEntity.ok(new TopicoDto(topico));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> remover(@PathVariable Long id){
        Optional<Topico> optional = topicosRepository.findById(id);
        if (optional.isPresent()){
            topicosRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
