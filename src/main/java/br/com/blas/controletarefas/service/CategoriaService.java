package br.com.blas.controletarefas.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import br.com.blas.controletarefas.filter.CategoriaFilter;
import br.com.blas.controletarefas.model.Categoria;
import br.com.blas.controletarefas.service.exception.RegraNegocioException;

public interface CategoriaService {

    public void salvar(Categoria categoria) throws RegraNegocioException;

    public void deletar(Long id);

    public Page<Categoria> listar(CategoriaFilter filtro, Pageable pageable);

    public List<Categoria> listar();

}
