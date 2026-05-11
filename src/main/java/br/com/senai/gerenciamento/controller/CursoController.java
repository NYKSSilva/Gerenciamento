package br.com.senai.gerenciamento.controller;

import br.com.senai.gerenciamento.curso.Curso;
import br.com.senai.gerenciamento.curso.Curso.*;

import br.com.senai.gerenciamento.curso.CursoRepository;
import br.com.senai.gerenciamento.curso.DadosCadastroCurso;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("Cursos")
public class CursoController {

    @Autowired
    private CursoRepository repository;

    @PostMapping
    @Transactional
    public void cadastrarCurso(@RequestBody @Valid DadosCadastroCurso dados){
        if(repository.existsByNomeAndAtivoTrue(dados.nome()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Curso já cadastrado no sistema");

        repository.save(new Curso(dados));

    }



}
