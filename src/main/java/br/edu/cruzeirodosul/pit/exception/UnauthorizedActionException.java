package br.edu.cruzeirodosul.pit.exception;

public class UnauthorizedActionException extends RuntimeException{
    public UnauthorizedActionException(String msg) {
        super(msg);
    }
}
