package com.nicolas.qa;

import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {
        Scanner sn = new Scanner(System.in);
        Calculator calc = new Calculator();

        System.out.print("Enter first number: ");
        double a = sn.nextDouble();
        System.out.print("Enter second number: ");
        double b = sn.nextDouble();

        System.out.println("Addition: " + calc.add(a, b));
        System.out.println("Subtraction: " + calc.subtract(a, b));
        System.out.println("Multiplication: " + calc.multiply(a, b));
        try {
            System.out.println("Division: " + calc.divide(a, b));
        } catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
