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
    const form = document.getElementById(id);
    if (form) {
        form.classList.toggle('hidden');
    }
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

/* ─── Formulário de produto: abrir em modo "novo" ─── */
function novoProduto() {
    const form = document.getElementById('produto-form');
    form.reset();
    document.getElementById('p-id').value = '';
    document.getElementById('p-foto-atual').textContent = '';
    form.action = form.dataset.cadastrarUrl;
    document.getElementById('form-produto-titulo').textContent = 'Novo produto';

    const box = document.getElementById('form-produto');
    box.classList.remove('hidden');
    box.scrollIntoView({ behavior: 'smooth', block: 'start' });
}

/* ─── Formulário de produto: abrir em modo "edição" ─── */
function editarProduto(btn) {
    const row = btn.closest('tr');
    const form = document.getElementById('produto-form');

    document.getElementById('p-id').value        = row.dataset.id;
    document.getElementById('p-nome').value       = row.dataset.nome;
    document.getElementById('p-preco').value      = row.dataset.preco;
    document.getElementById('p-quantidade').value = row.dataset.quantidade;
    document.getElementById('p-cat').value        = row.dataset.categoria;
    document.getElementById('p-descricao').value  = row.dataset.descricao || '';
    document.getElementById('p-foto').value       = '';
    document.getElementById('p-foto-atual').textContent = row.dataset.foto
        ? ('Foto atual: ' + row.dataset.foto + ' — envie um novo arquivo apenas se quiser substituí-la.')
        : 'Este produto ainda não possui foto.';

    form.action = form.dataset.atualizarUrl;
    document.getElementById('form-produto-titulo').textContent = 'Editar produto';

    const box = document.getElementById('form-produto');
    box.classList.remove('hidden');
    box.scrollIntoView({ behavior: 'smooth', block: 'start' });
}

/* ─── Formulário de categoria: abrir em modo "novo" ─── */
function novaCategoria() {
    const form = document.getElementById('categoria-form');
    form.reset();
    document.getElementById('c-id').value = '';
    form.action = form.dataset.cadastrarUrl;
    document.getElementById('form-categoria-titulo').textContent = 'Nova categoria';

    const box = document.getElementById('form-categoria');
    box.classList.remove('hidden');
    box.scrollIntoView({ behavior: 'smooth', block: 'start' });
}

/* ─── Formulário de categoria: abrir em modo "edição" ─── */
function editarCategoria(btn) {
    const row = btn.closest('tr');
    const form = document.getElementById('categoria-form');

    document.getElementById('c-id').value = row.dataset.id;
    document.getElementById('c-nome').value = row.dataset.nome;
    document.getElementById('c-descricao').value = row.dataset.descricao || '';

    form.action = form.dataset.atualizarUrl;
    document.getElementById('form-categoria-titulo').textContent = 'Editar categoria';

    const box = document.getElementById('form-categoria');
    box.classList.remove('hidden');
    box.scrollIntoView({ behavior: 'smooth', block: 'start' });
}

/* ─── Ao carregar: reabre a aba correta após redirect e limpa os params da URL ─── */
document.addEventListener('DOMContentLoaded', function () {
    const params = new URLSearchParams(window.location.search);
    const secao = params.get('secao');

    if (secao) {
        const btn = document.querySelector('.nav-btn[data-secao="' + secao + '"]');
        if (btn) showPage(secao, btn);
    }

    if (params.has('sucesso') || params.has('erro')) {
        const url = new URL(window.location.href);
        url.searchParams.delete('sucesso');
        url.searchParams.delete('erro');
        url.searchParams.delete('secao');
        window.history.replaceState({}, '', url);
    }
});
