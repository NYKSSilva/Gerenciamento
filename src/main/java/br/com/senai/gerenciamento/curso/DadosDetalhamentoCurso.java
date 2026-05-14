package br.com.senai.gerenciamento.curso;

import br.com.senai.gerenciamento.periodo.Periodo;

public record DadosDetalhamentoCurso(
        Long id,
        String nome,
        Periodo periodo
) {
    public DadosDetalhamentoCurso(Curso curso){
        this(curso.getId(), curso.getNome(), curso.getPeriodo());
    }
}
