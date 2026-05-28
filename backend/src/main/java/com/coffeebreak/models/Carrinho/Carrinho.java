package Carrinho;

import Usuario.Usuario;

import java.sql.Timestamp;

public class Carrinho {
    private int id;
    private String status;
    private Timestamp dataCriacao;
    private Usuario usuario;

    public Carrinho(int id, String status, Timestamp dataCriacao, Usuario usuario) {
        this.id = id;
        this.status = status;
        this.dataCriacao = dataCriacao;
        this.usuario = usuario;
    }
    public Carrinho(String status, Usuario usuario) {
        this.status = status;
        this.usuario = usuario;
    }

    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Timestamp getDataCriacao() {
        return this.dataCriacao;
    }
    public Usuario getUsuario() {
        return this.usuario;
    }

    @Override
    public String toString() {
        return "ID: " + this.id + " | Status: " + this.status + " | Usuario: " + this.usuario.getNome();
    }
}
