package com.qualibits.jeval;

import java.util.LinkedList;

public record ExecutionStackEntry(LinkedList<Term> termList, Operand answer) {}
