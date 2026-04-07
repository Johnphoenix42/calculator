package com.qualibits.qualeval.exec;

import com.qualibits.qualeval.term.Operand;
import com.qualibits.qualeval.term.Term;

import java.util.LinkedList;

public record ExecutionStackEntry(LinkedList<Term> termList, Operand answer) {}
