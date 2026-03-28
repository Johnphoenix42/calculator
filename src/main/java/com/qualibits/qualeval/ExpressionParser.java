package com.qualibits.qualeval;

import com.qualibits.qualeval.operator.ParenthesisOperator;

import java.util.*;

public class ExpressionParser {

    private final LinkedList<Term> expressionQueue;
    private static LinkedList<ExecutionStackEntry> executionStack;

    public ExpressionParser(LinkedList<Term> expressionQueue){
        this.expressionQueue = expressionQueue;
    }

    public Operand evaluateExpressionQueue() {
        if (executionStack == null) throw new NullPointerException("Attach an executionMemory store to the parser");
        ListIterator<Term> queueIterator = expressionQueue.listIterator();

        ExecutionStackEntry lastEntry = executionStack.peek();
        Operand result = new Operand(lastEntry == null ? 0 : lastEntry.answer().getValue());

        while(queueIterator.hasNext()) {
            result = computeExpression(queueIterator.next(), queueIterator, result);
        }
        return result;
    }

    private Operand computeExpression(Term op, Iterator<Term> queueIterator, Operand result) {
        if (op instanceof Operand) return (Operand) op;
        if (op instanceof ParenthesisOperator pop) {
            if (pop.isOpen()) return evaluateParenthesis(queueIterator.next(), queueIterator, result);
        }
        try {
            Operand param = computeExpression(queueIterator.next(), queueIterator, result);
            return op.compute(null, result, param);
        } catch (NoSuchElementException | NumberFormatException e) {
            //expressionScreen.setText(e.getMessage());
            return new Operand(Double.NaN);
        }
    }

    private Operand evaluateParenthesis(Term op, Iterator<Term> queueIterator, Operand result) {
        if (op instanceof ParenthesisOperator && !((ParenthesisOperator)op).isOpen()) return result;
        Operand param = computeExpression(op, queueIterator, result);
        return evaluateParenthesis(queueIterator.next(), queueIterator, param);
    }

    public static void setExecutionMemory(LinkedList<ExecutionStackEntry> executionMemory) {
        ExpressionParser.executionStack = executionMemory;
    }

}
