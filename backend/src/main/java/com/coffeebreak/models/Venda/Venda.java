package com.coffeebreak.models.Venda;

import com.coffeebreak.models.Usuario.Usuario;

import java.sql.Timestamp;

public class Venda {
    private int id;
    private Timestamp dataHora;
    private double valorTotal;
    private String status; // PENDENTE, PAGO, ENVIADO, CANCELADO
    private Usuario usuario;

    public Venda(int id, Timestamp dataHora, double valorTotal, String status, Usuario usuario) {
        this.id = id;
        this.dataHora = dataHora;
        this.valorTotal = valorTotal;
        this.status = status;
        this.usuario = usuario;

    }

    public Venda(double valorTotal, String status, Usuario usuario) {
        this.valorTotal = valorTotal;
        this.status = status;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Timestamp getDataHora() {
        return dataHora;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    @Override
    public String toString() {
        return "ID: " + this.id + " | Usuario: " + this.usuario.getNome() + " | Total: R$" + this.valorTotal + " | Status: " + this.status;
    }
}
