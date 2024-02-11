package com.teste.apipagamento.pagamento;

public class ResponseDTO {
    private Object data;
    private String error;
    private int errorCode;

    public ResponseDTO(Object data){
        this.data = data;
    }

    public ResponseDTO(String error, int errorCode){
        this.error = error;
        this.errorCode = errorCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
