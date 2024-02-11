package com.teste.apipagamento.pagamento;

import jakarta.persistence.*;

@Entity
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int codigoDebito;
    @Column
    private String cpfCnpjPagador;
    @Column
    @Enumerated(EnumType.STRING)
    private MetodoPagamento metodoPagamento;
    @Column
    private String numeroCartao;
    @Column
    private double valorPagamento;
    @Column
    @Enumerated(EnumType.STRING)
    private StatusPagamento statusPagamento;

    public Pagamento(Integer codigoDebito, String cpfCnpjPagador, MetodoPagamento metodoPagamento, String numeroCartao, Double valorPagamento, StatusPagamento statusPagamento) {
        this.codigoDebito = codigoDebito;
        this.cpfCnpjPagador = cpfCnpjPagador;
        this.metodoPagamento = metodoPagamento;
        this.numeroCartao = numeroCartao;
        this.valorPagamento = valorPagamento;
        this.statusPagamento = statusPagamento;
    }

    public Pagamento() {

    }

    public int getCodigoDebito() {
        return codigoDebito;
    }

    public void setCodigoDebito(int codigoDebito) {
        this.codigoDebito = codigoDebito;
    }

    public String getCpfCnpjPagador() {
        return cpfCnpjPagador;
    }

    public void setCpfCnpjPagador(String cpfCnpjPagador) {
        this.cpfCnpjPagador = cpfCnpjPagador;
    }

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public Double getValorPagamento() {
        return valorPagamento;
    }

    public void setValorPagamento(Double valorPagamento) {
        this.valorPagamento = valorPagamento;
    }

    public StatusPagamento getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(StatusPagamento statusPagamento) {
        this.statusPagamento = statusPagamento;
    }
}
