package br.com.senai.gerenciamento.curso;

import br.com.senai.gerenciamento.periodo.Periodo;
import jakarta.validation.constraints.Size;

public record DadosAtualizarCurso(
        Long id,

        @Size(min = 3)
        String nome,

        Periodo periodo
) {
}
