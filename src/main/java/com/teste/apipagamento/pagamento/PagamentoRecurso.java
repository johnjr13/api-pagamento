package com.teste.apipagamento.pagamento;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/pagamentos")
@Tag(name = "API Pagamentos")
public class PagamentoRecurso {
    private final PagamentoService pagamentoService;
    private final PagamentoConversao pagamentoConversao;

    @Autowired
    public PagamentoRecurso(PagamentoService pagamentoService, PagamentoConversao pagamentoConversao) {
        this.pagamentoService = pagamentoService;
        this.pagamentoConversao = pagamentoConversao;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lista todos os pagamentos.")
    public List<PagamentoDTO> obterTodosPagamentos() {
        return pagamentoService.findAllPagamentos()
                .stream()
                .map(pagamentoConversao::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca pagamento por Id")
    public ResponseEntity<PagamentoDTO> obterPagamentoById(@PathVariable Long id){
        return pagamentoService.findPagamentoById(id)
                .map(pagamentoConversao::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Cria um novo pagamento.")
    public ResponseEntity<ResponseDTO> criarPagamento(@RequestBody PagamentoDTO pagamentoDTO) {
        try {
            return ResponseEntity.ok(new ResponseDTO(pagamentoConversao.toDTO(pagamentoService.salvarPagamento(pagamentoConversao.toEntity(pagamentoDTO)))));
        } catch (IllegalStateException e){
            return ResponseEntity.badRequest().body(new ResponseDTO("Erro ao cadastrar pagamento: " + e.getMessage(), 400));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ResponseDTO("Erro ao cadastrar pagamento.", 500));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um pagamento")
    public ResponseEntity<String> excluirPagamento(@PathVariable Long id) {
        try{
            pagamentoService.excluirPagamento(id);
            return ResponseEntity.ok("Pagamento excluído com sucesso!");
        } catch (EmptyResultDataAccessException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pagamento não encontrado!");
        } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir o pagamento: " + e.getMessage());
    }
    }

    @PatchMapping("/atualizar-status/{id}")
    @Operation(summary = "Atualiza o status de um pagamento.")
    public ResponseEntity<String> atualizarPagamento (@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
        try {
            pagamentoService.atualizarPagamento(id, StatusPagamento.valueOf(requestBody.get("status")));
            return ResponseEntity.ok("Status do pagamento atualizado com sucesso!");
        }catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar Status" + e.getMessage());
        }
    }

    @GetMapping("/buscar-pagamentos")
    @Operation(summary = "Busca pagamentos por filtros(codigoDebito, cpfCnpjPagador, statusPagamento")
    public List<PagamentoDTO> buscarPagamentosComFiltros(
            @RequestParam (required = false) Integer codigoDebito,
            @RequestParam (required = false) String cpfCnpjPagador,
            @RequestParam (required = false) StatusPagamento statusPagamento){
        return pagamentoService.findPagamentosByFiltros(codigoDebito, cpfCnpjPagador, statusPagamento)
                .stream()
                .map(pagamentoConversao::toDTO)
                .toList();
    }



}
