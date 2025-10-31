import java.util.*;

public class NFA_Simulator {
    
    private Map<Integer, Map<Character, Set<Integer>>> transitions;
    private int startState;
    private Set<Integer> acceptStates;
    
    public NFA_Simulator() {
        transitions = new HashMap<>();
        startState = 0;
        acceptStates = new HashSet<>();
        acceptStates.add(2);
        buildNFA();
    }
    
    private void buildNFA() {
        Map<Character, Set<Integer>> state0 = new HashMap<>();
        state0.put('a', new HashSet<>(Arrays.asList(0, 1)));
        state0.put('b', new HashSet<>(Arrays.asList(0)));
        transitions.put(0, state0);
        
        Map<Character, Set<Integer>> state1 = new HashMap<>();
        state1.put('a', new HashSet<>(Arrays.asList(0, 1)));
        state1.put('b', new HashSet<>(Arrays.asList(0, 2)));
        transitions.put(1, state1);
        
        Map<Character, Set<Integer>> state2 = new HashMap<>();
        state2.put('a', new HashSet<>(Arrays.asList(0, 1, 2)));
        state2.put('b', new HashSet<>(Arrays.asList(0, 2)));
        transitions.put(2, state2);
    }
    
    public boolean simulate(String input) {
        if (input == null || input.isEmpty()) {
            System.out.println("Empty string - Rejected");
            return false;
        }
        
        Set<Integer> currentStates = new HashSet<>();
        currentStates.add(startState);
        System.out.println("Start: " + currentStates);
        
        for (int i = 0; i < input.length(); i++) {
            char symbol = input.charAt(i);
            Set<Integer> nextStates = new HashSet<>();
            
            for (int state : currentStates) {
                if (transitions.containsKey(state)) {
                    Map<Character, Set<Integer>> stateTransitions = transitions.get(state);
                    if (stateTransitions.containsKey(symbol)) {
                        nextStates.addAll(stateTransitions.get(symbol));
                    }
                }
            }
            
            if (nextStates.isEmpty()) {
                System.out.println("Step " + (i + 1) + ": Read '" + symbol + 
                                 "' - No valid transitions - Rejected");
                return false;
            }
            
            currentStates = nextStates;
            System.out.println("Step " + (i + 1) + ": Read '" + symbol + 
                             "' → States " + currentStates);
        }
        
        boolean accepted = false;
        for (int state : currentStates) {
            if (acceptStates.contains(state)) {
                accepted = true;
                break;
            }
        }
        
        System.out.println("Final states: " + currentStates + 
                         " - " + (accepted ? "Accepted ✓" : "Rejected ✗"));
        return accepted;
    }
    
    public void printNFADefinition() {
        System.out.println("=== NFA Definition ===");
        System.out.println("Accepts strings containing 'ab' as a substring\n");
        System.out.println("States: {0, 1, 2}");
        System.out.println("Start State: " + startState);
        System.out.println("Accept States: " + acceptStates);
        System.out.println("\nTransition Table:");
        System.out.println("State 0: a → {0, 1}, b → {0}");
        System.out.println("State 1: a → {0, 1}, b → {0, 2}");
        System.out.println("State 2: a → {0, 1, 2}, b → {0, 2}");
        System.out.println("======================\n");
    }
    
    public static void main(String[] args) {
        NFA_Simulator nfa = new NFA_Simulator();
        nfa.printNFADefinition();
        
        String[] testCases = {"aab", "bbaa", "ababab", "bbb", "ab", "a", "b", ""};
        
        System.out.println("=== Running Test Cases ===\n");
        for (String testCase : testCases) {
            System.out.println("Input: \"" + testCase + "\"");
            boolean result = nfa.simulate(testCase);
            System.out.println("Output: " + (result ? "Accepted" : "Rejected"));
            System.out.println("-----------------------------------\n");
        }
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Interactive Mode ===");
        System.out.println("Enter strings to test (alphabet: a, b)");
        System.out.println("Type 'exit' to quit\n");
        
        while (true) {
            System.out.print("Input: ");
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                break;
            }
            
            if (!input.matches("[ab]*")) {
                System.out.println("Invalid input! Only 'a' and 'b' are allowed.\n");
                continue;
            }
            
            boolean result = nfa.simulate(input);
            System.out.println("Output: " + (result ? "Accepted" : "Rejected"));
            System.out.println();
        }
        
        scanner.close();
    }
}