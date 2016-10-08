package br.com.blas.controletarefas.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import br.com.blas.controletarefas.enumerator.PrioridadeTarefa;

@Entity
public class Tarefa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "{tarefa.nome.obrigatorio}")
    @Size(min = 1, max = 45, message = "{tarefa.nome.tamanho}")
    private String nome;

    @NotEmpty(message = "{tarefa.descricao.obrigatoria}")
    @Size(min = 8, max = 240, message = "{tarefa.descricao.tamanho}")
    private String descricao;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date dataCadastro = new Date();

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date dataConclusao;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "{tarefa.prioridade.obrigatoria}")
    private PrioridadeTarefa prioridade;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    @NotNull(message = "{tarefa.categoria.obrigatoria}")
    private Categoria categoria;

    public Tarefa() {
	super();
    }

    public Tarefa(Long id, String nome, String descricao, Date dataCadastro, Date dataConclusao, PrioridadeTarefa prioridade,
	    Categoria categoria) {
	super();

	this.id = id;
	this.nome = nome;
	this.descricao = descricao;
	this.dataCadastro = dataCadastro;
	this.dataConclusao = dataConclusao;
	this.prioridade = prioridade;
	this.categoria = categoria;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getNome() {
	return nome;
    }

    public void setNome(String nome) {
	this.nome = nome;
    }

    public String getDescricao() {
	return descricao;
    }

    public void setDescricao(String descricao) {
	this.descricao = descricao;
    }

    public Date getDataCadastro() {
	return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
	this.dataCadastro = dataCadastro;
    }

    public Date getDataConclusao() {
	return dataConclusao;
    }

    public void setDataConclusao(Date dataConclusao) {
	this.dataConclusao = dataConclusao;
    }

    public PrioridadeTarefa getPrioridade() {
	return prioridade;
    }

    public void setPrioridade(PrioridadeTarefa prioridade) {
	this.prioridade = prioridade;
    }

    public Categoria getCategoria() {
	return categoria;
    }

    public void setCategoria(Categoria categoria) {
	this.categoria = categoria;
    }

    public String getEstiloCssPrioridade() {
	switch (prioridade) {

	case BAIXA:
	    return "label-success";

	case MEDIA:
	    return "label-warning";

	case ALTA:
	    return "label-danger";

	default:
	    return "label-default";
	}
    }

    public String getNomeTarefaComDataConclusao() {
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	String nomeTarefaComDataConclusao = getNome() + " (" + dateFormat.format(getDataConclusao()) + ")";

	return nomeTarefaComDataConclusao;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Tarefa other = (Tarefa) obj;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return nome;
    }

}