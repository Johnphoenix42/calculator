# Qual-Eval: A High-Precision Math Engine (Expression Evaluation Engine)
A thread-safe Expression Parser using my own version of Recursive Descent algorithm with $O(n)$ time complexity.

## Overview
The system developed in this project seeks to implement a high-performance Expression Entry model using JavaFX. While many modern apps (like the Windows (10/11) "Standard" calculator) still rely on immediate execution for simplicity, this project prioritizes accuracy through a Recursive Descent approach.
By leveraging Java's object-oriented capabilities, the proposed system treats every input as a Term within a Queue (More on this in chapter 3). This allows the system to not only handle standard operator precedence but also to solve nested parentheses of arbitrary depth—a feature often missing in basic calculator implementations.
Other innovations of the proposed system include:
Recursive Logic: The system handles nested parentheses (e.g., ((2+3)*5)/2) with a depth that is theoretically limited only by the system's memory.
State Persistence: The system allows the user to carry the "Last Answer" into new expressions seamlessly, mimicking the workflow of high-end scientific workstations.


## Recursive Descent and the Recursive Approach
For complex expression evaluation, the Recursive Descent methodology is often preferred over the Shunting yard approach (described in chapter 2). This approach treats a mathematical expression as a set of nested sub-problems (Milne & McAdam, 2011). By calling the evaluation function recursively, the system can handle parentheses and nested operations by solving the "inner" expressions before returning the result to the "outer" calculation. This method is highly compatible with Java’s object-oriented nature, simplifying the parsing of complex grammars into abstract syntax trees (Milne & McAdam, 2011).

## Project Architecture
### Structural Design (UML Class Diagram)
The class diagram represents the static structure of your application.
Term (Interface): This system defines a term as an expression that when resolved, stands as a meaningful single unit, i.e. resolves to an operand. This is the base type of everything the user can type in as part of an expression. Every element in the queue (defined in No. 2 of section 3.1) is a Term.
Operand (Class): Inherits from Term. It stores numeric values.
Subclasses: AddOperator, MultiplyOperator, DecimalPointOperator etc.
Operator (Abstract Class): Inherits from Term. It defines the abstract compute() method. Each operator defines how to compute the operands given to it.
Subclasses: AddOperator, MultiplyOperator, DecimalPointOperator etc.
ParenthesisOperator (Class): Usually modelled as a Term, but I have modelled it as an operator because it can sometimes be used in place of multiplication. It contains a boolean isOpen() to signal the start or end of a recursive evaluation block.
Expression Parser (Class): The "Engine." It contains your evaluateExpressionQueue(), which starts the recursive descent by running the computeExpression().

Fig 3-2	Class diagram of the System

### Behavioral Design (Use Case Diagram)
The Use Case diagram describes how the user interacts with these functions.
Actor: User
Use Cases:
Input Expression: (Interaction with JavaFX Buttons/Keyboard)
Clear Memory: (Resetting the Queue and Stack)
Execute Calculation: (Triggering the Recursive Evaluator)
Recall Previous Result: (Peeking into the executionStack)
Show Expression History: (Displaying past evaluated expression)
Change Evaluation Mode: (Change preferences on operand and ExpressionParser)

Fig 3-3 Use case Diagram for the Calculator System


### Behavioral Design (State Machine Diagram)
A state diagram is vital for a calculator because the system behaves differently depending on what the user just pressed.
Idle State: Waiting for input.
Input State: Building the expressionQueue.
Evaluation State: Running the recursive computeExpression() logic.
Result State: Displaying the final Operand.
Error State: Triggered if compute() fails (e.g., Syntax Error).

Fig 3-4		State machine diagram of the Calculator System
