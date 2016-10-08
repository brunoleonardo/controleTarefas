package br.com.blas.controletarefas.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import br.com.blas.controletarefas.filter.CategoriaFilter;
import br.com.blas.controletarefas.model.Categoria;
import br.com.blas.controletarefas.repository.CategoriaRepository;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public void salvar(Categoria categoria) {
	categoriaRepository.save(categoria);
    }

    @Override
    public void deletar(Long id) {
	categoriaRepository.delete(id);
    }

    @Override
    public Page<Categoria> listar(CategoriaFilter filtro, Pageable pageable) {
	String descricao = filtro.getDescricao() == null ? "%" : filtro.getDescricao();

	return categoriaRepository.findByDescricaoContainingIgnoreCaseOrderByDescricaoAsc(descricao, pageable);
    }

    @Override
    public List<Categoria> listar() {
	return categoriaRepository.findByOrderByDescricaoAsc();
    }

}
