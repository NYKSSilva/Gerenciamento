package br.com.senai.gerenciamento.curso;

import br.com.senai.gerenciamento.periodo.Periodo;

public record DadosListagemCurso(
        Long id,
        String nome
//        Periodo periodo
) {
    public DadosListagemCurso(Curso curso){
        this(curso.getId(), curso.getNome());
    }
}
