package pda.design;

import java.util.LinkedList;

/**
 *
 * @author Julius Andreas
 */
public class TransRule {
    public TransRule(State cuurentState, State nextState, String input,
            char topStack, String toBePushed){
        this.nextState = nextState;
        this.cuurentState = cuurentState;
        this.input = input;
        this.toBePushed = toBePushed;
        this.topStack = topStack;
    }
    
    
    private final State cuurentState;
    private final State nextState;
    private final char topStack;
    private final String input;
    private final String toBePushed;
    
    @Override
    public String toString() {
        return "delta"+"("+getCuurentState()+","+getInput()+","+getTopStack()+
                ")=("+getNextState()+","+getToBePushed()+")";
    }

    /**
     * @return the cuurentState
     */
    public State getCuurentState() {
        return cuurentState;
    }

    /**
     * @return the nextState
     */
    public State getNextState() {
        return nextState;
    }

    /**
     * @return the topStack
     */
    public char getTopStack() {
        return topStack;
    }

    /**
     * @return the input
     */
    public String getInput() {
        return input;
    }

    /**
     * @return the toBePushed
     */
    public String getToBePushed() {
        return toBePushed;
    }
}
