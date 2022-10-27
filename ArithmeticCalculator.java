package hw1;

import java.util.regex.*;
import java.lang.Math;

public class ArithmeticCalculator {
    String expression;

    // Used in findLastOperator()
    int lowestPriority;
    int position; // Position of last operator

    Tree treeNodes = new Tree();

    // Constructor of the Arithmetic Calculator 
    public ArithmeticCalculator(String initExpression) {
        expression = initExpression;

        // Remove all the white spaces, the '\n' and the tabs from the given expression 
        expression = expression.replaceAll(" ", "");
        expression = expression.replaceAll("\n", "");
        expression = expression.replaceAll("\t", "");

        int i, j;

        // Variables used in expansion
        String numToCheckS; // Initially store the "number" in string to check if double
        int numToCheck; // If it isn't double, store it in int
        int posToCopy;

        // Check 1. (expansion)
        // System.out.println("-> 1. Checking for expansion.");
        // -- check if there is the character \
        Pattern p = Pattern.compile("\\\\");
        Matcher m = p.matcher(expression);
        while (m.find()) {
            // m.end() is the pos of operator of expansion
            int posOfOperator = m.end();

            // Check if the operator is valid
            if (checkIfOperator(expression, posOfOperator) == 0) {
                System.out.println("[ERROR] Invalid expansion expression");
                System.exit(1);
            }

            // Check if the number is valid
            // Find when the number "ends"
            j = posOfOperator+1;
            if (checkIfOperator(expression, j) == 1) {
                System.out.println("[ERROR] Invalid expansion expression");
                System.exit(1);
            }
            // j is the position of the next operator (find where the number "ends")
            while (checkIfOperator(expression, j) == 0 || expression.charAt(j) == ')') {
                if (j == (expression.length()-1)) {
                    break;
                }
                j = j + 1;
            }

            if (j == (expression.length()-1)) {
                numToCheckS = expression.substring(posOfOperator+1);
            }
            else {
                numToCheckS = expression.substring(posOfOperator + 1, j);
            }

            // Check if the number is double (if it contains .)
            if (numToCheckS.contains(".")) { // number is double
                System.out.println("[ERROR] Invalid expansion expression");
                System.exit(1);
            }
            numToCheck = Integer.parseInt(numToCheckS);
            if (numToCheck < 1) {
                System.out.println("[ERROR] Invalid expansion expression");
                System.exit(1);
            }

            // Perform the expansion
            // Initially split the strings
            String leftString;
            String rightString;
            leftString = expression.substring(0, posOfOperator-1);
            rightString = expression.substring(j);
            
            int open, close;
            
            open = 0;
            close = 0;
            posToCopy = 0;
            
            // Find the position to start coping
            for (i = leftString.length()-1; i > 0; i--) {
                if (leftString.charAt(i) == '(') {
                    open++;
                }
                if (leftString.charAt(i) == ')') {
                    close++;
                }
                if (open == close) {
                    posToCopy = i;
                    break;
                }
            }

            // Additional strings to enter a '(' before the expansion
            String subString1, subString2;
            subString1 = leftString.substring(0, posToCopy);
            subString2 = leftString.substring(posToCopy);
            subString1 = subString1 + "(";
            leftString = subString1+subString2;

            String stringToCopy;
            stringToCopy = leftString.substring(posToCopy+1);
            for (i = 0; i < numToCheck-1; i++) {
                leftString = leftString + expression.charAt(posOfOperator);
                leftString = leftString + stringToCopy;
            }
            expression = leftString + ")" + rightString;

            // Renew the expression for the pattern
            p = Pattern.compile("\\\\");
            m = p.matcher(expression);

        }
        // End of check 1.

        // Check 2. (parenthesis I)
        //System.out.println("-> 2. Checking if all parenthesis are closing.");
        int numOfOpenP;
        int numOfCloseP;
        numOfOpenP = 0;
        numOfCloseP = 0;
        // Check if all the opened parenthesis are closing
        for ( i = 0; i < expression.length(); i++) { // Search all the string
            if (expression.charAt(i) == '(') {
                numOfOpenP++;
            }
            if (expression.charAt(i) == ')') {
                numOfCloseP++;
            }
        }
        //System.out.println("closing = " + numOfCloseP + " and opening = " + numOfOpenP);
        if (numOfCloseP < numOfOpenP) {
            System.out.println("[ERROR] Not closing opened parenthesis");
            System.exit(1);
        }
        // End of check 2.

        // Check 3. (parenthesis II)
        //System.out.println("-> 3. Checking if there are more closing parenthesis.");
        // Check if there are more )
        if (numOfCloseP > numOfOpenP) {
            System.out.println("[ERROR] Closing unopened parenthesis");
            System.exit(1);
        }
        // End of check 3.

        // Check 4. (invalid character)
        //System.out.println("-> 4. Checking for invalid characters.");
        for (i = 0; i < expression.length(); i++) {
            switch(expression.charAt(i)) {
                case '+' : break;
                case '-' : break;
                case '/' : break;
                case '*' : break;
                case 'x' : break;
                case '^' : break;
                case ' ' : break;
                case '.' : break;
                case '0' : break;
                case '1' : break;
                case '2' : break;
                case '3' : break;
                case '4' : break;
                case '5' : break;
                case '6' : break;
                case '7' : break;
                case '8' : break;
                case '9' : break;
                case '(' : break;
                case ')' : break;
                default :
                    System.out.println("[ERROR] Invalid character");
                    System.exit(1);
            }
        }
        // End of check 4.

        // Check 5. (double operators)
        int newPos;
        newPos = 0;
        //System.out.println("-> 5. Checking for two consecutive operands.");
        for (i = 0; i < expression.length(); i++) {
            if (checkIfOperator(expression, i) == 1) {
                newPos = i+1; // Position after first operator
                if (checkIfOperator(expression, newPos) == 1) {
                    System.out.println("[ERROR] Two consecutive operands");
                    System.exit(1);
                }
            }
        }
        // End of check 5.

        // Check 6. (no parenthesis before an operator)
        //System.out.println("-> 6. Checking for error.");
        int newPos2;
        // After a '(' can't exist an operator
        for (i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '(') {
                newPos2 = i+1; // Position after '('
                if (checkIfOperator(expression, newPos2) == 1) {
                    System.out.println("[ERROR] Operand appears after opening parenthesis");
                    System.exit(1);
                }
            }

            // After an operator can't exist a ')'
            if (checkIfOperator(expression, i) == 1) {
                newPos = i+1; //position after an operator
                if (expression.charAt(newPos) == ')') {
                    System.out.println("[ERROR] Operand appears before closing parenthesis");
                    System.exit(1);
                }
            }
        }
        // End of check 6.

