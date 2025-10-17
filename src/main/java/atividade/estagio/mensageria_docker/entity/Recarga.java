package atividade.estagio.mensageria_docker.entity;


import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "recargas")
public class Recarga implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_celular")
    private String numeroCelular;

    @Column(name = "valor")
    private int valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusRecarga status;

    @Column(name = "correlation_id")
    private String correlationId;


    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;


    @ManyToOne
    @JoinColumn(name = "metodo_pagamento_id")
    private MetodoPagamento metodoPagamento;

    public Recarga(String numeroCelular, int valor, StatusRecarga status, String correlationId) {
        this.numeroCelular = numeroCelular;
        this.valor = valor;
        this.status = status;
        this.correlationId = correlationId;
    }

    public Recarga(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public StatusRecarga getStatus() {
        return status;
    }

    public void setStatus(StatusRecarga status) {
        this.status = status;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }
}







