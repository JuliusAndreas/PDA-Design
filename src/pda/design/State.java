package pda.design;


/**
 *
 * @author Julius Andreas
 */
public class State {
    public State(StateKind kind){
        this.kind = kind;
    }
        
    private StateKind kind;

    public StateKind getKind() {
        return kind;
    }
    
    @Override
    public String toString(){
        if(this.kind==StateKind.FINAL){
            return "qf";
        }
        if(this.kind==StateKind.INITIAL){
            return "q0";
        }
        else{
            return "q1";
        }
    }
}

