// ===== CARRINHO (estado global) =====
let carrinho = JSON.parse(localStorage.getItem("carrinho")) || [];

// ===== UTILIDADES =====
function formatarPreco(valor) {
    return valor.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
}

function salvarCarrinho() {
    localStorage.setItem("carrinho", JSON.stringify(carrinho));
}

// ===== DOM READY =====
document.addEventListener('DOMContentLoaded', () => {

    // ---------- Captura dos elementos ----------
    const inputBusca = document.getElementById('busca-nome');
    const selectCategoria = document.getElementById('filtro-categoria');
    const produtosCards = document.querySelectorAll('.card-produto');
    const botoesAdicionar = document.querySelectorAll('.btn-adicionar');

    // Carrinho – elementos do painel
    const carrinhoIconWrapper = document.getElementById('carrinho-icon-wrapper');
    const carrinhoBadge = document.getElementById('carrinho-badge');
    const carrinhoPainel = document.getElementById('carrinho-painel');
    const carrinhoOverlay = document.getElementById('carrinho-overlay');
    const carrinhoFechar = document.getElementById('carrinho-fechar');
    const carrinhoLista = document.getElementById('carrinho-lista');
    const carrinhoTotalValor = document.getElementById('carrinho-total-valor');

    // =============================================
    //  FILTROS
    // =============================================
    function filtrarProdutos() {
        const termoBusca = inputBusca.value.toLowerCase().trim();
        const categoriaSelecionada = selectCategoria.value.toLowerCase();

        // Itera sobre todos os cards de produtos
        produtosCards.forEach(card => {
            // Pegamos o nome do produto no card atual
            const nomeProduto = card.querySelector('.nome-produto').textContent.toLowerCase();

            // Pegamos a categoria no "data-categoria" attribute do card atual
            const categoriaProduto = card.getAttribute('data-categoria').toLowerCase();

            // Verifica se bate com a busca de texto
            const combinaComBusca = nomeProduto.includes(termoBusca);

            // Verifica se bate com o filtro de categoria
            const combinaComCategoria = (categoriaSelecionada === 'todos') || (categoriaProduto === categoriaSelecionada);

            // Oculta ou mostra baseado nos filtros
            if (combinaComBusca && combinaComCategoria) {
                card.classList.remove('escondido');
            } else {
                card.classList.add('escondido');
            }
        });
    }

    // Adiciona o EventListener para filtrar enquanto o usuário digita (tempo real)
    if (inputBusca) inputBusca.addEventListener('input', filtrarProdutos);

    // Adiciona o EventListener para filtrar sempre que houver mudança no Select
    if (selectCategoria) selectCategoria.addEventListener('change', filtrarProdutos);

    // =============================================
    //  BOTÕES "ADICIONAR" – com feedback visual
    // =============================================
    botoesAdicionar.forEach(botao => {
        botao.addEventListener('click', () => {
            const card = botao.closest('.card-produto');
            const nome = card.querySelector('.nome-produto').textContent;
            let precoTexto = card.querySelector('.preco-produto').textContent;
            let preco = parseFloat(precoTexto.replace('R$', '').replace(',', '.').trim());

            adicionarAoCarrinho(nome, preco);

            // Feedback visual inline (substitui o alert)
            const textoOriginal = botao.textContent;
            botao.textContent = '✓ Adicionado';
            botao.classList.add('adicionado');

            setTimeout(() => {
                botao.textContent = textoOriginal;
                botao.classList.remove('adicionado');
            }, 1200);
        });
    });

    // =============================================
    //  ABRIR / FECHAR PAINEL DO CARRINHO
    // =============================================
    function abrirPainel() {
        carrinhoPainel.classList.add('aberto');
        carrinhoOverlay.classList.add('ativo');
        document.body.style.overflow = 'hidden'; // impede scroll do body
        renderizarCarrinho();
    }

    function fecharPainel() {
        carrinhoPainel.classList.remove('aberto');
        carrinhoOverlay.classList.remove('ativo');
        document.body.style.overflow = '';
    }

    if (carrinhoIconWrapper) carrinhoIconWrapper.addEventListener('click', abrirPainel);
    if (carrinhoFechar) carrinhoFechar.addEventListener('click', fecharPainel);
    if (carrinhoOverlay) carrinhoOverlay.addEventListener('click', fecharPainel);

    // Fechar com ESC
    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape' && carrinhoPainel.classList.contains('aberto')) {
            fecharPainel();
        }
    });

    // =============================================
    //  RENDERIZAR ITENS DO CARRINHO NO PAINEL
    // =============================================
    function renderizarCarrinho() {
        carrinhoLista.innerHTML = '';

        if (carrinho.length === 0) {
            carrinhoLista.innerHTML = '<p class="carrinho-vazio">Seu carrinho está vazio</p>';
            carrinhoTotalValor.textContent = 'R$ 0,00';
            return;
        }

        carrinho.forEach((item, index) => {
            const div = document.createElement('div');
            div.className = 'carrinho-item';

            div.innerHTML = `
                <div class="carrinho-item-info">
                    <p class="carrinho-item-nome">${item.nome}</p>
                    <p class="carrinho-item-preco">${formatarPreco(item.preco)} cada</p>
                </div>
                <div class="carrinho-item-qty">
                    <button data-action="diminuir" data-index="${index}" aria-label="Diminuir quantidade">−</button>
                    <span>${item.quantidade}</span>
                    <button data-action="aumentar" data-index="${index}" aria-label="Aumentar quantidade">+</button>
                </div>
                <span class="carrinho-item-subtotal">${formatarPreco(item.preco * item.quantidade)}</span>
                <button class="carrinho-item-remover" data-action="remover" data-index="${index}" aria-label="Remover item" title="Remover">&times;</button>
            `;

            carrinhoLista.appendChild(div);
        });

        // Calcula e exibe o total geral
        atualizarTotal();
    }

    // =============================================
    //  ATUALIZAR TOTAL DO CARRINHO
    // =============================================
    function atualizarTotal() {
        const total = carrinho.reduce((acc, item) => acc + item.preco * item.quantidade, 0);
        carrinhoTotalValor.textContent = formatarPreco(total);
    }

    // =============================================
    //  AÇÕES DENTRO DO PAINEL (delegação de evento)
    // =============================================
    carrinhoLista.addEventListener('click', (e) => {
        const botao = e.target.closest('[data-action]');
        if (!botao) return;

        const action = botao.dataset.action;
        const index = parseInt(botao.dataset.index, 10);

        switch (action) {
            case 'aumentar':
                carrinho[index].quantidade++;
                break;

            case 'diminuir':
                if (carrinho[index].quantidade > 1) {
                    carrinho[index].quantidade--;
                } else {
                    carrinho.splice(index, 1);
                }
                break;

            case 'remover':
                carrinho.splice(index, 1);
                break;
        }

        salvarCarrinho();
        atualizarBadge();
        renderizarCarrinho();
    });

    // =============================================
    //  BADGE – atualizar quantidade
    // =============================================
    function atualizarBadge() {
        const quantidadeTotal = carrinho.reduce((total, item) => total + item.quantidade, 0);
        carrinhoBadge.textContent = quantidadeTotal;

        // Animação de bump
        carrinhoBadge.classList.remove('bump');
        // Force reflow para reiniciar a animação
        void carrinhoBadge.offsetWidth;
        carrinhoBadge.classList.add('bump');
    }

    // Atualiza o badge ao carregar a página (caso haja itens salvos)
    atualizarBadge();

    // =============================================
    //  EXPÕE A FUNÇÃO GLOBAL DE ADICIONAR
    // =============================================
    window._atualizarBadge = atualizarBadge;
    window._renderizarCarrinho = renderizarCarrinho;
});

// =============================================
//  ADICIONAR AO CARRINHO (escopo global)
// =============================================
function adicionarAoCarrinho(nome, preco) {
    // Verifica se o produto já está no carrinho
    const produtoExistente = carrinho.find(item => item.nome === nome);

    if (produtoExistente) {
        // Se já existe, aumenta a quantidade
        produtoExistente.quantidade++;
    } else {
        // Se não, adiciona um novo produto
        carrinho.push({ nome, preco, quantidade: 1 });
    }

    // Atualiza o carrinho no localStorage
    salvarCarrinho();

    // Atualiza badge e painel
    if (window._atualizarBadge) window._atualizarBadge();
    if (window._renderizarCarrinho) window._renderizarCarrinho();
}

function atualizarCarrinhoNaTela() {
    if (window._atualizarBadge) window._atualizarBadge();
}