package com.qualibits.qualeval.exec;

import com.qualibits.qualeval.term.Operand;
import com.qualibits.qualeval.term.Term;
import com.qualibits.qualeval.term.operator.ParenthesisOperator;

import java.util.*;

public class ExpressionParser {

    private final LinkedList<Term> expressionQueue;
    private ListIterator<Term> queueIterator;
    private static LinkedList<ExecutionStackEntry> executionStack;

    public ExpressionParser(LinkedList<Term> expressionQueue){
        this.expressionQueue = expressionQueue;
    }

    public Operand evaluateExpressionQueue() throws ArithmeticException, NumberFormatException {
        if (executionStack == null) throw new NullPointerException("Attach an executionMemory store to the parser");
        queueIterator = expressionQueue.listIterator();

        ExecutionStackEntry lastEntry = executionStack.peek();
        Operand result = new Operand(lastEntry == null ? 0 : lastEntry.answer().getValue());

        while(queueIterator.hasNext()) {
            result = computeExpression(queueIterator.next(), result);
        }
        return result;
    }

    private Operand computeExpression(Term op, Operand result) throws ArithmeticException, NumberFormatException {
        if (op instanceof Operand) return (Operand) op;
        if (op instanceof ParenthesisOperator pop) {
            if (pop.isOpen()) return evaluateParenthesis(queueIterator.next(), result);
        }
        try {
            Operand param = computeExpression(queueIterator.next(), result);
            return op.compute(null, result, param);
        } catch (NoSuchElementException e) {
            //expressionScreen.setText(e.getMessage());
            System.out.println(e.getMessage());
            return new Operand(Double.NaN);
        }
    }

    private Operand evaluateParenthesis(Term op, Operand result) {
        if (op instanceof ParenthesisOperator && !((ParenthesisOperator)op).isOpen()) return result;
        Operand param = computeExpression(op, result);
        return evaluateParenthesis(queueIterator.next(), param);
    }

    public static void setExecutionMemory(LinkedList<ExecutionStackEntry> executionMemory) {
        ExpressionParser.executionStack = executionMemory;
    }

}
