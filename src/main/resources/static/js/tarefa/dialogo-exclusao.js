$('#dialogoExclusaoTarefa').on('show.bs.modal', function(event) {
	var button = $(event.relatedTarget);
	
	var idEntidade = button.data('id');
	var descricaoEntidade = button.data('descricao');
	
	var modal = $(this);
	var form = modal.find('form');
	var action = form.data('url-base');
	if (!action.endsWith('/')) { // Tratamento para adicionar barra na action caso ela venha sem.
		action += '/';
	}
	form.attr('action', action + idEntidade);
	
	modal.find('.modal-body span').html('Tem certeza que deseja excluir a Tarefa <strong>' + descricaoEntidade + '</strong>?');
});
