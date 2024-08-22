package interpreter.inputs;

import java.util.Scanner;

public class ConsoleReadInput implements ReadInputSource {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String readInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
