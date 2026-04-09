const sections = document.querySelectorAll(".pagina")
const links = document.querySelectorAll("aside nav a")

links.forEach(link => {
    link.addEventListener("click", (event) => {
        sections.forEach(section => section.style.display = "none")
        
        const clickedsection = event.target.dataset.section
        document.getElementById(clickedsection).style.display = "block"
    })
})