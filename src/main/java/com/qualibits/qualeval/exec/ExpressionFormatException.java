package com.qualibits.qualeval.exec;

public class ExpressionFormatException extends RuntimeException{

    public ExpressionFormatException() {
        super("Expression not well-formed");
    }

    public ExpressionFormatException(String reason) {
        super(reason);
    }
}
