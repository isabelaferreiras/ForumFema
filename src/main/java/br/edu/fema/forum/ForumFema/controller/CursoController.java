package br.edu.fema.forum.ForumFema.controller;


import br.edu.fema.forum.ForumFema.controller.dto.AtualizacaoCursoForm;
import br.edu.fema.forum.ForumFema.controller.dto.CursoDto;
import br.edu.fema.forum.ForumFema.controller.dto.CursosForm;
import br.edu.fema.forum.ForumFema.domain.Curso;
import br.edu.fema.forum.ForumFema.repository.CursoRepository;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    @Cacheable(value = "ListaDeCursos")
    public Page<CursoDto> lista(@PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 10) Pageable paginacao){

        Page<Curso> cursos = cursoRepository.findAll(paginacao);
        return CursoDto.converter(cursos);
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = "ListaDeCursos", allEntries = true)
    public ResponseEntity<CursoDto> cadastrar(@RequestBody @Valid CursosForm form, UriComponentsBuilder uriBuilder){
        Curso curso = form.converter();
        cursoRepository.save(curso);

        URI uri = uriBuilder.path("/cursos/{id}").buildAndExpand(curso.getId()).toUri();
        return ResponseEntity.created(uri).body(new CursoDto(curso));
    }

    @PutMapping("/{id}")
    @Transactional
    @CacheEvict(value = "ListaDeCursos", allEntries = true)
    public ResponseEntity<CursoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoCursoForm form){
        Optional<Curso> optional = cursoRepository.findById(id);
        if (optional.isPresent()){
            Curso curso = form.atualizar(id, cursoRepository);
            return ResponseEntity.ok(new CursoDto(curso));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = "ListaDeCursos", allEntries = true)
    public ResponseEntity<?> remover(@PathVariable Long id) {
            Optional<Curso> optional = cursoRepository.findById(id);
            if (optional.isPresent()){
                cursoRepository.deleteById(id);
                return ResponseEntity.ok().build();
            }

            return ResponseEntity.notFound().build();
    }

}
