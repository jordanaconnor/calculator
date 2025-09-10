package com.github.jordanaconnor.calculator.UI;

import java.util.*;

public class ShuntingYardAlgorithm {

    private static final Map<String, Integer> order = new HashMap<>();
    private static final Map<String, Boolean> rightAssoc = new HashMap<>();

    static {
        order.put("+", 1);
        order.put("-", 1);
        order.put("*", 2);
        order.put("/", 2);
        order.put("mod", 2);
        order.put("%", 2);
        order.put("^", 3);
        order.put("√", 4);
        order.put("x²", 4);

        rightAssoc.put("+", false);
        rightAssoc.put("-", false);
        rightAssoc.put("*", false);
        rightAssoc.put("/", false);
        rightAssoc.put("mod", false);
        rightAssoc.put("%", false);
        rightAssoc.put("^", true);
        rightAssoc.put("√", true);
        rightAssoc.put("x²", true);
    }

    //infix to postfix
    public static List<String> toPostfix(String expression) {
        List<String> output = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();

        StringTokenizer tokenizer = new StringTokenizer(expression, "+-*/^() %", true);

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();
            if (token.isEmpty()) continue;

            if (token.matches("\\d+(\\.\\d+)?")) {
                output.add(token);
            } else if (order.containsKey(token)) {
                while (!stack.isEmpty() && order.containsKey(stack.peek())) {
                    if ((!rightAssoc.get(token) && order.get(token) <= order.get(stack.peek())) ||
                            (rightAssoc.get(token) && order.get(token) < order.get(stack.peek()))) {
                        output.add(stack.pop());
                        continue;
                    }
                    break;
                }
                stack.push(token);
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    output.add(stack.pop());
                }
                if (!stack.isEmpty() && stack.peek().equals("(")) stack.pop();
            }
        }

        while (!stack.isEmpty()) {
            output.add(stack.pop());
        }

        return output;
    }

    //postfix
    public static double evalPostfix(List<String> postfix) {
        Deque<Double> stack = new ArrayDeque<>();

        for (String token : postfix) {
            if (token.matches("\\d+(\\.\\d+)?")) {
                stack.push(Double.valueOf(token));
            } else {
                switch (token) {
                    case "+": stack.push(stack.pop() + stack.pop()); break;
                    case "-": {
                        double b = stack.pop(), a = stack.pop();
                        stack.push(a - b); break;
                    }
                    case "*": stack.push(stack.pop() * stack.pop()); break;
                    case "/": {
                        double b = stack.pop(), a = stack.pop();
                        stack.push(a / b); break;
                    }
                    case "mod": {
                        double b = stack.pop(), a = stack.pop();
                        stack.push(a % b); break;
                    }
                    case "%": stack.push(stack.pop() / 100.0); break; //use % as percentage
                    case "^": {
                        double b = stack.pop(), a = stack.pop();
                        stack.push(Math.pow(a, b)); break;
                    }
                    case "√": stack.push(Math.sqrt(stack.pop())); break;
                    case "x²": {
                        double a = stack.pop();
                        stack.push(Math.pow(a, 2)); break;
                    }
                }
            }
        }
        return stack.pop();
    }

    public static void main(String[] args) {
        String expr = "5 mod 2 + √ 9 + 3 x² + 4 n";
        List<String> postfix = toPostfix(expr);
        System.out.println("Postfix: " + postfix);
        System.out.println("Result: " + evalPostfix(postfix));
    }
}
