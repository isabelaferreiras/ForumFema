package br.edu.fema.forum.ForumFema.controller;


import br.edu.fema.forum.ForumFema.controller.dto.CursoDto;
import br.edu.fema.forum.ForumFema.controller.dto.CursosForm;
import br.edu.fema.forum.ForumFema.domain.Curso;
import br.edu.fema.forum.ForumFema.repository.CursoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public List<CursoDto> lista(){

        List<Curso> cursos = cursoRepository.findAll();
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



}
