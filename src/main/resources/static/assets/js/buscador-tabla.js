document.addEventListener('DOMContentLoaded', function () {
	var buscador = document.getElementById('buscadorTabla');
	var tabla = document.querySelector('.table-responsive table tbody');

	if (!buscador || !tabla) {
		return;
	}

	buscador.addEventListener('input', function () {
		var termino = buscador.value.trim().toLowerCase();
		var filas = tabla.querySelectorAll('tr');

		filas.forEach(function (fila) {
			var celdasBuscables = fila.querySelectorAll('td.buscable');
			var texto = celdasBuscables.length > 0
				? Array.prototype.map.call(celdasBuscables, function (celda) { return celda.textContent; }).join(' ')
				: fila.textContent;
			fila.style.display = texto.toLowerCase().includes(termino) ? '' : 'none';
		});
	});
});
