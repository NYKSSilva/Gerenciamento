package br.com.senai.gerenciamento.controller;

import br.com.senai.gerenciamento.curso.*;

import br.com.senai.gerenciamento.periodo.Periodo;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

//http://localhost:8080/swagger-ui/index.html


@RestController
@RequestMapping("Cursos")
@Tag(name="Cursos", description = "Gerenciamento dos Cursos ")
@OpenAPIDefinition(tags ={
        @Tag(name = "Criar Curso",description = "Criação de cursos"),
        @Tag(name = "Listar todos os cursos",description = "Listagem de todos os cursos"),
        @Tag(name = "Listar curso por ID",description = "Listagem de produtos especificos"),
        @Tag(name = "Excluir curso",description = "Excluir curso"),
        @Tag(name = "Atualizar curso",description = "Atualizar curso"),
        @Tag(name = "Listar periodo",description = "Listagem de periodos")

})
public class CursoController {

    @Autowired
    private CursoRepository repository;


    @PostMapping
    @Transactional
    @Operation(summary = "Criar um novo curso")
    @Tag(name = "Criar Curso", description = "Salva os dados do curso no Banco de dados")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DadosDetalhamentoCurso.class))
                    }),
            @ApiResponse(responseCode = "409", description = "Curso já cadastrado", content = @Content)})

    public void cadastrarCurso(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DadosCadastroCurso.class),
                            examples = @ExampleObject(
                                    value = "{ \"nome\": \"Nome curso\",\t\n" +
                                            "\t\"periodo\": \"MATUTINO\"}"
                            )
                    )
            )
            @RequestBody @Valid DadosCadastroCurso dados){
        if(repository.existsByNomeAndPeriodoAndAtivoTrue(dados.nome(), dados.periodo()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Curso já cadastrado no sistema");
        repository.save(new Curso(dados));

    }

    @GetMapping
    @Operation(summary = "Listar cursos")
    @Tag(name = "Listar todos os cursos", description = "Lista todos os cursos existentes no banco de dados")

    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Listagem ocorreu com sucesso",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DadosDetalhamentoCurso.class))
                    })
    })
    public Page<DadosListagemCurso> listarCurso(
            @Parameter(description = "Parâmetros de paginação",
                    example = "{\"page\":0,\"size\":6,\"sort\":\"nome\"}")
            @PageableDefault(size=6, sort= {"nome"}) Pageable paginacao){
        return repository.findAllByAtivoTrue(paginacao)
                .map(DadosListagemCurso::new);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Listar um curso especifico")
    @Tag(name = "Listar curso por ID", description = "Listagem expecifica de um curso de acordo com o id ")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Listagem ocorreu com sucesso",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DadosDetalhamentoCurso.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado no sistema", content = @Content),
    })
    public DadosDetalhamentoCurso detalharCurso(@PathVariable Long id){
        var curso = repository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Curso não existe." ));
        return new DadosDetalhamentoCurso(curso);
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Atualizar um curso")
    @Tag(name = "Atualizar curso")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Curso atualizado com sucesso",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DadosDetalhamentoCurso.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado no sistema ", content = @Content),
            @ApiResponse(responseCode = "409", description = "Curso já cadastrado no sistema", content = @Content),


    })
    public void atualizarCurso(@RequestBody @Valid DadosAtualizarCurso dados){
        var curso = repository.findByIdAndAtivoTrue(dados.id())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));

        if(dados.periodo()!=null) {
            if (repository.existsByNomeAndPeriodoAndAtivoTrue(dados.nome(),dados.periodo()))
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Curso nesse periodo já cadastrado no sistema");
        }
        curso.atualizarCurso(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir um produto")
    @Tag(name = "Excluir Produto")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204", description = "Curso excluido com sucesso",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DadosDetalhamentoCurso.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado no sistema", content = @Content),


    })
    public void deletarCliente(@PathVariable Long id){
        var curso = repository.findByIdAndAtivoTrue(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));
        curso.excluirCurso();
    }

    @GetMapping("/periodo")
    @Tag(name = "Listar periodo")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Listagem ocorreu com sucesso",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Periodo.class))
                    })
    })
    public List<Periodo> listarPeriodo(){
        return Arrays.asList(Periodo.values());
    }
}
