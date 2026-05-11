package br.com.senai.gerenciamento.curso;

import br.com.senai.gerenciamento.periodo.Periodo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DadosCadastroCurso(
        @NotBlank
        @Size(min=3)
        String nome,

        Periodo periodo
) {
}
