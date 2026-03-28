package com.qualibits.qualeval;

import java.util.LinkedList;

public record ExecutionStackEntry(LinkedList<Term> termList, Operand answer) {}
