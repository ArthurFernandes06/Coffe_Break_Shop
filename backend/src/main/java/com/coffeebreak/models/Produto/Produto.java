package Produto;

import Categoria.Categoria;

public class Produto {
    private int id;
    private String nome;
    private double preco;
    private String foto;
    private String descricao;
    private int quantidade;
    private Categoria categoria;

    public Produto(int id, String nome, double preco, String foto, String descricao, int quantidade, Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.foto = foto;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.categoria = categoria;
    }

    public Produto(String nome, double preco, String foto, String descricao, int quantidade, Categoria categoria) {
        this.nome = nome;
        this.preco = preco;
        this.foto = foto;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.categoria = categoria;
    }

    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNome() {
        return this.nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public double getPreco() {
        return this.preco;
    }
    public void setPreco(double preco) {
        this.preco = preco;
    }
    public String getFoto() {
        return this.foto;
    }
    public void setFoto(String foto) {
        this.foto = foto;
    }
    public String getDescricao() {
        return this.descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public int getQuantidade() {
        return this.quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    public Categoria getCategoria() {
        return this.categoria;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "ID: " + this.id + " | Nome: " + this.nome + " | Preço: " + this.preco;
    }
 }
