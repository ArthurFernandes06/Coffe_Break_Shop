/**
 * admin_script.js
 * Navegação entre páginas, filtros de tabela e ações simuladas.
 *
 * Para integrar com backend, substitua os alert() por chamadas fetch().
 */

/* ─── Troca de página ─── */
function showPage(id, btn) {
    document.querySelectorAll('.page').forEach(p => p.classList.remove('active'));
    document.getElementById('page-' + id).classList.add('active');

    document.querySelectorAll('.nav-btn').forEach(b => b.classList.remove('active'));
    btn.classList.add('active');
}

/* ─── Mostra / esconde formulário ─── */
function toggleForm(id) {
    document.getElementById(id).classList.toggle('hidden');
}

/* ─── Filtro de tabela por coluna ─── */
function filtrar(inputId, tabelaId, coluna) {
    const termo = document.getElementById(inputId).value.toLowerCase();
    document.querySelectorAll('#' + tabelaId + ' tbody tr').forEach(tr => {
        const texto = tr.cells[coluna] ? tr.cells[coluna].textContent.toLowerCase() : '';
        tr.style.display = texto.includes(termo) ? '' : 'none';
    });
}

/* ─── Filtro de pedidos por status ─── */
function filtrarStatus() {
    const val = document.getElementById('filtro-status').value;
    document.querySelectorAll('#tb-pedidos tbody tr').forEach(tr => {
        const status = tr.cells[3] ? tr.cells[3].textContent : '';
        tr.style.display = (!val || status.includes(val)) ? '' : 'none';
    });
}

/* ─── Confirmar pedido ─── */
function confirmar(btn) {
    const row = btn.closest('tr');
    row.cells[3].innerHTML = '<span class="badge ok">Entregue</span>';
    row.cells[4].textContent = '—';
    // TODO: fetch('/api/pedidos/confirmar', { method: 'PATCH', ... })
}

/* ─── Salvar novo produto ─── */
function salvarProduto() {
    const nome  = document.getElementById('p-nome').value.trim();
    const preco = document.getElementById('p-preco').value;
    const cat   = document.getElementById('p-cat').value;

    if (!nome || !preco) {
        alert('Preencha o nome e o preço do produto.');
        return;
    }

    const tbody = document.querySelector('#tb-produtos tbody');
    const tr = document.createElement('tr');
    tr.innerHTML = `
        <td>${nome}</td>
        <td>${cat}</td>
        <td>R$ ${parseFloat(preco).toFixed(2).replace('.', ',')}</td>
        <td><span class="badge ok">Ativo</span></td>
    `;
    tbody.appendChild(tr);

    // Limpa o formulário e fecha
    document.getElementById('p-nome').value  = '';
    document.getElementById('p-preco').value = '';
    toggleForm('form-produto');

    // TODO: fetch('/api/produtos', { method: 'POST', body: JSON.stringify({ nome, preco, cat }) })
}
