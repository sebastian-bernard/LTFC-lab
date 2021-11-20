import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Transition {
    public Character from;
    public Character to;
    public Character label;

    public Transition() {
    }

    public Transition(Character from, Character to, Character label) {
        this.from = from;
        this.to = to;
        this.label = label;
    }
}

class FiniteAutomaton {
    public final List<Character> states = new ArrayList<>();
    public final List<Character> alphabet = new ArrayList<>();
    public Character initialState;
    public final List<Character> outputStates = new ArrayList<>();
    public final List<Transition> transitions = new ArrayList<>();

    FiniteAutomaton(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader(filename)));
        scanner.nextLine(); //skip over "states"
        int n = scanner.nextInt();
        for (int i = 0; i < n; ++i)
            states.add(scanner.next().charAt(0));
        scanner.nextLine(); //skip over "input_state"
        scanner.nextLine(); //skip over "input_state"
        initialState = scanner.next().charAt(0);
        scanner.nextLine(); //skip over "output_state"
        scanner.nextLine(); //skip over "output_state"
        n = scanner.nextInt();
        for (int i = 0; i < n; ++i)
            outputStates.add(scanner.next().charAt(0));
        scanner.nextLine(); //skip over "alphabet"
        scanner.nextLine(); //skip over "alphabet"
        n = scanner.nextInt();
        for (int i = 0; i < n; ++i)
            alphabet.add(scanner.next().charAt(0));
        scanner.nextLine(); //skip over "transitions"
        scanner.nextLine(); //skip over "transitions"
        n = scanner.nextInt();
        for (int i = 0; i < n; ++i) {
            char from = scanner.next().charAt(0);
            char to = scanner.next().charAt(0);
            char label = scanner.next().charAt(0);
            transitions.add(new Transition(from, to, label));
        }
    }

    public boolean checkIfAccepted(String word) {
        char currentState = initialState;
        for (char c : word.toCharArray()) {
            Transition t = new Transition();
            boolean foundAny = false;
            for (Transition transition : transitions)
                if (transition.from == currentState && transition.label == c) {
                    t = transition;
                    foundAny = true;
                }
            if (!foundAny)
                return false;
            currentState = t.to;
        }
        return outputStates.contains(currentState);
    }
}


public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        FiniteAutomaton fa = new FiniteAutomaton("finite_automaton.in");
        System.out.println("1. Print states");
        System.out.println("2. Print alphabet");
        System.out.println("3. Print output states");
        System.out.println("4. Print in state");
        System.out.println("5. Print transitions");
        System.out.println("6. Check word");
        System.out.println("0. Exit");
        Scanner scanner = new Scanner(System.in);
        boolean exited = false;
        while (!exited) {
            System.out.print("> ");
            String choice = scanner.next();
            switch (choice) {
                case "1":
                    for (Character c : fa.states)
                        System.out.print(c + " ");
                    System.out.println();
                    break;
                case "2":
                    for (Character c : fa.alphabet)
                        System.out.print(c + " ");
                    System.out.println();
                    break;
                case "3":
                    for (Character c : fa.outputStates)
                        System.out.print(c + " ");
                    System.out.println();
                    break;
                case "4":
                    System.out.println(fa.initialState);
                    break;
                case "5":
                    for (Transition t : fa.transitions)
                        System.out.println(t.from + " " + t.to + " " + t.label);
                    break;
                case "6":
                    String word = scanner.next();
                    System.out.println(fa.checkIfAccepted(word));
                    break;
                case "0":
                    exited = true;
                    break;
            }
        }
    }
}
