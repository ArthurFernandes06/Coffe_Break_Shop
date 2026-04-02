const caminhoProduto = "../imgs/produtos/";
const produtos = {
    "produto01" : `${caminhoProduto}/produto_01.png`,
    "produto02" : `${caminhoProduto}/produto02.png`,
    "produto03" : `${caminhoProduto}/produto03.png`,
    "produto04" : `${caminhoProduto}/produto04.png`
};

document.addEventListener("DOMContentLoaded", () => {
    const section_main = document.getElementById("section_main");

    function ajuste_layout() 
    {
        if (window.innerWidth < 760) {
            section_main.style.display = "grid";
        } else {
            section_main.style.display = "flex";
        }
    }

    function carregar_produtos(produtos)
    {
        divOfertas = document.getElementById("div_ofertas");
        divProdutosDestaque = document.getElementById("div_produtos_destaque");
        divTodosProdutos = document.getElementById("div_todos_produtos");

            

        for(let produto in produtos)
        {
            let img1 = document.createElement("img");
            img1.src = produtos[produto];
            img1.classList.add("produto_img");

            let img2 = img1.cloneNode();
            let img3 = img1.cloneNode();

            divOfertas.appendChild(img1);
            divProdutosDestaque.appendChild(img2);
            divTodosProdutos.appendChild(img3);

        }
    }

    carregar_produtos(produtos);
    ajuste_layout();
    window.addEventListener("resize", ajuste_layout);
});