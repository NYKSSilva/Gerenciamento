package br.com.senai.gerenciamento.curso;

import br.com.senai.gerenciamento.periodo.Periodo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name="cursos")
@Entity(name= "curso")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;

    @Enumerated(EnumType.STRING)
    private Periodo periodo;
    private boolean ativo;


    public Curso(DadosCadastroCurso dados){
        this.nome = dados.nome();
        this.periodo = dados.periodo();
        this.ativo = true;
    }

    public void atualizarCurso(DadosAtualizarCurso dados){
        if (dados.nome() !=null && !dados.nome().isBlank())
            this.nome = dados.nome();
        if (dados.periodo() !=null)
            this.periodo = dados.periodo();
    }

    public void excluirCurso(){
        this.ativo = false;
    }
}
