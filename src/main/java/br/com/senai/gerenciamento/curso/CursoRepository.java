package br.com.senai.gerenciamento.curso;

import br.com.senai.gerenciamento.periodo.Periodo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso,Long> {
    boolean existsByNomeAndPeriodoAndAtivoTrue(String nome, Periodo periodo);
    Page<Curso> findAllByAtivoTrue(Pageable paginacao);
    Optional<Curso> findByIdAndAtivoTrue(Long id);
}
