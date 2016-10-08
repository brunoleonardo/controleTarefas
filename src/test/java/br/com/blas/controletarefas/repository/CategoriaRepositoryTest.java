package br.com.blas.controletarefas.repository;

import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import br.com.blas.controletarefas.model.Categoria;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoriaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    public void deveRetornarCategoriasOrdenadasPorDescricao() {
	Categoria categoriaA = new Categoria("Categoria A");
	Categoria categoriaB = new Categoria("Categoria B");
	Categoria categoriaC = new Categoria("Categoria C");

	// Faz a inserção fora de ordem.
	this.entityManager.persist(categoriaB);
	this.entityManager.persist(categoriaC);
	this.entityManager.persist(categoriaA);

	List<Categoria> categorias = categoriaRepository.findByOrderByDescricaoAsc();
	assertEquals(categoriaA.getDescricao(), categorias.get(0).getDescricao());
    }

}
