$(document).ready(function() { 
	concluirTarefa();
	
	alterarTamanhoPaginaTabela();
	
	$('[rel="tooltip"]').tooltip();	
});

function concluirTarefa() {
	$('.js-concluir-tarefa').on('click', function(event) { 
		event.preventDefault();
				
		var botaoConcluirTarefa = $(event.currentTarget);
		var urlConcluirTarefa = botaoConcluirTarefa.attr('href'); 
				
		var response = $.ajax({
			url: urlConcluirTarefa,
			type: 'PUT'
		});
		
		
		response.done(function(nomeTarefaComDataConclusao) {
			var idTarefa = botaoConcluirTarefa.data('id');			
			
			$('[data-role=' + idTarefa + ']').html(nomeTarefaComDataConclusao).addClass("tarefa-concluida");
			
			botaoConcluirTarefa.hide();
		});
		
		response.fail(function(e) {
			console.log(e);
			alert('Ops... Ocorreu um erro ao Concluir a Tarefa.');
		});
		
	});
}

function alterarTamanhoPaginaTabela() {
	$('#pageSizeSelect').change(function(evt) {
		window.location.replace("/tarefas/?pageSize=" + this.value + "&page=1");
	});
}


