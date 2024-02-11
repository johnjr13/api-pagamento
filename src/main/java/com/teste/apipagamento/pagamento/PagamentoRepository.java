package com.teste.apipagamento.pagamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    @Query("SELECT p FROM Pagamento p " +
            "       WHERE (:codigoDebito IS NULL OR p.codigoDebito = :codigoDebito) " +
            "       AND (:cpfCnpjPagador IS NULL OR p.cpfCnpjPagador = :cpfCnpjPagador)  "+
            "       AND (:statusPagamento IS NULL OR p.statusPagamento = :statusPagamento)")
    List<Pagamento> findByFiltros(
            @Param("codigoDebito") Integer codigoDebito,
            @Param("cpfCnpjPagador") String cpfCnpjPagador,
            @Param("statusPagamento") StatusPagamento statusPagamento
    );
}

