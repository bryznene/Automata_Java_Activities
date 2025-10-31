import java.util.Scanner;

public class DFAStringChecker {
    
    private enum State {
        Q0, Q1, Q2
    }
    
    public static boolean acceptString(String input) {
        State currentState = State.Q0;
        
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            
            if (ch != '0' && ch != '1') {
                throw new IllegalArgumentException("Invalid input: String must contain only 0s and 1s");
            }
            
            switch (currentState) {
                case Q0:
                    if (ch == '0') {
                        currentState = State.Q1;
                    }
                    break;
                    
                case Q1:
                    if (ch == '1') {
                        currentState = State.Q2;
                    }
                    break;
                    
                case Q2:
                    if (ch == '0') {
                        currentState = State.Q1;
                    } else {
                        currentState = State.Q0;
                    }
                    break;
            }
        }
        
        return currentState == State.Q2;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("DFA String Acceptance Checker");
        System.out.println("This DFA accepts binary strings ending with '01'");
        System.out.println("=============================================");
        
        while (true) {
            System.out.print("\nEnter a binary string (or 'exit' to quit): ");
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                break;
            }
            
            if (input.isEmpty()) {
                System.out.println("Output: Rejected (empty string)");
                continue;
            }
            
            try {
                boolean accepted = acceptString(input);
                System.out.println("Output: " + (accepted ? "Accepted" : "Rejected"));
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        
        scanner.close();
    }
}