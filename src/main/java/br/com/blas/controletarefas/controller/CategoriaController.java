package br.com.blas.controletarefas.controller;

import java.util.Locale;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import br.com.blas.controletarefas.filter.CategoriaFilter;
import br.com.blas.controletarefas.model.Categoria;
import br.com.blas.controletarefas.repository.Paginator;
import br.com.blas.controletarefas.service.CategoriaService;
import br.com.blas.controletarefas.service.exception.RegraNegocioException;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    private static final String PESQUISA_VIEW = "categorias/PesquisaCategorias";

    private static final String CADASTRO_VIEW = "categorias/CadastroCategoria";

    private static final String URL_BASE_REDIRECT = "redirect:/categorias";

    private static final int BUTTONS_TO_SHOW = 5;

    private static final int INITIAL_PAGE = 0;

    private static final int INITIAL_PAGE_SIZE = 5;

    private static final int[] PAGE_SIZES = { 5, 10, 20 };

    @Autowired
    private CategoriaService categoriaService;

    private Log log = LogFactory.getLog(CategoriaController.class);

    @RequestMapping
    public ModelAndView pesquisar(@ModelAttribute("filtro") CategoriaFilter filtro,
	    @RequestParam(value = "pageSize", required = false) Integer pageSize,
	    @RequestParam(value = "page", required = false) Integer page) {
	log.info("Pesquisando categorias");

	ModelAndView modelAndView = new ModelAndView(PESQUISA_VIEW);

	pageSize = (pageSize == null) ? INITIAL_PAGE_SIZE : pageSize;
	page = (page == null || page < 1) ? INITIAL_PAGE : page - 1;

	Page<Categoria> categorias = categoriaService.listar(filtro, new PageRequest(page, pageSize));
	Paginator paginator = new Paginator(categorias.getTotalPages(), categorias.getNumber(), BUTTONS_TO_SHOW);

	modelAndView.addObject("categorias", categorias);
	modelAndView.addObject("selectedPageSize", pageSize);
	modelAndView.addObject("pageSizes", PAGE_SIZES);
	modelAndView.addObject("paginator", paginator);

	return modelAndView;
    }

    @RequestMapping("/nova")
    public ModelAndView nova() {
	log.info("Nova categoria");

	ModelAndView modelAndView = new ModelAndView(CADASTRO_VIEW);
	modelAndView.addObject(new Categoria());

	return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST)
    @CacheEvict(value = "categorias", allEntries = true)
    public String salvar(@Validated Categoria categoria, Errors errors, RedirectAttributes redirectAttributes) {
	log.info("Salvar categoria " + categoria.toString());

	if (errors.hasErrors()) {
	    return CADASTRO_VIEW;
	}

	try {
	    categoriaService.salvar(categoria);
	} catch (RegraNegocioException ex) {
	    errors.reject(ex.getMessage(), null, Locale.getDefault().toString());
	    return CADASTRO_VIEW;
	}

	redirectAttributes.addFlashAttribute("mensagem", "Categoria salva com sucesso!");

	return URL_BASE_REDIRECT + "/nova";

    }

    @RequestMapping("{id}")
    public ModelAndView editar(@PathVariable("id") Categoria categoria) {
	log.info("Editar categoria " + categoria.getId());

	ModelAndView modelAndView = new ModelAndView(CADASTRO_VIEW);
	modelAndView.addObject(categoria);

	return modelAndView;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @CacheEvict(value = "categorias", allEntries = true)
    public String excluir(@PathVariable Long id, RedirectAttributes attributes) {
	log.info("Excluir categoria " + id);

	categoriaService.deletar(id);

	attributes.addFlashAttribute("mensagem", "Categoria excluída com sucesso!");
	return URL_BASE_REDIRECT;
    }

}