        // Create the tree
        treeNodes.root = findLastOperator(expression);

    }

    // priorityOfOrder : Returns the priority of the char check
    // ^ = (3), / * = (2) , + - = (1), default (0)
    public int priorityOfOperator(char check) {
        switch (check) {
            case '^' : return(3);
            case '/' : return(2);
            case '*' : return(2);
            case 'x' : return(2);
            case '+' : return(1) ;
            case '-' : return(1);
            default : break;
        }
        return(0);
    }

    // findLastOperator : Find the last operator of the expression and inserts it in an array of String
    // and creates the tree
    public Node findLastOperator(String mExpression) {

        // Check if mExpression is a number
        if (((mExpression.contains("^") != true) && (mExpression.contains("/") != true) && (mExpression.contains("*") != true) && (mExpression.contains("+") != true) && (mExpression.contains("-") != true)) || (mExpression.equals(""))){
            Node newnode = new Node(mExpression);
            return(newnode);
        }

        lowestPriority = 5; // Init value
        position = 0; // Position of last operator
        String left, right;
        int i, numberOfOpen, numberOfClose, flag;
        numberOfClose = 0;
        numberOfOpen = 0;
        flag = 0; // flag = 0 : if checking outside of parenthesis , = 1 : if checking inside

        for (i = 0; i < mExpression.length(); i++) {
            if (mExpression.charAt(i) == '(') {
                numberOfOpen++;
                flag = 1;
            }
            if (mExpression.charAt(i) == ')') {
                numberOfClose++;
            }
            if ((numberOfClose == numberOfOpen)) { //skip
                flag = 0;
                numberOfClose = 0;
                numberOfOpen = 0;
            }
            if ((checkIfOperator(mExpression, i) == 1) && (flag == 0)) { // If in this position there is an operator and it is outside the parenthesis
                if (priorityOfOperator(mExpression.charAt(i)) <= lowestPriority) { // = to calculate first the right operation
                    lowestPriority = priorityOfOperator(mExpression.charAt(i));
                    position = i; // Position of last operator

                }
            }
        }

        // If the expression is (3+5) => remove the parenthesis and calculate again
        if ((lowestPriority == 5) && (mExpression.charAt(0) == '(') && (mExpression.charAt(mExpression.length()-1) == ')')) {
            mExpression = mExpression.substring(1, mExpression.length()-1);
            Node new1 = findLastOperator(mExpression);
            return(new1);
        }

        Node newNode = new Node(String.valueOf(mExpression.charAt(position)));

        left = mExpression.substring(0, position);
        right = mExpression.substring(position+1);
        newNode.right = findLastOperator(right);
        newNode.left = findLastOperator(left);
        return(newNode);
    }

    // checkIfOperator : Checks if the position in the mathExpression is a valid operator
    // if it is : returns 1, else : returns 0
    public int checkIfOperator(String mathExpression, int position) {
        switch(mathExpression.charAt(position)) {
            case '+' : return(1);
            case '-' : return(1);
            case '/' : return(1);
            case '*' : return(1);
            case 'x' : return(1);
            case '^' : return(1);
            default : return(0);
        }
    }

    // Option -d
    public String toDotString() {
        treeNodes.dotString(); // function in tree class
        String toDot;
        toDot = treeNodes.dotString();
        return(toDot);
    }
    // End of option -d.

    // Option -s
    String order = ""; // String that toString returns (postOrder expression)
    @Override
    public String toString() {

        String postFix = "";
        if (treeNodes.root == null) {
            return(null);
        }
        postFix = postOrder(treeNodes.root);
        return(postFix);
    }

    // postOrder : Traverse the tree with postOrder and copy the nodes in a string (order)
    // Returns : String order
    public String postOrder (Node current) {

        if (current == null) {
            return("");
        }

        // If it is an operator open a parenthesis (!root)
        if (isOperator(current.value) == 1 && current!= treeNodes.root) {
            order = order + "(";
        }

        postOrder(current.left);
        postOrder(current.right);

        // If it is a number put parenthesis around
        if (isOperator(current.value) == 0) {
            order = order + "(" + current.value + ")";
        }

        // If it is an operator close parenthesis (!root)
        if (isOperator(current.value) == 1  && current!= treeNodes.root) {
            order = order + current.value + ")";
        }

        // If root put operator
        if (current == treeNodes.root) {
            order = order + current.value;
        }

        return(order);
    }
    // End of option -s.

    // Option -c
    // isOperator : checks if String check is an operator
    // returns : 1 if it is, 0 if it is a number
    public int isOperator (String check) {
        switch(check) {
            case "+" : return(1);
            case "-" : return(1);
            case "/" : return(1);
            case "*" : return(1);
            case "x" : return(1);
            case "^" : return(1);
            default : return(0);
        }
    }

    // doEvaluation : calculates the operation between l and r depending on which operator node is
    // returns : the result (double)
    public double doEvaluation(Node node, double l, double r) {
        switch(node.value) {
            case "+" : return(l + r);
            case "-" : return(l - r);
            case "/" : return(l / r);
            case "*" : return(l * r);
            case "x" : return(l * r);
            case "^" : return(Math.pow(l,r));
            default : return(0);
        }
    }

    // calc : recursive function that calculates the result of the left and right subtree with the function doEvaluation
    // returns : the final result
    public double calc(Node current) {

        if (current == null) {
            return(0);
        }

        // If current is a number return its double value
        if (isOperator(current.value) == 0) {
            return(Double.parseDouble(current.value));
        }

        double rightRes;
        double leftRes;

        // Calculate the left subtree
        leftRes = calc(current.left);

        // Calculate the right subtree
        rightRes = calc(current.right);

        return(doEvaluation(current,leftRes, rightRes));
    }

    public double calculate () {
        Node curr = treeNodes.root;
        double result;
        if (curr == null) {
            return(0);
        }
        result = calc(curr);
        return(result);
    }
    // End of option -c.

    public static void main(String[] args) {
        java.util.Scanner sc = new java.util.Scanner(System.in);
        System.out.print("Expression: ");
        String line = sc.nextLine();
        System.out.println();

        // Create expression
        hw1.ArithmeticCalculator mathExpression = new ArithmeticCalculator(line);
        sc.hasNextLine();
        String command = sc.nextLine();
        command = command.replaceAll(" ", "");
        command = command.replaceAll("\n", "");
        command = command.replaceAll("\t", "");
        for (int i = 0; i < command.length(); i++) {
                if (command.charAt(i) == 'd') {
                    String toDotString;
                    toDotString = mathExpression.toDotString();
                    System.out.println("\n" + toDotString);
                }
                if (command.charAt(i) == 's') {
                    String post = mathExpression.toString();
                    System.out.println("\nPostfix: " + post);
                }
                if (command.charAt(i) == 'c') {
                    double result = mathExpression.calculate();
                    System.out.println("\nResult: " + result);
                }
        }

    }

}
