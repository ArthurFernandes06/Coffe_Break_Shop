package com.coffeebreak.models.Usuario;

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private boolean tipo;

    public Usuario() {};

    public Usuario(int id, String nome, String email, String senha, boolean tipo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
    }
    public Usuario(String nome, String email, String senha, boolean tipo) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
    }
    public int getId() {return this.id;}
    public void setId(int id) {this.id = id;}

    public String getNome() {return this.nome;}
    public void setNome(String nome) {this.nome = nome;}

    public String getEmail() {return this.email;}
    public void setEmail(String email) {this.email = email;}

    public String getSenha() {return this.senha;}
    public void setSenha(String senha) {this.senha = senha;}

    public boolean isTipo() {return this.tipo;}
    public void setTipo(boolean tipo) {this.tipo = tipo;}

    @Override
    public String toString() {
        return "ID: " + this.id + " | NOME: " + this.nome;
    }
}

