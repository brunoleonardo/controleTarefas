package br.com.blas.controletarefas.service;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import br.com.blas.controletarefas.filter.TarefaFilter;
import br.com.blas.controletarefas.model.Tarefa;
import br.com.blas.controletarefas.repository.TarefaRepository;
import br.com.blas.controletarefas.service.exception.RegraNegocioException;

@Service
public class TarefaServiceImpl implements TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Override
    public void salvar(Tarefa tarefa) throws RegraNegocioException {
	if (tarefa.getDataConclusao() != null) {
	    if (tarefa.getDataConclusao().after(new Date())) {
		throw new RegraNegocioException("tarefa.dataconclusao.maior.dataatual");
	    }

	    if (tarefa.getDataConclusao().before(tarefa.getDataCadastro())) {
		throw new RegraNegocioException("tarefa.dataconclusao.menor.datacadastro");
	    }
	}

	tarefaRepository.save(tarefa);
    }

    @Override
    public void deletar(Long id) {
	tarefaRepository.delete(id);
    }

    @Override
    public Page<Tarefa> listar(TarefaFilter filtro, Pageable pageable) {
	String nome = filtro.getNome() == null ? "%" : filtro.getNome();

	return tarefaRepository.findByNomeContainingIgnoreCaseOrderByDataCadastroAsc(nome, pageable);
    }

    @Override
    public String concluir(Long id) {
	Tarefa tarefa = tarefaRepository.findOne(id);
	tarefa.setDataConclusao(new Date());

	tarefaRepository.save(tarefa);

	return tarefa.getNomeTarefaComDataConclusao();
    }

}
