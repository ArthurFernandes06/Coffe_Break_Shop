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
        const btnEditar = document.getElementById(`btn-editar-${clickedbtn}`)

        const titulo = document.getElementById(`exibir-${clickedbtn}`)
        titulo.innerHTML = valor

        input.style.display = "none"
        label.style.display = "none"

        btnEditar.style.display = "block"
        btn.style.display = "none"
    })
})