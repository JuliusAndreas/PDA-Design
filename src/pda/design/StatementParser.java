package pda.design;

import java.util.LinkedList;

/**
 *
 * @author Julius Andreas
 */
public final class StatementParser {

    public StatementParser(String tempInput) {
        variables = new LinkedList<String>();
        terminals = new LinkedList<String>();
        this.variableToDerive = tempInput.charAt(0);
        int i = 3;
        String tempString;
        do {
            tempString = "";
            terminals.add(tempInput.charAt(i) + "");
            i++;
            while (true) {
                if (i < tempInput.length() && tempInput.charAt(i) == '|') {
                    i++;
                    break;
                }
                if (i < tempInput.length()) {
                    tempString += tempInput.charAt(i);
                    i++;
                } else {
                    break;
                }
            }
            variables.add(tempString);
            if (i >= tempInput.length()) {
                break;
            }
        } while (true);
    }

    private char variableToDerive;
    private LinkedList<String> terminals;
    private LinkedList<String> variables;

    public char getVariableToDerive() {
        return variableToDerive;
    }

    public LinkedList<String> getTerminals() {
        return terminals;
    }

    public LinkedList<String> getVariables() {
        return variables;
    }

}
