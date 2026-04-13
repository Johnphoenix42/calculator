package com.qualibits.qualeval.exec;

import com.qualibits.qualeval.term.Operand;
import com.qualibits.qualeval.term.Term;
import com.qualibits.qualeval.term.operator.MultiplicationOperator;
import com.qualibits.qualeval.term.operator.Operator;
import com.qualibits.qualeval.term.Parenthesis;

import java.util.*;

public class ExpressionParser {

    private final LinkedList<Term> expressionQueue;
    private ListIterator<Term> queueIterator;
    private static LinkedList<ExecutionStackEntry> executionStack;
    int pointer = 0;

    public ExpressionParser(LinkedList<Term> expressionQueue){
        this.expressionQueue = expressionQueue;
    }

    public Operand evaluateExpressionQueue() throws ArithmeticException, NumberFormatException {
        if (executionStack == null) throw new NullPointerException("Attach an executionMemory store to the parser");
        queueIterator = expressionQueue.listIterator();

        ExecutionStackEntry lastEntry = executionStack.peek();
        Operand result = new Operand(lastEntry == null ? 0 : lastEntry.answer().getValue());

        while (pointer < expressionQueue.size()) {
            Term term = expressionQueue.get(pointer);
            result = computeExpression(term, result);
            pointer++;
        }
        pointer = 0;
        return result;
    }

    private Operand computeNextOperand (Operator leftOperator, Operand operand, Operator rightOperator) {
        if (Operator.firstPrecedesSecond(leftOperator, rightOperator)) return operand;
        Term nextTerm = expressionQueue.get(++pointer);
        if (nextTerm instanceof Operator operator)
            return computeExpression(operator, operand);
        else {
            --pointer;
            return computeNextExpression(new MultiplicationOperator(), operand);
        }
    }

    /**
     * Computes an operand from
     * @param operator the associating operator
     * @param leftOperand the base or initial value like in an accumulator
     * @return an operand
     */
    private Operand computeNextExpression (Operator operator, Operand leftOperand) {
        Term nextTerm = expressionQueue.get(++pointer);
        if (nextTerm instanceof Operand operand) {
            Term nextForwardPeek = null;
            if (pointer + 1 < expressionQueue.size()) nextForwardPeek = expressionQueue.get(pointer + 1);
            if (nextForwardPeek instanceof Operator nextOperatorPeek) {
                Operand rightOperand = computeNextOperand(operator, operand, nextOperatorPeek);
                return operator.compute(null, rightOperand, leftOperand);
            }
        } else if (nextTerm instanceof Parenthesis parenthesis) {
            if (parenthesis.isOpen()) return evaluateParenthesis(expressionQueue.get(++pointer), leftOperand);
        }
        throw new ExpressionFormatException("computeNextException: expression not properly formed");
    }

    private Operand computeExpression(Term op, Operand result) throws ArithmeticException, NumberFormatException {
        if (op instanceof Operand operand) {
            Term prevTerm = null;
            if (pointer - 1 >= 0) prevTerm = expressionQueue.get(pointer - 1);
            prevTerm = prevTerm instanceof Parenthesis ? null : prevTerm;
            Term nextTerm = null;
            if (pointer + 1 < expressionQueue.size()) nextTerm = expressionQueue.get(pointer + 1);
            nextTerm = nextTerm instanceof Parenthesis ? null : nextTerm;

            System.out.println(prevTerm + ", " + nextTerm);
            if ((prevTerm == null || prevTerm instanceof Operator) && (nextTerm == null || nextTerm instanceof Operator)) {
                Operand nextOperand = computeNextOperand((Operator) prevTerm, operand, (Operator) nextTerm);
                System.out.println(nextOperand);
                return nextOperand;
            } else throw new ExpressionFormatException();
        } else if (op instanceof Parenthesis pop) {
            if (pop.isOpen()) return evaluateParenthesis(expressionQueue.get(++pointer), result);
        }
        try {
            pointer++;
            Operand param = computeExpression(expressionQueue.get(pointer), result);
            return op.compute(null, result, param);
        } catch (NoSuchElementException e) {
            //expressionScreen.setText(e.getMessage());
            System.out.println(e.getMessage());
            return new Operand(Double.NaN);
        }
    }

    private Operand evaluateParenthesis(Term op, Operand result) {
        if (op instanceof Parenthesis parenthesis && !parenthesis.isOpen()) return result;
        Operand param = computeExpression(op, result);
        return evaluateParenthesis(expressionQueue.get(++pointer), param);
    }

    public static void setExecutionMemory(LinkedList<ExecutionStackEntry> executionMemory) {
        ExpressionParser.executionStack = executionMemory;
    }

}
