package br.com.blas.controletarefas.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.blas.controletarefas.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    public Page<Categoria> findByDescricaoContainingIgnoreCaseOrderByDescricaoAsc(String descricao, Pageable pageable);

    public List<Categoria> findByOrderByDescricaoAsc();

}
