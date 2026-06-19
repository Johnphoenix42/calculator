package com.qualibits.qualeval.exec;

import com.qualibits.qualeval.AppSetting;
import com.qualibits.qualeval.term.Operand;
import com.qualibits.qualeval.term.PartialOperand;
import com.qualibits.qualeval.term.Term;
import com.qualibits.qualeval.term.operator.Functions;
import com.qualibits.qualeval.term.operator.MultiplicationOperator;
import com.qualibits.qualeval.term.operator.Operator;
import com.qualibits.qualeval.term.Parenthesis;

import java.util.*;

public class ExpressionParser {

    private AppSetting appSetting;
    private final LinkedList<Term> expressionQueue;
    private static LinkedList<ExecutionStackEntry> executionStack;
    int pointer = 0; // a pointer for our current position in the expressionQueue

    private boolean shouldCloseParenthesis = false;

    public ExpressionParser(LinkedList<Term> expressionQueue){
        appSetting = AppSetting.getSettings();
        this.expressionQueue = expressionQueue;
    }

    public Operand evaluateExpressionQueue() throws ArithmeticException, NumberFormatException {
        if (executionStack == null) throw new NullPointerException("Attach an executionMemory store to the parser");

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
            return computeExpression(new MultiplicationOperator(), operand);
        }
    }

    private Operand computeExpression(Term op, Operand result) throws ArithmeticException, NumberFormatException {
        if (op instanceof Operand operand) {
            Term prevTerm = null;
            if (pointer - 1 >= 0) prevTerm = expressionQueue.get(pointer - 1);
            prevTerm = prevTerm instanceof Parenthesis ? null : prevTerm;
            Term nextTerm = null;
            if (pointer + 1 < expressionQueue.size()) nextTerm = expressionQueue.get(pointer + 1);
            nextTerm = nextTerm instanceof Parenthesis ? null : nextTerm;

            if ((prevTerm == null || prevTerm instanceof Operator) && (nextTerm == null || nextTerm instanceof Operator)){
                return computeNextOperand((Operator) prevTerm, operand, (Operator) nextTerm);
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
            return new Operand(Double.NaN);
        } catch (IndexOutOfBoundsException e) {
            return new Operand(Double.NaN);
        }
    }

    private Operand evaluateParenthesis(Term op, Operand result) {
        if (op instanceof Parenthesis parenthesis && !parenthesis.isOpen()) return result;
        Operand param = computeExpression(op, result);
        if (pointer + 1 < expressionQueue.size()) {
            return evaluateParenthesis(expressionQueue.get(++pointer), param);
        } else return result;
    }

    /**
     * Prints an expression unto a String object from the operation queue.
     * @return a String containing the expression
     */
    public String printExpressionQueue(Term token) {
        StringBuilder expressionString = new StringBuilder();
        if (!PartialOperand.getStringValue().isEmpty()) {
            Operand operand = new Operand(Double.parseDouble(PartialOperand.getStringValue()), PartialOperand.getStringValue());
            normalizeExpression(operand);
            expressionQueue.addLast(operand);
            if (shouldCloseParenthesis) expressionQueue.addLast(new Parenthesis(false));
            shouldCloseParenthesis = false;
        }

        if (token != null) {
            normalizeExpression(token);
            expressionQueue.addLast(token);
            if (shouldCloseParenthesis) expressionQueue.addLast(new Parenthesis(false));
            shouldCloseParenthesis = false;
        }

        for (Term term : expressionQueue) {
            String shownString = term.toString();
            // if term is an instance of Operand and operand alone, not PartialOperand
            if (term instanceof Operand && !(term instanceof PartialOperand)) {
                boolean shouldUseVariableName = appSetting.getScreenSettings().shouldUseVariableName();
                shownString = shouldUseVariableName ? ((Operand) term).getDenotation() : shownString;
            }
            expressionString.append(shownString);
        }
        PartialOperand.setStringValue("");
        return expressionString.toString();
    }

    /**
     * In the context of this app, normalizations refer to the adjustments made to the input to convert
     * inconsistent or undecipherable expressions in the computing system input and making it
     * decipherable so that the app can execute it safely without triggering errors based on certain
     * assumptions
     * The user should be able to opt out of the normalization
     * @param token the term just entered. The adjustment will be made before adding it to the
     *              expressionQueue
     */
    private void normalizeExpression(Term token){
        try {
            Term lastToken = expressionQueue.getLast();
            if (lastToken instanceof Operator && !(lastToken instanceof Functions)) return;
            // if the last token is not an open parenthesis, and token is of either type Operand,
            // open parenthesis or function operator
            if (!(lastToken instanceof Parenthesis p && p.isOpen()) && !(lastToken instanceof Functions) &&
                    ((token instanceof Operand || token instanceof Parenthesis p && p.isOpen()) ||
                            token instanceof Functions)) {
                expressionQueue.addLast(new MultiplicationOperator());
            }
            if (token instanceof Operand && lastToken instanceof Functions) {
                expressionQueue.addLast(new Parenthesis(true));
                shouldCloseParenthesis = true;
            }
        }catch (NoSuchElementException ne) {
            System.err.println("normalizeExpression >> "+ne.getMessage());
        }
    }

    public static void setExecutionMemory(LinkedList<ExecutionStackEntry> executionMemory) {
        ExpressionParser.executionStack = executionMemory;
    }

}
