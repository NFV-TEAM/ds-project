package gr.hua.dit.ds.springbootdemo.cli;

import java.io.Console;
import java.util.Scanner;

public class InputReader {

    private final boolean useConsole;
    private final Scanner scanner;

    public InputReader() {
        Console console = System.console();
        //console == null when not available
        useConsole = (console !=null);
        scanner = (useConsole)?null:new Scanner(System.in);

    }

    public String readLine(String prompt) {
        System.out.print(prompt);
        return (useConsole) ? System.console().readLine() : scanner.nextLine();
    }

    public String readPassword(String prompt) {
        if (useConsole) {
            System.out.print(prompt);
            return new String(System.console().readPassword());
        } else {
            // If using Scanner, treat readPassword as readLine for simplicity
            System.out.print(prompt);
            return scanner.nextLine();
        }

    }
}

