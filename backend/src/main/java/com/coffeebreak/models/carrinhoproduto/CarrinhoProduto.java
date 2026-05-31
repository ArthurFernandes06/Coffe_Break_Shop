package com.coffeebreak.models.carrinhoproduto;

import com.coffeebreak.models.Carrinho.Carrinho;
import com.coffeebreak.models.Produto.Produto;

public class CarrinhoProduto {
    private Carrinho carrinho;
    private Produto produto;
    private int quantidade;

    public CarrinhoProduto(Carrinho carrinho, Produto produto, int quantidade) {
        this.carrinho = carrinho;
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "Carrinho: " + this.carrinho.getId() + " | Produto: " + this.produto.getNome() + " | Quantidade: " + this.quantidade;
    }
}
