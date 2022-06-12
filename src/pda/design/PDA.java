package pda.design;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author Julius Andreas
 */
public class PDA {

    public PDA(Scanner scanner) {
        this.scanner = scanner;
        pdaStack = new Stack<Character>();
        pdaStack.push('z');
        initialState = new State(StateKind.INITIAL);
        middleState = new State(StateKind.MIDDLE);
        finalState = new State(StateKind.FINAL);
        parsers = new LinkedList<StatementParser>();
        while (true) {
            String tempInput = scanner.next();
            if (tempInput.equalsIgnoreCase("end")) {
                break;
            } else {
                parsers.add(new StatementParser(tempInput));
            }
        }
    }
    private Scanner scanner;
    private LinkedList<StatementParser> parsers;
    private final State initialState;
    private final State middleState;
    private final State finalState;
    private State currentState;
    static Stack<Character> pdaStack;
    private ArrayList<TransRule> transRules = new ArrayList<>();

    public void createPDA() {
        String s = parsers.getFirst().getVariableToDerive() + "";
        String initialPush = "" + s + "z" + "";
        this.transRules.add(new TransRule(
                initialState, middleState, "lambda", 'z', s + 'z' + ""));
        for (StatementParser parser : parsers) {
            for (int i = 0; i < parser.getTerminals().size(); i++) {
                String terminalInput = parser.getTerminals().get(i);
                String variablesToPush;
                if (parser.getVariables().get(i) == "") {
                    variablesToPush = "lambda";
                } else {
                    variablesToPush = parser.getVariables().get(i);
                }
                char topStack = parser.getVariableToDerive();
                this.transRules.add(new TransRule(
                        middleState, middleState, terminalInput, topStack, variablesToPush));
            }
        }
        this.transRules.add(new TransRule(
                middleState, finalState, "lambda", 'z', 'z' + ""));
        
        System.out.println("Here is your PDA transition funcion:");
        for (TransRule transRule : transRules) {
            System.out.println(transRule);
        }
    }

    public void accepter() {
        System.out.println("Now that your PDA is generated, you can enter some strings"
                + " to see if they are accepted by PDA or not, you can end the program "
                + "by simply typing *");
        while (true) {
            String checkingString = scanner.next();
            this.currentState = initialState;
            PDA.pdaStack.removeAllElements();
            PDA.pdaStack.push('z');
            for (int i = 0; i < checkingString.length(); i++) {
                if (checkingString.charAt(i) == '*') {
                    System.exit(0);
                }
            }
            Stack<Stack<Character>> backupStacks = new Stack();
            Stack<String> backupStrings = new Stack<>();
            if (this.tracker(checkingString, backupStacks, backupStrings)) {
                System.out.println("PDA accepted this string");
            } else {
                System.out.println("PDA did not accept this string");
            }
        }
    }

    public boolean tracker(
            String checkingString, Stack<Stack<Character>> backupStacks, Stack<String> backupStrings) {
        boolean result = false;
        if (this.currentState == initialState && PDA.pdaStack.peek() == 'z') {
            pdaStack.pop();
            currentState = middleState;
            int length = transRules.get(0).getToBePushed().length();
            for (int i = length - 1; i >= 0; i--) {
                pdaStack.push(transRules.get(0).getToBePushed().charAt(i));
            }
            return this.tracker(checkingString, backupStacks, backupStrings);
        }
        if (this.currentState == middleState && checkingString.isEmpty()
                && pdaStack.peek() == 'z') {
            pdaStack.pop();
            pdaStack.push('z');
            currentState = finalState;
            result = true;
            return result;
        } else if ((currentState == middleState) && (!checkingString.isEmpty())
                && (pdaStack.peek() != 'z')) {
            for (TransRule checkingTransRule : transRules) {
                if (checkingTransRule.getTopStack() == pdaStack.peek()
                        && checkingTransRule.getInput().equals(checkingString.charAt(0) + "")) {
                    Stack<Character> tempStack = new Stack<>();
                    tempStack.addAll(pdaStack);
                    backupStacks.push(tempStack);
                    String tempString = new String();
                    backupStrings.push(tempString.concat(checkingString));
                    pdaStack.pop();
                    int length = checkingTransRule.getToBePushed().length();
                    if (!checkingTransRule.getToBePushed().equals("lambda")) {
                        for (int i = length - 1; i >= 0; i--) {
                            pdaStack.push(checkingTransRule.getToBePushed().charAt(i));
                        }
                    }
                    if (checkingString.length() == 1) {
                        checkingString = "";
                    } else {
                        checkingString = checkingString.substring(1, checkingString.length());
                    }
                    result = this.tracker(checkingString, backupStacks, backupStrings);
                    if (!result) {
                        if (!(backupStacks.empty() && backupStrings.empty())) {
                            checkingString = backupStrings.pop();
                            pdaStack = backupStacks.pop();
                        }
                    }
                }
                if (result) {
                    return result;
                }
            }
        } else {
            result = false;
            return result;
        }
        return result;
    }

}
