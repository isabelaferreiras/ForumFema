package br.edu.fema.forum.ForumFema.controller;

import br.edu.fema.forum.ForumFema.controller.dto.AtualizacaoTopicoForm;
import br.edu.fema.forum.ForumFema.controller.dto.DetalhesDoTopicosDto;
import br.edu.fema.forum.ForumFema.controller.dto.TopicoDto;
import br.edu.fema.forum.ForumFema.controller.dto.TopicoForm;
import br.edu.fema.forum.ForumFema.domain.StatusTopico;
import br.edu.fema.forum.ForumFema.domain.Topico;
import br.edu.fema.forum.ForumFema.repository.CursoRepository;
import br.edu.fema.forum.ForumFema.repository.TopicosRepository;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    @Cacheable(value = "ListaDeTopicos")
    public Page<TopicoDto> lista(@RequestParam(required = false) String nomeCurso,
                                 @RequestParam(required = false) StatusTopico status,
                                 @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 10)
                                 Pageable paginacao){

        Page<Topico> topicos = null;

        if (nomeCurso != null){
            topicos = topicosRepository.findByCursoNome(nomeCurso, paginacao);
        } else if (status != null){
            topicos = topicosRepository.findByStatus(status, paginacao);
        } else {
            topicos = topicosRepository.findAll(paginacao);
        }
        return TopicoDto.converter(topicos);
    }


    @PostMapping
    @Transactional
    @CacheEvict(value = "ListaDeTopicos", allEntries = true)
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
    @CacheEvict(value = "ListaDeTopicos", allEntries = true)
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
    @CacheEvict(value = "ListaDeTopicos", allEntries = true)
    public ResponseEntity<?> remover(@PathVariable Long id){
        Optional<Topico> optional = topicosRepository.findById(id);
        if (optional.isPresent()){
            topicosRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
