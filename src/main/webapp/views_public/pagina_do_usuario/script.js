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

// Abre a seção indicada pelo servlet ou "perfil" por padrão
mostrarSecao(window.activeSection || "perfil")

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
