package calculator;

public class PiOperand extends Operand{

    public PiOperand(){
        super(Math.PI);
    }

    @Override
    public String toString() {
        return "(⫪)";
    }
}
