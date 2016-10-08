package br.com.blas.controletarefas.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.blas.controletarefas.model.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    public Page<Tarefa> findByNomeContainingIgnoreCaseOrderByDataCadastroAsc(String nome, Pageable pageable);

}
