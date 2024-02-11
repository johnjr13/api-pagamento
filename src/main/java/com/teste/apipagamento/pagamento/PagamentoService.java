package com.teste.apipagamento.pagamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;

    @Autowired
    public PagamentoService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    public List<Pagamento> findAllPagamentos(){
        return pagamentoRepository.findAll();
    }

    public Optional<Pagamento> findPagamentoById(long id){
        return pagamentoRepository.findById(id);
    }

    public List<Pagamento> findPagamentosByFiltros(Integer codigoDebito, String cpfCnpjPagador, StatusPagamento statusPagamento){
        return pagamentoRepository.findByFiltros(codigoDebito, cpfCnpjPagador, statusPagamento);
    }

    public Pagamento salvarPagamento(Pagamento pagamento){
        pagamento.setStatusPagamento(StatusPagamento.PENDENTE_DE_PROCESSAMENTO);
        if (pagamento.getMetodoPagamento().equals(MetodoPagamento.PIX) && pagamento.getNumeroCartao() != null) {
            throw new IllegalStateException("Pagamento via PIX não necessita informar número do cartão!");
        }
        else if (pagamento.getMetodoPagamento().equals(MetodoPagamento.BOLETO) && pagamento.getNumeroCartao() != null) {
            throw new IllegalStateException("Pagamento via BOLETO não deve informar número do cartão!");
        }
        else if (pagamento.getMetodoPagamento().equals(MetodoPagamento.CARTAO_CREDITO) &&
                    pagamento.getMetodoPagamento().equals(MetodoPagamento.CARTAO_DEBITO) && pagamento.getNumeroCartao() == null){
            throw new IllegalStateException("Pagamento via CARTAO_CREDITO ou CARTAO_DEBITO é necessário informar número do cartão!");
        }
        return pagamentoRepository.save(pagamento);
    }
    public void excluirPagamento(Long id) {
        Pagamento pagamento = findPagamentoById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException("Pagamento não encontrado", 1));
        if (pagamento.getStatusPagamento().equals(StatusPagamento.PENDENTE_DE_PROCESSAMENTO)) {
            pagamentoRepository.deleteById(id);
        } else {
            throw new IllegalStateException("Não é possível excluir um pagamento com status diferente de PENDENTE_DE_PROCESSAMENTO");
        }
    }
    public Pagamento atualizarPagamento(Long id, StatusPagamento novoStatus) {
        return pagamentoRepository.findById(id)
                .map(pagamento -> {
                    StatusPagamento statusAtual = pagamento.getStatusPagamento();
                    if (statusAtual == StatusPagamento.PENDENTE_DE_PROCESSAMENTO) {
                        if (novoStatus == StatusPagamento.PROCESSADO_COM_SUCESSO || novoStatus == StatusPagamento.PROCESSADO_COM_FALHA) {
                            pagamento.setStatusPagamento(novoStatus);
                            return pagamentoRepository.save(pagamento);
                        } else {
                            throw new IllegalStateException("Novo Status inválido para pagamento");
                        }
                    } else if (statusAtual == StatusPagamento.PROCESSADO_COM_FALHA && novoStatus == StatusPagamento.PENDENTE_DE_PROCESSAMENTO) {
                        pagamento.setStatusPagamento(novoStatus);
                        return pagamentoRepository.save(pagamento);
                    } else {
                        throw new IllegalStateException("O pagamento não pode ter seu status alterado.");
                    }
                })
                .orElseThrow(() -> new IllegalStateException("Pagamento não encontrado"));
    }

}
