// ── Navegação lateral ──────────────────────────────────────────
const sections = document.querySelectorAll(".pagina")
const links = document.querySelectorAll(".nav-link[data-section]")

function mostrarSecao(id) {
    sections.forEach(s => s.style.display = "none")
    links.forEach(l => l.classList.remove("active"))
    const alvo = document.getElementById(id)
    if (alvo) alvo.style.display = "block"
    const linkAtivo = document.querySelector(`.nav-link[data-section="${id}"]`)
    if (linkAtivo) linkAtivo.classList.add("active")
}

// Abre "perfil" por padrão
mostrarSecao("perfil")

links.forEach(link => {
    link.addEventListener("click", (e) => {
        e.preventDefault()
        mostrarSecao(link.dataset.section)
    })
})

// ── Editar campos ───────────────────────────────────────────────
let camposEditados = new Set()

const btnsEdit = document.querySelectorAll(".editar")
btnsEdit.forEach(btn => {
    btn.addEventListener("click", () => {
        const campo = btn.dataset.campo
        if (!campo) return
        document.getElementById(`input-${campo}`).style.display = "block"
        document.querySelector(`label[for="input-${campo}"]`).style.display = "block"
        document.getElementById(`btn-salvar-${campo}`).style.display = "inline-block"
        btn.style.display = "none"
    })
})

const btnSave = document.querySelectorAll(".salvar")
btnSave.forEach(btn => {
    btn.addEventListener("click", () => {
        const campo = btn.dataset.campo
        const input = document.getElementById(`input-${campo}`)
        const valor = input.value.trim()

        if (!valor) {
            alert("O campo não pode ficar vazio!")
            return
        }
        if (campo === "email" && !valor.includes("@")) {
            alert("Insira um e-mail válido!")
            return
        }

        const exibir = document.getElementById(`exibir-${campo}`)
        if (campo === "senha") {
            exibir.textContent = "••••••••"
        } else {
            exibir.textContent = valor
        }

        input.style.display = "none"
        document.querySelector(`label[for="input-${campo}"]`).style.display = "none"
        document.getElementById(`btn-editar-${campo}`).style.display = "inline-block"
        btn.style.display = "none"

        camposEditados.add(campo)
        document.getElementById("btn-atualizar-dados").style.display = "inline-block"
    })
})

// ── Pedidos ─────────────────────────────────────────────────────
const pedidos = [
    { produto: "Café Especial", data: "10/04/2025", status: "entregue", valor: 49.90 },
    { produto: "Xícara",        data: "05/04/2025", status: "enviado",  valor: 29.90 }
]
document.getElementById("total-pedidos").textContent = pedidos.length
document.getElementById("ultimo-pedido").textContent = pedidos[pedidos.length - 1].produto

const tbody = document.getElementById("historico-pedidos")
pedidos.forEach(p => {
    const tr = document.createElement("tr")
    tr.innerHTML = `<td>${p.produto}</td><td>${p.data}</td><td>${p.status}</td><td>R$ ${p.valor.toFixed(2)}</td>`
    tbody.appendChild(tr)
})

// ── Carrinho ────────────────────────────────────────────────────
const carrinho = [
    { produto: "Café",    valor: 2.00  },
    { produto: "Coxinha", valor: 15.90 }
]
let precoTotalVal = 0
const precoTotalEl   = document.getElementById("preco-total")
const itensCarrinhoEl = document.getElementById("itens-carrinho")

carrinho.forEach(item => {
    const p = document.createElement("p")
    p.textContent = `Produto: ${item.produto} | Preço: R$ ${item.valor.toFixed(2)}`
    itensCarrinhoEl.appendChild(p)
    precoTotalVal += item.valor
})
precoTotalEl.textContent = `Preço total: R$ ${precoTotalVal.toFixed(2)}`

document.getElementById("btn-finalizar").addEventListener("click", () => {
    precoTotalEl.textContent = "Preço total: R$ 0,00"
    itensCarrinhoEl.innerHTML = "Carrinho vazio..."
})

// ── Modal excluir conta ─────────────────────────────────────────
const modalDeletar      = document.getElementById("modal-deletar")
const btnAbrirModal     = document.getElementById("btn-abrir-modal-deletar")
const btnCancelarModal  = document.getElementById("btn-cancelar-deletar")

btnAbrirModal.addEventListener("click", () => {
    modalDeletar.style.display = "flex"
})
btnCancelarModal.addEventListener("click", () => {
    modalDeletar.style.display = "none"
})
modalDeletar.addEventListener("click", (e) => {
    if (e.target === modalDeletar) modalDeletar.style.display = "none"
})