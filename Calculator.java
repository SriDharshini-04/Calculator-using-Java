import java.util.*;
public class Calculator {
    static double result; 
    static String expression;
    static double operand;

    public static String formatResult(double result) {
        if (result == Math.floor(result)) 
            return String.format("%.0f", result);   // No decimal places 
        else 
            return String.format("%.2f", result);   // Two decimal places
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\t\tBasic Calculator");
        System.out.println("\t\t----------------");
        System.out.println("Do you want to calculate an expression (E) or two numbers (N)?");
        char choice = sc.next().toUpperCase().charAt(0);

        if (choice == 'E')
            calculateExpression();
         else if (choice == 'N') 
            calculateTwoNumbers();
         else 
            System.out.println("Invalid choice.Please enter 'E' for expression or 'N' for two numbers.");
        sc.close();
    }
    static void calculateTwoNumbers() {
	Scanner sc = new Scanner(System.in);

    System.out.print("Enter 1st Number: ");
    result = sc.nextDouble();
    expression = String.valueOf(formatResult(result));

    while (true) {
        System.out.print("Enter an operator to calculate(+, -, *, /, %) or '=' to finish: ");
        char operator = sc.next().charAt(0);

        if (operator == '=') {
            System.out.println("Final Result: " + formatResult(result));
            break;
        }

        System.out.print("Enter the next number: ");
        operand = sc.nextDouble();
       
            
            switch (operator) {
                case '+':
                    performAddition(sc);
                    break;

                case '-':
                    performSubtraction(sc);
                    break;

                case '*':
                    performMultiplication(sc);
                    break;

                case '/':
                    performDivision(sc);
                    break;

                case '%':
                    performPercentage(sc);
                    break;
                    	
                case '^':
                	performPower(sc);
                	break;
                	
                case '=':
                    System.out.println("Final Result: " + formatResult(result));
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid Operator");
            }
        }
}
    static void performAddition(Scanner sc) {
        result += operand;
        expression += " + " + formatResult(operand);
        displayResult();
        expression = String.valueOf(formatResult(result));
    }

    static void performSubtraction(Scanner sc) {
        result -= operand;
        expression += " - " + formatResult(operand);
        displayResult();
        expression = String.valueOf(formatResult(result));
    }

    static void performMultiplication(Scanner sc) {
        result *= operand;
        expression += " * " + formatResult(operand);
        displayResult();
        expression = String.valueOf(formatResult(result));
    }

    static void performDivision(Scanner sc) {
        while (true) {

            if (operand == 0) {
                System.out.println("Division by zero is not allowed. Please enter a non-zero number.");
            } else {
                result /= operand;
                expression += " / " + formatResult(operand);
                break; 
            }
        }
        displayResult();
        expression = String.valueOf(formatResult(result));
    }

    static void performPercentage(Scanner sc) {
        result = (result * operand) / 100; 
        expression += " % " + formatResult(operand);
        displayResult();
        expression = String.valueOf(formatResult(result));
    }
    static void performPower(Scanner sc) {
    	result=Math.pow(result,operand);
    	expression += " ^ " + formatResult(operand);
    	displayResult();
    	 expression = String.valueOf(formatResult(result));
    }
    static void displayResult() {
        System.out.print(expression);
        System.out.println(" = " + formatResult(result));
    }
    static void calculateExpression() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a mathematical expression (eg.6+6*5)\n(Use any of these operators(+ - * / %)in expression");
        String expression = sc.nextLine();
        double result = evaluate(expression);
        System.out.println("The result of the expression is: " + formatResult(result));
        sc.close();
    }
    static double evaluate(String expression) {
        Stack<Double> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            if (Character.isWhitespace(ch)) 
                continue;
            if (Character.isDigit(ch) || ch == '.') {
                StringBuilder number = new StringBuilder();
                while(i<expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    number.append(expression.charAt(i));
                    i++;
                }
                values.push(Double.parseDouble(number.toString()));
                i--; 
            }
            else if (ch == '(') 
                operators.push(ch);
            else if (ch == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    double b = values.pop();
                    double a = values.pop();
                    char op = operators.pop();
                    values.push(applyOperation(a, b, op));
                }
                operators.pop(); 
            }
           
            else if (ch == '+' || ch == '-' || ch == '*' || ch == '/'|| ch == '%'||ch=='^') {
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(ch)) {
                    Double b = values.pop();
                    Double a = values.pop();
                    char op = operators.pop();
                    values.push(applyOperation(a, b, op));
                }
                operators.push(ch);
            }
        }

        
        while (!operators.isEmpty()) {
            double b = values.pop();
            double a = values.pop();
            char op = operators.pop();
            values.push(applyOperation(a, b, op));
        }

        
        return values.pop();
    }

    static double applyOperation(double a, double b, char operator) {
        switch (operator) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': 
                if (b == 0) throw new ArithmeticException("Cannot divide by zero");
                return a / b;
            case '%':
            	return (a*b)/100; 
            case '^':
            		return Math.pow(a,b);
        }
        return 0;
    }

    static int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-': 
                return 1;
            case '*':
            case '/':
            case '%': 
            case '^':
                return 2;
        }
        return -1;
    }

}
