# Arithmetic-Calculator

- This program implements an Arithmetic Calculator in Java that performs complex arithmetic operations like
addition (+), subtraction (-), multiplication (*), division (/) and exponentiation (^), using as basic structure 
a binary tree.
- Before performing the calculations, a number of correctness checks are done. 
    - The first check is related to the parenthesis, like if all the parenthesis are matched and if the matched
    parenthesis are in the correct order (an opening parenthesis shoulf come before the closing parenthesis).
    - The second check is related to the valid operators. The valid operators are : +,-,/,*,x and ^. Also, another check is 
    done to ensure that before or after an operator there is a number and not another operator or a parenthesis.
    For example the inputs 4++5 and (+5) are invalid.
- Functionality of the program:
    - Initially the program prints "Expression: " and expects an expression from the user. After the referred above checks 
    are done, the expansion of the expression is performed. The expansion is done only if the special operator '\?X' (where 
    ? is a valid operator and X is an integer number greater than 1) is present in the expression. An example of the expansion 
    is given below: 
         5 + ((((3.3 + 2.8) * 19.2) - 10.54) \*2 / 2.323) = 5 + ((((3.3 + 2.8) * 19.2 ) - 10.54) * (((3.3 + 2.8) * 19.2) - 10.54) / 2.323) \^2 + 8
    - After the expansion, the binary tree is constructed using the expanded expression.
    - Lastly, the program based on the user's selection, performs the desired action. Below are the possible user selections:
        - (-d) : The program print in the stdout the equivalent expression in the appropriate format for the user to be 
        able to set it as input in the graphviz.
        - (-s) : The program prints in the stdout the Postfix version of the expression. 
        - (-c) : The program prints in the stdout the result of the expression after performing the calculations. 
