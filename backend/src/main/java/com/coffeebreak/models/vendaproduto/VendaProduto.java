package vendaproduto;

import Produto.Produto;
import Venda.Venda;

public class VendaProduto {
    private Venda venda;
    private Produto produto;
    private int quantidade;
    private double precoUnitario;

    public VendaProduto(Venda venda, Produto produto, int quantidade, double precoUnitario) {
        this.venda = venda;
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    @Override
    public String toString() {
        return "Venda: " + this.venda.getId() + " | Produto: " + this.produto.getNome() + " | Quantidade: " + this.quantidade + " | Preço Unitario: R$" + this.precoUnitario;
    }
}
