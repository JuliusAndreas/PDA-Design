package pda.design;

import java.util.Scanner;

/**
 *
 * @author Julius Andreas
 */
public class PDADesign {

    public static void main(String[] args) {
        System.out.println("Please enter your grammar rules and seperate them with "
                + "new line and don't use space, finally finish it by typing end:");
        Scanner scanner = new Scanner(System.in);
        PDA pda = new PDA(scanner);
        pda.createPDA();
        pda.accepter();
    }
    
}
