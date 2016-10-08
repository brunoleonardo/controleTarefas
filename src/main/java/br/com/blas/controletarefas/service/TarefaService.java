package br.com.blas.controletarefas.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import br.com.blas.controletarefas.filter.TarefaFilter;
import br.com.blas.controletarefas.model.Tarefa;
import br.com.blas.controletarefas.service.exception.RegraNegocioException;

public interface TarefaService {

    public void salvar(Tarefa tarefa) throws RegraNegocioException;

    public void deletar(Long id);

    public Page<Tarefa> listar(TarefaFilter filtro, Pageable pageable);

    public String concluir(Long id);

}
