package br.com.blas.controletarefas.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import br.com.blas.controletarefas.enumerator.PrioridadeTarefa;
import br.com.blas.controletarefas.filter.TarefaFilter;
import br.com.blas.controletarefas.model.Categoria;
import br.com.blas.controletarefas.model.Tarefa;
import br.com.blas.controletarefas.repository.Paginator;
import br.com.blas.controletarefas.service.CategoriaServiceImpl;
import br.com.blas.controletarefas.service.TarefaService;
import br.com.blas.controletarefas.service.exception.RegraNegocioException;

@Controller
@RequestMapping("/tarefas")
public class TarefasController {

    private static final String PESQUISA_VIEW = "tarefas/PesquisaTarefas";

    private static final String CADASTRO_VIEW = "tarefas/CadastroTarefa";

    private static final String URL_BASE_REDIRECT = "redirect:/tarefas";

    private static final int BUTTONS_TO_SHOW = 5;

    private static final int INITIAL_PAGE = 0;

    private static final int INITIAL_PAGE_SIZE = 5;

    private static final int[] PAGE_SIZES = { 5, 10, 20 };

    @Autowired
    private TarefaService tarefaService;

    @Autowired
    private CategoriaServiceImpl cargoService;

    private Log log = LogFactory.getLog(TarefasController.class);

    @Cacheable("categorias")
    @ModelAttribute("categorias")
    public List<Categoria> getCategorias() {
	List<Categoria> categorias = cargoService.listar();
	return categorias;
    }

    @ModelAttribute("prioridadesTarefa")
    public List<PrioridadeTarefa> getPrioridadesTarefa() {
	return Arrays.asList(PrioridadeTarefa.values());
    }

    @RequestMapping
    public ModelAndView pesquisar(@ModelAttribute("filtro") TarefaFilter filtro,
	    @RequestParam(value = "pageSize", required = false) Integer pageSize,
	    @RequestParam(value = "page", required = false) Integer page) {
	log.info("Pesquisando tarefas");

	ModelAndView modelAndView = new ModelAndView(PESQUISA_VIEW);

	pageSize = (pageSize == null) ? INITIAL_PAGE_SIZE : pageSize;
	page = (page == null || page < 1) ? INITIAL_PAGE : page - 1;

	Page<Tarefa> tarefas = tarefaService.listar(filtro, new PageRequest(page, pageSize));
	Paginator paginator = new Paginator(tarefas.getTotalPages(), tarefas.getNumber(), BUTTONS_TO_SHOW);

	modelAndView.addObject("tarefas", tarefas);
	modelAndView.addObject("selectedPageSize", pageSize);
	modelAndView.addObject("pageSizes", PAGE_SIZES);
	modelAndView.addObject("paginator", paginator);

	return modelAndView;
    }

    @RequestMapping("/nova")
    public ModelAndView nova() {
	log.info("Nova tarefa");

	ModelAndView modelAndView = new ModelAndView(CADASTRO_VIEW);
	modelAndView.addObject(new Tarefa());

	return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String salvar(@Validated Tarefa tarefa, Errors errors, RedirectAttributes redirectAttributes) {
	log.info("Salvar tarefa " + tarefa.toString());

	if (errors.hasErrors()) {
	    return CADASTRO_VIEW;
	}

	try {
	    tarefaService.salvar(tarefa);
	} catch (RegraNegocioException ex) {
	    errors.reject(ex.getMessage(), null, Locale.getDefault().toString());
	    return CADASTRO_VIEW;
	}

	redirectAttributes.addFlashAttribute("mensagem", "Tarefa salva com sucesso!");

	return URL_BASE_REDIRECT + "/nova";

    }

    @RequestMapping("{id}")
    public ModelAndView editar(@PathVariable("id") Tarefa tarefa) {
	log.info("Editar tarefa " + tarefa.getId());

	ModelAndView modelAndView = new ModelAndView(CADASTRO_VIEW);
	modelAndView.addObject(tarefa);

	return modelAndView;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public String excluir(@PathVariable Long id, RedirectAttributes attributes) {
	log.info("Excluir tarefa " + id);

	tarefaService.deletar(id);

	attributes.addFlashAttribute("mensagem", "Tarefa excluída com sucesso!");
	return URL_BASE_REDIRECT;
    }

    @RequestMapping(value = "/{id}/concluir", method = RequestMethod.PUT)
    public @ResponseBody String concluir(@PathVariable Long id) {
	log.info("Concluir tarefa " + id);

	return tarefaService.concluir(id);
    }

}
