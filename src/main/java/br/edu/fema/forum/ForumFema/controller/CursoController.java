package br.edu.fema.forum.ForumFema.controller;


import br.edu.fema.forum.ForumFema.controller.dto.AtualizacaoCursoForm;
import br.edu.fema.forum.ForumFema.controller.dto.CursoDto;
import br.edu.fema.forum.ForumFema.controller.dto.CursosForm;
import br.edu.fema.forum.ForumFema.domain.Curso;
import br.edu.fema.forum.ForumFema.repository.CursoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.apache.commons.lang3.text.translate.NumericEntityUnescaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<CursoDto> lista(@RequestParam int pagina,
                                @RequestParam int qtde,
                                @RequestParam String ordenacao){
        Pageable paginacao = PageRequest.of(pagina, qtde, Sort.Direction.ASC, ordenacao);

        Page<Curso> cursos = cursoRepository.findAll(paginacao);
        return CursoDto.converter(cursos);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<CursoDto> cadastrar(@RequestBody @Valid CursosForm form, UriComponentsBuilder uriBuilder){
        Curso curso = form.converter();
        cursoRepository.save(curso);

        URI uri = uriBuilder.path("/cursos/{id}").buildAndExpand(curso.getId()).toUri();
        return ResponseEntity.created(uri).body(new CursoDto(curso));
    }

    @PutMapping("/{id}")
    @Transactional
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
    public ResponseEntity<?> remover(@PathVariable Long id) {
            Optional<Curso> optional = cursoRepository.findById(id);
            if (optional.isPresent()){
                cursoRepository.deleteById(id);
                return ResponseEntity.ok().build();
            }

            return ResponseEntity.notFound().build();
    }

}
