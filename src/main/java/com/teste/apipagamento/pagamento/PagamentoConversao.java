package com.teste.apipagamento.pagamento;

import org.springframework.stereotype.Component;

@Component
public class PagamentoConversao {
    public PagamentoDTO toDTO(Pagamento pagamento){
        PagamentoDTO pagamentoDTO = new PagamentoDTO();
        pagamentoDTO.setCodigoDebito(pagamento.getCodigoDebito());
        pagamentoDTO.setCpfCnpjPagador(pagamento.getCpfCnpjPagador());
        pagamentoDTO.setMetodoPagamento(pagamento.getMetodoPagamento());
        pagamentoDTO.setNumeroCartao(pagamento.getNumeroCartao());
        pagamentoDTO.setValorPagamento(pagamento.getValorPagamento());
        pagamentoDTO.setStatusPagamento(pagamento.getStatusPagamento());
        return pagamentoDTO;
    }

    public Pagamento toEntity(PagamentoDTO pagamentoDTO) {
        Pagamento pagamento = new Pagamento();
        pagamento.setCpfCnpjPagador(pagamentoDTO.getCpfCnpjPagador());
        pagamento.setMetodoPagamento(pagamentoDTO.getMetodoPagamento());
        pagamento.setNumeroCartao(pagamentoDTO.getNumeroCartao());
        pagamento.setValorPagamento(pagamentoDTO.getValorPagamento());
        pagamento.setStatusPagamento(StatusPagamento.PENDENTE_DE_PROCESSAMENTO);
        return pagamento;
    }
}
