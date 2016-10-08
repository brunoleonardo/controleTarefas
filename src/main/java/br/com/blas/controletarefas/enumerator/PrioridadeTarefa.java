package br.com.blas.controletarefas.enumerator;

public enum PrioridadeTarefa {

    BAIXA("Baixa"), MEDIA("Média"), ALTA("Alta");

    private String descricao;

    PrioridadeTarefa(String descricao) {
	this.descricao = descricao;
    }

    public String getDescricao() {
	return descricao;
    }

}
