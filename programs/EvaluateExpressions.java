package com.demo.stream;

import java.util.Scanner;
import java.util.Stack;

public class EvaluateExpressions {

    /**
     * This method is used to validate the given expression before evaluation
     * @param expression
     * @return
     */
    private boolean validateExpression(String expression){
        int countOpenBraces = 0;
        int countCloseBraces = 0;
        int countOpenCurly = 0;
        int countCloseCurly = 0;
        for(Character character : expression.toCharArray()) {
            switch (character) {
                case '(':
                    countOpenBraces++;
                    break;
                case ')':
                    countCloseBraces++;
                    break;
                case '{':
                    countOpenCurly++;
                    break;
                case '}':
                    countCloseCurly++;
                    break;
            }
        }
        return (countOpenBraces == countCloseBraces) && (countOpenCurly == countCloseCurly);
    }

    /**
     * This Method is used to get priority of the operator according to BODMAS Rule
     * @param operator
     * @return
     */
    private int getPriority(char operator){
        if(operator == '*' || operator == '/'){
            return 2;
        } else if(operator == '+' || operator == '-'){
            return 1;
        }else{
            return 0;
        }
    }

    /**
     * This Method is used to validate the given input is an operator
     * @param operator
     * @return
     */
    private boolean isOperator(char operator){
        return(operator == '*' || operator == '/' || operator == '+' || operator == '-');
    }

    /**
     * This Method is used to validate the given input is an operend / number
     * @param operend
     * @return
     */
    private boolean isOperend(char operend){
        return Character.isDigit(operend);
    }

    /**
     * This method is used to perform arithmetic operations of given operator
     * @param left
     * @param right
     * @param operator
     * @return
     * @throws Exception
     */
    private int performOperetion(int left, int right, char operator) throws Exception{
        int result = 0;

        switch (operator){
            case '*' : result = left * right; break;
            case '/':
                if(right == 0){
                    throw new Exception("divide by 0");
                }
                result = left / right; break;
            case '+' : result = left + right; break;
            case '-' : result = left - right; break;
        }

        return result;

    }

    /**
     * This method is used to evaluate the given expression
     * @param expression
     * @return
     * @throws Exception
     */
    public int evaluate(String expression) throws Exception{
        int result = 0;

        int left,right;

        Stack<Integer> operends = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for(int i=0; i<expression.length(); i++){
            char ch = expression.charAt(i);

            if(isOperend(ch)){
                System.out.println(ch);
                operends.push(Integer.parseInt(String.valueOf(ch)));
            } else if(ch == '('){
                System.out.println(ch);
                operators.push(ch);
            } else if(ch == ')'){
                System.out.println(ch);
                while(operators.peek() != '('){
                    if(operends.size()<2){
                        throw new Exception("Invalid expression");
                    }
                    right = operends.pop();
                    left = operends.pop();
                    result = performOperetion(left,right,operators.pop());
                    System.out.println("result after operation = " + result);
                    operends.push(result);
                }
                operators.pop(); // remove '('
            } else if( isOperator(ch)){
                System.out.println(ch);
                while(!operators.isEmpty() && getPriority(operators.peek()) >= getPriority(ch)){
                    if(operends.size()<2){
                        throw new Exception("Invalid expression");
                    }
                    right = operends.pop();
                    left = operends.pop();
                    result = performOperetion(left,right,operators.pop());
                    System.out.println("result after operation = " + result);
                    operends.push(result);
                }

                operators.push(ch);
            }
        }

        while(!operators.isEmpty()){
            System.out.println("inside last loop");
            if(operends.size()<2){
                throw new Exception("Invalid expression");
            }
            right = operends.pop();
            left = operends.pop();
            result = performOperetion(left,right,operators.pop());
            System.out.println("result after operation = " + result);
            operends.push(result);
        }
        System.out.println(operends);
        if(operends.size() == 1){
            result = operends.pop();
        }

        return result;
    }

    public static void main(String args[]) throws Exception {
        EvaluateExpressions evaluateExpressions = new EvaluateExpressions();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the expression to validate ===================> ");
        String expression = scanner.next();
        if (evaluateExpressions.validateExpression(expression)) {
            int result = evaluateExpressions.evaluate(expression);
            System.out.println("result is "+result);
        } else {
            System.out.println("Invalid String");
        }
    }

}
