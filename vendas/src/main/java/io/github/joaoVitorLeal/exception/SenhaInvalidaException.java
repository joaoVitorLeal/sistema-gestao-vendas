package io.github.joaoVitorLeal.exception;

public class SenhaInvalidaException extends RuntimeException {
    public SenhaInvalidaException() {
        super("Credenciais incorretas.");
    }
}
