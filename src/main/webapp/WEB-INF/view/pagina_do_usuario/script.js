const sections = document.querySelectorAll(".pagina")
const links = document.querySelectorAll("aside nav a")

links.forEach(link => {
    link.addEventListener("click", (event) => {
        sections.forEach(section => section.style.display = "none")
        
        const clickedsection = event.target.dataset.section
        document.getElementById(clickedsection).style.display = "block"
    })
})

const btnsEdit = document.querySelectorAll(".editar")

btnsEdit.forEach(btn => {
    btn.addEventListener("click", (event) => {
        const clickedbtn = event.target.dataset.campo
        
        const input = document.getElementById(`input-${clickedbtn}`)
        const label = document.querySelector(`label[for="input-${clickedbtn}"]`)
        const btnSalvar = document.getElementById(`btn-salvar-${clickedbtn}`)

        input.style.display = "block"
        label.style.display = "block"

        btnSalvar.style.display = "block"
        btn.style.display = "none"
    })
})

const btnSave = document.querySelectorAll(".salvar")

btnSave.forEach(btn => {
    btn.addEventListener("click", (event) => {
        const clickedbtn = event.target.dataset.campo

        const input = document.getElementById(`input-${clickedbtn}`)    
        const label = document.querySelector(`label[for="input-${clickedbtn}"]`)
        const valor = input.value 

        if (clickedbtn === "email" && !valor.includes("@")) {
            alert("Insira um email válido!")
            return
        }

        const btnEditar = document.getElementById(`btn-editar-${clickedbtn}`)

        const titulo = document.getElementById(`exibir-${clickedbtn}`)
        const prefixo = titulo.dataset.prefixo || ""

        titulo.textContent = prefixo + valor
        input.style.display = "none"
        label.style.display = "none"
        btnEditar.style.display = "block"
        btn.style.display = "none"
    })
})

const pedidos = [
  { produto: "Café Especial", data: "10/04/2025", status: "entregue", valor: 49.90 },
  { produto: "Xícara", data: "05/04/2025", status: "enviado", valor: 29.90 }
]

document.getElementById("total-pedidos").innerHTML = pedidos.length

const ultimoPedido = document.getElementById("ultimo-pedido")
ultimoPedido.innerHTML = pedidos[pedidos.length - 1].produto

const tbody = document.getElementById("historico-pedidos")

pedidos.forEach(pedido => {
  const linha = document.createElement("tr")
  linha.innerHTML = `
    <td>${pedido.produto}</td>
    <td>${pedido.data}</td>
    <td>${pedido.status}</td>
    <td>R$ ${pedido.valor}</td>
  `
  tbody.appendChild(linha)
})

const carrinho = [
  { produto: "Café", valor: 2.00 },
  { produto: "Coxinha", valor: 15.90 }
]

let preco_total = 0;

const precoTotal = document.getElementById("preco-total")
const itensCarrinho = document.getElementById("itens-carrinho")

carrinho.forEach(produto => {

    const linha = document.createElement("p")
    linha.innerHTML = `
    Produto: ${produto.produto} | Preço: ${produto.valor.toFixed(2)}
    `

    itensCarrinho.appendChild(linha)
    preco_total += produto.valor;
    precoTotal.innerHTML = `Preço total: R$${preco_total.toFixed(2)}`
})

const btnFinalizar = document.getElementById("btn-finalizar")
btnFinalizar.addEventListener("click", () => {
    precoTotal.innerHTML = `Preço total: R$ 0,00`
    itensCarrinho.innerHTML = 'Carrinho vazio...';
})

document.querySelector('[data-section="sair"]').addEventListener("click", () => {
    window.location.href = "../pagina_inicial/index.html"
})